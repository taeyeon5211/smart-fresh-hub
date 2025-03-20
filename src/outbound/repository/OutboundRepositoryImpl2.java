package outbound.repository;

import object.ObjectIo;
import outbound.dto.OutboundDTO;
import outbound.vo.OutboundVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 출고(Outbound) 관련 데이터 처리를 담당하는 Repository 구현체.
 * 출고 요청 생성, 조회, 상태 업데이트, 재고 반영 기능을 포함한다.
 */
public class OutboundRepositoryImpl2 implements OutboundRepository {

    // DTO ↔ VO 변환을 위한 Function 객체
    private static final Function<OutboundDTO, OutboundVO> changeToVO = OutboundVO::new;
    private static final Function<OutboundVO, OutboundDTO> changeToDTO = OutboundDTO::new;

    /**
     * 신규 출고 요청을 생성하는 메서드.
     * 출고 요청 시, 제품 ID와 출고 수량을 받아 `outbound_table`에 삽입한다.
     *
     * @param outboundDTO 출고 요청 데이터
     */
    @Override
    public void createOutboundRequest(OutboundDTO outboundDTO) {
        String sql = "INSERT INTO outbound_table (outbound_amount, product_id) VALUES (?, ?)";
        OutboundVO outboundVO = changeToVO.apply(outboundDTO); // DTO → VO 변환

        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outboundVO.getOutboundAmount()); // 출고 수량
            pstmt.setInt(2, outboundVO.getProductId()); // 제품 ID
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("출고 요청 생성 중 오류 발생", e);
        }
    }

    /**
     * 특정 사업체의 출고 요청 목록을 조회하는 메서드.
     * `product` 테이블과 조인하여 특정 사업체가 요청한 출고 목록을 가져온다.
     *
     * @param businessId 사업체 ID
     * @return 해당 사업체의 출고 요청 목록 (Optional<List<OutboundDTO>>)
     */
    @Override
    public Optional<List<OutboundDTO>> readOutboundStatus(int businessId) {
        String sql = "SELECT * FROM outbound_table " +
                "WHERE product_id IN (SELECT product_id FROM product WHERE business_id = ?)";
        List<OutboundDTO> result = new ArrayList<>();

        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, businessId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OutboundVO outboundVO = OutboundVO.builder()
                            .outboundId(rs.getInt("outbound_id")) // 출고 ID
                            .outboundDate(rs.getTimestamp("outbound_date") != null
                                    ? rs.getTimestamp("outbound_date").toLocalDateTime() : null) // 출고일 (NULL 가능)
                            .outboundRequestDate(rs.getTimestamp("outbound_request_date").toLocalDateTime()) // 요청일
                            .outboundStatus(rs.getString("outbound_status")) // 상태 (대기, 승인, 취소 등)
                            .adminId(rs.getInt("admin_id")) // 승인한 관리자 ID
                            .outboundAmount(rs.getInt("outbound_amount")) // 출고 수량
                            .productId(rs.getInt("product_id")) // 제품 ID
                            .build();
                    result.add(changeToDTO.apply(outboundVO)); // VO → DTO 변환 후 리스트에 추가
                }
                return Optional.of(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("출고 요청 상태 조회 중 오류 발생", e);
        }
    }

    /**
     * 현재 `대기` 상태인 출고 요청 목록을 조회하는 메서드.
     * 관리자가 승인/취소를 결정해야 하는 요청들을 가져온다.
     *
     * @return '대기' 상태 출고 요청 목록 (Optional<List<OutboundDTO>>)
     */
    @Override
    public Optional<List<OutboundDTO>> readOutboundRequest() {
        String sql = "SELECT * FROM outbound_table WHERE outbound_status = '대기'";
        List<OutboundDTO> result = new ArrayList<>();

        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OutboundVO outboundVO = OutboundVO.builder()
                            .outboundId(rs.getInt("outbound_id"))
                            .outboundDate(rs.getTimestamp("outbound_date") != null
                                    ? rs.getTimestamp("outbound_date").toLocalDateTime() : null)
                            .outboundRequestDate(rs.getTimestamp("outbound_request_date").toLocalDateTime())
                            .outboundStatus(rs.getString("outbound_status"))
                            .adminId(rs.getInt("admin_id"))
                            .outboundAmount(rs.getInt("outbound_amount"))
                            .productId(rs.getInt("product_id"))
                            .build();
                    result.add(changeToDTO.apply(outboundVO));
                }
                return Optional.of(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("대기 상태 출고 요청 조회 중 오류 발생", e);
        }
    }

    /**
     * 출고 요청의 상태를 변경하는 메서드.
     * 관리자가 출고 요청을 승인 또는 취소할 때 사용된다.
     * 승인 시 `outbound_date`가 현재 날짜로 업데이트된다.
     *
     * @param newStatus 변경할 상태 (승인 / 취소)
     * @param adminId 처리한 관리자 ID
     * @param outboundDate 출고 날짜 (승인 시 현재 시간 입력)
     * @param outboundId 출고 요청 ID
     */
    @Override
    public void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId) {
        String sql = "UPDATE outbound_table SET outbound_status = ?, admin_id = ?, outbound_date = ? WHERE outbound_id = ?";

        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, adminId);
            pstmt.setObject(3, outboundDate);
            pstmt.setInt(4, outboundId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("출고 요청 상태 업데이트 중 오류 발생", e);
        }
    }


    /**
     * 승인된 출고 요청을 반영하여 재고를 감소시키는 메서드.
     * @param outboundId 출고 요청 ID
     */
    @Override
    public void updateRevenue(int outboundId) {
        try (Connection conn = ObjectIo.getConnection()) {
            int productId;
            int outboundAmount;
            int currentStock = 0;

            // 1. 출고 요청에서 product_id 및 출고량 가져오기
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT product_id, outbound_amount FROM outbound_table WHERE outbound_id = ?")) {
                pstmt.setInt(1, outboundId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        productId = rs.getInt("product_id");
                        outboundAmount = rs.getInt("outbound_amount");
                    } else {
                        throw new RuntimeException("출고 요청을 찾을 수 없습니다.");
                    }
                }
            }

            // 2. 현재 재고 확인
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT SUM(revenue_amount) FROM revenue_table WHERE product_id = ?")) {
                pstmt.setInt(1, productId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        currentStock = rs.getInt(1); // 현재 재고량
                    }
                }
            }

            // 3. 재고가 부족하면 출고 불가능하도록 예외 발생
            if (outboundAmount > currentStock) {
                throw new RuntimeException("출고 승인 불가: 재고 부족 (현재 재고: " + currentStock + ", 요청 출고량: " + outboundAmount + ")");
            }

            // 4. 재고 감소 처리
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE revenue_table SET revenue_amount = revenue_amount - ? WHERE product_id = ? AND revenue_amount >= ? LIMIT 1")) {
                pstmt.setInt(1, outboundAmount);
                pstmt.setInt(2, productId);
                pstmt.setInt(3, outboundAmount);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("재고 감소 완료: " + affectedRows + "건 업데이트됨");
                } else {
                    throw new RuntimeException("출고 승인 불가: 해당 제품의 재고가 부족하거나 데이터 오류 발생");
                }
            }

            // 5. 재고가 0이면 삭제
            deleteZeroRevenue();
        } catch (SQLException e) {
            throw new RuntimeException("출고 승인 후 재고 감소 처리 중 오류 발생", e);
        }
    }



    //출고요청 전체조회
    @Override
    public Optional<List<OutboundDTO>> readAllOutboundRequest() {
        String sql = "select * from outbound_table";
        List<OutboundDTO> result = new ArrayList<>();
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OutboundVO outboundVO = OutboundVO.builder()
                            .outboundId(rs.getInt("outbound_id"))
                            .outboundDate(rs.getTimestamp("outbound_date") != null ? rs.getTimestamp("outbound_date").toLocalDateTime() : null)
                            .outboundRequestDate(rs.getTimestamp("outbound_request_date").toLocalDateTime())
                            .outboundStatus(rs.getString("outbound_status"))
                            .adminId(rs.getInt("admin_id"))
                            .outboundAmount(rs.getInt("outbound_amount"))
                            .productId(rs.getInt("product_id"))
                            .build();
                    result.add(changeToDTO.apply(outboundVO));
                }
                rs.close();
                return Optional.of(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    //재고 테이블 수량 0이면 삭제하는 기능
    public void deleteZeroRevenue() {
        String sql = "delete from revenue_table where revenue_amount = 0";
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("출고 승인 후 재고 감소 처리 중 오류 발생", e);
        }
    }

    @Override
    public List<Integer> getRevenueAmount(int outboundId) {
        return List.of();
    }


    public static void main(String[] args) {
        OutboundRepositoryImpl2 outboundRepository = new OutboundRepositoryImpl2();

        outboundRepository.updateRevenue(1);
    }
}

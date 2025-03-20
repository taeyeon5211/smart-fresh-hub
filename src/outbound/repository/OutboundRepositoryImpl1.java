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

public class OutboundRepositoryImpl1 implements OutboundRepository{
    private static final Function<OutboundDTO, OutboundVO> changeToVO = OutboundVO::new;
    private static final Function<OutboundVO, OutboundDTO> changeToDTO = OutboundDTO::new;

    @Override
    public void createOutboundRequest(OutboundDTO outboundDTO) {
        String sql = "insert into outbound_table (outbound_amount, product_id) values (?, ?)";
        OutboundVO outboundVO = changeToVO.apply(outboundDTO);

        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, outboundVO.getOutboundAmount());
            pstmt.setInt(2, outboundVO.getProductId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //출고요청 -> (승인, 취소) -> 승인시 -> 재고적용 -> 재고히스토리 적용
    //                     -> 취소시 ->

    //재고 테이블에서 사업체아이디로 접근하여 제품아이디 선택

    //

    @Override
    public Optional<List<OutboundDTO>> readOutboundStatus(int businessId) {
        String sql = "select * from outbound_table" +
                " where product_id in (select product_id from product where business_id = ?)";
        List<OutboundDTO> result = new ArrayList<>();
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, businessId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OutboundVO outboundVO = OutboundVO.builder()
                            .outboundId(rs.getInt("outbound_id"))
                            .outboundDate(rs.getTimestamp("outbound_date") != null ? rs.getTimestamp("outbound_date").toLocalDateTime() : null)
                            .outboundRequestDate(rs.getTimestamp("outbound_request_date").toLocalDateTime())
                            .outboundStatus(rs.getString("outbound_status"))
                            .adminId(rs.getInt("admin_id"))
                            .outboundAmount(rs.getInt("outbound_amount"))
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

    @Override
    public Optional<List<OutboundDTO>> readOutboundRequest() {
        String sql = "select * from outbound_table where outbound_status = '대기'";
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

    //업데이트시 상태가 승인이면 재고테이블에 적용
    //toString 원하는 속성만
    @Override
    public void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId) {
        String sql = "update outbound_table set outbound_status = ?, admin_id = ?, outbound_date = ? where outbound_id = ?";
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, adminId);
            pstmt.setObject(3, outboundDate);
            pstmt.setInt(4, outboundId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //출고 상태 승인 -> 재고 히스토리 | 재고 0되면 삭제 -> 서비스단에서 가져와서
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getRevenueAmount(int outboundId) {
        return List.of();
    }

    public static void main(String[] args) {
        OutboundRepositoryImpl1 outboundRepository = new OutboundRepositoryImpl1();
        OutboundDTO outboundDTO0 = new OutboundDTO().builder().outboundAmount(100).productId(1).build();
        outboundRepository.createOutboundRequest(outboundDTO0);

        outboundRepository.updateOutboundStatus("승인", 1, LocalDateTime.now(), 2);
//
//        Optional<List<OutboundDTO>> outboundDTOS = outboundRepository.readOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }

//        Optional<List<OutboundDTO>> outboundDTOS = outboundRepository.readAllOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }

//        outboundRepository.deleteZeroRevenue();
//        outboundRepository.updateRevenue(1);
//
//        outboundRepository.updateOutboundStatus("승인", 1, LocalDateTime.now(), 11);
//
//        Optional<List<OutboundDTO>> outboundDTOS1 = outboundRepository.readOutboundStatus(2);
//        for (OutboundDTO outboundDTO : outboundDTOS1.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }
    }
}

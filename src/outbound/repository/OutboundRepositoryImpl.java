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

public class OutboundRepositoryImpl implements OutboundRepository{
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
    //재고 수량 보다 입력수량이 많을시 예외처리 - db값 가져와서
    @Override
    public void updateRevenue(int outboundId) {
        String sql = "UPDATE revenue_table r" +
                " JOIN product p ON r.product_id = p.product_id" +
                " JOIN outbound_table o ON o.product_id = p.product_id" +
                " SET r.revenue_amount = r.revenue_amount - o.outbound_amount" +
                " WHERE o.outbound_status = '승인'" +
                " AND o.outbound_id = ?" +
                " AND r.revenue_amount >= o.outbound_amount";
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outboundId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void updateRevenue(int outboundId) {
//        try (Connection conn = ObjectIo.getConnection()) {
//            int productId;
//            int outboundAmount;
//
//            // 1. 출고 요청에서 product_id 및 출고량 가져오기
//            try (PreparedStatement pstmt = conn.prepareStatement(
//                    "SELECT product_id, outbound_amount FROM outbound_table WHERE outbound_id = ?")) {
//                pstmt.setInt(1, outboundId);
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        productId = rs.getInt("product_id");
//                        outboundAmount = rs.getInt("outbound_amount");
//                    } else {
//                        throw new RuntimeException("출고 요청을 찾을 수 없습니다.");
//                    }
//                }
//            }
//
//            // 2. 재고 감소 처리
//            try (PreparedStatement pstmt = conn.prepareStatement(
//                    "UPDATE revenue_table SET revenue_amount = revenue_amount - ? WHERE product_id = ?")) {
//                pstmt.setInt(1, outboundAmount);
//                pstmt.setInt(2, productId);
//                pstmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("출고 승인 후 재고 감소 처리 중 오류 발생", e);
//        }
//    }


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

    //outbound_id로 재고 수량 받아오는 기능
    @Override
    public List<Integer> getRevenueAmount(int outboundId) {
        String sql = "SELECT distinct r.revenue_amount, o.outbound_amount " +
                "FROM revenue_table r " +
                "JOIN product p ON r.product_id = p.product_id " +
                "JOIN outbound_table o ON o.product_id = p.product_id " +
                "WHERE o.outbound_id = ?";
        List<Integer> result = new ArrayList<>();
        try (Connection conn = ObjectIo.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outboundId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result.add(rs.getInt("revenue_amount"));
                    result.add(rs.getInt("outbound_amount"));
                } else {
                    throw new RuntimeException("No revenue found for the given outbound_id: " + outboundId);
                }
                rs.close();
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        OutboundRepositoryImpl outboundRepository = new OutboundRepositoryImpl();
//        OutboundDTO outboundDTO0 = new OutboundDTO().builder().outboundAmount(100).productId(1).build();
//        outboundRepository.createOutboundRequest(outboundDTO0);
//
//        outboundRepository.updateOutboundStatus("승인", 1, LocalDateTime.now(), 2);
//
        Optional<List<OutboundDTO>> outboundDTOS = outboundRepository.readOutboundRequest();
        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
            System.out.println(outboundDTO.toString());
        }

//        Optional<List<OutboundDTO>> outboundDTOS = outboundRepository.readAllOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }

//        outboundRepository.deleteZeroRevenue();
//        List<Integer> list = outboundRepository.getRevenueAmount(1);
//        for (Integer i : list) {
//            System.out.println(i);
//        }
//        outboundRepository.updateRevenue(2);
//
//        outboundRepository.updateOutboundStatus("승인", 1, LocalDateTime.now(), 11);
//
//        Optional<List<OutboundDTO>> outboundDTOS1 = outboundRepository.readOutboundStatus(2);
//        for (OutboundDTO outboundDTO : outboundDTOS1.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }
    }
}

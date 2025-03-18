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

    public static void main(String[] args) {
        OutboundRepositoryImpl outboundRepository = new OutboundRepositoryImpl();
//        OutboundDTO outboundDTO0 = new OutboundDTO().builder().outboundAmount(100).productId(1).build();
//        outboundRepository.createOutboundRequest(outboundDTO0);
//
//        outboundRepository.updateOutboundStatus("승인", 1, LocalDateTime.now(), 2);
//
//        Optional<List<OutboundDTO>> outboundDTOS = outboundRepository.readOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }

        Optional<List<OutboundDTO>> outboundDTOS1 = outboundRepository.readOutboundStatus(1);
        for (OutboundDTO outboundDTO : outboundDTOS1.orElse(null)) {
            System.out.println(outboundDTO.toString());
        }
    }
}

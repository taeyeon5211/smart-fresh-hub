package inbound.repository;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;
import object.ObjectIo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class InboundRepositoryImp implements InboundRepository {

    Connection conn = ObjectIo.getConnection();

    @Override
    public int insertInboundRequest(InboundVo inboundRequest) {
        String sql = "INSERT INTO inbound_table (business_id, product_id, inbound_amount, inbound_status, inbound_request_date) " +
                "VALUES (?, ?, ?, '대기', NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, inboundRequest.getBusinessId());
            pstmt.setInt(2, inboundRequest.getProductId());
            pstmt.setInt(3, inboundRequest.getInboundAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 inbound_id 반환
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환

    }

    @Override
    public InboundVo findInboundRequestById(int inboundId) {
        return null;
    }

    @Override
    public List<InboundVo> findInboundRequestsByBusiness(int businessId) {
        return List.of();
    }

    @Override
    public boolean updateInboundStatus(int inboundId, String status) {
        return false;
    }

    @Override
    public boolean insertInboundHistory(int inboundId, int adminId) {
        return false;
    }

    @Override
    public List<InboundHistoryVo> findAllInboundHistory() {
        return List.of();
    }

    @Override
    public List<InboundHistoryVo> findInboundHistoryByProduct(int productId) {
        return List.of();
    }

    @Override
    public List<InboundHistoryVo> findInboundHistoryByBusiness(int businessId) {
        return List.of();
    }

    public static void main(String[] args) {
            InboundRepository inboundRepository = new InboundRepositoryImp();

            // 테스트용 입고 요청 객체 생성
            InboundVo inboundVo = InboundVo.builder()
                    .businessId(1)   // 화주(사업체) ID (테이블에 존재하는 값 사용해야 함)
                    .productId(2)    // 제품 ID (테이블에 존재하는 값 사용해야 함)
                    .inboundAmount(50) // 입고 수량
                    .build();

            // 입고 요청 등록 테스트
            int inboundId = inboundRepository.insertInboundRequest(inboundVo);
            System.out.println("테스트 입고 요청 ID: " + inboundId);

            // MySQL에서 데이터 확인
            if (inboundId > 0) {
                System.out.println("입고 요청이 성공적으로 등록되었습니다!");
            } else {
                System.out.println("입고 요청 등록에 실패했습니다.");
            }
        }
    }


package inbound.repository;

import inbound.dto.RevenueHistoryDto;
import inbound.exception.RevenueException;
import object.ObjectIo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevenueRepositoryImp implements RevenueRepository {

    private final Connection conn = ObjectIo.getConnection();


    @Override
    public boolean existsRevenue(int productId, int areaId) {
        String sql = "SELECT COUNT(*) FROM revenue_table WHERE product_id = ? AND area_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, areaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // 존재하면 true
            }
        } catch (SQLException e) {
            throw new RuntimeException("재고 존재 여부 확인 중 오류 발생", e);
        }

        return false;
    }

    @Override
    public int updateRevenue(int productId, int areaId, int additionalAmount) {
        String sql = "UPDATE revenue_table SET revenue_amount = revenue_amount + ? WHERE product_id = ? AND area_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, additionalAmount);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, areaId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("재고 업데이트 중 오류 발생", e);
        }
    }

    @Override
    public int insertRevenue(int productId, int areaId, int amount) {
        String sql = "INSERT INTO revenue_table (revenue_amount, product_id, area_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, areaId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("재고 삽입 중 오류 발생", e);
        }
    }

    /**
     * 관리자가 모든 재고 변경 이력을 조회하는 메서드.
     * `revenue_history_table`을 조회하고, 관련 제품 및 사업체 정보를 포함한다.
     */
    @Override
    public List<RevenueHistoryDto> findAllRevenueHistory() {
        // 저장 프로시저를 호출하는 SQL
        String sql = "{CALL GetAllRevenueHistory()}";

        List<RevenueHistoryDto> historyList = new ArrayList<>();

        try (
                // CallableStatement를 사용하여 저장 프로시저 호출
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery() // 실행 후 결과(ResultSet) 가져오기
        ) {
            while (rs.next()) {
                // 결과를 리스트에 추가 (DTO 객체 생성)
                //  결과를 리스트에 추가 (Builder 패턴 사용)
                historyList.add(RevenueHistoryDto.builder()
                        .revenueId(rs.getInt("revenue_id"))  // 재고 변경 이력 ID
                        .changeDate(rs.getTimestamp("change_date").toLocalDateTime())  // 변경 일시
                        .revenueQuantity(rs.getInt("revenue_quantity"))  // 변경된 수량
                        .changeType(rs.getString("change_type"))  // 변경 유형 (입고, 출고 등)
                        .productName(rs.getString("product_name"))  // 제품명
                        .businessName(rs.getString("business_name"))  // 사업체명
                        .build()); // 최종적으로 객체 생성

            }
        } catch (SQLException e) {
            throw new RevenueException("재고 이력 조회 중 DB 오류 발생: " + e.getMessage(), e);
        }

        if (historyList.isEmpty()) {
            throw new RevenueException("현재 등록된 재고 이력이 없습니다.");
        }

        return historyList; // 최종 리스트 반환
    }

    public static void main(String[] args) {
        RevenueRepository R = new RevenueRepositoryImp();
        List<RevenueHistoryDto> allRevenueHistory = R.findAllRevenueHistory();

        for (RevenueHistoryDto revenueHistoryDto : allRevenueHistory) {
            System.out.println(revenueHistoryDto);
        }
    }
}

package inbound.repository;

import object.ObjectIo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageConditionRepositoryImp implements StorageConditionRepository{

    private final Connection conn = ObjectIo.getConnection();


    @Override
    public boolean isStorageConditionMatch(int storageId, int storageTemp) {
        String sql = """
            SELECT min_temp, max_temp FROM storage_condition WHERE storage_id = ?
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storageId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int minTemp = rs.getInt("min_temp");
                int maxTemp = rs.getInt("max_temp");

                //  제품 보관 온도가 최소~최대 온도 범위 내에 포함되는지 확인
                return storageTemp >= minTemp && storageTemp <= maxTemp;
            }
        } catch (SQLException e) {
            throw new RuntimeException("보관 상태 정보 조회 중 오류 발생", e);
        }

        return false; // 조회 결과가 없으면 보관 불가능으로 판단
    }

    public static void main(String[] args) {
                StorageConditionRepository s = new StorageConditionRepositoryImp();
        boolean storageConditionMatch = s.isStorageConditionMatch(5, 22);
        System.out.println(storageConditionMatch);
    }

}


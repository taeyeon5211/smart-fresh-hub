package inbound.repository;


import inbound.exception.InboundException;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;
import object.ObjectIo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InboundRepositoryImp implements InboundRepository {

    private final Connection conn = ObjectIo.getConnection();

    // 새로운 입고 요청을 등록한다.
    @Override
    public int insertInboundRequest(InboundVo inboundRequest) {
        String sql = "INSERT INTO inbound_table (product_id, inbound_amount, inbound_status, inbound_request_date, admin_id) " +
                "VALUES (?, ?, '대기', ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, inboundRequest.getProductId());
            pstmt.setInt(2, inboundRequest.getInboundAmount());
            pstmt.setTimestamp(3, Timestamp.valueOf(inboundRequest.getRequestDate()));
            pstmt.setInt(4, inboundRequest.getAdminId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InboundException("입고 요청 등록 실패!");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // 생성된 inbound_id 반환
            } else {
                throw new InboundException(" 생성된 입고 요청 ID를 가져오지 못했습니다.");
            }
        } catch (SQLException e) {
            throw new InboundException("입고 요청 등록 중 DB 오류 발생", e);
        }
    }





    //  입고 요청 상태를 변경한다. (승인, 취소)
    @Override
    public boolean updateInboundStatus(int inboundId, String status) {
        String sql = "UPDATE inbound_table SET inbound_status = ? WHERE inbound_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, inboundId);

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows == 0) {
                throw new InboundException(" 입고 요청 상태 변경 실패! (ID: " + inboundId + ")");
            }
            return true;
        } catch (SQLException e) {
            throw new InboundException("입고 상태 업데이트 중 DB 오류 발생", e);
        }
    }

    //  모든 '대기' 상태 입고 요청 목록 조회
    @Override
    public List<InboundHistoryVo> findAllPendingInboundRequests() {
        String sql = """
            SELECT i.inbound_id, i.inbound_request_date, i.inbound_date, 
                   i.inbound_status, i.inbound_amount, 
                   p.product_name, b.business_name, a.admin_id
            FROM inbound_table i
            JOIN product p ON i.product_id = p.product_id
            JOIN business_table b ON p.business_id = b.business_id
            JOIN admin_table a ON i.admin_id = a.admin_id
            WHERE i.inbound_status = '대기'
            ORDER BY i.inbound_request_date DESC
        """;

        List<InboundHistoryVo> inboundList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                inboundList.add(new InboundHistoryVo(
                        rs.getInt("inbound_id"),
                        rs.getTimestamp("inbound_request_date").toLocalDateTime(),
                        rs.getTimestamp("inbound_date") != null ? rs.getTimestamp("inbound_date").toLocalDateTime() : null,
                        rs.getString("inbound_status"),
                        rs.getInt("inbound_amount"),
                        rs.getString("product_name"),
                        rs.getString("business_name"),
                        rs.getInt("admin_id")
                ));
            }
        } catch (SQLException e) {
            throw new InboundException("'대기' 상태 입고 요청 조회 중 DB 오류 발생", e);
        }

        if (inboundList.isEmpty()) {
            throw new InboundException("현재 '대기' 상태의 입고 요청이 없습니다.");
        }

        return inboundList;
    }



    // 모든 입고 내역을 조회한다.
    @Override
    public List<InboundHistoryVo> findAllInboundHistory() {
        String sql = """
            SELECT i.inbound_id, i.inbound_request_date, i.inbound_date, 
                   i.inbound_status, i.inbound_amount, 
                   p.product_name, b.business_name, a.admin_id
            FROM inbound_table i
            JOIN product p ON i.product_id = p.product_id
            JOIN business_table b ON p.business_id = b.business_id
            JOIN admin_table a ON i.admin_id = a.admin_id
            ORDER BY i.inbound_request_date DESC
        """;

        List<InboundHistoryVo> inboundList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                inboundList.add(new InboundHistoryVo(
                        rs.getInt("inbound_id"),
                        rs.getTimestamp("inbound_request_date").toLocalDateTime(),
                        rs.getTimestamp("inbound_date") != null ? rs.getTimestamp("inbound_date").toLocalDateTime() : null,
                        rs.getString("inbound_status"),
                        rs.getInt("inbound_amount"),
                        rs.getString("product_name"),
                        rs.getString("business_name"),
                        rs.getInt("admin_id")
                ));
            }
        } catch (SQLException e) {
            throw new InboundException(" 모든 입고 내역 조회 중 DB 오류 발생", e);
        }

        if (inboundList.isEmpty()) {
            throw new InboundException(" 현재 등록된 입고 내역이 없습니다.");
        }

        return inboundList;
    }


    // 특정 사업체(화주)의 입고 내역을 조회한다.
    @Override
    public List<InboundHistoryVo> findInboundHistoryByBusiness(int businessId) {
        String sql = """
    SELECT i.inbound_id, i.inbound_request_date, i.inbound_date, 
           i.inbound_status, i.inbound_amount, 
           p.product_name, b.business_name, a.admin_id
    FROM inbound_table i
    JOIN product p ON i.product_id = p.product_id  -- 제품명 가져오기
    JOIN business_table b ON p.business_id = b.business_id  -- 사업체명 가져오기
    JOIN admin_table a ON i.admin_id = a.admin_id
    WHERE p.business_id = ? 
    ORDER BY i.inbound_request_date DESC
    """;

        List<InboundHistoryVo> inboundList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, businessId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                InboundHistoryVo inboundHistory = new InboundHistoryVo(
                        rs.getInt("inbound_id"),
                        rs.getTimestamp("inbound_request_date").toLocalDateTime(),
                        rs.getTimestamp("inbound_date") != null ? rs.getTimestamp("inbound_date").toLocalDateTime() : null,
                        rs.getString("inbound_status"),
                        rs.getInt("inbound_amount"),
                        rs.getString("product_name"),
                        rs.getString("business_name"),
                        rs.getInt("admin_id")
                );
                inboundList.add(inboundHistory);
            }
        } catch (SQLException e) {
            throw new InboundException(" 입고 내역 조회 중 DB 오류 발생: " + e.getMessage(), e);
        }

        // 조회된 입고 내역이 없을 경우 예외 발생
        if (inboundList.isEmpty()) {
            throw new InboundException(" 해당 사업체(ID: " + businessId + ")의 입고 내역이 존재하지 않습니다.");
        }

        return inboundList;
    }




    @Override
    public Map<Integer, String> findAllBusinesses() {
        String sql = "SELECT business_id, business_name FROM business_table";
        Map<Integer, String> businessMap = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                businessMap.put(rs.getInt("business_id"), rs.getString("business_name"));
            }
        } catch (SQLException e) {
            throw new InboundException("사업체 목록 조회 중 DB 오류 발생: " + e.getMessage(), e);
        }

        // 등록된 사업체가 없는 경우 예외 발생
        if (businessMap.isEmpty()) {
            throw new InboundException("⚠현재 등록된 사업체가 없습니다.");
        }

        return businessMap;
    }


    @Override
    public Map<Integer, String> findProductsByBusiness(int businessId) {
        String sql = "SELECT product_id, product_name FROM product WHERE business_id = ?";
        Map<Integer, String> productMap = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, businessId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productMap.put(rs.getInt("product_id"), rs.getString("product_name"));
            }
        } catch (SQLException e) {
            throw new InboundException("제품 목록 조회 중 DB 오류 발생: " + e.getMessage(), e);
        }

        // 조회된 제품이 없는 경우 예외 발생
        if (productMap.isEmpty()) {
            throw new InboundException("⚠해당 사업체 (business_id: " + businessId + ")가 등록한 제품이 없습니다.");
        }

        return productMap;
    }




        public static void main (String[] args){
            InboundRepository inboundRepository = new InboundRepositoryImp();

        }
    }







package inbound.repository;


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
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 inbound_id 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환
    }

    // 특정 사업체(화주)의 입고 요청 목록을 조회한다.
    @Override
    public List<InboundVo> findInboundRequestsByBusiness(int businessId) {
        String sql = """
        SELECT i.inbound_id, i.product_id, i.inbound_amount, 
               i.inbound_status, i.inbound_request_date, i.admin_id
        FROM inbound_table i
        JOIN product p ON i.product_id = p.product_id  -- product 테이블 통해 business_id 조회
        WHERE p.business_id = ? 
        ORDER BY i.inbound_request_date DESC
        """;

        List<InboundVo> inboundList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, businessId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                InboundVo inboundVo = InboundVo.builder()
                        .inboundId(rs.getInt("inbound_id"))
                        .productId(rs.getInt("product_id")) //
                        .inboundAmount(rs.getInt("inbound_amount"))
                        .status(rs.getString("inbound_status"))
                        .requestDate(rs.getTimestamp("inbound_request_date").toLocalDateTime())
                        .adminId(rs.getInt("admin_id"))
                        .build();
                inboundList.add(inboundVo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inboundList;
    }


    // 입고 요청 상태를 변경한다. (승인, 취소 등)
    @Override
    public boolean updateInboundStatus(int inboundId, String status) {
        String sql = "UPDATE inbound_table SET inbound_status = ? WHERE inbound_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, inboundId);

            int updatedRows = pstmt.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //  모든 입고 내역을 조회한다.
    @Override
    public List<InboundHistoryVo> findAllInboundHistory() {
        String sql = """
        SELECT i.inbound_id, i.inbound_request_date, i.inbound_date, 
               i.inbound_status, i.inbound_amount, 
               p.product_name, b.business_name, a.admin_id
        FROM inbound_table i
        JOIN product p ON i.product_id = p.product_id  --  business_id는 product 테이블에서 조회
        JOIN business_table b ON p.business_id = b.business_id  -- product 테이블에서 business_id 연결
        JOIN admin_table a ON i.admin_id = a.admin_id
        ORDER BY i.inbound_request_date DESC
        """;

        List<InboundHistoryVo> inboundList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
            e.printStackTrace();
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
        JOIN product p ON i.product_id = p.product_id  -- product 테이블에서 business_id 조회
        JOIN business_table b ON p.business_id = b.business_id  -- 수정된 부분
        JOIN admin_table a ON i.admin_id = a.admin_id
        WHERE p.business_id = ?  -- WHERE 조건 수정
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
            e.printStackTrace();
        }

        return inboundList;
    }

    // 특정 사업체가 등록한 제품 목록을 조회한다.
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
            e.printStackTrace();
        }

        return productMap;
    }

//        public static void main (String[] args){
//            InboundRepository inboundRepository = new InboundRepositoryImp();
//            List<InboundHistoryVo> allInboundHistory = inboundRepository.findAllInboundHistory();
//            for (InboundHistoryVo inboundHistoryVo : allInboundHistory) {
//                System.out.println(inboundHistoryVo);
//            }
//            boolean a = inboundRepository.updateInboundStatus(16, "승인");
//            System.out.println(a);
//            List<InboundHistoryVo> allInboundHistory1 = inboundRepository.findAllInboundHistory();
//            for (InboundHistoryVo inboundHistoryVo : allInboundHistory1) {
//                System.out.println(inboundHistoryVo);
//            }
//        }
    }







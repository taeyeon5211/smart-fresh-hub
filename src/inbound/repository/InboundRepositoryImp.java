package inbound.repository;


import inbound.dto.InboundRequestDto;
import inbound.exception.InboundException;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;
import object.ObjectIo;

import java.sql.*;
import java.util.*;

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
        String sql = "{CALL GetPendingInboundRequests()}"; //  저장 프로시저 호출

        List<InboundHistoryVo> inboundList = new ArrayList<>();
        try (CallableStatement cstmt = conn.prepareCall(sql);

             ResultSet rs = cstmt.executeQuery()) { //  저장 프로시저 실행

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


    /**
     * 특정 입고 요청 정보를 데이터베이스에서 조회하는 메서드.
     *
     * @param inboundId 조회할 입고 요청의 ID
     * @return 해당 입고 요청 정보를 포함하는 Optional<InboundRequestDto>,
     *         입고 요청이 존재하지 않으면 Optional.empty()
     * @throws InboundException 데이터베이스 조회 중 오류가 발생한 경우
     */
    @Override
    public Optional<InboundRequestDto> getInboundById(int inboundId) {
        String sql = """
        SELECT inbound_id, product_id, inbound_amount, inbound_status, 
               inbound_request_date, admin_id
        FROM inbound_table
        WHERE inbound_id = ?
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inboundId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(InboundRequestDto.builder()
                        .inboundId(rs.getInt("inbound_id"))
                        .productId(rs.getInt("product_id"))
                        .amount(rs.getInt("inbound_amount"))
                        .status(rs.getString("inbound_status"))
                        .requestDate(rs.getTimestamp("inbound_request_date").toLocalDateTime())
                        .adminId(rs.getInt("admin_id"))
                        .build()
                );
            }
        } catch (SQLException e) {
            throw new InboundException("입고 요청 조회 중 DB 오류 발생", e);
        }

        return Optional.empty(); // 요청이 없을 경우 빈 값 반환
    }


    // 모든 입고 내역을 조회한다.
    @Override
    public List<InboundHistoryVo> findAllInboundHistory() {
        // 저장 프로시저를 호출하는 SQL
        String sql = "{CALL GetAllInboundHistory()}";

        List<InboundHistoryVo> inboundList = new ArrayList<>();

        try (
                // CallableStatement를 사용하여 저장 프로시저 호출
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery() // 실행 후 결과(ResultSet) 가져오기
        ) {
            while (rs.next()) {
                // 결과를 리스트에 추가 (VO 객체 생성)
                inboundList.add(new InboundHistoryVo(
                        rs.getInt("inbound_id"),  // 입고 요청 ID
                        rs.getTimestamp("inbound_request_date").toLocalDateTime(),  // 요청 날짜
                        rs.getTimestamp("inbound_date") != null ? rs.getTimestamp("inbound_date").toLocalDateTime() : null,  // 실제 입고 날짜 (null 가능)
                        rs.getString("inbound_status"),  // 입고 상태 (승인, 대기, 취소)
                        rs.getInt("inbound_amount"),  // 입고 수량
                        rs.getString("product_name"),  // 제품명
                        rs.getString("business_name"),  // 사업체명
                        rs.getInt("admin_id")  // 담당 관리자 ID
                ));
            }
        } catch (SQLException e) {
            //  SQL 실행 중 오류 발생 시 예외 처리
            throw new InboundException("모든 입고 내역 조회 중 DB 오류 발생", e);
        }

        //  조회된 데이터가 없을 경우 예외 발생
        if (inboundList.isEmpty()) {
            throw new InboundException("현재 등록된 입고 내역이 없습니다.");
        }

        return inboundList; //  최종 리스트 반환
    }


    // 특정 사업체(화주)의 입고 내역을 조회한다.
    @Override
    public List<InboundHistoryVo> findInboundHistoryByBusiness(int businessId) {
        String sql = "{CALL GetInboundHistoryByBusiness(?)}";

        List<InboundHistoryVo> inboundList = new ArrayList<>();

        try (
                //  CallableStatement를 사용하여 저장 프로시저 호출
                CallableStatement cstmt = conn.prepareCall(sql)
        ) {
            cstmt.setInt(1, businessId); // 사업체 ID를 프로시저에 전달
            ResultSet rs = cstmt.executeQuery(); // 실행 후 결과(ResultSet) 가져오기

            while (rs.next()) {
                // 결과를 리스트에 추가 (VO 객체 생성)
                inboundList.add(new InboundHistoryVo(
                        rs.getInt("inbound_id"),  // 입고 요청 ID
                        rs.getTimestamp("inbound_request_date").toLocalDateTime(),  // 요청 날짜
                        rs.getTimestamp("inbound_date") != null ? rs.getTimestamp("inbound_date").toLocalDateTime() : null,  // 실제 입고 날짜 (null 가능)
                        rs.getString("inbound_status"),  // 입고 상태 (승인, 대기, 취소)
                        rs.getInt("inbound_amount"),  // 입고 수량
                        rs.getString("product_name"),  // 제품명
                        rs.getString("business_name"),  // 사업체명
                        rs.getInt("admin_id")  // 담당 관리자 ID
                ));
            }
        } catch (SQLException e) {
            //  SQL 실행 중 오류 발생 시 예외 처리
            throw new InboundException("입고 내역 조회 중 DB 오류 발생: " + e.getMessage(), e);
        }

        //  조회된 데이터가 없을 경우 예외 발생
        if (inboundList.isEmpty()) {
            throw new InboundException("해당 사업체(ID: " + businessId + ")의 입고 내역이 존재하지 않습니다.");
        }

        return inboundList; // 최종 리스트 반환
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
            throw new InboundException("해당 사업체 (business_id: " + businessId + ")가 등록한 제품이 없습니다.");
        }

        return productMap;
    }




        public static void main (String[] args){
            InboundRepository inboundRepository = new InboundRepositoryImp();
            List<InboundHistoryVo> allPendingInboundRequests = inboundRepository.findInboundHistoryByBusiness(2);
            for (InboundHistoryVo allPendingInboundRequest : allPendingInboundRequests) {
                System.out.println(allPendingInboundRequest);
            }


        }
    }







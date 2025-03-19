package inbound.repository;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;

import java.util.List;
import java.util.Map;

public interface InboundRepository {

        /**
         * 새로운 입고 요청을 등록한다.
         * 제품 ID(product_id)를 기반으로 처리한다.
         * @param inboundRequest 입고 요청 데이터
         * @return 등록된 입고 요청 ID (생성된 inbound_id 반환)
         */
        int insertInboundRequest(InboundVo inboundRequest);



        /**
         * 입고 요청 상태를 변경한다. (승인, 취소 등)
         * @param inboundId 입고 요청 ID
         * @param status 변경할 상태 (승인, 취소 등)
         * @return 변경 성공 여부 (true: 성공, false: 실패)
         */
        boolean updateInboundStatus(int inboundId, String status);

        /**
         * 모든 입고 요청 중 '대기' 상태인 목록을 조회하는 메서드.
         * 전체 입고 요청 중 승인 대기 중인 요청만 반환한다.
         *
         * @return '대기' 상태의 입고 요청 리스트
         */
        List<InboundHistoryVo> findAllPendingInboundRequests();


        /**
         * 모든 입고 내역을 조회한다.
         * @return 전체 입고 내역 리스트
         */
        List<InboundHistoryVo> findAllInboundHistory();

        /**
         * 특정 사업체(화주)의 입고 내역을 조회한다.
         * 입고 테이블에서 business_id를 직접 조회할 수 없으므로 product 테이블을 조인하여 검색한다.
         * @param businessId 사업체 ID
         * @return 해당 사업체의 입고 내역 리스트
         */
        List<InboundHistoryVo> findInboundHistoryByBusiness(int businessId);

        /**
         * 특정 사업체가 등록한 제품 목록을 조회한다.
         * business_id를 기준으로 product 테이블에서 product_id와 product_name을 조회한다.
         * @param businessId 사업체 ID
         * @return 해당 사업체의 제품 목록 (product_id, product_name)
         */
        Map<Integer, String> findProductsByBusiness(int businessId);

        /**
         * 모든 등록된 사업체 목록을 조회하는 메서드.
         *
         * @return 등록된 모든 사업체의 (사업체 ID, 사업체명) 목록
         */
        Map<Integer, String> findAllBusinesses();
}



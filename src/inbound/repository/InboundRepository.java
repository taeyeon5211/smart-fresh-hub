package inbound.repository;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;

import java.util.List;

public interface InboundRepository {

        /**
         * 새로운 입고 요청을 등록한다.
         * @param inboundRequest 입고 요청 데이터
         * @return 등록된 입고 요청 ID
         */
        int insertInboundRequest(InboundVo inboundRequest);

        /**
         * 특정 입고 요청 정보를 조회한다.
         * @param inboundId 입고 요청 ID
         * @return 입고 요청 상세 정보
         */
        InboundVo findInboundRequestById(int inboundId);

        /**
         * 특정 사업체(화주)의 입고 요청 목록을 조회한다.
         * @param businessId 사업체 ID
         * @return 해당 사업체의 입고 요청 목록
         */
        List<InboundVo> findInboundRequestsByBusiness(int businessId);

        /**
         * 입고 요청 상태를 변경한다. (승인, 취소 등)
         * @param inboundId 입고 요청 ID
         * @param status 변경할 상태 (승인, 취소 등)
         * @return 변경 성공 여부
         */
        boolean updateInboundStatus(int inboundId, String status);

        /**
         * 승인된 입고 요청을 실제 입고 내역으로 저장한다.
         * @param inboundId 입고 요청 ID
         * @param adminId 입고를 승인한 관리자 ID
         * @return 입고 내역 저장 성공 여부
         */
        boolean insertInboundHistory(int inboundId, int adminId);

        /**
         * 모든 입고 내역을 조회한다.
         * @return 전체 입고 내역 리스트
         */
        List<InboundHistoryVo> findAllInboundHistory();

        /**
         * 특정 제품의 입고 내역을 조회한다.
         * @param productId 제품 ID
         * @return 해당 제품의 입고 내역 리스트
         */
        List<InboundHistoryVo> findInboundHistoryByProduct(int productId);

        /**
         * 특정 사업체(화주)의 입고 내역을 조회한다.
         * @param businessId 사업체 ID
         * @return 해당 사업체의 입고 내역 리스트
         */
        List<InboundHistoryVo> findInboundHistoryByBusiness(int businessId);
    }



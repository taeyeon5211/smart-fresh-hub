package inbound.service;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;

import java.util.List;
import java.util.Map;

public interface InboundService {
    /**
     * 새로운 입고 요청을 생성한다.
     * 입고 요청은 사업체가 등록한 제품(product_id) 기준으로 생성된다.
     * @param inboundRequestDto 입고 요청 DTO
     * @return 등록된 입고 요청 ID (생성된 inbound_id 반환)
     */
    int createInboundRequest(InboundRequestDto inboundRequestDto);

    /**
     * 특정 사업체(화주)가 등록한 제품 목록을 조회한다.
     * product 테이블에서 business_id를 기준으로 조회한다.
     * @param businessId 사업체 ID
     * @return 해당 사업체의 제품 목록 (product_id, product_name)
     */
    Map<Integer, String> getAvailableProducts(int businessId);

    /**
     * 모든 입고 요청 중 '대기' 상태인 목록을 조회한다.
     * 입고 요청 상태가 '대기'인 것만 반환한다.
     *
     * @return '대기' 상태의 입고 요청 리스트
     */
    List<InboundHistoryDto> getAllPendingInboundList();

    /**
     * 입고 요청 상태를 변경한다. (승인, 취소 등)
     * @param inboundId 입고 요청 ID
     * @param status 변경할 상태 (승인, 취소 등)
     * @return 변경 성공 여부 (true: 성공, false: 실패)
     */
    boolean updateInboundStatus(int inboundId, String status);

    /**
     * 모든 입고 내역을 조회한다.
     * 입고 테이블에서 모든 데이터를 가져오며, 제품 정보(product_name) 및 사업체 정보(business_name)도 포함한다.
     * @return 전체 입고 내역 리스트
     */
    List<InboundHistoryDto> getAllInboundHistory();

    /**
     * 특정 사업체의 입고 내역을 조회한다.
     * product 테이블을 JOIN하여 business_id를 기준으로 입고 내역을 필터링한다.
     * @param businessId 사업체 ID
     * @return 해당 사업체의 입고 내역 리스트
     */
    List<InboundHistoryDto> getInboundHistoryByBusiness(int businessId);


    /**
     * 모든 사업체 목록을 조회하는 메서드 (관리자용).
     *
     * @return 등록된 모든 사업체의 (사업체 ID, 사업체명) 목록
     */
    Map<Integer, String> getAllBusinesses();
}


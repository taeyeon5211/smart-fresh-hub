package inbound.controller;
/**
 * 입고 요청을 처리하는 컨트롤러 인터페이스.
 *
 * 다양한 입고 요청 처리를 위한 컨트롤러 구현체가 이 인터페이스를 상속받아 사용될 수 있다.
 */
public interface InboundController {


    /**
     * 새로운 입고 요청을 처리하는 메서드.
     * 사용자가 입력한 데이터를 기반으로 입고 요청을 생성하고, 결과를 출력한다.
     */
    void handleInboundRequest();

    /**
     * 특정 사업체가 등록한 제품 목록을 조회하는 메서드.
     * business_id를 입력하여 해당 사업체의 등록된 제품 목록을 출력한다.
     */
    void showAvailableProducts();


    /**
     * 모든 '대기' 상태의 입고 요청 목록을 조회하는 메서드.
     * 전체 입고 요청 중 승인 대기 중인 요청만 출력한다.
     */
    void showAllPendingInboundList();

    /**
     * 특정 사업체의 입고 내역을 조회하는 메서드.
     * business_id를 입력하여 해당 사업체의 모든 입고 내역(승인, 취소, 대기 포함)을 출력한다.
     */
    void showInboundHistoryByBusiness();

    /**
     * 관리자가 입고 요청 상태를 변경하는 메서드.
     * inbound_id와 변경할 상태(승인, 취소)를 입력받아 입고 상태를 업데이트한다.
     */
    void updateInboundStatus();

    /**
     * 모든 사업체 목록을 조회하는 메서드 (관리자용).
     * 등록된 모든 사업체의 ID와 이름을 출력한다.
     */
    void showAllBusinesses();
}


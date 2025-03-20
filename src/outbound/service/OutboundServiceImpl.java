package outbound.service;

import outbound.dto.OutboundDTO;
import outbound.repository.OutboundRepository;
import outbound.repository.OutboundRepositoryImpl;
import outbound.repository.OutboundRepositoryImpl2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 출고(Outbound) 관련 서비스 구현 클래스.
 * 출고 요청 생성, 조회, 상태 변경 및 재고 감소 기능을 제공한다.
 */
public class OutboundServiceImpl implements OutboundService {
    private final OutboundRepository outboundRepository;

    /**
     * 생성자: 출고 서비스 구현체를 생성한다.
     * @param outboundRepository 출고 관련 데이터 처리 객체
     */
    public OutboundServiceImpl(OutboundRepository outboundRepository) {
        this.outboundRepository = outboundRepository;
    }

    /**
     * 신규 출고 요청을 생성하는 메서드.
     * @param outboundDTO 출고 요청 데이터 객체
     */
    @Override
    public void createOutboundRequest(OutboundDTO outboundDTO) {
        outboundRepository.createOutboundRequest(outboundDTO);
    }

    /**
     * 특정 사업체의 출고 요청 상태를 조회하는 메서드.
     * @param businessId 조회할 사업체 ID
     * @return 출고 요청 목록 (Optional<List<OutboundDTO>>)
     */
    @Override
    public Optional<List<OutboundDTO>> readOutboundStatus(int businessId) {
        return outboundRepository.readOutboundStatus(businessId);
    }

    /**
     * 현재 '대기' 상태인 출고 요청 목록을 조회하는 메서드.
     * @return 대기 상태의 출고 요청 목록 (Optional<List<OutboundDTO>>)
     */
    @Override
    public Optional<List<OutboundDTO>> readOutboundRequest() {
        return outboundRepository.readOutboundRequest();
    }

    /**
     * 출고 요청의 상태를 변경하는 메서드.
     * @param newStatus 변경할 상태 ('승인' 또는 '취소')
     * @param adminId 처리한 관리자 ID
     * @param outboundDate 출고 날짜 (승인 시 현재 시간 입력)
     * @param outboundId 출고 요청 ID
     */
    @Override
    public void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId) {
        outboundRepository.updateOutboundStatus(newStatus, adminId, outboundDate, outboundId);

        // 출고가 승인된 경우 재고 감소 로직 실행
        if ("승인".equals(newStatus)) {
            outboundRepository.updateRevenue(outboundId);
            outboundRepository.deleteZeroRevenue();
        }
    }

    /**
     * 승인된 출고 요청을 반영하여 재고를 감소시키는 메서드.
     * @param businessId 출고 요청이 속한 사업체 ID
     */
    @Override
    public void updateRevenue(int businessId) {
        outboundRepository.updateRevenue(businessId);
    }

    /**
     * 전체 출고 요청 목록을 조회하는 메서드.
     * @return 모든 출고 요청 목록 (Optional<List<OutboundDTO>>)
     */
    @Override
    public Optional<List<OutboundDTO>> readAllOutboundRequest() {
        return outboundRepository.readAllOutboundRequest();
    }

    /**
     * 재고 테이블에서 수량이 0인 항목을 삭제하는 메서드.
     */
    @Override
    public void deleteZeroRevenue() {
        outboundRepository.deleteZeroRevenue();
    }

    /**
     * 출고 서비스 동작 테스트용 메인 메서드.
     * 출고 요청 생성 및 상태 업데이트를 실행한다.
     */
    public static void main(String[] args) {
        OutboundRepository repo = new OutboundRepositoryImpl2();
        OutboundService service = new OutboundServiceImpl(repo);

//        // 1. 출고 요청 생성 테스트
//        OutboundDTO newOutbound = OutboundDTO.builder()
//                .outboundAmount(5)
//                .productId(2)
//                .build();
//        service.createOutboundRequest(newOutbound);
//        System.out.println("출고 요청 생성 테스트 완료");

        // 2. 출고 요청 상태 변경 테스트 (승인)
        int testOutboundId = 2;
        service.updateOutboundStatus("승인", 1, LocalDateTime.now(), testOutboundId);
        System.out.println("출고 요청 승인 테스트 완료");




    }
}


package inbound.service;

import inbound.dto.RevenueHistoryDto;

import java.util.List;

/**
 * 재고 변경 이력을 관리하는 서비스 인터페이스
 */
public interface RevenueHistoryService {

    /**
     * 모든 재고 변경 이력을 조회한다. (관리자 전용)
     *
     * @return 재고 변경 이력 리스트
     */
    List<RevenueHistoryDto> getAllRevenueHistory();
}

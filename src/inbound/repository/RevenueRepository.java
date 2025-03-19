package inbound.repository;

import inbound.dto.RevenueHistoryDto;
import inbound.vo.RevenueHistoryVo;

import java.util.List;

public interface RevenueRepository {

    /**
     * 특정 제품이 특정 구역에 이미 존재하는지 확인한다.
     *
     * @param productId 제품 ID
     * @param areaId 구역 ID
     * @return 존재하면 true, 없으면 false
     */
    boolean existsRevenue(int productId, int areaId);

    /**
     * 기존 재고를 업데이트한다. (제품이 이미 존재할 경우)
     *
     * @param productId 제품 ID
     * @param areaId 구역 ID
     * @param additionalAmount 추가할 수량
     * @return 업데이트된 행 개수
     */
    int updateRevenue(int productId, int areaId, int additionalAmount);

    /**
     * 새로운 재고를 삽입한다. (제품이 처음 보관되는 경우)
     *
     * @param productId 제품 ID
     * @param areaId 구역 ID
     * @param amount 보관할 수량
     * @return 삽입된 행 개수
     */
    int insertRevenue(int productId, int areaId, int amount);

    /**
     * 관리자가 모든 재고 변경 이력을 조회하는 메서드.
     * `revenue_history_table`을 조회하고 관련 제품 및 사업체 정보를 포함한다.
     *
     * @return 재고 변경 이력 리스트
     */
    List<RevenueHistoryDto> findAllRevenueHistory();
}

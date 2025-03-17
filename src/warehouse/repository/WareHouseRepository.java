package warehouse.repository;

import warehouse.dto.WareHouseDto;

import java.util.List;
import java.util.Optional;

/**
 * 창고관리 repository
 */
public interface WareHouseRepository {
    /**
     * 창고 생성 메서드
     * @param warehouseDto 입력받은 창고 dto
     */
    void createWarehouse(WareHouseDto warehouseDto);

    /**
     * 전체 창고 리스트 조회
     * @return 창고 리스트
     */
    Optional<List<WareHouseDto>> getWarehouseAll();

    /**
     * 도시명으로 해당하는 전체 창고 리스트 조회
     * @param warehouseAddress 도시명 입력
     * @return 해당 창고 리스트
     */
    Optional<List<WareHouseDto>> getWarehouseByAddress(String warehouseAddress);

    /**
     * 창고 아이디로 해당하는 창고 조회
     * @param warehouseId 창고 아이디
     * @return 해당 창고 dto 반환
     */
    Optional<WareHouseDto> getWarehouseById(Integer warehouseId);
}

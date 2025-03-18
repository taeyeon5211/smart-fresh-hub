package warehouse.service;

import warehouse.dto.WareHouseDto;
import java.util.List;
import java.util.Optional;

public interface WareHouseService {
    /**
     * 창고 생성 메서드
     * @param warehouseDto 입력받은 창고 dto
     */
    void createWarehouse(WareHouseDto warehouseDto);

    /**
     * 창고 리스트 반환 메서드
     * @return 창고 테이블 리스트 반환
     */
    List<WareHouseDto> getWarehouseAll();

    /**
     * 도시명 검색후 해당 창고 리스트 반환 메서드
     * @param warehouseAddress 도시명 입력
     * @return 도시명에 해당하는 테이블 리스트 반환
     */
    List<WareHouseDto> getWarehouseByAddress(String warehouseAddress);

    /**
     * 창고 아이디로 해당하는 창고 조회
     * @param warehouseId 창고 아이디
     * @return 해당 창고 dto 반환
     */
    WareHouseDto getWarehouseById(Integer warehouseId);
}

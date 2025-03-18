package warehouse.service;

import warehouse.dto.WareHouseDto;
import warehouse.repository.WareHouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 창고관리 서비스 구현체
 */
public class WareHouseServiceImp implements WareHouseService {
    WareHouseRepository warehouseRepository;

    public WareHouseServiceImp(WareHouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * 창고 생성 메서드
     * @param warehouseDto 입력받은 창고 dto
     */
    @Override
    public void createWarehouse(WareHouseDto warehouseDto) {
        warehouseRepository.createWarehouse(warehouseDto);
    }

    /**
     * 창고 리스트 반환 메서드
     * @return 창고 테이블 리스트 반환
     */
    @Override
    public List<WareHouseDto> getWarehouseAll() {
        return warehouseRepository.getWarehouseAll().orElseGet(() -> {
            System.out.println("비어있습니다.");
            return new ArrayList<WareHouseDto>();
        });
    }

    /**
     * 도시명 검색후 해당 창고 리스트 반환 메서드
     * @param warehouseAddress 도시명 입력
     * @return 도시명에 해당하는 테이블 리스트 반환
     */
    @Override
    public List<WareHouseDto> getWarehouseByAddress(String warehouseAddress) {
        return warehouseRepository.getWarehouseByAddress(warehouseAddress).orElseGet(() -> {
            System.out.println("비어있습니다.");
            return new ArrayList<WareHouseDto>();
        });
    }

    /**
     * 창고 아이디로 해당하는 창고 조회
     * @param warehouseId 창고 아이디
     * @return 해당 창고 dto 반환
     */
    @Override
    public WareHouseDto getWarehouseById(Integer warehouseId) {
        return warehouseRepository.getWarehouseById(warehouseId).orElseGet(() ->
        {
            System.out.println("해당 아이디 값의 창고가 없습니다.");
            return new WareHouseDto();
        });
    }
}

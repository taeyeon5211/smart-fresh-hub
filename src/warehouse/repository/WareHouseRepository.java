package warehouse.repository;

import warehouse.dto.WareHouseDto;

public interface WareHouseRepository {
    void createWarehouse(WareHouseDto warehouseDto);
}

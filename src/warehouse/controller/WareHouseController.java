package warehouse.controller;

import warehouse.dto.WareHouseDto;

public interface WareHouseController {
    void createWarehouse();
    void getWarehouseAll();
    void getWarehouseByAddress();
    void getWarehouseById();
}

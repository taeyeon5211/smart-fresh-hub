package diconfig;

import warehouse.controller.WareHouseController;
import warehouse.controller.WareHouseControllerImp;
import warehouse.repository.WareHouseRepositoryImp;
import warehouse.service.WareHouseService;
import warehouse.service.WareHouseServiceImp;

public class DiConfig {

    private WareHouseService getWarehouseService() {
        return new WareHouseServiceImp(new WareHouseRepositoryImp());
    }
    public WareHouseController getWarehouseController() {
        return new WareHouseControllerImp(getWarehouseService());
    }
}

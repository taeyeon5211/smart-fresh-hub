package area.controller;

import area.dto.AreaDto;
import area.dto.WarehouseInfoSpaceDto;
import area.service.AreaService;

import java.util.List;

public class AreaControllerImp implements AreaController{
    AreaService areaService;

    public AreaControllerImp(AreaService areaService) {
        this.areaService = areaService;
    }

    @Override
    public void getAreaAll() {
        areaService.getAreaAll().forEach(System.out::println);
    }

    @Override
    public void getAreaListByWarehouseId(int warehouseId) {
        areaService.getAreaListByWarehouseId(warehouseId).forEach(System.out::println);
    }

    @Override
    public void getWarehouseInfoSpaceAll() {
        areaService.getWarehouseInfoSpaceAll().forEach(System.out::println);
    }

    @Override
    public void UpdateAreaTemp(Integer areaId, Integer storageId) {
        AreaDto areaDto = AreaDto.builder()
                .areaId(areaId)
                .storageId(storageId)
                .build();
        areaService.UpdateAreaTemp(areaDto);
    }

    @Override
    public void CreateArea(Integer areaSpace, Integer areaPrice, Integer warehouseId, Integer storageId) {
        areaService.CreateArea(AreaDto.builder()
                .areaSpace(areaSpace)
                .areaPrice(areaPrice)
                .warehouseId(warehouseId)
                .storageId(storageId)
                .build());
    }
}

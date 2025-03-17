package warehouse.controller;

import diconfig.DiConfig;
import warehouse.dto.WareHouseDto;
import warehouse.service.WareHouseService;

import java.util.Scanner;
import java.util.function.Supplier;

public class WareHouseControllerImp implements WareHouseController {
    Scanner sc = new Scanner(System.in);
    WareHouseService wareHouseService;

    public WareHouseControllerImp(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    private CreateWarehouseDto<String,Integer,String,Integer,WareHouseDto> createWareHouseDto =
            (name,space,address, amount) -> {
                return WareHouseDto.builder()
                        .warehouseName(name)
                        .warehouseSpace(space)
                        .warehouseAddress(address)
                        .warehouseAmount(amount)
                        .build();
    };

    @Override
    public void createWarehouse() {
        System.out.println("추가하고싶은 창고 이름");
        String warehouse_name = sc.nextLine();
        System.out.println("창고 공간");
        Integer warehouse_space = sc.nextInt();
        sc.nextLine();
        System.out.println("창고 주소");
        String warehouse_address = sc.nextLine();
        System.out.println("창고 지출내역");
        Integer warehouse_amount = sc.nextInt();
        sc.nextLine();
        wareHouseService.createWarehouse(createWareHouseDto.apply(warehouse_name, warehouse_space, warehouse_address, warehouse_amount));
    }

    @Override
    public void getWarehouseAll() {
        System.out.println("전체 창고 출력");
        wareHouseService.getWarehouseAll().forEach(System.out::println);
    }

    @Override
    public void getWarehouseByAddress() {
        System.out.println("도시명 입력");
        String warehouse_name = sc.nextLine();
        wareHouseService.getWarehouseByAddress(warehouse_name).forEach(System.out::println);
    }

    @Override
    public void getWarehouseById() {
        System.out.println("창고 아이디 입력");
        Integer warehouse_id = sc.nextInt();
        System.out.println(wareHouseService.getWarehouseById(warehouse_id));
    }

//    public static void main(String[] args) {
//        DiConfig diConfig = new DiConfig();
//        diConfig.getWarehouseController().createWarehouse();
//        diConfig.getWarehouseController().getWarehouseAll();
//        diConfig.getWarehouseController().getWarehouseByAddress();
//        diConfig.getWarehouseController().getWarehouseById();
//    }
}

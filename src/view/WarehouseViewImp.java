package view;

import area.controller.AreaController;
import area.controller.AreaControllerImp;
import area.repository.AreaRepositoryImp;
import area.service.AreaServiceImp;
import warehouse.controller.WareHouseController;
import warehouse.controller.WareHouseControllerImp;
import warehouse.repository.WareHouseRepositoryImp;
import warehouse.service.WareHouseServiceImp;

import java.util.Scanner;

public class WarehouseViewImp implements WarehouseView {
    Scanner scanner = new Scanner(System.in);
    WareHouseController warehouseController;
    AreaController areaController;

    public WarehouseViewImp(WareHouseController warehouseController, AreaController areaController) {
        this.warehouseController = warehouseController;
        this.areaController = areaController;
    }

    @Override
    public void CreateView() {
        while(true){
            boolean running = true;
            while (running) {
                warehouseController.getWarehouseAll();
                System.out.println("창고관리 번호를 입력하세요");
                System.out.println("1: 창고생성 2: 도시명으로 검색 3: 창고id로 검색 4: 창고별 남은 공간 확인 5: 온도 변경(위험) 6:종료" );
                int menuNum = scanner.nextInt();
                scanner.nextLine();
                switch (menuNum) {
                    case 1: warehouseController.createWarehouse(); break; //창고 생성
                    case 2: warehouseController.getWarehouseByAddress();break; // 도시명으로 검색
                    case 3: { // 창고id 검색
                        System.out.println("상세보기 하실 창고 아이디를 입력하세요");
                        int warehouse_id = scanner.nextInt();
                        scanner.nextLine();
                        warehouseController.getWarehouseById(warehouse_id); // 창고 정보 출력
                        boolean runWarehouse = true;
                        while(runWarehouse) { // 반복
                            System.out.println("1. 해당 창고의 구역정보 출력 2. 구역 생성 3. 뒤로가기");
                            int warehouseNum = scanner.nextInt();
                            scanner.nextLine();
                            switch (warehouseNum) {
                                case 1: {
                                    System.out.println("창고번호 " + warehouse_id + "의 구역 정보입니다.");
                                    areaController.getAreaListByWarehouseId(warehouse_id);} break;
                                case 2: {
                                    System.out.println("구역 크기를 입력하세요.");
                                    int space = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("구역의 지출 금액을 입력하세요.");
                                    int price = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("구역의 설정 온도 아이디를 입력하세요.");
                                    System.out.println("1 - Ultra-Low_Temperature(-80 ~ -60)");
                                    System.out.println("2 - Frozen_Storage(-20 ~ -10)");
                                    System.out.println("3 - Refrigerated_Storage(1 ~ 8)");
                                    System.out.println("4 - Cool_Storage(10 ~ 15)");
                                    System.out.println("5 - Room_Temperature(18 ~ 22)");
                                    System.out.println("6 - Heated_Storage(30 ~ 40)");
                                    int storage = scanner.nextInt();
                                    scanner.nextLine();
                                    areaController.CreateArea(space,price,warehouse_id,storage);
                                } break;
                                case 3: runWarehouse = false; break;
                                default:
                                    System.out.println("다시 입력하세요.");
                            }
                        }
                    }break;
                    case 4: {
                        System.out.println("창고별 남은 공간 리스트");
                        areaController.getWarehouseInfoSpaceAll();} break;
                    case 5: {
                        System.out.println("전체 구역 정보입니다.");
                        areaController.getAreaAll();
                        System.out.println("변경을 원하시는 구역 번호를 입력하세요.");
                        int areaId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("원하시는 설정 온도 번호를 입력하세요.(주의##)");
                        System.out.println("1 - Ultra-Low_Temperature(-80 ~ -60)");
                        System.out.println("2 - Frozen_Storage(-20 ~ -10)");
                        System.out.println("3 - Refrigerated_Storage(1 ~ 8)");
                        System.out.println("4 - Cool_Storage(10 ~ 15)");
                        System.out.println("5 - Room_Temperature(18 ~ 22)");
                        System.out.println("6 - Heated_Storage(30 ~ 40)");
                        int storage = scanner.nextInt();
                        scanner.nextLine();
                        areaController.UpdateAreaTemp(areaId,storage);
                    }
                    case 6: running = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        WarehouseViewImp warehouseViewImp = new WarehouseViewImp(new WareHouseControllerImp(new WareHouseServiceImp(new WareHouseRepositoryImp())),
                new AreaControllerImp(new AreaServiceImp(new AreaRepositoryImp(),new WareHouseRepositoryImp())));
        warehouseViewImp.CreateView();
    }
}

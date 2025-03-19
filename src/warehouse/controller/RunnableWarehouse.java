package warehouse.controller;

import java.util.Scanner;

public class RunnableWarehouse implements Runnable {
    Scanner scanner = new Scanner(System.in);
    WareHouseController warehouseController;

    public RunnableWarehouse(WareHouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            warehouseController.getWarehouseAll();
            System.out.println("창고관리 번호를 입력하세요");
            System.out.println("1: 창고생성 2: 도시명으로 검색 3: 창고id로 검색");
            int menuNum = scanner.nextInt();
            scanner.nextLine();
            switch (menuNum) {
                case 1: warehouseController.createWarehouse(); break;
                case 2: warehouseController.getWarehouseByAddress();break;
                case 3: warehouseController.getWarehouseById();break;
                case 4: running = false;
            }
        }
    }
}

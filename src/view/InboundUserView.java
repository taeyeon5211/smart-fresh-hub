package view;

import diconfig.DiConfig;
import inbound.controller.InboundController;
import inbound.controller.ProductController;

import java.util.Scanner;

public class InboundUserView extends InboundView implements Runnable {
    InboundController inboundController = new DiConfig().getInboundController();
    ProductController productController = new DiConfig().getProductController();
    Scanner scanner = new Scanner(System.in);
    @Override
    public void run() {
        while (true) {
            printClientMenu();
            System.out.print("화주 기능 선택: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> inboundController.handleInboundRequest(); // 입고 요청
                    case "2" -> inboundController.showInboundHistoryByBusiness(); // 내 사업체의 입고 내역 조회
                    case "3" -> inboundController.showAvailableProducts(); // 내 사업체의 등록된 제품 목록 조회
                    case "4" -> productController.handleProductRegistration(); // 제품 등록
                    case "0" -> {
                        System.out.println("화주 콘솔을 종료합니다.");
                        return;
                    }
                    default -> System.out.println(" 잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }
        }
    }
    /**
     * 화주 메뉴 출력
     */

}

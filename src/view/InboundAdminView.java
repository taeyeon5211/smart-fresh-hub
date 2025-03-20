package view;

import diconfig.DiConfig;
import inbound.controller.InboundController;
import inbound.controller.ProductController;
import inbound.controller.RevenueHistoryController;

import java.util.Scanner;

public class InboundAdminView extends InboundView implements Runnable {

    InboundController inboundController = new DiConfig().getInboundController();
    ProductController productController = new DiConfig().getProductController();
    RevenueHistoryController revenueHistoryController = new DiConfig().getRevenueHistoryController();
    Scanner scanner = new Scanner(System.in);

    // 관리자 기능
    @Override
    public void run() {
        while (true) {
            printAdminMenu();
            System.out.print("관리자 기능 선택: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> inboundController.updateInboundStatus(); // 입고 요청 승인 / 취소
                    case "2" -> inboundController.showAllPendingInboundList(); // 대기 중인 입고 요청 조회
                    case "3" -> inboundController.showInboundHistoryByBusiness(); // 특정 사업체의 입고 내역 조회
                    case "4" -> inboundController.showAllBusinesses(); // 모든 사업체 조회
                    case "5" -> inboundController.showAvailableProducts(); // 특정 사업체의 등록된 제품 목록 조회
                    case "6" -> productController.handleProductRegistration(); // 제품 등록
                    case "7" -> revenueHistoryController.viewAllRevenueHistory(); // 재고 변경 이력 조회
                    case "0" -> {
                        System.out.println("관리자 콘솔을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("⚠ 잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("⚠ 오류 발생: " + e.getMessage());
            }
        }
    }
}

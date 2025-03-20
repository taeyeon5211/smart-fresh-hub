package outbound.view;

import area.repository.AreaRepositoryImp;
import area.service.AreaService;
import area.service.AreaServiceImp;
import inbound.controller.*;
import inbound.repository.*;
import inbound.service.*;
import warehouse.repository.WareHouseRepositoryImp;

import java.util.Scanner;

public class AdminClientConsole {

    private final Scanner scanner = new Scanner(System.in);
    private final InboundController inboundController;
    private final ProductController productController;
    private final RevenueHistoryController revenueHistoryController;

    public AdminClientConsole() {
        // Repository 생성
        InboundRepository inboundRepository = new InboundRepositoryImp();
        ProductRepository productRepository = new ProductRepositoryImp();
        StorageConditionRepository storageConditionRepository = new StorageConditionRepositoryImp();
        RevenueRepository revenueRepository = new RevenueRepositoryImp();
        AreaService areaService = new AreaServiceImp(new AreaRepositoryImp(), new WareHouseRepositoryImp());

        // Service 생성
        InboundService inboundService = new InboundServiceImp(inboundRepository, productRepository, storageConditionRepository, revenueRepository, areaService);
        ProductService productService = new ProductServiceImp(productRepository);
        RevenueHistoryService revenueHistoryService = new RevenueHistoryServiceImp(revenueRepository);

        // Controller 생성 (출고 기능 제외)
        this.inboundController = new InboundControllerImp(inboundService);
        this.productController = new ProductControllerImp(productService);
        this.revenueHistoryController = new RevenueHistoryControllerImp(revenueHistoryService);
    }

    public void start() {
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

    /**
     * 🔹 화주 전용 기능 실행
     */
    private void startClientConsole() {
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

    private void printAdminMenu() {
        System.out.println("\n=================================");
        System.out.println("  [관리자 전용 기능]");
        System.out.println("1️. 입고 요청 승인 / 취소");
        System.out.println("2️. 대기 중인 입고 요청 조회");
        System.out.println("3️. 특정 사업체의 입고 내역 조회");
        System.out.println("4️. 모든 사업체 조회");
        System.out.println("5️. 특정 사업체의 등록된 제품 목록 조회");
        System.out.println("6️. 제품 등록");
        System.out.println("7️. 재고 변경 이력 조회");
        System.out.println("0️. 종료");
        System.out.println("=================================");
    }

    /**
     * 화주 메뉴 출력
     */
    private void printClientMenu() {
        System.out.println("\n=================================");
        System.out.println("[화주 전용 기능]");
        System.out.println("1️. 입고 요청");
        System.out.println("2️. 내 사업체의 입고 내역 조회");
        System.out.println("3️. 내 사업체의 등록된 제품 목록 조회");
        System.out.println("4️. 제품 등록");
        System.out.println("0️. 종료");
        System.out.println("=================================");
    }
    public static void main(String[] args) {
        AdminClientConsole adminClientConsole = new AdminClientConsole();
        adminClientConsole.start();
//        adminClientConsole.startClientConsole();

    }
}







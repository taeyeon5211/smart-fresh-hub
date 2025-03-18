package inbound.controller;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.repository.InboundRepositoryImp;
import inbound.service.InboundService;
import inbound.service.InboundServiceImp;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InboundControllerImp implements InboundController {

    private final InboundService inboundService;

    public InboundControllerImp(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    @Override
    public void handleInboundRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== 입고 요청 등록 ===");
        System.out.print("제품 ID 입력: ");
        int productId = scanner.nextInt();
        System.out.print("입고 수량 입력: ");
        int amount = scanner.nextInt();
        System.out.print("담당 관리자 ID 입력: ");
        int adminId = scanner.nextInt();

        // DTO 생성
        InboundRequestDto inboundRequestDto = InboundRequestDto.builder()
                .productId(productId)
                .amount(amount)
                .adminId(adminId)
                .build();

        // 서비스 호출 (입고 요청 생성)
        int inboundId = inboundService.createInboundRequest(inboundRequestDto);

        // 결과 출력
        if (inboundId > 0) {
            System.out.println("입고 요청 성공! 등록된 inbound_id: " + inboundId);
        } else {
            System.out.println(" 입고 요청 실패");
        }

    }

    @Override
    public void showAvailableProducts() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n 조회할 사업체 ID(business_id) 입력: ");
        int businessId = scanner.nextInt();

        // 특정 사업체가 등록한 제품 목록 조회
        Map<Integer, String> products = inboundService.getAvailableProducts(businessId);

        if (products.isEmpty()) {
            System.out.println(" 해당 사업체의 등록된 제품이 없습니다.");
        } else {
            System.out.println("\n=== 사업체 ID " + businessId + "의 제품 목록 ===");
            for (Map.Entry<Integer, String> entry : products.entrySet()) {
                System.out.println(" 제품 ID: " + entry.getKey() + " | 제품명: " + entry.getValue());
            }
        }
    }


    @Override
    public void showAllPendingInboundList() {
        System.out.println("\n=== 모든 '대기' 상태 입고 요청 목록 ===");

        List<InboundHistoryDto> pendingList = inboundService.getAllPendingInboundList();

        if (pendingList.isEmpty()) {
            System.out.println(" 대기 중인 입고 요청이 없습니다.");
        } else {
            for (InboundHistoryDto inbound : pendingList) {
                System.out.println("입고 ID: " + inbound.getInboundId() +
                        " | 제품명: " + inbound.getProductName() +
                        " | 사업체명: " + inbound.getBusinessName() +
                        " | 수량: " + inbound.getAmount() +
                        " | 요청일: " + inbound.getRequestDate() +
                        " | 상태: " + inbound.getStatus()); //  상태 추가!
            }
        }
    }


    /**
     * 특정 사업체의 입고 내역을 조회하는 메서드.
     */
    @Override
    public void showInboundHistoryByBusiness() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n조회할 사업체 ID 입력: ");
        int businessId = scanner.nextInt();

        List<InboundHistoryDto> inboundHistory = inboundService.getInboundHistoryByBusiness(businessId);

        if (inboundHistory.isEmpty()) {
            System.out.println(" 해당 사업체의 입고 내역이 없습니다.");
        } else {
            System.out.println("\n=== 사업체 ID " + businessId + "의 입고 내역 ===");
            for (InboundHistoryDto inbound : inboundHistory) {
                System.out.println("입고 ID: " + inbound.getInboundId() +
                        " | 제품명: " + inbound.getProductName() +
                        " | 수량: " + inbound.getAmount() +
                        " | 상태: " + inbound.getStatus() +
                        " | 요청일: " + inbound.getRequestDate() +
                        " | 입고일: " + (inbound.getInboundDate() != null ? inbound.getInboundDate() : "미입고"));
            }
        }
    }

    /**
     * 관리자가 입고 요청 상태를 변경하는 메서드.
     */
    @Override
    public void updateInboundStatus() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n변경할 입고 요청 ID 입력: ");
        int inboundId = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 제거
        System.out.print("변경할 상태 입력 (승인 / 취소): ");
        String status = scanner.nextLine();

        boolean isUpdated = inboundService.updateInboundStatus(inboundId, status);

        if (isUpdated) {
            System.out.println("입고 요청 상태가 정상적으로 변경되었습니다.");
        } else {
            System.out.println(" 입고 요청 상태 변경 실패.");
        }
    }

    /**
     * 모든 사업체 목록을 조회하는 메서드 (관리자용).
     */
    @Override
    public void showAllBusinesses() {
        Map<Integer, String> businesses = inboundService.getAllBusinesses();

        if (businesses.isEmpty()) {
            System.out.println(" 등록된 사업체가 없습니다.");
        } else {
            System.out.println("\n=== 등록된 사업체 목록 ===");
            for (Map.Entry<Integer, String> entry : businesses.entrySet()) {
                System.out.println("사업체 ID: " + entry.getKey() + " | 사업체명: " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        InboundService inboundService = new InboundServiceImp(new InboundRepositoryImp());

        InboundControllerImp inboundControllerImp = new InboundControllerImp(inboundService);

        inboundControllerImp.showAllBusinesses();
    }
}

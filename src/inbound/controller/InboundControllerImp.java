package inbound.controller;

import area.repository.AreaRepositoryImp;
import area.service.AreaService;
import area.service.AreaServiceImp;
import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.repository.*;
import inbound.service.InboundService;
import inbound.service.InboundServiceImp;
import inbound.service.ProductService;
import inbound.service.ProductServiceImp;
import warehouse.repository.WareHouseRepositoryImp;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InboundControllerImp implements InboundController {

    private final InboundService inboundService;
    private static final Scanner scanner = new Scanner(System.in);

    public InboundControllerImp(InboundService inboundService) {
        this.inboundService = inboundService;
    }

    /**
     * 새로운 입고 요청을 처리하는 메서드.
     */
    @Override
    public void handleInboundRequest() {
        printSeparator();
        System.out.println("[입고 요청 등록]");

        try {
            int productId = getValidIntInput(" 제품 ID 입력: ", 1, Integer.MAX_VALUE);
            int amount = getValidIntInput("입고 수량 입력: ", 1, Integer.MAX_VALUE);
            int adminId = getValidIntInput("담당 관리자 ID 입력: ", 1, Integer.MAX_VALUE);

            // DTO 생성
            InboundRequestDto inboundRequestDto = InboundRequestDto.builder()
                    .productId(productId)
                    .amount(amount)
                    .adminId(adminId)
                    .build();

            // 서비스 호출 (입고 요청 생성)
            int inboundId = inboundService.createInboundRequest(inboundRequestDto);

            // 결과 출력
            printSuccessMessage("입고 요청 성공! 등록된 inbound_id: " + inboundId);
        } catch (Exception e) {
            printErrorMessage("오류 발생: " + e.getMessage());
        }
    }

    /**
     * 특정 사업체의 등록된 제품 목록을 조회하는 메서드.
     */
    @Override
    public void showAvailableProducts() {
        printSeparator();
        System.out.println("[사업체 등록 제품 목록 조회]");

        try {
            int businessId = getValidIntInput(" 조회할 사업체 ID 입력: ", 1, Integer.MAX_VALUE);

            Map<Integer, String> products = inboundService.getAvailableProducts(businessId);

            if (products.isEmpty()) {
                printErrorMessage(" 해당 사업체의 등록된 제품이 없습니다.");
            } else {
                System.out.println("\n 사업체 ID " + businessId + "의 제품 목록");
                products.forEach((id, name) -> System.out.println(" - 제품 ID: " + id + " | 제품명: " + name));
            }
        } catch (Exception e) {
            printErrorMessage("오류 발생: " + e.getMessage());
        }
    }

    /**
     * 모든 '대기' 상태의 입고 요청 목록을 조회하는 메서드.
     */
    @Override
    public void showAllPendingInboundList() {
        printSeparator();
        System.out.println("[대기 중인 입고 요청 목록]");

        try {
            List<InboundHistoryDto> pendingList = inboundService.getAllPendingInboundList();

            if (pendingList.isEmpty()) {
                printErrorMessage("현재 대기 중인 입고 요청이 없습니다.");
            } else {
                pendingList.forEach(inbound ->
                        System.out.printf("입고 ID: %d | 제품명: %s | 사업체명: %s | 수량: %d | 요청일: %s | 상태: %s%n",
                                inbound.getInboundId(), inbound.getProductName(), inbound.getBusinessName(),
                                inbound.getAmount(), inbound.getRequestDate(), inbound.getStatus()));
            }
        } catch (Exception e) {
            printErrorMessage(" 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 특정 사업체의 입고 내역을 조회하는 메서드.
     */
    @Override
    public void showInboundHistoryByBusiness() {
        printSeparator();
        System.out.println(" [특정 사업체 입고 내역 조회]");

        try {
            int businessId = getValidIntInput("조회할 사업체 ID 입력: ", 1, Integer.MAX_VALUE);

            List<InboundHistoryDto> inboundHistory = inboundService.getInboundHistoryByBusiness(businessId);

            if (inboundHistory.isEmpty()) {
                printErrorMessage(" 해당 사업체의 입고 내역이 없습니다.");
            } else {
                inboundHistory.forEach(inbound ->
                        System.out.printf(" 입고 ID: %d | 제품명: %s | 수량: %d | 상태: %s | 요청일: %s | 입고일: %s%n",
                                inbound.getInboundId(), inbound.getProductName(), inbound.getAmount(),
                                inbound.getStatus(), inbound.getRequestDate(),
                                inbound.getInboundDate() != null ? inbound.getInboundDate() : "미입고"));
            }
        } catch (Exception e) {
            printErrorMessage("오류 발생: " + e.getMessage());
        }
    }

    /**
     * 관리자가 입고 요청 상태를 변경하는 메서드.
     */
    @Override
    public void updateInboundStatus() {
        printSeparator();
        System.out.println(" [입고 요청 상태 변경]");

        try {
            int inboundId = getValidIntInput("변경할 입고 요청 ID 입력: ", 1, Integer.MAX_VALUE);
            scanner.nextLine(); // 개행 문자 제거
            System.out.print("변경할 상태 입력 (승인 / 취소): ");
            String status = scanner.nextLine().trim();

            if (!status.equals("승인") && !status.equals("취소")) {
                throw new IllegalArgumentException("⚠ 상태는 '승인' 또는 '취소'만 입력 가능합니다.");
            }

            boolean isUpdated = inboundService.updateInboundStatus(inboundId, status);

            if (isUpdated) {
                printSuccessMessage(" 입고 요청 상태가 정상적으로 변경되었습니다.");
            } else {
                printErrorMessage(" 입고 요청 상태 변경 실패.");
            }
        } catch (Exception e) {
            printErrorMessage(" 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 모든 사업체 목록을 조회하는 메서드 (관리자용).
     */
    @Override
    public void showAllBusinesses() {
        printSeparator();
        System.out.println(" [등록된 사업체 목록 조회]");

        try {
            Map<Integer, String> businesses = inboundService.getAllBusinesses();

            if (businesses.isEmpty()) {
                printErrorMessage("등록된 사업체가 없습니다.");
            } else {
                businesses.forEach((id, name) ->
                        System.out.printf(" 사업체 ID: %d | 사업체명: %s%n", id, name));
            }
        } catch (Exception e) {
            printErrorMessage(" 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 유효한 정수 입력을 받아 반환하는 메서드.
     *
     * @param message 사용자에게 출력할 메시지
     * @param min     최소 허용값
     * @param max     최대 허용값
     * @return 유효한 정수 값
     */
    private int getValidIntInput(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value < min || value > max) {
                    throw new IllegalArgumentException("입력값이 유효한 범위를 벗어났습니다. (" + min + " ~ " + max + ")");
                }
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("숫자로 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printSeparator() {
        System.out.println("=====================================");
    }

    private void printSuccessMessage(String message) {
        System.out.println(" " + message);
    }

    private void printErrorMessage(String message) {
        System.out.println("" + message);
    }



}


package outbound.controller;

import outbound.dto.OutboundDTO;
import outbound.repository.OutboundRepository;
import outbound.repository.OutboundRepositoryImpl;
import outbound.service.OutboundService;
import outbound.service.OutboundServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OutboundControllerImpl implements OutboundController {
    OutboundService outboundService;
    private static Scanner scanner = new Scanner(System.in);
    public OutboundControllerImpl(OutboundService outboundService) {
        this.outboundService = outboundService;
    }

    @Override
    public void createOutboundRequest() {
        System.out.println("- 출고 요청");

        try {
            System.out.print("출고 수량 입력 : ");
            int outboundAmount = Integer.parseInt(scanner.nextLine());
            System.out.print("제품 ID 입력 : ");
            int productId = Integer.parseInt(scanner.nextLine());
            outboundService.createOutboundRequest(new OutboundDTO().builder().outboundAmount(outboundAmount).productId(productId).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readOutboundStatus() {
        System.out.println("- 출고 요청 조회");
        try {
            System.out.print("사업체 ID 입력 : ");
            int businessId = Integer.parseInt(scanner.nextLine());
            Optional<List<OutboundDTO>> outboundDTOS = outboundService.readOutboundStatus(businessId);
            for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
                System.out.println(outboundDTO.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readOutboundRequest() {
        System.out.println("- 출고 대기 목록 조회");
        try {
            Optional<List<OutboundDTO>> outboundDTOS = outboundService.readOutboundRequest();
            for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
                System.out.println(outboundDTO.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOutboundStatus() {
        System.out.println("- 출고 상태 변경");
        try {
            System.out.print("(승인 | 취소) 입력 : ");
            String newStatus = scanner.nextLine();
            System.out.print("관리자 ID 입력 : ");
            int adminId = Integer.parseInt(scanner.nextLine());
            LocalDateTime outboundDate = getValidLocalDateTimeInput("출고 날짜 입력 (형식: yyyy-MM-dd HH:mm): ");
            System.out.print("출고 ID 입력 : ");
            int outboundId = Integer.parseInt(scanner.nextLine());
            outboundService.updateOutboundStatus(newStatus, adminId, outboundDate, outboundId);
        } catch (RuntimeException e) {
            System.out.println("오류 : " + e.getMessage());
        }
    }

    @Override
    public void readAllOutboundRequest() {
        System.out.println("- 출고 목록 전체조회");
        try {
            Optional<List<OutboundDTO>> outboundDTOS = outboundService.readAllOutboundRequest();
            for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
                System.out.println(outboundDTO.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private LocalDateTime getValidLocalDateTimeInput(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("유효하지 않은 형식입니다. 다음 예시를 참고하세요: 2025-03-25 14:30");
            }
        }
    }

    @Override
    public void printAdminMenu() {
        System.out.println("========================");
        System.out.println("[출고 기능]");
        System.out.println("- 관리자 메뉴");
        System.out.println("1. 출고 목록 전체조회");
        System.out.println("2. 출고 대기 목록 조회");
        System.out.println("3. 출고 요청 (승인 / 취소)");
        System.out.println("0. 종료");
        System.out.println("========================");

    }

    @Override
    public void printMemberMenu() {
        System.out.println("========================");
        System.out.println("[출고 기능]");
        System.out.println("- 회원 메뉴");
        System.out.println("1. 출고 요청");
        System.out.println("2. 출고 요청 조회");
        System.out.println("0. 종료");
        System.out.println("========================");
    }
}

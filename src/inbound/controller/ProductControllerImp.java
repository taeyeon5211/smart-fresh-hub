package inbound.controller;

import inbound.dto.ProductDto;
import inbound.repository.ProductRepository;
import inbound.repository.ProductRepositoryImp;
import inbound.service.ProductService;
import inbound.service.ProductServiceImp;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * 제품 등록 기능을 구현한 컨트롤러 클래스.
 * <p>
 * 사용자 입력을 받아 제품을 등록하고, 성공 여부를 출력한다.
 */
public class ProductControllerImp implements ProductController {

    private final ProductService productService;
    private static final Scanner scanner = new Scanner(System.in);


    public ProductControllerImp(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 카테고리를 조회하고 출력하는 메서드.
     */
    private void showCategories() {
        Map<Integer, String> categories = productService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("⚠ 등록된 카테고리가 없습니다.");
            return;
        }
        System.out.println("\n📌 [등록된 카테고리 목록]");
        categories.forEach((id, name) -> System.out.println(" - " + id + ": " + name));
        printSeparator();
    }
    /**
     * 제품 등록을 처리하는 메서드.
     */
    @Override
    public void handleProductRegistration() {
        printSeparator();
        System.out.println(" [제품 등록]");

        showCategories(); // 카테고리 목록 출력

        try {
            System.out.print("제품명: ");
            String productName = scanner.nextLine();
            if (productName.trim().isEmpty()) {
                throw new IllegalArgumentException(" 제품명은 비워둘 수 없습니다.");
            }

            int productSize = getValidIntInput("제품 크기(m²): ", 1, Integer.MAX_VALUE);
            int categoryMidId = getValidIntInput("카테고리 ID: ", 1, Integer.MAX_VALUE);
            int storageTemperature = getValidIntInput(" 보관 온도(℃): ", -100, 100); // 보관 온도 범위 (-100~100)

            LocalDate expirationDate = getValidExpirationDate(); // 유통기한 입력

            int businessId = getValidIntInput("사업체 ID: ", 1, Integer.MAX_VALUE);

            // DTO 객체 생성
            ProductDto productDto = ProductDto.builder()
                    .productName(productName)
                    .productSize(productSize)
                    .categoryMidId(categoryMidId)
                    .storageTemperature(storageTemperature)
                    .expirationDate(expirationDate)
                    .businessId(businessId)
                    .build();

            // 서비스 계층을 호출하여 제품 등록 실행
            int productId = productService.registerProduct(productDto);

            // 결과 출력
            if (productId > 0) {
                printSuccessMessage(" 제품 등록 성공! 등록된 제품 ID: " + productId);
            } else {
                printErrorMessage("제품 등록 실패");
            }
        } catch (IllegalArgumentException e) {
            printErrorMessage(e.getMessage()); // 입력값 유효성 검사 예외 처리
        } catch (Exception e) {
            printErrorMessage(" 알 수 없는 오류 발생: " + e.getMessage()); // 기타 예외 처리
        }
    }




    /**
     * 유효한 정수 입력을 받아 반환하는 메서드.
     * @param message 사용자에게 출력할 메시지
     * @param min 최소 허용값
     * @param max 최대 허용값
     * @return 유효한 정수 값
     */
    private int getValidIntInput(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int value = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
                if (value < min || value > max) {
                    throw new IllegalArgumentException("⚠ 입력값이 유효한 범위를 벗어났습니다. (" + min + " ~ " + max + ")");
                }
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // 잘못된 입력 처리 후 버퍼 비우기
                System.out.println("⚠ 숫자로 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 유효한 유통기한(LocalDate)을 입력받는 메서드.
     * @return 유효한 LocalDate (없으면 null 반환)
     */
    private LocalDate getValidExpirationDate() {
        while (true) {
            System.out.print("유통기한(YYYY-MM-DD, 없으면 0 입력): ");
            String expirationInput = scanner.nextLine();
            if (expirationInput.equals("0")) {
                return null;
            }
            try {
                LocalDate date = LocalDate.parse(expirationInput);
                if (date.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("⚠ 유통기한은 현재 날짜 이후여야 합니다.");
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("유통기한 입력이 잘못되었습니다. 올바른 형식(YYYY-MM-DD)으로 입력하세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 구분선 출력
     */
    private void printSeparator() {
        System.out.println("=====================================");
    }

    /**
     * 성공 메시지 출력
     */
    private void printSuccessMessage(String message) {
        System.out.println(" " + message);
    }

    /**
     * 오류 메시지 출력
     */
    private void printErrorMessage(String message) {
        System.out.println(" " + message);
    }



    public static void main(String[] args) {
        ProductControllerImp productControllerImp = new ProductControllerImp(new ProductServiceImp(new ProductRepositoryImp()));

        productControllerImp.handleProductRegistration();
    }
}

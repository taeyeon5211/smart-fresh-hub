package inbound.controller;

import inbound.dto.ProductDto;
import inbound.repository.ProductRepository;
import inbound.repository.ProductRepositoryImp;
import inbound.service.ProductService;
import inbound.service.ProductServiceImp;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

/**
 * 제품 등록 기능을 구현한 컨트롤러 클래스.
 * <p>
 * 사용자 입력을 받아 제품을 등록하고, 성공 여부를 출력한다.
 */
public class ProductControllerImp implements ProductController {

    private final ProductService productService;


    public ProductControllerImp(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 사용자의 입력을 받아 제품을 등록하는 메서드.
     * <p>
     * - 제품명, 크기, 카테고리, 보관 온도, 유통기한, 사업체 ID를 입력받는다.
     * - 유통기한이 없으면 '0'을 입력하여 처리할 수 있다.
     * - 입력받은 데이터를 ProductDto 객체로 변환 후 서비스 계층을 호출하여 저장.
     */
    @Override
    public void handleProductRegistration() {
        Scanner scanner = new Scanner(System.in);

        Map<Integer, String> categories = productService.getAllCategories();
        System.out.println(" [등록된 카테고리 목록]");
        for (Map.Entry<Integer, String> entry : categories.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("\n=== 제품 등록 ===");
        System.out.print("제품명: ");
        String productName = scanner.nextLine();
        System.out.print("제품 크기(m2,): ");
        int productSize = scanner.nextInt();
        System.out.print("카테고리 ID: ");
        int categoryMidId = scanner.nextInt();
        System.out.print("보관 온도(℃): ");
        int storageTemperature = scanner.nextInt();
        System.out.print("유통기한(YYYY-MM-DD, 없으면 0 입력): ");
        String expirationInput = scanner.next();
        System.out.print("사업체 ID: ");
        int businessId = scanner.nextInt();

        // 유통기한이 없으면 null 처리
        LocalDate expirationDate = expirationInput.equals("0") ? null : LocalDate.parse(expirationInput);

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
            System.out.println(" 제품 등록 성공! 등록된 제품 ID: " + productId);
        } else {
            System.out.println(" 제품 등록 실패");
        }
    }

    public static void main(String[] args) {
        ProductControllerImp productControllerImp = new ProductControllerImp(new ProductServiceImp(new ProductRepositoryImp()));

        productControllerImp.handleProductRegistration();
    }
}

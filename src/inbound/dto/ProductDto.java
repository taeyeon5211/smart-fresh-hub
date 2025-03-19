package inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 제품 정보를 담는 DTO (Data Transfer Object)
 * - Controller ↔ Service 간 데이터 전달 용도
 */
@Getter
@Setter
@Builder
public class ProductDto {
    private int productId;            // 제품 ID
    private int productSize;          // 제품 크기
    private String productName;       // 제품명
    private int categoryMidId;        // 카테고리 중분류 ID
    private int storageTemperature;   // 보관 온도
    private LocalDate expirationDate; // 유통기한
    private int businessId;           // 사업체 ID
    private LocalDateTime createdAt;  // 등록 날짜


    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", productSize=" + productSize +
                ", productName='" + productName + '\'' +
                ", categoryMidId=" + categoryMidId +
                ", storageTemperature=" + storageTemperature +
                ", expirationDate=" + expirationDate +
                ", businessId=" + businessId +
                ", createdAt=" + createdAt +
                '}';
    }
}
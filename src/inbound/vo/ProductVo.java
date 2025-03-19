package inbound.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 제품 정보를 담는 VO (Value Object)
 * - DB의 `product` 테이블과 1:1 매칭
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {
    private int productId;            // 제품 ID (PK, 자동 증가)
    private int productSize;          // 제품 크기
    private String productName;       // 제품명
    private int categoryMidId;        // 카테고리 중분류 ID (FK)
    private int storageTemperature;   // 보관 온도
    private LocalDate expirationDate; // 유통기한
    private int businessId;           // 등록한 사업체 ID (FK)
    private LocalDateTime createdAt;  // 등록 날짜 (기본값: 현재 시간)


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVo productVo = (ProductVo) o;
        return productId == productVo.productId && productSize == productVo.productSize && categoryMidId == productVo.categoryMidId && storageTemperature == productVo.storageTemperature && businessId == productVo.businessId && Objects.equals(productName, productVo.productName) && Objects.equals(expirationDate, productVo.expirationDate) && Objects.equals(createdAt, productVo.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productSize, productName, categoryMidId, storageTemperature, expirationDate, businessId, createdAt);
    }

    @Override
    public String toString() {
        return "ProductVo{" +
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

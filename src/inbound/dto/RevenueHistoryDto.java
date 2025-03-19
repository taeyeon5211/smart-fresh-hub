package inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 재고 변경 이력 정보를 담는 DTO (Data Transfer Object)
 * - Controller ↔ Service 간 데이터 전달 용도
 */
@Getter
@Setter
@Builder
public class RevenueHistoryDto {
    private int revenueId;         // 재고 변경 이력 ID
    private LocalDateTime changeDate;  // 변경 일시
    private int revenueQuantity;   // 변경된 수량
    private String changeType;     // 변경 유형 (입고, 출고 등)
    private String productName;    // 제품명
    private String businessName;   // 사업체명

    @Override
    public String toString() {
        return "RevenueHistoryDto{" +
                "revenueId=" + revenueId +
                ", changeDate=" + changeDate +
                ", revenueQuantity=" + revenueQuantity +
                ", changeType='" + changeType + '\'' +
                ", productName='" + productName + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}

package inbound.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 재고 변경 이력 정보를 담는 VO (Value Object)
 * - DB ↔ Repository 간 데이터 매핑 용도
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueHistoryVo {
    private int revenueId;         // 재고 변경 이력 ID
    private LocalDateTime changeDate;  // 변경 일시
    private int revenueQuantity;   // 변경된 수량
    private String changeType;     // 변경 유형 (입고, 출고 등)
    private String productName;    // 제품명
    private String businessName;   // 사업체명

    @Override
    public String toString() {
        return "RevenueHistoryVo{" +
                "revenueId=" + revenueId +
                ", changeDate=" + changeDate +
                ", revenueQuantity=" + revenueQuantity +
                ", changeType='" + changeType + '\'' +
                ", productName='" + productName + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}

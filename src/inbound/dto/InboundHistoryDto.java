package inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InboundHistoryDto {
    private int inboundId;          // 입고 ID
    private String productName;     // 제품명 (추가)
    private String businessName;    // 사업체명 (추가)
    private int amount;             // 입고 수량
    private String status;          // 입고 상태 (승인, 대기, 취소)
    private LocalDateTime requestDate; // 입고 요청 날짜
    private LocalDateTime inboundDate; // 실제 입고 날짜
    private int adminId;            // 담당 관리자 ID


    @Override
    public String toString() {
        return "InboundHistoryDto{" +
                "inboundId=" + inboundId +
                ", productName='" + productName + '\'' +
                ", businessName='" + businessName + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", inboundDate=" + inboundDate +
                ", adminId=" + adminId +
                '}';
    }
}

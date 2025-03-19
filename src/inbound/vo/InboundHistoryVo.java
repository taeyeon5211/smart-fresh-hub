package inbound.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InboundHistoryVo {
    private final int inboundId;              // 입고 ID
    private final LocalDateTime inboundRequestDate; // 입고 요청 날짜
    private final LocalDateTime inboundDate;  // 실제 입고 날짜
    private final String inboundStatus;       // 입고 상태 (승인, 대기, 취소)
    private final int inboundAmount;          // 입고 수량
    private final String productName;         // 제품명 (추가)
    private final String businessName;        // 사업체명 (추가)
    private final int adminId;                // 담당 관리자 ID


    @Override
    public String toString() {
        return "InboundHistoryVo{" +
                "inboundId=" + inboundId +
                ", inboundRequestDate=" + inboundRequestDate +
                ", inboundDate=" + inboundDate +
                ", inboundStatus='" + inboundStatus + '\'' +
                ", inboundAmount=" + inboundAmount +
                ", productName='" + productName + '\'' +
                ", businessName='" + businessName + '\'' +
                ", adminId=" + adminId +
                '}';
    }
}


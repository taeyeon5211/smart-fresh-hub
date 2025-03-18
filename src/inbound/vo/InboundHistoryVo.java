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
    private final int inboundId;
    private final LocalDateTime inboundRequestDate;
    private final LocalDateTime inboundDate;
    private final String inboundStatus;
    private final int inboundAmount;
    private final String productName;
    private final String businessName;
    private final int adminId;

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


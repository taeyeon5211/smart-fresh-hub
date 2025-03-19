package inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InboundRequestDto {
    private int inboundId;
    private int productId;
    private int amount;
    private String status; // "승인", "대기", "취소"
    private LocalDateTime requestDate;
    private int adminId;


    @Override
    public String toString() {
        return "InboundRequestDto{" +
                "inboundId=" + inboundId +
                ", productId=" + productId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", adminId=" + adminId +
                '}';
    }
}

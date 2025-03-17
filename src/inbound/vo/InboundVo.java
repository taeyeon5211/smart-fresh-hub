package inbound.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundVo {
    private int inboundId;
    private int businessId;
    private int productId;
    private int inboundAmount;
    private String status; // 승인, 대기, 취소
    private LocalDateTime requestDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundVo inboundVo = (InboundVo) o;
        return inboundId == inboundVo.inboundId && businessId == inboundVo.businessId && productId == inboundVo.productId && inboundAmount == inboundVo.inboundAmount && Objects.equals(status, inboundVo.status) && Objects.equals(requestDate, inboundVo.requestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboundId, businessId, productId, inboundAmount, status, requestDate);
    }

    @Override
    public String toString() {
        return "InboundVo{" +
                "inboundId=" + inboundId +
                ", businessId=" + businessId +
                ", productId=" + productId +
                ", inboundAmount=" + inboundAmount +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}

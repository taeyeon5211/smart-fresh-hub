package inbound.vo;




import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundVo {
    private int inboundId;
    private int productId;
    private int inboundAmount;
    private String status; // 승인, 대기, 취소
    private LocalDateTime requestDate;
    private LocalDateTime inboundDate;
    private int adminId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundVo inboundVo = (InboundVo) o;
        return inboundId == inboundVo.inboundId && productId == inboundVo.productId && inboundAmount == inboundVo.inboundAmount && adminId == inboundVo.adminId && Objects.equals(status, inboundVo.status) && Objects.equals(requestDate, inboundVo.requestDate) && Objects.equals(inboundDate, inboundVo.inboundDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inboundId, productId, inboundAmount, status, requestDate, inboundDate, adminId);
    }

    @Override
    public String toString() {
        return "InboundVo{" +
                "inboundId=" + inboundId +
                ", productId=" + productId +
                ", inboundAmount=" + inboundAmount +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", inboundDate=" + inboundDate +
                ", adminId=" + adminId +
                '}';
    }
}

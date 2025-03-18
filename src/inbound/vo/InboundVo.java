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
    private int inboundId;          // 입고 ID
    private int productId;          // 제품 ID
    private int inboundAmount;      // 입고 수량
    private String status;          // 입고 상태 (승인, 대기, 취소)
    private LocalDateTime requestDate; // 입고 요청 날짜
    private LocalDateTime inboundDate; // 실제 입고 날짜
    private int adminId;            // 담당 관리자 ID

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

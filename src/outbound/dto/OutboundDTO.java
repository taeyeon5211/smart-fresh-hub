package outbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import outbound.vo.OutboundVO;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OutboundDTO {
    private Integer outboundId;
    private LocalDateTime outboundDate;
    private LocalDateTime outboundRequestDate;
    private String outboundStatus;
    private Integer adminId;
    private Integer outboundAmount;
    private Integer productId;

    public OutboundDTO() {};

    public OutboundDTO(OutboundVO vo) {
        this.outboundId = vo.getOutboundId();
        this.outboundDate = vo.getOutboundDate();
        this.outboundRequestDate = vo.getOutboundRequestDate();
        this.outboundStatus = vo.getOutboundStatus();
        this.adminId = vo.getAdminId();
        this.outboundAmount = vo.getOutboundAmount();
        this.productId = vo.getProductId();
    }

    @Override
    public String toString() {
        return "OutboundDTO{" +
                "outboundId=" + outboundId +
                ", outboundDate=" + outboundDate +
                ", outboundRequestDate=" + outboundRequestDate +
                ", outboundStatus='" + outboundStatus + '\'' +
                ", adminId=" + adminId +
                ", outboundAmount=" + outboundAmount +
                ", productId=" + productId +
                '}';
    }
}

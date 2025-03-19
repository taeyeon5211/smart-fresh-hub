package outbound.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import outbound.dto.OutboundDTO;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
@Builder
public class OutboundVO {
    private Integer outboundId;
    private LocalDateTime outboundDate;
    private LocalDateTime outboundRequestDate;
    private String outboundStatus;
    private Integer adminId;
    private Integer outboundAmount;
    private Integer productId;

    public OutboundVO(OutboundDTO dto) {
        this.outboundId = dto.getOutboundId();
        this.outboundDate = dto.getOutboundDate();
        this.outboundRequestDate = dto.getOutboundRequestDate();
        this.outboundStatus = dto.getOutboundStatus();
        this.adminId = dto.getAdminId();
        this.outboundAmount = dto.getOutboundAmount();
        this.productId = dto.getProductId();
    }

    @Override
    public String toString() {
        return "OutboundVO{" +
                "outboundId=" + outboundId +
                ", outboundDate=" + outboundDate +
                ", outboundRequestDate=" + outboundRequestDate +
                ", outboundStatus='" + outboundStatus + '\'' +
                ", adminId=" + adminId +
                ", outboundAmount=" + outboundAmount +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OutboundVO that = (OutboundVO) o;
        return Objects.equals(outboundId, that.outboundId) && Objects.equals(outboundDate, that.outboundDate) && Objects.equals(outboundRequestDate, that.outboundRequestDate) && Objects.equals(outboundStatus, that.outboundStatus) && Objects.equals(adminId, that.adminId) && Objects.equals(outboundAmount, that.outboundAmount) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outboundId, outboundDate, outboundRequestDate, outboundStatus, adminId, outboundAmount, productId);
    }
}

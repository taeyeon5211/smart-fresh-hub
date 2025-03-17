package inbound.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundHistoryVo {

    private int inboundId;
    private int adminId;
    private int productId;
    private int inboundAmount;
    private LocalDateTime inboundDate;
}

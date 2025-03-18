package inbound.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InboundHistoryDto {
    private int inboundId;
    private int adminId;
    private int productId;
    private int amount;
    private LocalDateTime inboundDate;
}

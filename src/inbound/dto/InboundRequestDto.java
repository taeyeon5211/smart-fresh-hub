package inbound.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InboundRequestDto {
    private int inboundId;
    private int businessId;
    private int productId;
    private int amount;
    private String status; // "승인", "대기", "취소"
    private LocalDateTime requestDate;
}

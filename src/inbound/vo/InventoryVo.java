package inbound.vo;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVo {
    private int revenueId;
    private int productId;
    private int revenueAmount;
    private int areaId; // 보관 구역
}

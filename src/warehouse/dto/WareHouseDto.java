package warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WareHouseDto {
    private int warehouseId; // 창고 아이디
    private String warehouseName; // 창고 이름
    private int warehouseSpace; // 창고 크기
    private String warehouseAddress; // 청고 주소
    private int warehouseAmount; // 창고 고정 지출 금액
}

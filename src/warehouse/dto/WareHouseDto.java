package warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import warehouse.vo.WareHouseVo;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WareHouseDto {
    private Integer warehouseId; // 창고 아이디
    private String warehouseName; // 창고 이름
    private Integer warehouseSpace; // 창고 크기
    private String warehouseAddress; // 청고 주소
    private Integer warehouseAmount; // 창고 고정 지출 금액

    public WareHouseDto() {};

    public WareHouseDto(WareHouseVo warehouseVo) {
        this.warehouseId = warehouseVo.getWarehouseId();
        this.warehouseName = warehouseVo.getWarehouseName();
        this.warehouseSpace = warehouseVo.getWarehouseSpace();
        this.warehouseAddress = warehouseVo.getWarehouseAddress();
        this.warehouseAmount = warehouseVo.getWarehouseAmount();
    }

    @Override
    public String toString() {
        return "WareHouseDto{" +
                "warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseSpace=" + warehouseSpace +
                ", warehouseAddress='" + warehouseAddress + '\'' +
                ", warehouseAmount=" + warehouseAmount +
                '}';
    }
}

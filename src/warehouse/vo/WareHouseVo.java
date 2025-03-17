package warehouse.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import warehouse.dto.WareHouseDto;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class WareHouseVo {
    private Integer warehouseId; // 창고 아이디
    private String warehouseName; // 창고 이름
    private Integer warehouseSpace; // 창고 크기
    private String warehouseAddress; // 청고 주소
    private Integer warehouseAmount; // 창고 고정 지출 금액

    public WareHouseVo(WareHouseDto dto) {
        this.warehouseId = dto.getWarehouseId();
        this.warehouseName = dto.getWarehouseName();
        this.warehouseSpace = dto.getWarehouseSpace();
        this.warehouseAddress = dto.getWarehouseAddress();
        this.warehouseAmount = dto.getWarehouseAmount();
    }

    @Override
    public String toString() {
        return "WareHouseVo{" +
                "warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseSpace=" + warehouseSpace +
                ", warehouseAddress='" + warehouseAddress + '\'' +
                ", warehouseAmount=" + warehouseAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WareHouseVo that = (WareHouseVo) o;
        return warehouseId == that.warehouseId && warehouseSpace == that.warehouseSpace && warehouseAmount == that.warehouseAmount && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(warehouseAddress, that.warehouseAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, warehouseName, warehouseSpace, warehouseAddress, warehouseAmount);
    }
}

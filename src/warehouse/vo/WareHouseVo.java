package warehouse.vo;

import lombok.Getter;

import java.util.Objects;

@Getter
public class WareHouseVo {
    private int warehouseId; // 창고 아이디
    private String warehouseName; // 창고 이름
    private int warehouseSpace; // 창고 크기
    private String warehouseAddress; // 청고 주소
    private int warehouseAmount; // 창고 고정 지출 금액


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

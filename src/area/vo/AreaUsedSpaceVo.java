package area.vo;

import lombok.Getter;

@Getter
public class AreaUsedSpaceVo {
    private Integer warehouseId;
    private Integer areaUsedSpace;

    public AreaUsedSpaceVo(Integer warehouseId, Integer areaUsedSpace) {
        this.warehouseId = warehouseId;
        this.areaUsedSpace = areaUsedSpace;
    }

    @Override
    public String toString() {
        return "AreaUsedSpaceVo{" +
                "warehouseId=" + warehouseId +
                ", areaUsedSpace=" + areaUsedSpace +
                '}';
    }
}

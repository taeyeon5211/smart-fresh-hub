package area.vo;

import lombok.Getter;

@Getter
public class ProductUsedSpaceVo {
    private Integer areaId;
    private Integer areaUsedSpace;

    public ProductUsedSpaceVo(Integer areaId, Integer areaUsedSpace) {
        this.areaId = areaId;
        this.areaUsedSpace = areaUsedSpace;
    }

    @Override
    public String toString() {
        return "AreaUsedSpaceVo{" +
                "warehouseId=" + areaId +
                ", areaUsedSpace=" + areaUsedSpace +
                '}';
    }
}

package area.vo;

import lombok.Getter;

@Getter
public class AreaVo {
    private Integer areaId;
    private Integer areaSpace;
    private Integer areaPrice;
    private Integer warehouseId;
    private Integer storage_id;

    public AreaVo(Integer areaId, Integer areaSpace, Integer areaPrice, Integer warehouseId, Integer storage_id) {
        this.areaId = areaId;
        this.areaSpace = areaSpace;
        this.areaPrice = areaPrice;
        this.warehouseId = warehouseId;
        this.storage_id = storage_id;
    }
}

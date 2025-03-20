package area.vo;

import lombok.Getter;

@Getter
public class AreaVo {
    private Integer areaId;
    private Integer areaSpace;
    private String areaCode;
    private Integer areaPrice;
    private Integer warehouseId;
    private Integer storage_id;

    public AreaVo(Integer areaId, Integer areaSpace, String areaCode, Integer areaPrice, Integer warehouseId, Integer storage_id) {
        this.areaId = areaId;
        this.areaSpace = areaSpace;
        this.areaCode = areaCode;
        this.areaPrice = areaPrice;
        this.warehouseId = warehouseId;
        this.storage_id = storage_id;
    }
}

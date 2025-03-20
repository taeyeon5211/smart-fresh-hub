package area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AreaDto {
    private Integer areaId;
    private Integer areaSpace;
    private String areaCode;
    private Integer areaPrice;
    private Integer warehouseId;
    private Integer storageId;

    private Integer available_space;

    @Override
    public String toString() {
        return "areaId: " + areaId +
                ", areaSpace: " + areaSpace +
                ", AreaCode: " + areaCode  +
                ", areaPrice: " + areaPrice +
                ", warehouseId: " + warehouseId +
                ", storageId: " + storageId +
                ", available_space: " + available_space;
    }
}

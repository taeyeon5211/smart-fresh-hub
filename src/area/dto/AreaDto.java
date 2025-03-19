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
    private String AreaCode;
    private Integer areaPrice;
    private Integer warehouseId;
    private Integer storageId;

    private Integer available_space;

    @Override
    public String toString() {
        return "AreaDto{" +
                "areaId=" + areaId +
                ", areaSpace=" + areaSpace +
                ", AreaCode='" + AreaCode + '\'' +
                ", areaPrice=" + areaPrice +
                ", warehouseId=" + warehouseId +
                ", storageId=" + storageId +
                ", available_space=" + available_space +
                '}';
    }
}

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
    private Integer areaPrice;
    private Integer warehouseId;
    private Integer storage_id;

    private Integer available_space;

    @Override
    public String toString() {
        return "AreaDto{" +
                "areaId=" + areaId +
                ", areaSpace=" + areaSpace +
                ", areaPrice=" + areaPrice +
                ", warehouseId=" + warehouseId +
                ", storage_id=" + storage_id +
                ", available_space=" + available_space +
                '}';
    }
}

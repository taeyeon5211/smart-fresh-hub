package area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarehouseInfoSpaceDto {
    private Integer warehouseId;
    private Integer space;
    private Integer availableSpace;

    @Override
    public String toString() {
        return "WarehouseInfoSpaceDto{" +
                "warehouseId=" + warehouseId +
                ", space=" + space +
                ", availableSpace=" + availableSpace +
                '}';
    }
}

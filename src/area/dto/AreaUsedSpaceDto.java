package area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AreaUsedSpaceDto {
    private Integer warehouseId;
    private Integer areaUsedSpace;

    @Override
    public String toString() {
        return "AreaUsedSpaceDto{" +
                "warehouseId=" + warehouseId +
                ", areaUsedSpace=" + areaUsedSpace +
                '}';
    }
}

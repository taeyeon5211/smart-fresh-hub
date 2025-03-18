package area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductUsedSpaceDto {
    private Integer areaId;
    private Integer areaUsedSpace;

    public ProductUsedSpaceDto(Integer areaId, Integer areaUsedSpace) {
        this.areaId = areaId;
        this.areaUsedSpace = areaUsedSpace;
    }

    @Override
    public String toString() {
        return "ProductUsedSpaceVo{" +
                "warehouseId=" + areaId +
                ", areaUsedSpace=" + areaUsedSpace +
                '}';
    }
}

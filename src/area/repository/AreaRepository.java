package area.repository;

import area.dto.AreaDto;
import area.dto.AreaUsedSpaceDto;
import area.dto.ProductUsedSpaceDto;
import area.vo.AreaUsedSpaceVo;
import java.util.List;
import java.util.Optional;

public interface AreaRepository {
    Optional<List<AreaUsedSpaceDto>> getSpaceGroupByWarehouse();
    Optional<List<AreaDto>> getAreaAll();
    Optional<ProductUsedSpaceDto> getUsedSpaceById(int areaId);
    void UpdateAreaTemp(AreaDto areaDto);
    void CreateArea(AreaDto areaDto);
}

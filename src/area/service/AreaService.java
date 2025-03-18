package area.service;

import area.dto.AreaDto;
import area.vo.AreaUsedSpaceVo;

import java.util.List;
import java.util.Optional;

public interface AreaService {
    List<AreaDto> getAreaAll();
}

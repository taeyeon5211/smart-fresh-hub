package area.service;

import area.dto.AreaDto;
import area.dto.WarehouseInfoSpaceDto;
import area.vo.AreaUsedSpaceVo;
import warehouse.dto.WareHouseDto;

import java.util.List;
import java.util.Optional;

public interface AreaService {
    /**
     * 구역별 남은공간 리스트 반환 메소드
     * @return
     */
    List<AreaDto> getAreaAll();

    /**
     * 창고별 공간, 남은공간 리스트 반환 메소드
     * @return
     */
    List<WarehouseInfoSpaceDto> getWarehouseInfoSpaceAll();

    /**
     * 구역 id, 보관온도 변경
     * @param areaDto
     */
    void UpdateAreaTemp(AreaDto areaDto);

    /**
     * 구역 정보 입력 받아서 구역 id 생성 후 create 실행
     * @param areaDto
     */
    void CreateArea(AreaDto areaDto);
}

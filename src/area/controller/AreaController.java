package area.controller;

import area.dto.AreaDto;
import area.dto.WarehouseInfoSpaceDto;

import java.util.List;

public interface AreaController {
    /**
     * 구역별 남은공간 리스트 반환 메소드
     * @return
     */
    void getAreaAll();

    /**
     * 해당 창고 아이디에 해당하는 구역 정보 출력
     * @param warehouseId
     * @return
     */
    void getAreaListByWarehouseId(int warehouseId);
    /**
     * 창고별 공간, 남은공간 리스트 반환 메소드
     * @return
     */
    void getWarehouseInfoSpaceAll();

    /**
     * 구역 id, 보관온도 변경
     * @param areaId 구역 아이디
     * @param storageId 온도 아이디
     */
    void UpdateAreaTemp(Integer areaId, Integer storageId);

    /**
     * 구역 정보 입력 받아서 구역 id 생성 후 create 실행
     * @param areaSpace 구역 공간
     * @param areaPrice 구역 가격
     * @param warehouseId 창고 아이디
     * @param storageId 보관온도 아이디
     */
    void CreateArea(Integer areaSpace, Integer areaPrice, Integer warehouseId, Integer storageId);
}

package area.service;

import area.dto.AreaDto;
import area.dto.AreaUsedSpaceDto;
import area.dto.ProductUsedSpaceDto;
import area.dto.WarehouseInfoSpaceDto;
import area.repository.AreaRepository;
import area.repository.AreaRepositoryImp;
import warehouse.dto.WareHouseDto;
import warehouse.repository.WareHouseRepository;
import warehouse.repository.WareHouseRepositoryImp;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AreaServiceImp implements AreaService{

    AreaRepository areaRepository;
    WareHouseRepository warehouseRepository;

    public AreaServiceImp(AreaRepository areaRepository, WareHouseRepository warehouseRepository) {
        this.areaRepository = areaRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * 구역별 정보 + 남은공간 반환 메서드
     * @return AreaDto 리스트 반환
     */
    @Override
    public List<AreaDto> getAreaAll() {
        return areaRepository.getAreaAll().orElseGet(ArrayList::new).stream()
                .map(x -> {
                    x.setAvailable_space(x.getAreaSpace() - areaRepository.getUsedSpaceById(x.getAreaId())
                            .orElseGet(() -> ProductUsedSpaceDto.builder()
                                    .areaId(x.getAreaId())
                                    .areaUsedSpace(0)
                                    .build()).getAreaUsedSpace());
                    return x;
                })
                .collect(Collectors.toList());
    }

    /**
     * 창고별 남은공간 확인
     * @return 창고별 공간, 남은공간, id 리스트 반환
     */
    @Override
    public List<WarehouseInfoSpaceDto> getWarehouseInfoSpaceAll() {
        List<WareHouseDto> warehouseList = warehouseRepository.getWarehouseAll().orElseGet(ArrayList::new);
        List<AreaUsedSpaceDto> areaUsedSpaceList = areaRepository.getSpaceGroupByWarehouse().orElseGet(ArrayList::new);

        // 키: 창고아이디 벨류: 창고 사용 공간
        Map<Integer, Integer> areaUsedSpaceMap = areaUsedSpaceList.stream()
                .collect(Collectors.toMap(AreaUsedSpaceDto::getWarehouseId, AreaUsedSpaceDto::getAreaUsedSpace));

        return warehouseList.stream()
                .map(warehouse -> {
                    return WarehouseInfoSpaceDto.builder()
                            .warehouseId(warehouse.getWarehouseId())
                            .space(warehouse.getWarehouseSpace())
                            .availableSpace(warehouse.getWarehouseSpace() - areaUsedSpaceMap.getOrDefault(warehouse.getWarehouseId(), 0))
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 해당 창고에 대한 구역 정보만 출력
     * @param warehouseId 원하는 창고아이디
     * @return 해당 창고의 구역정보 출력
     */
    @Override
    public List<AreaDto> getAreaListByWarehouseId(int warehouseId) {
        return getAreaAll().stream().filter(x -> x.getWarehouseId() == warehouseId).collect(Collectors.toList());
    }

    @Override
    public void UpdateAreaTemp(AreaDto areaDto) {
        areaRepository.UpdateAreaTemp(areaDto);
    }

    /**
     * 구역 코드 생성기 사용하여 구력 생성 (남은 창고 공간 고려)
     * @param areaDto
     */
    public void CreateArea(AreaDto areaDto) {
        Integer remainSpace = getWarehouseInfoSpaceAll().stream()
                .filter(x -> x.getWarehouseId().equals(areaDto.getWarehouseId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 WarehouseId를 찾을 수 없습니다."))
                        .getAvailableSpace(); //전체 창고 리스트에서 해당 구역dto가 포함된 창고의 남은공간을 가져옴

        runIfTrue(((remainSpace - areaDto.getAreaSpace()) >= 0),() ->{
            areaDto.setAreaCode(functionAreaCode.apply(areaDto));
            areaRepository.CreateArea(areaDto);
        }, "해당 구역크기가 창고의 남은 크기보다 큽니다.");
        // 남은 공간의 크기가 만들고자 하는 구역 공간의 크기보다 크면 생성, 아니면 메시지 출력
    }

    /**
     * true 면 runnable 실행, false 면 메시지 출력
     * @param status 상태값
     * @param runnable 실행함수
     * @param elseMsg 메시지
     */
    private void runIfTrue(Boolean status, Runnable runnable, String elseMsg) {
        if(status)runnable.run();
        else System.out.println(elseMsg);
    }


    /**
     * 구역 코드 생성기
     */
    final private Function<AreaDto, String> functionAreaCode = (areaDto) -> {
        Map<Integer,String> supplierTemp = new HashMap();
        supplierTemp.put(1,"A-");
        supplierTemp.put(2,"B-");
        supplierTemp.put(3,"C-");
        supplierTemp.put(4,"D-");
        supplierTemp.put(5,"E-");
        supplierTemp.put(6,"F-");

        StringBuilder areaCode = new StringBuilder();
        areaCode.append(supplierTemp.get(areaDto.getStorageId()));
        areaCode.append(areaDto.getWarehouseId());
        areaCode.append("-" + (int)(Math.random() * 1000000));
        return areaCode.toString();
    };

    public static void main(String[] args) {
        AreaServiceImp areaServiceImp = new AreaServiceImp(new AreaRepositoryImp(),new WareHouseRepositoryImp());
//        구역별 남은공간
//        areaServiceImp.getAreaAll().forEach(System.out::println);
//         창고별 남은공간
//        areaServiceImp.getWarehouseInfoSpaceAll().forEach(System.out::println);
//
//        AreaDto areaDto = AreaDto.builder()
//                .areaId(1)
//                .storageId(1)
//                .build();
//        areaServiceImp.UpdateAreaTemp(areaDto);

//        areaServiceImp.CreateArea(AreaDto.builder()
//                            .areaSpace(1)
//                            .areaPrice(1)
//                            .warehouseId(1)
//                            .storageId(1)
//                    .build());
        AreaDto areaDto = AreaDto.builder()
                .areaSpace(100)
                .warehouseId(2)
                .build();
        areaServiceImp.getAreaAll();
        System.out.println(areaServiceImp.getWarehouseInfoSpaceAll().stream()
                .filter(x -> x.getWarehouseId().equals(areaDto.getWarehouseId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 WarehouseId를 찾을 수 없습니다."))
                .getSpace());//전체 창고 리스트에서 해당 구역dto가 포함된 창고의 남은공간을 가져옴
    }
}

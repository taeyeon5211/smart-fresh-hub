package area.service;

import area.dto.AreaDto;
import area.dto.ProductUsedSpaceDto;
import area.repository.AreaRepository;
import area.repository.AreaRepositoryImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AreaServiceImp implements AreaService{

    AreaRepository areaRepository;

    public AreaServiceImp(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

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

    public static void main(String[] args) {
        AreaServiceImp areaServiceImp = new AreaServiceImp(new AreaRepositoryImp());
        areaServiceImp.getAreaAll().forEach(System.out::println);
    }
}

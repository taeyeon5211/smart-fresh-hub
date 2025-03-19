package inbound.service;


import area.dto.AreaDto;
import area.repository.AreaRepositoryImp;
import area.service.AreaService;
import area.service.AreaServiceImp;
import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.dto.ProductDto;
import inbound.exception.InboundException;
import inbound.exception.ProductException;
import inbound.repository.*;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;
import warehouse.repository.WareHouseRepositoryImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InboundServiceImp implements InboundService {

    private final InboundRepository inboundRepository;
    private final ProductRepository productRepository;
    private final StorageConditionRepository storageConditionRepository;

    private final RevenueRepository revenueRepository;

    private final AreaService areaService;

    public InboundServiceImp(InboundRepository inboundRepository, ProductRepository productRepository, StorageConditionRepository storageConditionRepository, RevenueRepository revenueRepository, AreaService areaService) {
        this.inboundRepository = inboundRepository;
        this.productRepository = productRepository;
        this.storageConditionRepository = storageConditionRepository;
        this.revenueRepository = revenueRepository;
        this.areaService = areaService;
    }


    @Override // 화주가 입고 요청
    public int createInboundRequest(InboundRequestDto inboundRequestDto) {

        InboundVo inboundVo = InboundVo.builder()
                .productId(inboundRequestDto.getProductId())
                .inboundAmount(inboundRequestDto.getAmount())
                .status("대기")
                .requestDate(LocalDateTime.now())
                .adminId(inboundRequestDto.getAdminId())
                .build();

        int inboundId = inboundRepository.insertInboundRequest(inboundVo);

        if (inboundId <= 0) {
            throw new InboundException(" 입고 요청 등록에 실패했습니다.");
        }

        return inboundId;
    }


    @Override // 관리자용 모든 등록된 사업체 id 사업체명 출력
    public Map<Integer, String> getAllBusinesses() {
        return inboundRepository.findAllBusinesses();
    }

    @Override // 관리자가 특정 사업체 아이디 제품 id랑  제품명 조회
    public Map<Integer, String> getAvailableProducts(int businessId) {
        return inboundRepository.findProductsByBusiness(businessId);
    }

    @Override
    public List<InboundHistoryDto> getAllPendingInboundList() {
        List<InboundHistoryVo> inboundList = inboundRepository.findAllPendingInboundRequests();

        if (inboundList.isEmpty()) {
            throw new InboundException(" 현재 '대기' 상태의 입고 요청이 없습니다.");
        }

        // VO → DTO 변환
        return inboundList.stream()
                .map(inbound -> InboundHistoryDto.builder()
                        .inboundId(inbound.getInboundId())
                        .productName(inbound.getProductName())
                        .businessName(inbound.getBusinessName())
                        .amount(inbound.getInboundAmount())
                        .status(inbound.getInboundStatus())
                        .requestDate(inbound.getInboundRequestDate())
                        .inboundDate(inbound.getInboundDate())
                        .adminId(inbound.getAdminId())
                        .build())
                .toList();
    }


    @Override // 특정 사업체의 입고 내역 조회
    public List<InboundHistoryDto> getInboundHistoryByBusiness(int businessId) {
        List<InboundHistoryVo> inboundList = inboundRepository.findInboundHistoryByBusiness(businessId);

        // VO → DTO 변환
        return inboundList.stream()
                .map(inbound -> InboundHistoryDto.builder()
                        .inboundId(inbound.getInboundId())
                        .productName(inbound.getProductName())
                        .businessName(inbound.getBusinessName())
                        .amount(inbound.getInboundAmount())
                        .status(inbound.getInboundStatus())
                        .requestDate(inbound.getInboundRequestDate())
                        .inboundDate(inbound.getInboundDate())
                        .adminId(inbound.getAdminId())
                        .build())
                .toList();
    }

    @Override // 관리자가 입고 요청 상태 변경
    public boolean updateInboundStatus(int inboundId, String status) {
        if (!"승인".equals(status)) {
            //
            boolean updated = inboundRepository.updateInboundStatus(inboundId, status);
            if (!updated) {
                throw new InboundException("입고 요청 상태 변경 실패!");
            }
            return true;
        }

        //  입고 요청 정보 가져오기
        Optional<InboundRequestDto> inboundOpt = inboundRepository.getInboundById(inboundId);
        if (inboundOpt.isEmpty()) {
            throw new InboundException("입고 요청을 찾을 수 없습니다.");
        }
        InboundRequestDto inbound = inboundOpt.get();

        int productId = inbound.getProductId();
        int inboundAmount = inbound.getAmount();

        //  제품 정보 가져오기
        Optional<ProductDto> productOpt = productRepository.getProductById(productId);
        if (productOpt.isEmpty()) {
            throw new ProductException("제품 정보를 찾을 수 없습니다.");
        }
        ProductDto product = productOpt.get();

        int productSize = product.getProductSize();  // 제품 크기
        int storageTemp = product.getStorageTemperature(); // 보관 온도

        // 사용 가능한 창고 구역 찾기
        List<AreaDto> availableAreas = areaService.getAreaAll().stream()  // 모든 구역(area_table) 가져오기
                .filter(area ->
                        area.getAvailable_space() >= (productSize * inboundAmount) && // 가용 공간이 충분한지 확인
                                storageConditionRepository.isStorageConditionMatch(area.getStorageId(), storageTemp) //  보관 상태가 제품 온도에 맞는지 확인
                )
                .collect(Collectors.toList()); //  조건을 만족하는 구역만 리스트로 수집

        if (availableAreas.isEmpty()) {
            throw new InboundException("보관할 수 있는 구역이 없습니다.");
        }

         // 적절한 구역 선택 (첫 번째 가능한 구역)
        AreaDto selectedArea = availableAreas.get(0);
        int areaId = selectedArea.getAreaId();

        // 재고 테이블(`revenue_table`)에 추가 또는 업데이트
        if (revenueRepository.existsRevenue(productId, areaId)) {
            //  기존 재고 업데이트
            revenueRepository.updateRevenue(productId, areaId, inboundAmount);
        } else {
            //  새로운 재고 삽입
            revenueRepository.insertRevenue(productId, areaId, inboundAmount);
        }

        //  입고 상태 업데이트
        boolean updated = inboundRepository.updateInboundStatus(inboundId, "승인");
        if (!updated) {
            throw new InboundException("입고 상태 업데이트 실패!");
        }

        return true;
    }

    @Override // 모든 입고 내역 조회
    public List<InboundHistoryDto> getAllInboundHistory() {
        List<InboundHistoryVo> inboundList = inboundRepository.findAllInboundHistory();
        return inboundList.stream()
                .map(inbound -> InboundHistoryDto.builder()
                        .inboundId(inbound.getInboundId())
                        .productName(inbound.getProductName()) // 제품명 추가
                        .businessName(inbound.getBusinessName()) // 사업체명 추가
                        .amount(inbound.getInboundAmount())
                        .status(inbound.getInboundStatus())
                        .requestDate(inbound.getInboundRequestDate())
                        .inboundDate(inbound.getInboundDate())
                        .adminId(inbound.getAdminId())
                        .build())
                .toList();

    }


    public static void main(String[] args) {
        InboundRepository inboundRepository = new InboundRepositoryImp();
        ProductRepository productRepository = new ProductRepositoryImp();
        AreaService areaService = new AreaServiceImp(new AreaRepositoryImp(),new WareHouseRepositoryImp());
        StorageConditionRepository storageConditionRepository = new StorageConditionRepositoryImp();
        RevenueRepository revenueRepository = new RevenueRepositoryImp();

        InboundService inboundService = new InboundServiceImp(
                inboundRepository,
                productRepository,
                storageConditionRepository,
                revenueRepository,
                areaService

        );
        int testInboundId = 4;

        try {
            System.out.println("입고 승인 테스트 시작!");
            inboundService.updateInboundStatus(testInboundId, "승인");
            System.out.println(" 입고 요청이 승인되었습니다.");
        } catch (InboundException | ProductException e) {
            System.err.println("오류 발생: " + e.getMessage());
        }
    }

}

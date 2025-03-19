package inbound.service;

import inbound.dto.RevenueHistoryDto;
import inbound.repository.RevenueRepository;
import inbound.repository.RevenueRepositoryImp;

import java.util.List;

public class RevenueHistoryServiceImp implements RevenueHistoryService {

    private final RevenueRepository revenueRepository;

    public RevenueHistoryServiceImp(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }
    @Override
    public List<RevenueHistoryDto> getAllRevenueHistory() {
        return revenueRepository.findAllRevenueHistory();
    }

    public static void main(String[] args) {
        //  서비스 테스트 실행
        RevenueHistoryService revenueHistoryService = new RevenueHistoryServiceImp(new RevenueRepositoryImp());
        List<RevenueHistoryDto> historyList = revenueHistoryService.getAllRevenueHistory();

        // 조회된 데이터 출력
        historyList.forEach(System.out::println);
}}

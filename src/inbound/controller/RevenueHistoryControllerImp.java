package inbound.controller;

import inbound.dto.RevenueHistoryDto;
import inbound.repository.RevenueRepositoryImp;
import inbound.service.RevenueHistoryService;
import inbound.service.RevenueHistoryServiceImp;

import java.util.List;

public class RevenueHistoryControllerImp implements RevenueHistoryController{

    private final RevenueHistoryService revenueHistoryService;

    public RevenueHistoryControllerImp(RevenueHistoryService revenueHistoryService) {
        this.revenueHistoryService = revenueHistoryService;
    }
    @Override
    public void viewAllRevenueHistory() {

        System.out.println(" [재고 변경 이력 조회]");
        try {
            List<RevenueHistoryDto> historyList = revenueHistoryService.getAllRevenueHistory();
            historyList.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("⚠ 오류 발생: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        RevenueHistoryServiceImp revenueHistoryServiceImp = new RevenueHistoryServiceImp(new RevenueRepositoryImp());
        RevenueHistoryController revenueHistoryController = new RevenueHistoryControllerImp(revenueHistoryServiceImp);

        revenueHistoryController.viewAllRevenueHistory();

    }
}

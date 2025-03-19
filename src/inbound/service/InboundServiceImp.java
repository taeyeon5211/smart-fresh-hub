package inbound.service;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.exception.InboundException;
import inbound.repository.InboundRepository;
import inbound.repository.InboundRepositoryImp;
import inbound.vo.InboundHistoryVo;
import inbound.vo.InboundVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InboundServiceImp implements InboundService {

    private final InboundRepository inboundRepository;

    public InboundServiceImp(InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
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


    }
}

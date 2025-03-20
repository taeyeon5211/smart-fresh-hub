package outbound.service;

import outbound.dto.OutboundDTO;
import outbound.repository.OutboundRepository;
import outbound.repository.OutboundRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OutboundServiceImpl implements OutboundService {
    OutboundRepository outboundRepository;

    public OutboundServiceImpl(OutboundRepository outboundRepository) {
        this.outboundRepository = outboundRepository;
    }

    @Override
    public void createOutboundRequest(OutboundDTO outboundDTO) {
        outboundRepository.createOutboundRequest(outboundDTO);
    }

    @Override
    public Optional<List<OutboundDTO>> readOutboundStatus(int businessId) {
        return outboundRepository.readOutboundStatus(businessId);
    }

    @Override
    public Optional<List<OutboundDTO>> readOutboundRequest() {
        return outboundRepository.readOutboundRequest();
    }

    @Override
    public void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId) {
        outboundRepository.updateOutboundStatus(newStatus, adminId, outboundDate, outboundId);
        if (newStatus.equals("승인")) {
            List<Integer> revenueAmounts = outboundRepository.getRevenueAmount(outboundId);
            int revenueAmount = revenueAmounts.get(0);
            int outboundAmount = revenueAmounts.get(1);
            if (revenueAmount < outboundAmount) {
                throw new RuntimeException("출고수량이 재고수량보다 더 많습니다.");
            } else {
                outboundRepository.updateRevenue(outboundId);
                if (revenueAmount == 0) {
                    outboundRepository.deleteZeroRevenue();
                }
            }
        }
    }

    @Override
    public void updateRevenue(int outboundId) {
        outboundRepository.updateRevenue(outboundId);
    }


    @Override
    public Optional<List<OutboundDTO>> readAllOutboundRequest() {
        return outboundRepository.readAllOutboundRequest();
    }

    @Override
    public void deleteZeroRevenue() {
        outboundRepository.deleteZeroRevenue();
    }

    @Override
    public List<Integer> getRevenueAmount(int outboundId) {
        return outboundRepository.getRevenueAmount(outboundId);
    }


    public static void main(String[] args) {
        OutboundRepository repo = new OutboundRepositoryImpl();
        OutboundService service = new OutboundServiceImpl(repo);
//        service.createOutboundRequest(new OutboundDTO().builder().outboundAmount(1).productId(2).build());
        service.updateOutboundStatus("승인", 1, LocalDateTime.now(), 2);

//        service.updateRevenue(11);
//        Optional<List<OutboundDTO>> outboundDTOS = service.readOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }
//        List<Integer> revenueAmount = service.getRevenueAmount(1);
//        for (Integer i : revenueAmount) {
//            System.out.println(i);
//        }

//        Optional<List<OutboundDTO>> outboundDTOS = service.readOutboundStatus(2);
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }
    }
}

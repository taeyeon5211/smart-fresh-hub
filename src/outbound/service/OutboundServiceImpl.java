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
    }

    @Override
    public void updateRevenue(int businessId) {
        outboundRepository.updateRevenue(businessId);
    }

    @Override
    public Optional<List<OutboundDTO>> readAllOutboundRequest() {
        return outboundRepository.readAllOutboundRequest();
    }

    @Override
    public void deleteZeroRevenue() {
        outboundRepository.deleteZeroRevenue();
    }


    public static void main(String[] args) {
        OutboundRepository repo = new OutboundRepositoryImpl();
        OutboundService service = new OutboundServiceImpl(repo);
        service.createOutboundRequest(new OutboundDTO().builder().outboundAmount(1).productId(2).build());
        service.updateOutboundStatus("승인", 1, LocalDateTime.now(), 11);
//
//        Optional<List<OutboundDTO>> outboundDTOS = service.readOutboundRequest();
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }


//        Optional<List<OutboundDTO>> outboundDTOS = service.readOutboundStatus(2);
//        for (OutboundDTO outboundDTO : outboundDTOS.orElse(null)) {
//            System.out.println(outboundDTO.toString());
//        }
    }
}

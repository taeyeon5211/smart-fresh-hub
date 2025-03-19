package outbound.service;

import outbound.dto.OutboundDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OutboundService {
    void createOutboundRequest(OutboundDTO outboundDTO);

    Optional<List<OutboundDTO>> readOutboundStatus(int outboundId);

    //관리자
    Optional<List<OutboundDTO>> readOutboundRequest();

    void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId);

    void updateRevenue();
}

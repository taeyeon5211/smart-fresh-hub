package outbound.controller;

import outbound.dto.OutboundDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OutboundController {
    void createOutboundRequest(OutboundDTO outboundDTO);

    Optional<List<OutboundDTO>> readOutboundStatus(int outboundId);

    Optional<List<OutboundDTO>> readOutboundRequest();

    void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId);

    void updateRevenue(int businessId);

    Optional<List<OutboundDTO>> readAllOutboundRequest();

    void deleteZeroRevenue();
}

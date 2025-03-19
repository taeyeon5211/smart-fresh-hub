package outbound.controller;

import outbound.dto.OutboundDTO;
import outbound.service.OutboundService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OutboundControllerImpl implements OutboundController {
    OutboundService outboundService;

    public OutboundControllerImpl(OutboundService outboundService) {
        this.outboundService = outboundService;
    }

    @Override
    public void createOutboundRequest(OutboundDTO outboundDTO) {

    }

    @Override
    public Optional<List<OutboundDTO>> readOutboundStatus(int outboundId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<OutboundDTO>> readOutboundRequest() {
        return Optional.empty();
    }

    @Override
    public void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId) {

    }

    @Override
    public void updateRevenue(int businessId) {

    }

    @Override
    public Optional<List<OutboundDTO>> readAllOutboundRequest() {
        return Optional.empty();
    }

    @Override
    public void deleteZeroRevenue() {

    }
}

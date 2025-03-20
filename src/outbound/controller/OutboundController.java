package outbound.controller;

import outbound.dto.OutboundDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OutboundController {
    void createOutboundRequest();

    void readOutboundStatus();

    void readOutboundRequest();

    void updateOutboundStatus();

    void readAllOutboundRequest();
}

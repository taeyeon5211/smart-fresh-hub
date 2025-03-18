package outbound.repository;

import outbound.dto.OutboundDTO;
import outbound.vo.OutboundVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * outbound repository
 */
public interface OutboundRepository {
    //회원
    void createOutboundRequest(OutboundDTO outboundDTO);

    Optional<List<OutboundDTO>> readOutboundStatus(int outboundId);

    //관리자
    Optional<List<OutboundDTO>> readOutboundRequest();

    void updateOutboundStatus(String newStatus, int adminId, LocalDateTime outboundDate, int outboundId);
}

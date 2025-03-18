package inbound.service;

import inbound.dto.InboundHistoryDto;
import inbound.dto.InboundRequestDto;
import inbound.exception.InboundException;
import inbound.repository.InboundRepository;
import inbound.repository.InboundRepositoryImp;
import inbound.vo.InboundVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InboundServiceImp implements InboundService{

    private final InboundRepository inboundRepository;

    public InboundServiceImp(InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }


    @Override
    public int createInboundRequest(InboundRequestDto inboundRequestDto) {
        return 0;
    }

    @Override
    public Map<Integer, String> getAvailableProducts(int businessId) {
        return null;
    }

    @Override
    public List<InboundVo> getInboundRequestsByBusiness(int businessId) {
        return null;
    }

    @Override
    public boolean updateInboundStatus(int inboundId, String status) {
        return false;
    }

    @Override
    public List<InboundHistoryDto> getAllInboundHistory() {
        return null;
    }

    @Override
    public List<InboundHistoryDto> getInboundHistoryByBusiness(int businessId) {
        return null;
    }

    public static void main(String[] args) {
        // 의존성 주입 (DI)
        InboundRepository repository = new InboundRepositoryImp();
        InboundService service = new InboundServiceImp(repository);


    }
}

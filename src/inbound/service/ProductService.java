package inbound.service;

import inbound.dto.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductService {


    /**
     * 새로운 제품을 등록하는 서비스 메서드.
     * 제품 정보(ProductDto)를 받아 ProductVo로 변환 후 Repository를 호출하여 DB에 저장한다.
     *
     * @param productDto 등록할 제품 정보 DTO
     * @return 생성된 제품 ID (등록 성공 시) / 실패 시 -1 반환
     */
     int registerProduct(ProductDto productDto);


    /**
     * 등록된 모든 카테고리 목록을 조회한다.
     *
     * @return 카테고리 목록 (카테고리 ID, 카테고리명) Map 형태 반환
     */
    Map<Integer, String> getAllCategories();

}

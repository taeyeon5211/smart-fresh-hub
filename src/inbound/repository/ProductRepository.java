package inbound.repository;

import inbound.dto.ProductDto;
import inbound.vo.ProductVo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 제품 관리 기능을 담당하는 Repository 인터페이스.
 * 제품 등록 및 조회 기능을 정의한다.
 */
public interface ProductRepository {

    /**
     * 새로운 제품을 등록한다.
     * @param productVo 등록할 제품 정보
     * @return 등록된 제품의 ID (product_id 반환)
     */
    int insertProduct(ProductVo productVo);


    /**
     * 등록된 모든 카테고리 목록을 조회한다.
     *
     * @return 카테고리 목록 (카테고리 ID, 카테고리명) Map 형태 반환
     */
    Map<Integer, String> findAllCategories();

    /**
     * 특정 제품 정보를 조회하는 메서드.
     * @param productId 조회할 제품 ID
     * @return 해당 제품 정보를 포함하는 Optional<ProductDto>, 없으면 Optional.empty()
     */
    Optional<ProductDto> getProductById(int productId);

}

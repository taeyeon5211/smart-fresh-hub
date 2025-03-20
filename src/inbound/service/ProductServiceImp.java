package inbound.service;

import inbound.dto.ProductDto;
import inbound.exception.ProductException;
import inbound.repository.InboundRepository;
import inbound.repository.ProductRepository;
import inbound.vo.ProductVo;

import java.util.Map;

public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int registerProduct(ProductDto productDto) {
        ProductVo productVo = ProductVo.builder()
                .productName(productDto.getProductName())
                .productSize(productDto.getProductSize())
                .categoryMidId(productDto.getCategoryMidId())
                .storageTemperature(productDto.getStorageTemperature())
                .expirationDate(productDto.getExpirationDate())
                .businessId(productDto.getBusinessId())
                .build();
        int productId = productRepository.insertProduct(productVo);
        if (productId <= 0) {
            throw new ProductException("제품 등록 실패");
        }
        return productId; // 성공적으로 등록된 product_id 반환
    }

    @Override
    public Map<Integer, String> getAllCategories() {
        return productRepository.findAllCategories(); // 예외 발생 시 그대로 던진다.
    }


}

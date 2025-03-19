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

        //  제품 등록 및 예외 처리
        try {
            int productId = productRepository.insertProduct(productVo);
            if (productId <= 0) {
                throw new ProductException(" 제품 등록 실패");
            }
            return productId;
        } catch (Exception e) {
            throw new ProductException(" 제품 등록 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<Integer, String> getAllCategories() {
        try {
            return productRepository.findAllCategories();
        } catch (Exception e) {
            throw new ProductException(" 카테고리 조회 중 오류 발생", e);
        }}


}

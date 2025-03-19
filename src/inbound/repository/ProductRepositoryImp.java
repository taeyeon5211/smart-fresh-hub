package inbound.repository;

import inbound.dto.ProductDto;
import inbound.vo.ProductVo;
import object.ObjectIo;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 제품 관련 DB 연동을 처리하는 Repository 구현체.
 */
public class ProductRepositoryImp implements ProductRepository {

    private final Connection conn = ObjectIo.getConnection();

    /**
     * 새로운 제품을 등록한다.
     *
     * @param productVo 등록할 제품 정보
     * @return 등록된 제품의 ID (product_id 반환)
     */
    @Override
    public int insertProduct(ProductVo productVo) {
        String sql = "INSERT INTO product (product_name, product_size, category_mid_id, storage_temperature, expiration_date, business_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, productVo.getProductName());
            pstmt.setInt(2, productVo.getProductSize());
            pstmt.setInt(3, productVo.getCategoryMidId());
            pstmt.setInt(4, productVo.getStorageTemperature());
            pstmt.setDate(5, Date.valueOf(productVo.getExpirationDate()));
            pstmt.setInt(6, productVo.getBusinessId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 product_id 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환


    }

    @Override
    public Map<Integer, String> findAllCategories() {
        String sql = "SELECT category_mid_id, category_name FROM c_mid_level";
        Map<Integer, String> categoryMap = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categoryMap.put(rs.getInt("category_mid_id"), rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryMap;
    }

    @Override
    public Optional<ProductDto> getProductById(int productId) {
        String sql = """
        SELECT product_id, product_size, product_name, category_mid_id, 
               storage_temperature, expiration_date, business_id, created_at
        FROM product
        WHERE product_id = ?
    """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(ProductDto.builder()
                        .productId(rs.getInt("product_id"))
                        .productSize(rs.getInt("product_size"))
                        .productName(rs.getString("product_name"))
                        .categoryMidId(rs.getInt("category_mid_id"))
                        .storageTemperature(rs.getInt("storage_temperature"))
                        .expirationDate(rs.getDate("expiration_date").toLocalDate())
                        .businessId(rs.getInt("business_id"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("제품 조회 중 DB 오류 발생", e);
        }

        return Optional.empty(); // 제품이 없으면 빈 값 반환
    }

    public static void main(String[] args) {
        ProductRepositoryImp productRepositoryImp1 = new ProductRepositoryImp();

        Optional<ProductDto> productById = productRepositoryImp1.getProductById(1);
        System.out.println(productById);

    }
}


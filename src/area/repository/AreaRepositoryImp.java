package area.repository;

import area.dto.AreaDto;
import area.dto.AreaUsedSpaceDto;
import area.dto.ProductUsedSpaceDto;
import area.vo.AreaUsedSpaceVo;
import area.vo.AreaVo;
import area.vo.ProductUsedSpaceVo;
import object.ObjectIo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AreaRepositoryImp implements AreaRepository {

    /**
     * 창고별 사용공간 리스트 구해오는 메서드
     * @return 창고id, 사용공간 리스트 반환
     */
    @Override
    public Optional<List<AreaUsedSpaceDto>> getSpaceGroupByWarehouse() {
        List<AreaUsedSpaceDto> list = new ArrayList<>();
        String sql = "select warehouse_id, sum(area_space) as used_space from area_table group by warehouse_id";
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                AreaUsedSpaceVo areaUsedSpaceVo = new AreaUsedSpaceVo(resultSet.getInt("warehouse_id"),
                        resultSet.getInt("used_space"));
                list.add(AreaUsedSpaceDto.builder()
                        .warehouseId(areaUsedSpaceVo.getWarehouseId())
                        .areaUsedSpace(areaUsedSpaceVo.getAreaUsedSpace())
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(list);
    }

    /**
     * 구역 전체 테이블 반환 메서드 (dto에서 마지막 파리미터 남은 공간 값은 비어있음)
     * @return 구역 리스트 반환
     */
    @Override
    public Optional<List<AreaDto>> getAreaAll() {
        List<AreaDto> list = new ArrayList<>();
        String sql = "select * from area_table";

        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                AreaVo areaVo = new AreaVo(resultSet.getInt("area_id"),
                        resultSet.getInt("area_space"),
                        resultSet.getString("area_code"),
                        resultSet.getInt("area_price"),
                        resultSet.getInt("warehouse_id"),
                        resultSet.getInt("storage_id"));

                list.add(AreaDto.builder()
                        .areaId(areaVo.getAreaId())
                        .areaSpace(areaVo.getAreaSpace())
                        .areaPrice(areaVo.getAreaPrice())
                        .warehouseId(areaVo.getWarehouseId())
                        .storageId(areaVo.getStorage_id())
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(list);
    }

    /**
     * 구역 아이디 받아서 사용중인 공간 을 찾기위해 ProductUsedSpaceDto 가져오는 메서드 (service )
     * @param areaId
     * @return
     */
    @Override
    public Optional<ProductUsedSpaceDto> getUsedSpaceById(int areaId) {
        String sql = "select area_id, sum(product_size * revenue_amount) as used_space\n" +
                "from (select * from product where product_id in (select product_id from inbound_table where inbound_status = '승인' )) as available_product\n" +
                "    inner join revenue_table on revenue_table.product_id = available_product.product_id\n" +
                "where area_id = ?\n" +
                "group by area_id;";
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, areaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ProductUsedSpaceVo productUsedSpaceVo = new ProductUsedSpaceVo(resultSet.getInt("area_id"),
                        resultSet.getInt("used_space"));
                ProductUsedSpaceDto productUsedSpaceDto = ProductUsedSpaceDto.builder()
                        .areaId(productUsedSpaceVo.getAreaId())
                        .areaUsedSpace(productUsedSpaceVo.getAreaUsedSpace())
                        .build();
                return Optional.of(productUsedSpaceDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * 아이디, 보관상태 아이디 받아서 업데이트
     * @param areaDto
     */
    @Override
    public void UpdateAreaTemp(AreaDto areaDto) {
        String sql = "update area_table set storage_id = ? where area_id = ?;";
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, areaDto.getStorageId());
            preparedStatement.setInt(2,areaDto.getAreaId());
            Optional<Integer> row = Optional.of(preparedStatement.executeUpdate());
            row.filter(x -> x > 0).orElseThrow(() -> new SQLException("구역 보관온도 업데이트 실패(id값을 확인하세요)"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateArea(AreaDto areaDto) {
        String sql = "insert into area_table (area_space, area_code , area_price, warehouse_id, storage_id) values (?, ?, ?, ?,?);";
        try(Connection connection = ObjectIo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, areaDto.getAreaSpace());
            preparedStatement.setString(2, areaDto.getAreaCode());
            preparedStatement.setInt(3,areaDto.getAreaPrice());
            preparedStatement.setInt(4,areaDto.getWarehouseId());
            preparedStatement.setInt(5,areaDto.getStorageId());
            Optional<Integer> row = Optional.of(preparedStatement.executeUpdate());
            row.filter(x -> x > 0).orElseThrow(() -> new SQLException("창고 추가 sql문 실행 실패"));
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

//        public static void main(String[] args) {
//        AreaRepositoryImp areaRepositoryImp = new AreaRepositoryImp();
//        areaRepositoryImp.getSpaceGroupByWarehouse().orElse(new ArrayList<>()).forEach(System.out::println);
//        areaRepositoryImp.getAreaAll().orElse(new ArrayList<>()).forEach(System.out::println);
//        System.out.println("zz");
//        System.out.println(areaRepositoryImp.getUsedSpaceById(10));
//            areaRepositoryImp.CreateArea(AreaDto.builder()
//                            .areaSpace(1)
//                            .AreaCode("A")
//                            .areaPrice(1)
//                            .warehouseId(1)
//                            .storageId(1)
//                    .build());
//    }
}

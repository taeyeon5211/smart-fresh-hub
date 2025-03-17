package warehouse.repository;

import object.ObjectIo;
import warehouse.dto.WareHouseDto;
import warehouse.vo.WareHouseVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 창고 repository
 */
public class WareHouseRepositoryImp implements WareHouseRepository {

    /**
     * dto -> vo 변환 함수
     */
    private final Function<WareHouseDto, WareHouseVo> changeToVo = WareHouseVo::new;
    /**
     * vo -> dto 변환 함수
     */
    private final Function<WareHouseVo, WareHouseDto> changeToDto = WareHouseDto::new;

    /**
     * 창고 생성 메서드
     * @param warehouseDto 입력받은 창고 dto
     */
    @Override
    public void createWarehouse(WareHouseDto warehouseDto) {
        WareHouseVo warehouseVo = changeToVo.apply(warehouseDto);
        String sql ="INSERT INTO warehouse_table (warehouse_name, warehouse_space, warehouse_address, warehouse_amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            preparedStatement.setString(1, warehouseVo.getWarehouseName());
            preparedStatement.setInt(2, warehouseVo.getWarehouseSpace());
            preparedStatement.setString(3, warehouseVo.getWarehouseAddress());
            preparedStatement.setInt(4, warehouseVo.getWarehouseAmount());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                int warehouse_id = generatedKeys.getInt(1);
                System.out.println("warehouse_id = " + warehouse_id);
            }
            generatedKeys.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 창고 전체 테이블 반환 메서드
     * @return 창고 리스트 Optional 반환
     */
    @Override
    public Optional<List<WareHouseDto>> getWarehouseAll() {
        String sql = "SELECT * FROM warehouse_table";
        List<WareHouseDto> warehouses = new ArrayList<>();

        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                WareHouseVo wareHouseVo = WareHouseVo.builder()
                        .warehouseId(resultSet.getInt("warehouse_id"))
                        .warehouseName(resultSet.getString("warehouse_name"))
                        .warehouseSpace(resultSet.getInt("warehouse_space"))
                        .warehouseAddress(resultSet.getString("warehouse_address"))
                        .warehouseAmount(resultSet.getInt("warehouse_amount"))
                        .build();
                warehouses.add(changeToDto.apply(wareHouseVo));
            }
            return Optional.of(warehouses);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 도시명으로 해당하는 전체 창고 리스트 조회
     * @param warehouseAddress 도시명 입력
     * @return 해당 창고 리스트
     */
    @Override
    public Optional<List<WareHouseDto>> getWarehouseByAddress(String warehouseAddress) {
        List<WareHouseDto> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse_table WHERE warehouse_address Like ?";
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, "%" + warehouseAddress + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                WareHouseVo wareHouseVo = WareHouseVo.builder()
                        .warehouseId(resultSet.getInt("warehouse_id"))
                        .warehouseName(resultSet.getString("warehouse_name"))
                        .warehouseSpace(resultSet.getInt("warehouse_space"))
                        .warehouseAddress(resultSet.getString("warehouse_address"))
                        .warehouseAmount(resultSet.getInt("warehouse_amount"))
                        .build();
                warehouses.add(changeToDto.apply(wareHouseVo));
            }
            resultSet.close();
            return Optional.of(warehouses);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 창고 아이디로 해당하는 창고 조회
     * @param warehouseId 창고 아이디
     * @return 해당 창고 dto 반환
     */
    @Override
    public Optional<WareHouseDto> getWarehouseById(Integer warehouseId) {
        String sql = "SELECT * FROM warehouse_table WHERE warehouse_id = ?";
        WareHouseDto warehouseDto = null;
        try (Connection connection = ObjectIo.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setInt(1, warehouseId);
             ResultSet resultSet = preparedStatement.executeQuery();
             if(resultSet.next()){
                 WareHouseVo wareHouseVo = WareHouseVo.builder()
                         .warehouseId(resultSet.getInt("warehouse_id"))
                         .warehouseName(resultSet.getString("warehouse_name"))
                         .warehouseSpace(resultSet.getInt("warehouse_space"))
                         .warehouseAddress(resultSet.getString("warehouse_address"))
                         .warehouseAmount(resultSet.getInt("warehouse_amount"))
                         .build();
                 warehouseDto = changeToDto.apply(wareHouseVo);
             }
             resultSet.close();
             return Optional.of(warehouseDto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//        public static void main(String[] args) {
//        WareHouseRepositoryImp wareHouseRepositoryImp = new WareHouseRepositoryImp();
//        WareHouseDto warehouseDto = WareHouseDto.builder()
//                .warehouseName("test")
//                .warehouseSpace(1)
//                .warehouseAddress("test")
//                .warehouseAmount(1)
//                .build();
//        wareHouseRepositoryImp.createWarehouse(warehouseDto);
//        wareHouseRepositoryImp.getWarehouseAll().orElse(new ArrayList<>()).stream().forEach(System.out::println);
//        wareHouseRepositoryImp.getWarehouseByAddress("서울시").orElse(new ArrayList<>()).stream().forEach(System.out::println);
//        System.out.println(wareHouseRepositoryImp.getWarehouseById(2));
//    }
}

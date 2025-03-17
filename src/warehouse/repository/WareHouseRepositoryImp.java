package warehouse.repository;

import object.ObjectIo;
import warehouse.dto.WareHouseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class WareHouseRepositoryImp implements WareHouseRepository {
    @Override
    public void createWarehouse(WareHouseDto warehouseDto) {
//        String sql = "INSERT INTO warehouse_table (warehouse_name, warehouse_space, warehouse_address, warehouse_amount) VALUES (?, ?, ?, ?)";
//
        Connection connection = ObjectIo.getConnection();
        try {
            System.out.println(Class.forName("com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

//        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//

    }

    public static void main(String[] args) {
        WareHouseRepositoryImp wareHouseRepositoryImp = new WareHouseRepositoryImp();
        wareHouseRepositoryImp.createWarehouse(new WareHouseDto());
    }
}

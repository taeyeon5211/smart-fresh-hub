package object;

import common.Ignore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ObjectIo { // 싱글톤

    private ObjectIo(){}

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Ignore.URL.getText(),"Employee", Ignore.PASSWORD.getText());
        }  catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

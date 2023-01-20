package peaksoft.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    public final static  String url = "jdbc:postgresql://localhost:5432/azimbek07";
    public final static  String username = "postgres";
    public final static  String password = "1234";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(url,username,password);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}

package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {

    private static Connection conn;
    private static String url = "jdbc:mysql://localhost:3306/liga";
    private static String username = "root";
    private static String password = "Msierradieg0";

    public static Connection getConnection()
    {
        if(conn == null)
        {
            try{
                conn = DriverManager.getConnection(url, username, password);
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        return conn;
    }
}

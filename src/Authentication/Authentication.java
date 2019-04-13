package Authentication;

import java.sql.*;

public class Authentication {
    public static Connection connect() throws SQLException{
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Stationary-DB","root","Vatsal@1999");

        return conn;
    }
}

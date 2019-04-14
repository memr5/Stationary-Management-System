package Authentication;

import java.sql.*;

public class Authentication {

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/stationary-db","root","memr5");
    }

    public static boolean isAdmin(String user_name) throws Exception{
        Connection connection = Authentication.connect();
        Statement statement = connection.createStatement();
        ResultSet rst = statement.executeQuery("SELECT type from user where user_name = '" + user_name + "'");
        rst.next();
        if(rst.getInt(1) == 0){
            return false;
        }
        return true;
    }
}

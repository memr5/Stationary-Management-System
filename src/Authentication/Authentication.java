package Authentication;

import java.sql.*;

public class Authentication {

    public Connection connect() throws Exception{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/stationary-db","root","memr5");
    }

    public static boolean isAdmin(String user_name) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        ResultSet rst = statement.executeQuery("SELECT type from user where user_name = '" + user_name + "'");
        //connection.close();
        if(rst.next() && rst.getInt(1) == 0){
            //connection.close();
            return false;
        }
        //connection.close();
        return true;
    }
}

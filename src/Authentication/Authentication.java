package Authentication;

import java.sql.*;

public class Authentication {
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Stationary-DB","root","memr5");
    }

    public static boolean isAdmin(String user_name){
        //Add code to check if user_name is of Admin or not
        return true;
    }
}

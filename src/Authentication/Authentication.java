
/////////////////////////////////////////////////////////////////////////////////////
//                                                                                 //
//                           STATIONARY-MANAGEMENT-SYSTEM                          //
//                                                                                 //
//                           Authors : ✔ @Vatsalparsaniya                          //
//                                     ✔ @memr5                                    //
//                                                                                 //
/////////////////////////////////////////////////////////////////////////////////////

package Authentication;

import java.sql.*;

public class Authentication {


    public Connection connect() throws Exception{

        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/stationary-db","root","Vatsal@1999");
    }

    public static boolean isAdmin(String user_name) throws Exception{

        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        ResultSet rst = statement.executeQuery("SELECT type from user where user_name = '" + user_name + "'");

        return !(rst.next() && rst.getInt(1) == 0);
    }
}

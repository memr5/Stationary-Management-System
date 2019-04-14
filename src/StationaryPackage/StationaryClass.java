package StationaryPackage;

import Authentication.Authentication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StationaryClass implements Pages{

    private String user_name;
    private int user_id;

    public void Banner(){
        System.out.print  ("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n\n" +
                           "---------------------STATIONARY-MANAGEMENT-SYSTEM--------------------\n\n" +
                           "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n\n");
    }

    public void WelcomePage(){
        this.Banner();
        System.out.print  ("\t\t1. LOGIN\n" +
                           "\t\t2. SIGN UP\n" +
                           "\t\t3. EXIT\n\n" +
                           "\t\tEnter your choice : ");
    }

    public boolean Login() throws SQLException {

        String[] credentials = this.getCredentials();
        String user_name = credentials[0];
        String password = credentials[1];

        Connection conn = Authentication.connect();
        ResultSet rst;
        try (Statement st = conn.createStatement()) {
            rst = st.executeQuery("SELECT password FROM user WHERE user_name = '" + user_name + "'");
            if(rst.next()){
                if(rst.getString(1).equals(password)) {
                    this.user_name = user_name;
                    return true;
                }
                System.out.println("WRONG PASSWORD!");
            }else{
                System.out.println("SORRY WRONG USER NAME OR PASSWORD ");
            }
        }catch (Exception ex) {
            System.out.println("Exception at Search user : \n" + ex);
        }

        return false;
    }

    public boolean Sign_up() throws SQLException{

        String[] credentials = this.getCredentials();
        String user_name = credentials[0];
        String password = credentials[1];

        //debug
        System.out.println(user_name + " " + password);

        Connection conn = Authentication.connect();
        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery("SELECT * FROM user WHERE user_name = \'" + user_name + "\'");

        if (rst.next()) {
            System.out.println("User Name already exists!");
            return false;
        } else {
            this.user_name = user_name;
            st.executeQuery("INSERT INTO user(type,password,user_name) VALUES (" + 0 + "," + password + "," + user_name + ")");
            return true;
        }
    }

    private String[] getCredentials(){

        Scanner in = new Scanner(System.in);
        System.out.print("\t\tUser_name : ");
        String user_name = in.nextLine();
        System.out.print("\t\tPassword : ");
        String password = in.nextLine();
        in.close();

        String[] credentials = new String[2];
        credentials[0] = user_name;
        credentials[1] = password;

        return credentials;
    }

    public String getUser_name(){
        return this.user_name;
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id()throws Exception{
        Connection conn = Authentication.connect();
        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery("SELECT user_id from user where user_name = " + "\"" +
                user_name + "\"");
        rst.next();
        user_id = rst.getInt(1);
    }
}

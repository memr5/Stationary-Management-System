package StationaryPackage;

import Authentication.Authentication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StationaryClass implements Pages{

    private static String user_name;
    private static int user_id;

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

    public int Login() throws Exception {

        String[] credentials = this.getCredentials();
        String user_name = credentials[0];
        String password = credentials[1];

        Connection connection = (new Authentication()).connect();
        ResultSet rst;
        try (Statement statement = connection.createStatement()) {
            rst = statement.executeQuery("SELECT password FROM user WHERE user_name = '" + user_name + "'");

            if(rst.next()){
                if(rst.getString(1).equals(password)) {
                    StationaryClass.user_name = user_name;
                    return 1;
                }
                System.out.println("WRONG PASSWORD!");
            }else{
                System.out.println("SORRY WRONG USER NAME OR PASSWORD ");
            }
        }catch (Exception ex) {
            System.out.println("Exception at Search user : \n" + ex);
        }

        return 0;
    }

    public int Sign_up() throws Exception{

        String[] credentials = this.getCredentials();
        String user_name = credentials[0];
        String password = credentials[1];

        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        ResultSet rst = statement.executeQuery("SELECT * FROM user WHERE user_name = \'" + user_name + "\'");

        if (rst.next()) {
            System.out.println("User Name already exists!");
            return 0;
        } else {
            this.user_name = user_name;
            statement.executeUpdate("INSERT INTO user(type,password,user_name) VALUES (" + 0 + ",\"" + password + "\",\"" + user_name + "\")");
            return 1;
        }
    }

    private String[] getCredentials(){

        Scanner in = new Scanner(System.in);
        System.out.print("\n\t\tUser_name : ");
        String user_name = in.nextLine();
        System.out.print("\t\tPassword : ");
        String password = in.nextLine();

        String[] credentials = new String[2];
        credentials[0] = user_name;
        credentials[1] = password;

        return credentials;
    }

    static String getUser_name(){
        return user_name;
    }

    static int getUser_id(){
        return user_id;
    }

    static void setUser_id() throws Exception{
        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        ResultSet rst = statement.executeQuery("SELECT user_id from user where user_name = " + "\"" +
                user_name + "\"");
        rst.next();
        user_id = rst.getInt(1);
    }
}

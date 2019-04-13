package StationaryPackage;

import Authentication.Authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String [] args){

        Scanner in = new Scanner(System.in);
        StationaryClass obj = new StationaryClass();
        obj.WelcomePage();
        boolean b = false;
        do {
            try {
                int choice = in.nextInt();
                switch (choice) {
                    case 1:
                        b = obj.Login();
                        break;

                    case 2:
                        b = obj.Sign_up();
                        break;

                    case 3:
                        System.exit(0);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }while(!b);
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = Authentication.connect();
            Statement statement = connection.createStatement();

            do{
                obj.Menu();
                int choice = in.nextInt();
                switch (choice){
                    case 1:

                }
            }while (true);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

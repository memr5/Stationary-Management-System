package StationaryPackage;

import Authentication.Authentication;

import java.util.Scanner;

public class Main {

    public static void main(String [] args){

        Scanner in = new Scanner(System.in);
        StationaryClass obj = new StationaryClass();

        boolean b = false;
       do {
            try {
                obj.WelcomePage();
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
            obj.setUser_id();
            Class.forName("com.mysql.cj.jdbc.Driver");

            if(Authentication.isAdmin(obj.getUser_name())) {
                Admin admin = new Admin();
                do {
                    admin.Menu();
                    int choice = in.nextInt();
                    switch (choice) {
                        case 1:

                    }
                } while (true);
            }
            else{
                Users user = new Users();
                do{
                    user.Menu();
                    int choice = in.nextInt();
                    switch (choice){
                        case 1:
                            user.purchase_item();
                            break;
                        case 2:
                            user.orders();
                        case 3:
                            user.showCart();
                            break;
                        case 4:
                            System.exit(0);
                    }
                }while (true);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

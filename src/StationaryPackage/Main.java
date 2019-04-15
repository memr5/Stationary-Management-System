package StationaryPackage;

import Authentication.Authentication;

import java.util.Scanner;

public class Main {

    public static void main(String [] args) {

        StationaryClass obj = new StationaryClass();
        Scanner in = new Scanner(System.in);
        int b = 0;
        int choice;
        try{
            do {
                obj.WelcomePage();
                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        b = obj.Login();
                        break;
                    case 2:
                        b = obj.Sign_up();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("\nEnter valid choice!\n");
                }
            } while (b != 1);
        }catch (Exception ex) {
            System.out.println(ex.toString());
        }

        try {
            StationaryClass.setUser_id();

            if(Authentication.isAdmin(StationaryClass.getUser_name())) {
                Admin admin = new Admin();
                do {
                    admin.Menu();
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            admin.purchase_item();
                            break;
                        case 2:
                            admin.orders();
                            break;
                        case 3:
                            admin.showCart();
                            break;
                        case 4:
                        case 5:
                        case 6:
                            System.exit(0);
                        default:
                            System.out.println("\nEnter valid choice!\n");
                    }
                } while (true);
            }
            else{
                Users user = new Users();
                do{
                    user.Menu();
                    choice = in.nextInt();
                    switch (choice){
                        case 1:
                            user.purchase_item();
                            break;
                        case 2:
                            user.orders();
                            break;
                        case 3:
                            user.showCart();
                            break;
                        case 4:
                            System.exit(0);
                        default:
                            System.out.println("\nEnter valid choice!\n");
                    }
                }while (true);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
}

package StationaryPackage;

import Authentication.Authentication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PrimitiveIterator;
import java.util.Scanner;


public class Customer {
    static int index = 1;
    public  void show_product_type(){
        Connection con = null;
        try {
            con = Authentication.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rst;
        try (Statement st = con.createStatement()) {
            rst = st.executeQuery("SELECT DISTINCT type_of_product FROM product ");
            while (rst.next()) System.out.println(rst.getString(1));
        }catch (Exception ex){
            System.out.println(ex);
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void  purchase_item() {
        float After_Discount_Price = 0;
        int buy_quantity = 0;
        int break_loop1 = 0;
        int break_loop2 = 0;
        int break_loop3 = 0;
        int break_loop4 = 0;
        int item_Number = 0;
        do {

            this.show_product_type();
            System.out.println("Which type of product you want :");
            Scanner sc = new Scanner(System.in);
            String product_type = sc.nextLine();
            String upper_product_type = product_type.toUpperCase();
            Connection con = null;
            try {
                con = Authentication.connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rst;
            try (Statement st = con.createStatement()) {
                rst = st.executeQuery("SELECT * FROM product WHERE type_of_product = '" + upper_product_type + "'");
                index = 1;
                do{
                    while (rst.next()) {
                        System.out.println(index + ")    " + rst.getString(3) + "[" + rst.getInt(8) + "]");
                        System.out.println("        Selling Price : " + rst.getInt(5));
                        System.out.println("        Discount : " + rst.getInt(6));
                        After_Discount_Price = (float) rst.getInt(5) - (float) (rst.getInt(5) * rst.getInt(6)) / 100;
                        System.out.println("        After Discount Price : " + After_Discount_Price + "\n\n");
                    }
                    rst.beforeFirst();
                    System.out.println("Select your choice : \n      1) Select item\n      2) Back");
                    int choice_1 = sc.nextInt();
                    do {
                        switch (choice_1) {
                            case 1:
                                System.out.println("Select item : ");
                                item_Number = sc.nextInt();
                                break_loop4 = 1;
                                break;
                            case 2:
                                break_loop3 = 1;
                                break;
                            default:
                                System.out.println("please enter valid number");
                        }
                    } while (break_loop3 != 1 || break_loop4 != 1);



                    if(break_loop3 == 1){
                        break;
                    }
                    for (int i = 0; i < item_Number; i++) {
                        rst.next();
                    }
                    System.out.println("You Select : \n");
                    System.out.println(index + ")    " + rst.getString(3) + "[" + rst.getInt(8) + "]");
                    System.out.println("        Selling Price : " + rst.getInt(5));
                    System.out.println("        Discount : " + rst.getInt(6));
                    System.out.println("        After Discount Price : " + After_Discount_Price + "\n\n");
                    System.out.println("        Specification : " + rst.getString(7));
                System.out.println("\n      1) Add TO Cart \n       2) back\n\n      Enter Your answer :");
                int selection = sc.nextInt();
                do {
                    switch (selection) {
                        case 1:
                            //customer buy this item
                            do {
                                System.out.println("How Many Quantity you want to Buy :");
                                buy_quantity = sc.nextInt();
                                if (buy_quantity > rst.getInt(8)) {
                                    System.out.println("Sorry We have only " + rst.getInt(8) + " Quantity");
                                }
                            } while (buy_quantity > rst.getInt(8));
                            addToCart(rst.getInt(1), buy_quantity);
                            System.out.println("Add To Cart SuccessFully......\n");
                            break_loop2 = 1;
                            break;
                        case 2:
                            //customer want to go Back
                            break_loop1 = 1;
                            break;
                        default:
                            System.out.println("Please Select appropriate Option");
                    }
                } while (break_loop1 != 1 || break_loop2 != 1);

            } while (break_loop2 != 1  ) ;

        }catch(Exception ex){
            System.out.println(ex);
        }
    }while(break_loop2 !=1 );

    }
    public static void main(String[] arg){

    }
}

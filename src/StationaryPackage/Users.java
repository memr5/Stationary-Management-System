package StationaryPackage;

import Authentication.Authentication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class Users {

    private Connection connection;
    private Statement statement;
    private ArrayList<Integer> cart;
    private ArrayList<Integer> quantity;
    private double cartValue;

    Users() throws SQLException {
       this.connection = Authentication.connect();
       this.statement = this.connection.createStatement();
       this.cart = new ArrayList<>();
       this.quantity = new ArrayList<>();
       this.cartValue = 0;
    }

    void Menu(){
        System.out.print   ("---------------------------------MENU--------------------------------\n\n" +
                "\t\t1. Search\n" +
                "\t\t2. Your Orders\n" +
                "\t\t3. Cart\n" +
                "\t\t4. Exit" +
                "---------------------------------------------------------------------\n\n" +
                "\t\tEnter your choice : ");
    }

    void orders()throws Exception{
        int user_id = (new StationaryClass()).getUser_id();
        ResultSet rst = statement.executeQuery("SELECT time_stamp,product_id,quantity from history where user_id = " +
                "\"" + user_id + "\"");
        while (rst.next()){
            ResultSet pname = statement.executeQuery("SELECT product_name from product where product_id = " +
                    "\"" + rst.getInt(2) + "\"");
            pname.next();
            System.out.println("Time : " + rst.getTimestamp(1) + "\nProduct Name : " + pname.getString(1) +
                    "\nQuantity = " + rst.getInt(3));
        }
    }

    void addToCart(Integer product_id, Integer quantity) throws Exception{
        ResultSet rst = statement.executeQuery("SELECT selling_price,discount from product where product_id = " + "\"" +
                product_id + "\"");
        rst.next();
        cartValue +=(rst.getInt(1)-rst.getInt(2))*quantity;
        cart.add(product_id);
        this.quantity.add(quantity);
    }

    void removeFromCart(int product_index)throws Exception{
        ResultSet rst = statement.executeQuery("SELECT selling_price,discount from product where product_id = " + "\"" +
                cart.get(product_index) + "\"");
        rst.next();
        cartValue -= (rst.getInt(1)-rst.getInt(2))*quantity.get(product_index);
        cart.remove(product_index);
        quantity.remove(product_index);
    }

    void checkout() throws Exception{
        for (int i=0;i<cart.size();i++){
            ResultSet rst = statement.executeQuery("SELECT quantity from product where product_id = " + "\"" +
                    cart.get(i) + "\"");
            rst.next();
            int new_quantity = rst.getInt(1) - quantity.get(i);
            statement.executeQuery("update product set quantity =" + new_quantity + "where product_id = " + "\"" +
                    cart.get(i) + "\"");
        }
        updateSellingHistory();
        cart.clear();
        quantity.clear();
        System.out.println("Cart Value : " + cartValue +
                           "\nPay the Bill!");
    }

    void updateSellingHistory() throws Exception{
        int user_id = (new StationaryClass()).getUser_id();
        for(int i=0;i<cart.size();i++){
            ResultSet rst = statement.executeQuery("select actual_price,selling_price,discount from product where product_id = " +
                    "\"" + cart.get(i) + "\"");
            rst.next();
            Timestamp tmp = new Timestamp(System.currentTimeMillis());
            int profit = (rst.getInt(2)-rst.getInt(3)-rst.getInt(1))*quantity.get(i);
            statement.executeQuery("insert into history(time_stamp,user_id,product_id,quantity,profit) values("
            + tmp +"," +user_id + "," + cart.get(i) + "," + quantity.get(i) + "," + profit + ")");
        }
    }

    void showCart() throws Exception{
        int choice;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("CART : ");
            for (int i = 0; i < cart.size(); i++) {
                System.out.println(i + 1 + " " + cart.get(i));
            }
            System.out.print(cart.size() + 1 + " " + "Checkout\n" +
                    cart.size() + 2 + " " + "Back to menu\n" +
                    "Choice : ");

            choice = in.nextInt();
            if (choice > 0 && choice <= cart.size()) {
                System.out.print(  "1 " + "Remove item\n" +
                                   "2 " + "Change Quantity\n" +
                                   "3 " + "Back\n" +
                                   "Choice : ");
                int c = in.nextInt();
                switch (c){
                    case 1:
                        removeFromCart(choice-1);
                        System.out.println("\nRemoved!\n");
                        break;
                    case 2:
                        System.out.print("Enter new quantity : ");
                        int q = in.nextInt();
                        quantity.set(choice-1, q);
                        System.out.println("\nChanged!\n");
                        break;
                    case 3:
                        break;
                }
            }
            else if(choice == cart.size() + 1){
                checkout();
            }
            else{
                break;
            }
        }while(true);
    }

    void show_product_type(){

        try {
            Connection con = Authentication.connect();
            ResultSet rst;
            Statement st = con.createStatement();
            rst = st.executeQuery("SELECT DISTINCT type_of_product FROM product ");
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }
            con.close();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    void  purchase_item() {
        float After_Discount_Price;
        int buy_quantity;
        int break_loop1 = 0;
        int break_loop2 = 0;
        int break_loop3 = 0;
        int break_loop4 = 0;
        int item_Number = 0;
        do {

            this.show_product_type();
            System.out.print("Which type of product you want : ");
            Scanner sc = new Scanner(System.in);
            String product_type = sc.nextLine();
            String upper_product_type = product_type.toUpperCase();
            try {
                Connection con = Authentication.connect();
                Statement st = con.createStatement();
                int index = 1;
                do {
                    ResultSet rst = st.executeQuery("SELECT * FROM product WHERE type_of_product = '" + upper_product_type + "'");
                    while (rst.next()) {
                        System.out.println(index++ + ")    " + rst.getString(3) + "[" + rst.getInt(8) + "]");
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


                    if (break_loop3 == 1) {
                        break;
                    }

                    for (int i = 0; i < item_Number; i++) {
                        rst.next();
                    }

                    After_Discount_Price = (float) rst.getInt(5) - (float) (rst.getInt(5) * rst.getInt(6)) / 100;
                    System.out.println("You Select : \n");
                    System.out.println(rst.getString(3) + "[" + rst.getInt(8) + "]");
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
                                    System.out.print("Add Quantity : ");
                                    buy_quantity = sc.nextInt();
                                    if (buy_quantity > rst.getInt(8)) {
                                        System.out.println("Sorry We have only " + rst.getInt(8) + " Quantity");
                                    }
                                } while (buy_quantity > rst.getInt(8));

                                addToCart(rst.getInt(1), buy_quantity);
                                System.out.println("Added To Cart SuccessFully......\n");
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

                } while (break_loop2 != 1);

            } catch (Exception ex) {
                System.out.println(ex);
            }
        } while (break_loop2 != 1);

    }

    Connection getConnection(){
        return this.connection;
    }

    Statement getStatement(){
        return this.statement;
    }

    ArrayList<Integer> getCart(){
        return this.cart;
    }

    ArrayList<Integer> getQuantity(){
        return this.quantity;
    }

    double getCartValue(){
        return this.cartValue;
    }
}

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

    void Search(){
        Scanner in = new Scanner(System.in);
        System.out.println("\t\tEnter product type : ");
        String type = in.nextLine();
        //Search product of this type | Display Results
        // with index numbers | (-1) to get Back to Main menu

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

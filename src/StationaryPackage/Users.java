
/////////////////////////////////////////////////////////////////////////////////////
//                                                                                 //
//                           STATIONARY-MANAGEMENT-SYSTEM                          //
//                                                                                 //
//                           Authors : ✔ @Vatsalparsaniya                          //
//                                     ✔ @memr5                                    //
//                                                                                 //
/////////////////////////////////////////////////////////////////////////////////////

package StationaryPackage;

import Authentication.Authentication;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Users {

    private Connection connection;
    private Statement statement;
    private ArrayList<Integer> cart;
    private ArrayList<Integer> quantity;
    private double cartValue;

    Users() throws Exception {
       this.connection = (new Authentication()).connect();
       this.statement = this.connection.createStatement();
       this.cart = new ArrayList<>();
       this.quantity = new ArrayList<>();
       this.cartValue = 0;
    }

    void Menu(){
        System.out.print("\n---------------------------------MENU--------------------------------\n" +
                "\t\t1. Search\n" +
                "\t\t2. Your Orders\n" +
                "\t\t3. Cart\n" +
                "\t\t4. Exit\n" +
                "---------------------------------------------------------------------\n\n" +
                "\t\tEnter your choice : ");
    }

    void orders()throws Exception{

        int user_id = StationaryClass.getUser_id();

        System.out.println("\nPurchase History : ");
        ResultSet rst = statement.executeQuery("SELECT date,product_name,quantity from history where user_id = " + user_id);
        if(!rst.next()){
            System.out.println("\nNo history found!\n");
        }
        rst.beforeFirst();
        while (rst.next()){
            System.out.println("\nDate : " + rst.getDate(1) + "\nProduct Name : " + rst.getString(2) +
                    "\nQuantity : " + rst.getInt(3));
        }

    }

    private void addToCart(Integer product_id, Integer quantity) throws Exception{

        ResultSet rst = statement.executeQuery("SELECT selling_price,discount from product where product_id = " +
                product_id );
        rst.next();
        cartValue += (rst.getDouble(1)-(rst.getDouble(1)*rst.getDouble(2)/100))*(double)quantity;
        cart.add(product_id);
        this.quantity.add(quantity);
    }

    private void removeFromCart(int product_index)throws Exception{

        ResultSet rst = statement.executeQuery("SELECT selling_price,discount from product where product_id = " +
                cart.get(product_index));
        rst.next();
        cartValue -= (rst.getDouble(1)-(rst.getDouble(1)*rst.getDouble(2)/100))*(double)quantity.get(product_index);
        cart.remove(product_index);
        quantity.remove(product_index);
    }

    private void checkout() throws Exception{

        if(cart.size()==0){
            System.out.println("\nEmpty Cart!\n");
            return;
        }

        for (int i=0;i<cart.size();i++){
            ResultSet rst = statement.executeQuery("SELECT quantity from product where product_id = " +
                    cart.get(i) );
            rst.next();
            int new_quantity = rst.getInt(1) - quantity.get(i);
            statement.executeUpdate("update product set quantity = " + new_quantity + " where product_id = " +
                    cart.get(i) );
        }
        updateSellingHistory();
        cart.clear();
        quantity.clear();
        System.out.println("Cart Value : " + cartValue +
                           "\nPay the Bill!");
    }

    private void updateSellingHistory() throws Exception{

        int user_id = StationaryClass.getUser_id();

        for(int i=0;i<cart.size();i++){
            ResultSet rst = statement.executeQuery("select product_name,actual_price,selling_price,discount from product where product_id = " + cart.get(i));
            rst.next();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(new Date());
            double profit = (rst.getDouble(3) - (rst.getDouble(3)*rst.getDouble(4)/100) - rst.getDouble(2))*(double)quantity.get(i);
            statement.executeUpdate("insert into history(date,user_id,user_name,product_id,product_name,quantity,profit) values" + "(\""
            + date + "\"," + user_id + ",\"" + StationaryClass.getUser_name() + "\"," + cart.get(i) + ",\"" + rst.getString(1) + "\"," + quantity.get(i) + "," + profit + ")");
        }
    }

    void showCart() throws Exception{
        int choice;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\nYour CART : ");
            if(cart.size()==0){
                System.out.println("(Cart is Empty)");
            }
            for (int i = 0; i < cart.size(); i++) {
                ResultSet rst = statement.executeQuery("select product_name from product where product_id = " + cart.get(i));
                rst.next();
                System.out.println(i+1 + ") " + rst.getString(1) + " | Quantity : " + quantity.get(i));
            }
            System.out.print("---------------------------------------------------------------------\n" + (cart.size()+1) + ") " + "Checkout\n" +
                    (cart.size() + 2) + ") " + "Back to menu\n" +
                    "Choice : ");

            choice = in.nextInt();
            if (choice > 0 && choice <= cart.size()) {
                System.out.print(  "1) " + "Remove item\n" +
                                   "2) " + "Change Quantity\n" +
                                   "3) " + "Back\n" +
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
                        ResultSet rst = statement.executeQuery("select quantity from product where product_id = "
                        + cart.get(choice-1));
                        rst.next();
                        if(q <= 0){
                            System.out.println("\nEnter valid quantity!\n");
                        }
                        else if(rst.getInt(1) >= q){
                            quantity.set(choice-1, q);
                            System.out.println("\nChanged!\n");
                        }
                        else {
                            System.out.println("\nOnly " + rst.getInt(1) + " Available\n");
                        }
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("\nEnter valid choice!\n");
                }
            }
            else if(choice == cart.size()+1){
                checkout();
                break;
            }
            else{
                break;
            }
        }while(true);
    }

    void show_product_type(){
        System.out.println("\nTypes of products available : \n");
        try {
            ResultSet rst = statement.executeQuery("SELECT DISTINCT type_of_product FROM product ");
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    void  purchase_item(){

        double After_Discount_Price;
        int buy_quantity;

        int break_loop1;
        int break_loop2;
        int break_loop3;
        int break_loop4;
        int item_Number;

        do {

            break_loop2 = 0;
            item_Number = 0;
            this.show_product_type();
            System.out.println("\n\t\t-1) Back to Menu");
            System.out.print("\n\t\tWhich type of product you want : ");

            Scanner sc = new Scanner(System.in);
            String product_type = sc.nextLine();

            if(product_type.equals("-1")){
                break;
            }

            String upper_product_type = product_type.toUpperCase();

            try {
                do {
                    int index = 1;
                    System.out.println("\nProduct Names and [ Quantity ] : \n");
                    ResultSet rst = statement.executeQuery("SELECT * FROM product WHERE type_of_product = '" + upper_product_type + "'");
                    while (rst.next()) {
                        System.out.println("\n" + index++ + ")    " + rst.getString(3) + " [" + rst.getInt(8) + "]");
                        System.out.println("\t\tSelling Price : " + rst.getDouble(5));
                        System.out.println("\t\tDiscount : " + rst.getDouble(6));
                        After_Discount_Price = rst.getDouble(5) -  (rst.getDouble(5) * rst.getDouble(6)) / 100;
                        System.out.println("\t\tAfter Discount Price : " + After_Discount_Price + "\n\n");
                    }
                    rst.beforeFirst();
                    System.out.print("Select your choice :\n\t\t1) Select item\n\t\t2) Back\n\t\tEnter your choice : ");
                    do {

                        int choice_1 = sc.nextInt();
                        break_loop3 = 0;
                        break_loop4 = 0;
                        switch(choice_1){
                            case 1:
                                System.out.print("Select item : ");
                                item_Number = sc.nextInt();
                                break_loop4 = 1;
                                break;
                            case 2:
                                break_loop3 = 1;
                                break;
                            default:
                                System.out.print("Please enter valid number :");
                        }
                    }while(break_loop3!=1 && break_loop4!=1);


                    if (break_loop3 == 1 || item_Number>=index) {
                        break;
                    }

                    for (int i = 0; i < item_Number; i++) {
                        rst.next();
                    }

                    After_Discount_Price =  rst.getDouble(5) - (rst.getDouble(5) * rst.getDouble(6)) / 100;
                    System.out.println("\n\nYou Selected : \n");
                    System.out.println("\t\t" + rst.getString(3) + " [" + rst.getInt(8) + "]");
                    System.out.println("\t\tSelling Price : " + rst.getDouble(5));
                    System.out.println("\t\tDiscount : " + rst.getDouble(6));
                    System.out.println("\t\tAfter Discount Price : " + After_Discount_Price);
                    System.out.println("\t\tSpecification : " + rst.getString(7));
                    System.out.print("\n\t\t1) Add TO Cart\n\t\t2) back\n\n\t\tEnter Your answer : ");


                    int selection;
                    do {
                        selection = sc.nextInt();
                        break_loop2 = 0;
                        break_loop1 = 0;
                        switch (selection) {
                            case 1:
                                do {
                                    System.out.print("Add Quantity : ");
                                    buy_quantity = sc.nextInt();
                                    if (buy_quantity > rst.getInt(8)) {
                                        System.out.println("\nSorry We have only " + rst.getInt(8) + " items in quantity\n");
                                        continue;
                                    }
                                    if(buy_quantity <= 0){
                                        System.out.println("\nEnter Valid Quantity!\n");
                                        continue;
                                    }
                                    break;
                                } while (true);

                                addToCart(rst.getInt(1), buy_quantity);
                                System.out.println("Added To Cart Successfully......\n");
                                break_loop2 = 1;
                                break;
                            case 2:
                                break_loop1 = 1;
                                break;
                            default:
                                System.out.print("Please Select appropriate Option :");
                        }
                    } while (break_loop1 != 1 && break_loop2 != 1);

                } while (break_loop2 != 1);

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } while (break_loop2 != 1);

    }

    Statement getStatement(){
        return this.statement;
    }

    Connection getConnection(){
        return connection;
    }
}

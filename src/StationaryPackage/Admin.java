package StationaryPackage;

import Authentication.Authentication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin extends Users{

    Admin() throws Exception {
        super();
    }

    public void Menu(){
        System.out.print   ("---------------------------------MENU--------------------------------\n\n" +
                            "\t\t1. Search\n" +
                            "\t\t2. Your Orders\n" +
                            "\t\t3. Cart\n" +
                            "\t\t4. Manipulate Data" +
                            "\t\t5. Report" +
                            "\t\t6. Exit" +
                            "---------------------------------------------------------------------\n\n" +
                            "\t\tEnter your choice : ");
    }


    public  void  manipulateData(){
        System.out.println("---------------------------------MENU--------------------------------\n\n"+
                            "\t\t1. Add Item\n"+
                            "\t\t2. Remove Item\n"+
                            "\t\t2. Add Quantity\n"+
                            "\t\t3. Remove Quantity\n"+
                            "\t\t4. Back");
    }
    public void Add_Item() throws Exception{
        Scanner sc = new Scanner(System.in);

        System.out.print("Which type of item you want to add : ");
        String item_type = sc.nextLine();
        item_type = item_type.toUpperCase();

        System.out.print("Product Name :");
        String product_name = sc.nextLine();

        System.out.print("Actual Price :");
        int actual_price = sc.nextInt();

        System.out.print("Selling price :");
        int selling_price = sc.nextInt();

        System.out.print("Discount :");
        int discount = sc.nextInt();

        System.out.print("Specification :");
        String specification = sc.nextLine();

        System.out.print("Quantity :");
        int quantity = sc.nextInt();

        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO product(type_of_product,produt_name,actual_price," +
                                    "selling_price,discount,specification,quantity) values " + "(\"" + item_type + "\",\"" +
                                     product_name + "\"," + actual_price + "," + selling_price + "," + discount + ",\"" +
                                     specification + "\"," + quantity +")"  );
        //Add item is already added or not
    }


    public void remove_item()throws Exception{
        Scanner sc = new Scanner(System.in);
        show_product_type();
        System.out.print("In Which Type of product you want to remove item :");
        String item_type = sc.nextLine();
        item_type = item_type.toUpperCase();

        Connection connection = (new Authentication()).connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from product where type_of_product ='" + item_type + "'");
        if(resultSet.next()){
             //Enter type of product is available
            resultSet.beforeFirst();
            int index = 1;
            while (resultSet.next()){
                System.out.println("\n" + index++ + ")    " + resultSet.getString(3) + "[" + resultSet.getInt(8) + "]");
            }
            System.out.print("\nWhich product you want to remove \nEnter product number : ");
            int remove_product_number = sc.nextInt();

            // delete row @remove_product_number in purchase table

        }else{
            System.out.print("That product is not available ");
        }
    }


    public void AddQuantity(){

    }


    public void report(){

    }

}

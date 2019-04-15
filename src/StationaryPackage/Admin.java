package StationaryPackage;

import Authentication.Authentication;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin extends Users{

    Admin() throws Exception {
        super();
    }

    void Menu(){
        System.out.print   ("\n\n---------------------------------MENU--------------------------------\n\n" +
                            "\t\t1. Search\n" +
                            "\t\t2. Your Orders\n" +
                            "\t\t3. Cart\n" +
                            "\t\t4. Manipulate Data\n" +
                            "\t\t5. Report\n" +
                            "\t\t6. Exit\n" +
                            "\n\n---------------------------------------------------------------------\n\n" +
                            "\t\tEnter your choice : ");
    }

    void  manipulateData() throws Exception{

        int choice;

        do{
            System.out.println ("\n\n------------------------------MANIPULATE DATA----------------------------\n\n"+
                                "\t\t1. Add Item\n"+
                                "\t\t2. Remove Item\n"+
                                "\t\t3. Change Quantity\n"+
                                "\t\t4. Back\n" +
                                "\n\n--------------------------------------------------------------------------\n\n");

            System.out.print("\t\tYour choice : ");
            Scanner in = new Scanner(System.in);
            choice = in.nextInt();

            if(choice==4){
                break;
            }

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    changeQuantity();
                    break;
                default:
                    System.out.println("\nEnter Valid choice!\n");
            }
        }while(true);
    }

    private void addItem() throws Exception{

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

        Statement statement = getStatement();
        statement.executeUpdate("INSERT INTO product(type_of_product,produt_name,actual_price," +
                                    "selling_price,discount,specification,quantity) values " + "(\"" + item_type + "\",\"" +
                                     product_name + "\"," + actual_price + "," + selling_price + "," + discount + ",\"" +
                                     specification + "\"," + quantity +")"  );
        System.out.println("\nItem Added!\n");
    }


    private void removeItem()throws Exception{

        Scanner sc = new Scanner(System.in);
        show_product_type();
        System.out.print("\nWhich Type of product you want to remove item :");
        String item_type = sc.nextLine();
        item_type = item_type.toUpperCase();

        Statement statement = getStatement();
        ResultSet resultSet = statement.executeQuery("select * from product where type_of_product = '" + item_type + "'");

        if(resultSet.next()){

            resultSet.beforeFirst();
            int index = 1;
            System.out.println("\nAvailable products of type : " + item_type);
            while (resultSet.next()){
                System.out.println("\n" + index++ + ")    " + resultSet.getString(3) + " [" + resultSet.getInt(8) + "]");
            }

            System.out.print("\nWhich product you want to remove?\nEnter product number : ");
            int remove_product_number = sc.nextInt();

            resultSet.beforeFirst();
            for (int i=0;i<remove_product_number;i++){
                resultSet.next();
            }

            getConnection().createStatement().executeUpdate("DELETE from product where " +
                    "product_id = " + resultSet.getInt(1));
            System.out.println("\nDeleted!\n");

        }else{
            System.out.print("That product is not available ");
        }
    }


    private void changeQuantity(){

    }


    void report(){

    }

}

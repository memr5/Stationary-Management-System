
/////////////////////////////////////////////////////////////////////////////////////
//                                                                                 //
//                           STATIONARY-MANAGEMENT-SYSTEM                          //
//                                                                                 //
//                           Authors : ✔ @Vatsalparsaniya                          //
//                                     ✔ @memr5                                    //
//                                                                                 //
/////////////////////////////////////////////////////////////////////////////////////

package StationaryPackage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class Admin extends Users{

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

        System.out.print("\nWhich type of item you want to add : ");
        String item_type = sc.nextLine();
        item_type = item_type.toUpperCase();

        System.out.print("Product Name :");
        String product_name = sc.nextLine();

        System.out.print("Actual Price :");
        double actual_price = sc.nextDouble();

        System.out.print("Selling price :");
        double selling_price = sc.nextDouble();

        System.out.print("Discount :");
        double discount = sc.nextDouble();

        sc.nextLine();
        System.out.print("Specification :");
        String specification = sc.nextLine();

        System.out.print("Quantity :");
        int quantity = sc.nextInt();

        Statement statement = getStatement();
        statement.executeUpdate("INSERT INTO product(type_of_product,product_name,actual_price," +
                "selling_price,discount,specification,quantity) values" + "(\"" + item_type + "\",\"" +
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

    private void changeQuantity ()throws  Exception{

        Scanner sc = new Scanner(System.in);
        show_product_type();
        System.out.print("\nType Of Product :");
        String type_Of_item = sc.nextLine();

        Statement statement = getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM product where type_of_product = '" + type_Of_item +"'");

        if(resultSet.next()){
            resultSet.beforeFirst();

            int index = 1;
            System.out.println("\nAvailable products of type : " + type_Of_item);

            while (resultSet.next()){
                System.out.println("\n" + index++ + ")    " + resultSet.getString(3) + " [" + resultSet.getInt(8) + "]");
            }

            System.out.print("\nitem Number :");
            int item_number = sc.nextInt();

            resultSet.beforeFirst();
            for (int i=0;i<item_number;i++){
                resultSet.next();
            }

            int product_id = resultSet.getInt(1);
            int selection;
            int quantity = 0;

            do {
                System.out.print("\n\t\t1. Add\n\t\t2. Remove");
                System.out.print("\n\t\tYour choice : ");
                selection = sc.nextInt();

                switch (selection){
                    case 1:
                        System.out.print("How many items you want ot add :");
                        quantity = sc.nextInt();
                        quantity = quantity + resultSet.getInt(8);
                        break;
                    case 2:
                        System.out.print("How many items you want to remove :");
                        quantity = sc.nextInt();
                        if(quantity > resultSet.getInt(8)){
                            System.out.print("\nSorry that many items not available : ");
                        }else {
                            quantity = resultSet.getInt(8) - quantity;
                        }
                        break;
                    default:
                        System.out.print("\nEnter valid choice!\n");
                }
            }while (selection!= 1 && selection != 2);

            getConnection().createStatement().executeUpdate("update product set quantity = " + quantity + " where product_id = " + product_id);
            System.out.println("\nQuantity Updated!\n");
        }else{
            System.out.print("\nThat type of item is not available\n");
        }

    }

    void report() throws Exception{

        System.out.println("\nSale Report : ");
        ResultSet resultSet = getStatement().executeQuery("select quantity,profit from history");

        double totalProfit = 0;
        int totalProducts = 0;

        while (resultSet.next()){
            totalProducts += resultSet.getInt(1);
            totalProfit += resultSet.getDouble(2);
        }

        System.out.println("\n\t\tTotal Products sold : " + totalProducts + "\n\t\tTotal Profit : " + totalProfit);
    }

}

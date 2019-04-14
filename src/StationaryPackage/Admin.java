package StationaryPackage;

import java.sql.SQLException;

public class Admin extends Users{

    Admin() throws SQLException {
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

}

package StationaryPackage;

import java.sql.SQLException;

public interface Pages {
    void Banner();
    void Menu();
    void WelcomePage();
    boolean Login() throws SQLException;
    boolean Sign_up() throws SQLException;
}

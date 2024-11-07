package admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class example {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package admin;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ProductSearch {
    /**
     * Searches for a product by name in the 'products' table.
     *
     * @param productName The name of the product to search for.
     * @return A string message indicating whether the product was found and its details.
     */
    public static String search(String productName) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root"; // Your MySQL username
        String dbPassword = "";     // Your MySQL password (empty in your case)

        String resultMessage = "Product not found: " + productName;

        String query = "SELECT * FROM products WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                resultMessage = "Product Found: Name = " + name + ", Price = " + price + ", Quantity = " + quantity;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resultMessage = "Error occurred while searching for the product.";
        }

        return resultMessage;
    }
}



class DeleteProductWindow extends Frame implements ActionListener, WindowListener {
    TextField productIdField;
    Button deleteButton;

    DeleteProductWindow() {
        setTitle("Delete Product");

        // Set layout and background color
        setLayout(new GridBagLayout());
        setBackground(new Color(100, 100, 100));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Product ID or Name
        Label productIdLabel = new Label("Product ID/Name:");
        productIdField = new TextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(productIdLabel, gbc);
        gbc.gridx = 1;
        add(productIdField, gbc);

        // Delete Button
        deleteButton = new Button("Delete Product");
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(deleteButton, gbc);

        deleteButton.addActionListener(this);
        addWindowListener(this);
        setSize(500, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            String productIdentifier = productIdField.getText();

            if (deleteProductFromDatabase(productIdentifier)) {
                System.out.println("Product Deleted: " + productIdentifier);
            } else {
                System.out.println("Product not found: " + productIdentifier);
            }
        }
    }

    private boolean deleteProductFromDatabase(String productIdentifier) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecommerce_db", "root", "")) {

            // Prepare query to delete by either ID (if integer) or name
            String deleteQuery;
            if (isNumeric(productIdentifier)) {
                deleteQuery = "DELETE FROM products WHERE id = ?";
            } else {
                deleteQuery = "DELETE FROM products WHERE name = ?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                if (isNumeric(productIdentifier)) {
                    stmt.setInt(1, Integer.parseInt(productIdentifier));
                } else {
                    stmt.setString(1, productIdentifier);
                }

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Helper method to check if input is numeric (for distinguishing ID from name)
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void windowClosing(WindowEvent we) {
        dispose();
    }

    public void windowOpened(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}
}
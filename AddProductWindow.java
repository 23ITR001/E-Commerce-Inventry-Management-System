package admin;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddProductWindow extends Frame implements ActionListener {
    TextField productNameField, priceField, quantityField;
    Button addButton;

    AddProductWindow() {
        setTitle("Add Product");
        setLayout(new GridLayout(4, 2, 10, 10));

        // Labels and text fields
        Label nameLabel = new Label("Product Name:");
        productNameField = new TextField();
        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();

        // Add button
        addButton = new Button("Add Product");
        addButton.addActionListener(this);

        // Add components to the layout
        add(nameLabel);
        add(productNameField);
        add(priceLabel);
        add(priceField);
        add(quantityLabel);
        add(quantityField);
        add(new Label()); // Empty space
        add(addButton);

        setSize(400, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Get the input values
            String name = productNameField.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();

            // Validate input (basic validation)
            if (!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                // Try to insert product into the database
                addProductToDatabase(name, price, quantity);

                // Clear input fields
                productNameField.setText("");
                priceField.setText("");
                quantityField.setText("");

                // Show confirmation
                System.out.println("Product added to database: " + name);
            } else {
                // Error handling if fields are empty
                System.out.println("Please fill all fields!");
            }
        }
    }

    // Method to add product details to the database
    private void addProductToDatabase(String name, String price, String quantity) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root"; // Update with your DB username
        String dbPassword = ""; // Update with your DB password if any

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Prepare SQL insert statement
            String query = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, price);
                stmt.setString(3, quantity);

                // Execute insert
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Product successfully added to the products table.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error adding product to the database.");
        }
    }

    public static void main(String[] args) {
        new AddProductWindow();
    }
}

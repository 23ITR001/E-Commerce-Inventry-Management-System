package admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

class InventoryWindow extends Frame implements ActionListener, WindowListener {
    TextField searchField;
    Button searchButton, refreshButton;
    Label alertLabel;
    JTable table;
    DefaultTableModel tableModel;

    public InventoryWindow() {
        setTitle("Inventory Management");
        setLayout(new BorderLayout());

        // Title
        Label titleLabel = new Label("Inventory", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Create table model and JTable
        String[] columns = {"Product Name", "Price", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Search section
        Panel searchPanel = new Panel(new GridLayout(1, 3, 10, 10));
        searchField = new TextField(20);
        searchButton = new Button("Search Product");
        refreshButton = new Button("Refresh Inventory");

        searchPanel.add(new Label("Search by Product Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Threshold alert section
        alertLabel = new Label();
        alertLabel.setForeground(Color.RED);
        add(alertLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
        addWindowListener(this);

        // Load initial inventory and check threshold alert
        loadInventory();
        checkThresholdAlert();

        setSize(600, 400);
        setVisible(true);
    }

    // Method to load inventory data from the database into the table
    private void loadInventory() {
        tableModel.setRowCount(0); // Clear existing rows
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root"; // Replace with your database username
        String dbPassword = "";     // Replace with your database password

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                tableModel.addRow(new Object[]{name, price, quantity});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading inventory from the database.");
        }
    }

    // Method to check for products below the threshold and display an alert
    private void checkThresholdAlert() {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root";
        String dbPassword = "";
        int threshold = 5;
        StringBuilder alertMessage = new StringBuilder();

        String query = "SELECT name, quantity FROM products WHERE quantity < ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                alertMessage.append("Low Stock: ").append(name).append(" (").append(quantity).append(")\n");
            }

            alertLabel.setText(alertMessage.length() > 0 ? alertMessage.toString() : "All items are above threshold.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertLabel.setText("Error checking inventory threshold.");
        }
    }

    // Event handling for search and refresh buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String productName = searchField.getText();
            if (!productName.isEmpty()) {
                String result = searchProduct(productName);
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a product name to search.");
            }
        } else if (e.getSource() == refreshButton) {
            loadInventory();
            checkThresholdAlert();
        }
    }

    // Method to search for a product in the database by name
    private String searchProduct(String productName) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root";
        String dbPassword = "";
        String query = "SELECT * FROM products WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                return "Product Found: Name = " + name + ", Price = " + price + ", Quantity = " + quantity;
            } else {
                return "Product not found: " + productName;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error occurred while searching for the product.";
        }
    }

    // Window listener methods
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

package admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewDetailsWindow extends Frame implements ActionListener, WindowListener {
    JTable table;
    DefaultTableModel tableModel;
    Button addButton;

    public ViewDetailsWindow() {
        setTitle("View Product Details");
        setLayout(new BorderLayout());

        // Create table model and JTable
        String[] columns = {"Product Name", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add to Cart Button
        addButton = new Button("Add to Cart");
        addButton.addActionListener(this);
        add(addButton, BorderLayout.SOUTH);

        // Load data from the database
        loadInventory();

        addWindowListener(this);
        setSize(600, 400);
        setVisible(true);
    }

    // Method to load available inventory from the database
    private void loadInventory() {
        tableModel.setRowCount(0); // Clear existing rows

        String url = "jdbc:mysql://localhost:3306/ecommerce_db"; // Your database URL
        String dbUsername = "root"; // Your database username
        String dbPassword = "";     // Your database password

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, price FROM products")) {

            while (rs.next()) {
                String name = rs.getString("name");
                String price = rs.getString("price");
                tableModel.addRow(new Object[] {name, price});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading inventory from the database.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Get selected product details
                String name = (String) tableModel.getValueAt(selectedRow, 0);
                String price = (String) tableModel.getValueAt(selectedRow, 1);

                // Add product to the cart
                admin.CartData.cart.add(new String[] { name, price });
                JOptionPane.showMessageDialog(this, name + " added to cart.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to add to cart.");
            }
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

package admin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public         class BuyProductsWindow extends Frame implements ActionListener, WindowListener  {
    JTable table;
    DefaultTableModel tableModel;
    Button buyButton, refreshButton;
    Label totalLabel;

    BuyProductsWindow() {
        setTitle("Cart - Buy Products");
        setLayout(new BorderLayout());

        // Create table model and JTable
        String[] columns = {"Product Name", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Total Price
        totalLabel = new Label("Total: $0.00");
        add(totalLabel, BorderLayout.NORTH);

        // Buttons: Buy and Refresh
        Panel buttonPanel = new Panel();
        buyButton = new Button("Buy");
        refreshButton = new Button("Refresh Cart");
        buttonPanel.add(buyButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buyButton.addActionListener(this);
        refreshButton.addActionListener(this);

        loadCart();
        updateTotal();

        addWindowListener(this);
        setSize(600, 400);
        setVisible(true);
    }

    // Method to load cart items into the table
    private void loadCart() {
        tableModel.setRowCount(0); // Clear existing rows

        for (String[] item : admin.CartData.cart) {
            tableModel.addRow(item);
        }
    }

    // Method to calculate and update the total price
    private void updateTotal() {
        double total = 0.0;
        for (String[] item : admin.CartData.cart) {
            total += Double.parseDouble(item[1]);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buyButton) {
            if (!admin.CartData.cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Purchase successful!");
                admin.CartData.cart.clear();  // Clear the cart after purchase
                loadCart();
                updateTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Your cart is empty!");
            }
        } else if (e.getSource() == refreshButton) {
            admin.CartData.cart.clear();  // Clear the cart without buying
            loadCart();
            updateTotal();
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


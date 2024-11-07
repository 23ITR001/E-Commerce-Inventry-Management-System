package admin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public         class OwnerDashboard extends Frame implements ActionListener, WindowListener {
    Button addProductButton, deleteProductButton, viewInventoryButton;

    OwnerDashboard() {
        setTitle("Owner Dashboard");

        // Set full-screen size
        setExtendedState(Frame.MAXIMIZED_BOTH);

        // Set background color to light blue
        setBackground(new Color(0, 0, 0));

        // Use GridBagLayout to center the elements
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Title
        Label titleLabel = new Label("Owner Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 45));
        titleLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Create a panel with white background for the form
        Panel buttonPanel = new Panel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(600, 500));
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(10, 10, 10, 10);
        panelGbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Product Button
        addProductButton = new Button("Add Product");
        addProductButton.setFont(new Font("Arial", Font.BOLD, 25));
        addProductButton.setBackground(Color.BLACK);
        addProductButton.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        buttonPanel.add(addProductButton, panelGbc);
        addProductButton.addActionListener(this);

        // Delete Product Button
        deleteProductButton = new Button("Delete Product");
        deleteProductButton.setFont(new Font("Arial", Font.BOLD, 25));
        deleteProductButton.setBackground(Color.BLACK);
        deleteProductButton.setForeground(Color.WHITE);
        panelGbc.gridy = 1;
        buttonPanel.add(deleteProductButton, panelGbc);
        deleteProductButton.addActionListener(this);

        // View Inventory Button
        viewInventoryButton = new Button("View Inventory");
        viewInventoryButton.setFont(new Font("Arial", Font.BOLD, 25));
        viewInventoryButton.setBackground(Color.BLACK);
        viewInventoryButton.setForeground(Color.WHITE);
        panelGbc.gridy = 2;
        buttonPanel.add(viewInventoryButton, panelGbc);
        viewInventoryButton.addActionListener(this);

        // Add the form panel to the main layout
        gbc.gridy = 1;
        add(buttonPanel, gbc);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProductButton) {
            new AddProductWindow(); // Opens the Add Product window
        } else if (e.getSource() == deleteProductButton) {
            new DeleteProductWindow(); // Opens the Delete Product window
        } else if (e.getSource() == viewInventoryButton) {
            new InventoryWindow(); // Opens the Inventory window
        }
    }

    public void windowClosing(WindowEvent we) {
        dispose();
        System.exit(0);
    }

    // Empty methods for other WindowListener events
    public void windowOpened(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}
}


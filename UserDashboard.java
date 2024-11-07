package admin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

class UserDashboard extends Frame implements ActionListener, WindowListener {

    Button viewDetailsButton, buyProductButton;
    Button loginButton, signUpButton;

    UserDashboard() {
        setTitle("User Dashboard");

        // Set full-screen size
        setExtendedState(Frame.MAXIMIZED_BOTH);

        // Set background color to black
        setBackground(new Color(100, 100, 100));

        // Use GridBagLayout to center the elements
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Create a panel with white background for the form
        Panel formPanel = new Panel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 300));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        Label titleLabel = new Label("User Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 45));
        titleLabel.setForeground(Color.WHITE);
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

        // View Details Button
        viewDetailsButton = new Button("View Details");
        viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 25));
        viewDetailsButton.setBackground(Color.BLACK);
        viewDetailsButton.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        buttonPanel.add(viewDetailsButton, panelGbc);
        viewDetailsButton.addActionListener(this);

        // Buy Product Button
        buyProductButton = new Button("Buy Product");
        buyProductButton.setFont(new Font("Arial", Font.BOLD, 25));
        buyProductButton.setBackground(Color.BLACK);
        buyProductButton.setForeground(Color.WHITE);
        panelGbc.gridy = 1;
        buttonPanel.add(buyProductButton, panelGbc);
        buyProductButton.addActionListener(this);

        // Login Button
        loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        formGbc.gridx = 1;
        formGbc.gridy = 2;
        formPanel.add(loginButton, formGbc);
        loginButton.addActionListener(this);

        // Sign-Up Button
        signUpButton = new Button("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setForeground(Color.WHITE);
        formGbc.gridx = 3;
        formGbc.gridy = 3;
        formPanel.add(signUpButton, formGbc);
        signUpButton.addActionListener(this);

        // Add the form panel to the main layout
        gbc.gridy = 1;
        add(buttonPanel, gbc);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewDetailsButton) {
            new ViewDetailsWindow();  // Open the ViewDetailsWindow to show product details
        } else if (e.getSource() == buyProductButton) {
            new BuyProductsWindow();  // Open the BuyProductsWindow to show cart items and allow purchase
        }
    }

    public void windowClosing(WindowEvent we) {
        dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}
}

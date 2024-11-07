package admin;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Supplier extends Frame implements ActionListener, WindowListener {
    Button loginButton, signUpButton;
    TextField usernameField, passwordField;

    public Supplier() {
        setTitle("E-Commerce Login");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setBackground(new Color(100, 100, 100));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        Label titleLabel = new Label("Login Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        Panel formPanel = new Panel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 300));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(usernameLabel, formGbc);

        usernameField = new TextField(20);
        formGbc.gridx = 1;
        formPanel.add(usernameField, formGbc);

        // Password Field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(passwordLabel, formGbc);

        passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        formGbc.gridx = 1;
        formPanel.add(passwordField, formGbc);

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
        formGbc.gridy = 3;
        formPanel.add(signUpButton, formGbc);
        signUpButton.addActionListener(this);

        gbc.gridy = 1;
        add(formPanel, gbc);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticateUser(username, password)) {
                System.out.println("Login Successful!");
                if (username.equalsIgnoreCase("owner")) {
                    new OwnerDashboard();
                } else {
                    new UserDashboard();
                }
                dispose();
            } else {
                System.out.println("Invalid login credentials!");
            }
        } else if (e.getSource() == signUpButton) {
            new SignUpWindow();
        }
    }

    // Authenticate user with MySQL database
    private boolean authenticateUser(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root";
        String dbPassword = "";  // No password as per your setup

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                return rs.next();  // Returns true if credentials are correct
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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

    public static void main(String[] args) {
        new Supplier();
    }
}

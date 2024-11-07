package admin;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class SignUpWindow extends Frame implements ActionListener, WindowListener {
    TextField usernameField, emailField, passwordField;
    Button submitButton;

    SignUpWindow() {
        setTitle("Sign-Up Page");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setBackground(new Color(100, 100, 100));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Title
        Label titleLabel = new Label("Sign Up Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Create a panel with white background for the form
        Panel formPanel = new Panel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 300));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(usernameLabel, formGbc);

        usernameField = new TextField(20);
        formGbc.gridx = 1;
        formPanel.add(usernameField, formGbc);

        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(emailLabel, formGbc);

        emailField = new TextField(20);
        formGbc.gridx = 1;
        formPanel.add(emailField, formGbc);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formPanel.add(passwordLabel, formGbc);

        passwordField = new TextField(20);
        passwordField.setEchoChar('*');
        formGbc.gridx = 1;
        formPanel.add(passwordField, formGbc);

        // Submit Button
        submitButton = new Button("Sign Up");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        formGbc.gridy = 3;
        formPanel.add(submitButton, formGbc);
        submitButton.addActionListener(this);

        // Add the form panel to the main layout
        gbc.gridy = 1;
        add(formPanel, gbc);

        addWindowListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Store data in the database
            saveUserToDatabase(username, email, password);

            System.out.println("Sign-Up Successful for: " + username);
            dispose();  // Close Sign-Up window
        }
    }

    // Method to save user details to the database
    private void saveUserToDatabase(String username, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/ecommerce_db";
        String dbUsername = "root";
        String dbPassword = "";  // Empty password as per your setup

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.executeUpdate();
                System.out.println("User added to database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

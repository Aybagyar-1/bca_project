package view;

import javax.swing.*;
import model.Member;
import java.awt.*;

//package view;

//import javax.swing.*;
//import java.awt.*;
import java.util.Date;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {
        setTitle("Cooperative Society Login");
        setSize(400, 380); // Increased height for register button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
            Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
            logoLabel.setHorizontalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            logoLabel.setText("Logo not found");
            System.err.println("Error loading logo: " + e.getMessage());
        }
        mainPanel.add(logoLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Email/Username:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        loginButton = new JButton("Login");
        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        registerButton = new JButton("Register");
        formPanel.add(new JLabel());
        formPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    // Method to show registration dialog
    public void showRegistrationDialog() {
        JDialog dialog = new JDialog(this, "Register New Member", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField surnameField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField middleNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(new JLabel("Surname:"));
        formPanel.add(surnameField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Middle Name (optional):"));
        formPanel.add(middleNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton submitButton = new JButton("Submit");
        formPanel.add(new JLabel());
        formPanel.add(submitButton);

        dialog.add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String surname = surnameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String middleName = middleNameField.getText().trim().isEmpty() ? null : middleNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (surname.isEmpty() || firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create Member object
            Member member = new Member(surname, firstName, middleName, email, phone, password, new Date(), "active");
            // Notify controller (handled in LoginController)
            firePropertyChange("registerMember", null, member);
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // Getter methods for controller
    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}

/*public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("CT & LS Jos Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email/Username:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
    }

    // Getter methods for controller
    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}*/
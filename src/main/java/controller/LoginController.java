package controller;

import view.LoginFrame;
import view.AdminDashboard;
import view.MemberDashboard;
import dao.AdminDAO;
import dao.MemberDAO;
import model.Admin;
import model.Member;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class LoginController {
    private LoginFrame loginFrame;
    private AdminDAO adminDAO;
    private MemberDAO memberDAO;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.adminDAO = new AdminDAO();
        this.memberDAO = new MemberDAO();

        initListeners();
    }

    private void initListeners() {
        loginFrame.getLoginButton().addActionListener(e -> handleLogin());
        loginFrame.getRegisterButton().addActionListener(e -> loginFrame.showRegistrationDialog());
        loginFrame.addPropertyChangeListener("registerMember", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Member member = (Member) evt.getNewValue();
                handleRegistration(member);
            }
        });
    }

    private void handleLogin() {
        String emailOrUsername = loginFrame.getEmailField().getText().trim();
        String password = new String(loginFrame.getPasswordField().getPassword()).trim();

        Admin admin = adminDAO.getAdminByUsername(emailOrUsername);
        if (admin != null && admin.getPassword().equals(hashPassword(password))) {
            loginFrame.dispose();
            new AdminDashboard().setVisible(true);
            return;
        }

        Member member = memberDAO.getMemberByEmail(emailOrUsername);
        if (member != null && member.getPassword().equals(hashPassword(password))) {
            loginFrame.dispose();
            new MemberDashboard(member).setVisible(true);
            return;
        }

        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleRegistration(Member member) {
        try {
            boolean success = memberDAO.registerMember(member);
            if (success) {
                JOptionPane.showMessageDialog(loginFrame, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginFrame, "Registration error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
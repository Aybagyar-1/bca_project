package controller;

import dao.AdminDAO;
import dao.MemberDAO;
import model.Admin;
import model.Member;
import view.AdminDashboard;
import view.MemberDashboard;
import view.LoginFrame;
import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private LoginFrame loginFrame;
    private AdminDAO adminDAO;
    private MemberDAO memberDAO;

    public LoginController(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.adminDAO = new AdminDAO();
        this.memberDAO = new MemberDAO();
        initActions();
    }

    private void initActions() {
        loginFrame.getLoginButton().addActionListener(e -> login());
    }

    public void login() {
        String emailOrUsername = loginFrame.getEmailField().getText().trim();
        String password = new String(loginFrame.getPasswordField().getPassword()).trim();

        if (emailOrUsername.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, "Please enter both email/username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Try admin login
            Admin admin = adminDAO.loginAdmin(emailOrUsername, password);
            if (admin != null) {
                loginFrame.dispose();
                new AdminDashboard().setVisible(true);
                return;
            }

            // Try member login
            Member member = memberDAO.loginMember(emailOrUsername, password);
            if (member != null) {
                loginFrame.dispose();
                new MemberDashboard(member).setVisible(true);
                return;
            }

            JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginFrame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
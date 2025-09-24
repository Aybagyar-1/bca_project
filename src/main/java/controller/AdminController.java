package controller;

import dao.LoanDAO;
import dao.NoticeDAO;
import model.Loan;
import model.Notice;
import view.AdminDashboard;
import javax.swing.*;

import java.awt.GridLayout;
import java.sql.SQLException;

public class AdminController {
    private AdminDashboard adminDashboard;
    private LoanDAO loanDAO;
    private NoticeDAO noticeDAO;

    public AdminController(AdminDashboard adminDashboard) {
        this.adminDashboard = adminDashboard;
        this.loanDAO = new LoanDAO();
        this.noticeDAO = new NoticeDAO();
        initActions();
    }

    private void initActions() {
        adminDashboard.getApproveLoanButton().addActionListener(e -> approveLoan());
        adminDashboard.getDenyLoanButton().addActionListener(e -> denyLoan());
        adminDashboard.getAddNoticeButton().addActionListener(e -> addNotice());
    }

    private void approveLoan() {
        JTable loanTable = adminDashboard.getLoanTable();
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(adminDashboard, "Please select a loan", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int loanId = (int) loanTable.getValueAt(selectedRow, 0);
        double amountRequested = (double) loanTable.getValueAt(selectedRow, 2);

        String input = JOptionPane.showInputDialog(adminDashboard, "Enter approved amount:", amountRequested);
        try {
            double amountApproved = Double.parseDouble(input);
            if (amountApproved <= 0) {
                JOptionPane.showMessageDialog(adminDashboard, "Approved amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            loanDAO.updateLoanStatus(loanId, amountApproved, "approved");
            adminDashboard.loadPendingLoans();
            JOptionPane.showMessageDialog(adminDashboard, "Loan approved successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(adminDashboard, "Invalid amount entered", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(adminDashboard, "Error approving loan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void denyLoan() {
        JTable loanTable = adminDashboard.getLoanTable();
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(adminDashboard, "Please select a loan", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int loanId = (int) loanTable.getValueAt(selectedRow, 0);
        try {
            loanDAO.updateLoanStatus(loanId, 0.0, "denied");
            adminDashboard.loadPendingLoans();
            JOptionPane.showMessageDialog(adminDashboard, "Loan denied successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(adminDashboard, "Error denying loan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNotice() {
        JTextField titleField = new JTextField();
        JTextArea descArea = new JTextArea(5, 20);
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descArea));
        int result = JOptionPane.showConfirmDialog(adminDashboard, panel, "Add Notice", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String description = descArea.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(adminDashboard, "Title is required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Notice notice = new Notice(title, description);
                noticeDAO.addNotice(notice);
                adminDashboard.loadNotices();
                JOptionPane.showMessageDialog(adminDashboard, "Notice added successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(adminDashboard, "Error adding notice: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

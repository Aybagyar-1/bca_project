package controller;

import dao.LoanDAO;
import dao.SavingDAO;
import dao.RepaymentDAO;
import model.Loan;
import model.Saving;
import model.Repayment;
import view.MemberDashboard;
import javax.swing.*;

import java.awt.GridLayout;
import java.sql.SQLException;

public class MemberController {
    private MemberDashboard memberDashboard;
    private LoanDAO loanDAO;
    private SavingDAO savingDAO;
    private RepaymentDAO repaymentDAO;

    public MemberController(MemberDashboard memberDashboard) {
        this.memberDashboard = memberDashboard;
        this.loanDAO = new LoanDAO();
        this.savingDAO = new SavingDAO();
        this.repaymentDAO = new RepaymentDAO();
        initActions();
    }

    private void initActions() {
        memberDashboard.getAddSavingsButton().addActionListener(e -> addSavings());
        memberDashboard.getApplyLoanButton().addActionListener(e -> applyLoan());
        memberDashboard.getAddRepaymentButton().addActionListener(e -> makeRepayment());
    }

    private void addSavings() {
        JTextField amountField = new JTextField();
        JTextField descField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        int result = JOptionPane.showConfirmDialog(memberDashboard, panel, "Add Savings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(memberDashboard, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String description = descField.getText().trim();
                Saving saving = new Saving(memberDashboard.getMember().getMemberId(), amount, description);
                savingDAO.addSaving(saving);
                memberDashboard.loadSavings();
                JOptionPane.showMessageDialog(memberDashboard, "Savings added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Invalid amount entered", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Error adding savings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyLoan() {
        JTextField amountField = new JTextField();
        JTextField monthsField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Amount Requested:"));
        panel.add(amountField);
        panel.add(new JLabel("Repayment Months:"));
        panel.add(monthsField);
        int result = JOptionPane.showConfirmDialog(memberDashboard, panel, "Apply for Loan", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                int months = Integer.parseInt(monthsField.getText().trim());
                if (amount <= 0 || months <= 0) {
                    JOptionPane.showMessageDialog(memberDashboard, "Amount and months must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Loan loan = new Loan(memberDashboard.getMember().getMemberId(), amount, months);
                loanDAO.applyLoan(loan);
                memberDashboard.loadLoans();
                JOptionPane.showMessageDialog(memberDashboard, "Loan application submitted!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Invalid input entered", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Error applying for loan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeRepayment() {
        JTextField loanIdField = new JTextField();
        JTextField amountField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Loan ID:"));
        panel.add(loanIdField);
        panel.add(new JLabel("Repayment Amount:"));
        panel.add(amountField);
        int result = JOptionPane.showConfirmDialog(memberDashboard, panel, "Make Repayment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int loanId = Integer.parseInt(loanIdField.getText().trim());
                double amount = Double.parseDouble(amountField.getText().trim());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(memberDashboard, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Optional: Verify loan belongs to member
                Repayment repayment = new Repayment(loanId, amount);
                repaymentDAO.addRepayment(repayment);
                memberDashboard.loadRepayments();
                JOptionPane.showMessageDialog(memberDashboard, "Repayment recorded successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Invalid input entered", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(memberDashboard, "Error recording repayment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

package dao;

import model.Loan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class LoanDAO {
    // Apply for a new loan
    public boolean applyLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loans (member_id, amount_requested, repayment_months, status, application_date) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getMemberId());
            stmt.setDouble(2, loan.getAmountRequested());
            stmt.setInt(3, loan.getRepaymentMonths());
            stmt.setString(4, loan.getStatus());
            stmt.setDate(5, new Date(loan.getApplicationDate().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Approve or deny a loan
    public boolean updateLoanStatus(int loanId, double amountApproved, String status) throws SQLException {
        String sql = "UPDATE Loans SET amount_approved = ?, status = ? WHERE loan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amountApproved);
            stmt.setString(2, status);
            stmt.setInt(3, loanId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Get all loans for a member
    public List<Loan> getLoansByMember(int memberId) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("loan_id"),
                    rs.getInt("member_id"),
                    rs.getDouble("amount_requested"),
                    rs.getObject("amount_approved") != null ? rs.getDouble("amount_approved") : null,
                    rs.getInt("repayment_months"),
                    rs.getString("status"),
                    rs.getDate("application_date")
                ));
            }
        }
        return loans;
    }

    // Get all pending loans (for admin)
    public List<Loan> getPendingLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE status = 'pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("loan_id"),
                    rs.getInt("member_id"),
                    rs.getDouble("amount_requested"),
                    rs.getObject("amount_approved") != null ? rs.getDouble("amount_approved") : null,
                    rs.getInt("repayment_months"),
                    rs.getString("status"),
                    rs.getDate("application_date")
                ));
            }
        }
        return loans;
    }
}
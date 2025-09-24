package dao;

import model.Repayment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RepaymentDAO {
    // Add a new repayment
    public boolean addRepayment(Repayment repayment) throws SQLException {
        String sql = "INSERT INTO Repayments (loan_id, amount, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, repayment.getLoanId());
            stmt.setDouble(2, repayment.getAmount());
            stmt.setDate(3, new Date(repayment.getDate().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Get repayments for a specific loan
    public List<Repayment> getRepaymentsByLoan(int loanId) throws SQLException {
        List<Repayment> repayments = new ArrayList<>();
        String sql = "SELECT * FROM Repayments WHERE loan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                repayments.add(new Repayment(
                    rs.getInt("repayment_id"),
                    rs.getInt("loan_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date")
                ));
            }
        }
        return repayments;
    }

    // Get total repaid amount for a loan
    public double getTotalRepaid(int loanId) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM Repayments WHERE loan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        }
    }

    // Get all repayments (for admin dashboard)
    public List<Repayment> getAllRepayments() throws SQLException {
        List<Repayment> repayments = new ArrayList<>();
        String sql = "SELECT * FROM Repayments";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                repayments.add(new Repayment(
                    rs.getInt("repayment_id"),
                    rs.getInt("loan_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date")
                ));
            }
        }
        return repayments;
    }

    // Get repayments for a specific member (for member dashboard)
    public List<Repayment> getRepaymentsByMember(int memberId) throws SQLException {
        List<Repayment> repayments = new ArrayList<>();
        String sql = "SELECT r.* FROM Repayments r JOIN Loans l ON r.loan_id = l.loan_id WHERE l.member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                repayments.add(new Repayment(
                    rs.getInt("repayment_id"),
                    rs.getInt("loan_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date")
                ));
            }
        }
        return repayments;
    }
}
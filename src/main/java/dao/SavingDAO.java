package dao;

import model.Saving;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SavingDAO {
    // Add a new savings entry
    public boolean addSaving(Saving saving) throws SQLException {
        String sql = "INSERT INTO Savings (member_id, amount, date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, saving.getMemberId());
            stmt.setDouble(2, saving.getAmount());
            stmt.setDate(3, new Date(saving.getDate().getTime()));
            stmt.setString(4, saving.getDescription());
            return stmt.executeUpdate() > 0;
        }
    }

    // Get savings for a specific member
    public List<Saving> getSavingsByMember(int memberId) throws SQLException {
        List<Saving> savings = new ArrayList<>();
        String sql = "SELECT * FROM Savings WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                savings.add(new Saving(
                    rs.getInt("saving_id"),
                    rs.getInt("member_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date"),
                    rs.getString("description")
                ));
            }
        }
        return savings;
    }

    // Get total savings for a member
    public double getTotalSavings(int memberId) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM Savings WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        }
    }

    // Get all savings (for admin dashboard)
    public List<Saving> getAllSavings() throws SQLException {
        List<Saving> savings = new ArrayList<>();
        String sql = "SELECT * FROM Savings";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                savings.add(new Saving(
                    rs.getInt("saving_id"),
                    rs.getInt("member_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date"),
                    rs.getString("description")
                ));
            }
        }
        return savings;
    }
}
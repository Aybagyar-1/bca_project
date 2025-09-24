package dao;

import model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;

public class MemberDAO {
    // Register a new member
    public boolean registerMember(Member member) throws SQLException {
        String sql = "INSERT INTO Members (surname, first_name, middle_name, email, phone, password, join_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, CURDATE(), 'active')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getSurname());
            stmt.setString(2, member.getFirstName());
            stmt.setString(3, member.getMiddleName());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getPhone());
            stmt.setString(6, hashPassword(member.getPassword()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Authenticate a member
    public Member loginMember(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Members WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                    rs.getInt("member_id"),
                    rs.getString("surname"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getDate("join_date"),
                    rs.getString("status")
                );
            }
            return null;
        }
    }

    // Get all members (for admin dashboard)
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Members";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("member_id"),
                    rs.getString("surname"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password"),
                    rs.getDate("join_date"),
                    rs.getString("status")
                ));
            }
        }
        return members;
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
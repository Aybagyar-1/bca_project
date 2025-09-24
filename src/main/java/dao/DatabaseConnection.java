package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cooperative";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Babyna@1"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            conn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS Members (" +
                "member_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "surname VARCHAR(50) NOT NULL, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "middle_name VARCHAR(50), " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "phone VARCHAR(15), " +
                "password VARCHAR(64) NOT NULL, " +
                "join_date DATE NOT NULL, " +
                "status VARCHAR(20) DEFAULT 'active')"
            );
            // Similar CREATE TABLE statements for Savings, Loans, Repayments, Admins, Notices
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

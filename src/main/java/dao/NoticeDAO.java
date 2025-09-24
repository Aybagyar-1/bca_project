package dao;

import model.Notice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {
    // Add a new notice
    public boolean addNotice(Notice notice) throws SQLException {
        String sql = "INSERT INTO Notices (title, description, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notice.getTitle());
            stmt.setString(2, notice.getDescription());
            stmt.setDate(3, new Date(notice.getDate().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Get all notices
    public List<Notice> getAllNotices() throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = "SELECT * FROM Notices ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notices.add(new Notice(
                    rs.getInt("notice_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("date")
                ));
            }
        }
        return notices;
    }
}

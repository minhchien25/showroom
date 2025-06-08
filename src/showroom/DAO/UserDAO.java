// File: ShowRoom/src/showroom/DAO/UserDAO.java
package showroom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // <--- Dòng quan trọng đã được sửa lại
import java.util.ArrayList;
import java.util.List;
import showroom.model.User;
import showroom.util.DatabaseConnection;

public class UserDAO {

 
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); // Thường không cần lấy lại pass
                    user.setFullName(rs.getString("full_name"));
                    user.setRole(rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    
    public boolean registerUser(User user) {
        String sql = "INSERT INTO Users(username, password, full_name, role) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi đăng ký người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Nếu rs.next() là true, có nghĩa là đã tìm thấy ít nhất 1 dòng
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra username: " + e.getMessage());
            e.printStackTrace();
        }
        // Trả về true để an toàn, tránh việc tạo trùng user nếu có lỗi CSDL
        return true; 
    }
    // --- THÊM CÁC PHƯƠNG THỨC NÀY VÀO FILE UserDAO.java ---

    
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                // Chúng ta không lấy mật khẩu để đảm bảo an toàn
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean updateUserRole(int userId, String newRole) {
        String sql = "UPDATE Users SET role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newRole);
            pstmt.setInt(2, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
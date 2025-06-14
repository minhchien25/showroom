package showroom.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Thông tin kết nối tới CSDL MySQL trong XAMPP
    private static final String DB_URL = "jdbc:mysql://localhost:3306/showroom_db";
    private static final String USER = "root"; // User mặc định của XAMPP
    private static final String PASS = "";     // Mật khẩu mặc định của XAMPP là rỗng

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Không cần Class.forName() với các JDBC driver hiện đại
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối CSDL MySQL: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console để dễ debug
        }
        return conn;
    }
}
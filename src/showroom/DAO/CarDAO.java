package showroom.DAO;

import showroom.model.Car; //
import showroom.util.DatabaseConnection; //

import java.sql.Connection; //
import java.sql.PreparedStatement; //
import java.sql.ResultSet; //
import java.sql.SQLException; //
import java.sql.Statement; //
import java.util.ArrayList; //
import java.util.List; //

public class CarDAO {

    public boolean addCar(Car car) {
        // Đã thay đổi tên cột để khớp với các thuộc tính của mô hình Car hơn
        String sql = "INSERT INTO Cars (car_name, manufacturer, year_of_manufacture, color, model_type, selling_price, quantity_in_stock, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; //
        try (Connection conn = DatabaseConnection.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //
            pstmt.setString(1, car.getCarName()); // Đã thay đổi từ getMake()
            pstmt.setString(2, car.getManufacturer()); // Đã thay đổi từ getModel()
            pstmt.setInt(3, car.getYearOfManufacture()); // Đã thay đổi từ getYear()
            pstmt.setString(4, car.getColor()); // Đã thêm
            pstmt.setString(5, car.getModelType()); // Đã thêm
            pstmt.setDouble(6, car.getSellingPrice()); // Đã thay đổi từ getPrice()
            pstmt.setInt(7, car.getQuantityInStock()); // Đã thay đổi từ getQuantity()
            pstmt.setString(8, car.getDescription()); // Đã thêm

            int affectedRows = pstmt.executeUpdate(); //

            if (affectedRows > 0) { //
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) { //
                    if (generatedKeys.next()) { //
                        car.setId(generatedKeys.getInt(1)); // Đã thay đổi từ setCarId()
                    }
                }
                return true; //
            }
        } catch (SQLException e) { //
            System.err.println("Lỗi khi thêm xe vào cơ sở dữ liệu: " + e.getMessage()); // Lỗi cụ thể hơn
            e.printStackTrace(); //
        }
        return false; //
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>(); //
        // Đã thay đổi tên cột để khớp với các thuộc tính của mô hình Car hơn
        String sql = "SELECT car_id, car_name, manufacturer, year_of_manufacture, color, model_type, selling_price, quantity_in_stock, description FROM Cars"; //
        try (Connection conn = DatabaseConnection.getConnection(); //
             Statement stmt = conn.createStatement(); //
             ResultSet rs = stmt.executeQuery(sql)) { //
            while (rs.next()) { //
                Car car = new Car(); //
                car.setId(rs.getInt("car_id")); // Đã thay đổi từ setCarId()
                car.setCarName(rs.getString("car_name")); // Đã thay đổi từ setMake()
                car.setManufacturer(rs.getString("manufacturer")); // Đã thay đổi từ setModel()
                car.setYearOfManufacture(rs.getInt("year_of_manufacture")); // Đã thay đổi từ setYear()
                car.setColor(rs.getString("color")); // Đã thêm
                car.setModelType(rs.getString("model_type")); // Đã thêm
                car.setSellingPrice(rs.getDouble("selling_price")); // Đã thay đổi từ setPrice()
                car.setQuantityInStock(rs.getInt("quantity_in_stock")); // Đã thay đổi từ setQuantity()
                car.setDescription(rs.getString("description")); // Đã thêm
                cars.add(car); //
            }
        } catch (SQLException e) { //
            System.err.println("Lỗi khi lấy danh sách xe từ cơ sở dữ liệu: " + e.getMessage()); // Lỗi cụ thể hơn
            e.printStackTrace(); //
        }
        return cars; //
    }

    public boolean updateCar(Car car) {
        // Đã thay đổi tên cột để khớp với các thuộc tính của mô hình Car hơn
        String sql = "UPDATE Cars SET car_name = ?, manufacturer = ?, year_of_manufacture = ?, color = ?, model_type = ?, selling_price = ?, quantity_in_stock = ?, description = ? WHERE car_id = ?"; //
        try (Connection conn = DatabaseConnection.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //
            pstmt.setString(1, car.getCarName()); // Đã thay đổi từ getMake()
            pstmt.setString(2, car.getManufacturer()); // Đã thay đổi từ getModel()
            pstmt.setInt(3, car.getYearOfManufacture()); // Đã thay đổi từ getYear()
            pstmt.setString(4, car.getColor()); // Đã thêm
            pstmt.setString(5, car.getModelType()); // Đã thêm
            pstmt.setDouble(6, car.getSellingPrice()); // Đã thay đổi từ getPrice()
            pstmt.setInt(7, car.getQuantityInStock()); // Đã thay đổi từ getQuantity()
            pstmt.setString(8, car.getDescription()); // Đã thêm
            pstmt.setInt(9, car.getId()); // Đã thay đổi từ getCarId()
            return pstmt.executeUpdate() > 0; //
        } catch (SQLException e) { //
            System.err.println("Lỗi khi cập nhật xe trong cơ sở dữ liệu: " + e.getMessage()); // Lỗi cụ thể hơn
            e.printStackTrace(); //
        }
        return false; //
    }

    public boolean deleteCar(int carId) {
        String sql = "DELETE FROM Cars WHERE car_id = ?"; //
        try (Connection conn = DatabaseConnection.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //
            pstmt.setInt(1, carId); //
            return pstmt.executeUpdate() > 0; //
        } catch (SQLException e) { //
            System.err.println("Lỗi khi xóa xe khỏi cơ sở dữ liệu: " + e.getMessage()); // Lỗi cụ thể hơn
            e.printStackTrace(); //
        }
        return false; //
    }

    public Car getCarById(int carId) {
        // Đã thay đổi tên cột để khớp với các thuộc tính của mô hình Car hơn
        String sql = "SELECT car_id, car_name, manufacturer, year_of_manufacture, color, model_type, selling_price, quantity_in_stock, description FROM Cars WHERE car_id = ?"; //
        Car car = null; //
        try (Connection conn = DatabaseConnection.getConnection(); //
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //
            pstmt.setInt(1, carId); //
            try (ResultSet rs = pstmt.executeQuery()) { //
                if (rs.next()) { //
                    car = new Car(); //
                    car.setId(rs.getInt("car_id")); // Đã thay đổi từ setCarId()
                    car.setCarName(rs.getString("car_name")); // Đã thay đổi từ setMake()
                    car.setManufacturer(rs.getString("manufacturer")); // Đã thay đổi từ setModel()
                    car.setYearOfManufacture(rs.getInt("year_of_manufacture")); // Đã thay đổi từ setYear()
                    car.setColor(rs.getString("color")); // Đã thêm
                    car.setModelType(rs.getString("model_type")); // Đã thêm
                    car.setSellingPrice(rs.getDouble("selling_price")); // Đã thay đổi từ setPrice()
                    car.setQuantityInStock(rs.getInt("quantity_in_stock")); // Đã thay đổi từ setQuantity()
                    car.setDescription(rs.getString("description")); // Đã thêm
                }
            }
        } catch (SQLException e) { //
            System.err.println("Lỗi khi tìm xe theo ID trong cơ sở dữ liệu: " + e.getMessage()); // Lỗi cụ thể hơn
            e.printStackTrace(); //
        }
        return car; //
    }
}
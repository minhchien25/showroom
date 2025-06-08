// File: ShowRoom/src/showroom/DAO/CarDAO.java
package showroom.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import showroom.model.Car;
import showroom.util.DatabaseConnection;

public class CarDAO {

    
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM Cars";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("car_id"));
                car.setCarName(rs.getString("car_name"));
                car.setManufacturer(rs.getString("manufacturer"));
                car.setYearOfManufacture(rs.getInt("year_of_manufacture"));
                car.setColor(rs.getString("color"));
                car.setModelType(rs.getString("model_type"));
                car.setSellingPrice(rs.getDouble("selling_price"));
                car.setQuantityInStock(rs.getInt("quantity_in_stock"));
                car.setDescription(rs.getString("description"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách xe: " + e.getMessage());
            e.printStackTrace();
        }
        return cars;
    }

    
    public void addCar(Car car) {
        String sql = "INSERT INTO Cars(car_name, manufacturer, year_of_manufacture, color, model_type, selling_price, quantity_in_stock, description) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getCarName());
            pstmt.setString(2, car.getManufacturer());
            pstmt.setInt(3, car.getYearOfManufacture());
            pstmt.setString(4, car.getColor());
            pstmt.setString(5, car.getModelType());
            pstmt.setDouble(6, car.getSellingPrice());
            pstmt.setInt(7, car.getQuantityInStock());
            pstmt.setString(8, car.getDescription());

            pstmt.executeUpdate();
            System.out.println("Đã thêm xe mới thành công vào CSDL!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm xe vào CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void updateCar(Car car) {
        String sql = "UPDATE Cars SET car_name = ?, manufacturer = ?, year_of_manufacture = ?, " +
                     "color = ?, model_type = ?, selling_price = ?, quantity_in_stock = ?, " +
                     "description = ? WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getCarName());
            pstmt.setString(2, car.getManufacturer());
            pstmt.setInt(3, car.getYearOfManufacture());
            pstmt.setString(4, car.getColor());
            pstmt.setString(5, car.getModelType());
            pstmt.setDouble(6, car.getSellingPrice());
            pstmt.setInt(7, car.getQuantityInStock());
            pstmt.setString(8, car.getDescription());
            pstmt.setInt(9, car.getId()); // car_id cho điều kiện WHERE

            pstmt.executeUpdate();
            System.out.println("Đã cập nhật xe có ID = " + car.getId() + " trong CSDL!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật xe trong CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void deleteCar(int carId) {
        String sql = "DELETE FROM Cars WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, carId);
            pstmt.executeUpdate();
            System.out.println("Đã xóa xe có ID = " + carId + " khỏi CSDL!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa xe khỏi CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    public Car getCarById(int carId) {
        String sql = "SELECT * FROM Cars WHERE car_id = ?";
        Car car = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                car = new Car();
                car.setId(rs.getInt("car_id"));
                car.setCarName(rs.getString("car_name"));
                car.setManufacturer(rs.getString("manufacturer"));
                car.setYearOfManufacture(rs.getInt("year_of_manufacture"));
                car.setColor(rs.getString("color"));
                car.setModelType(rs.getString("model_type"));
                car.setSellingPrice(rs.getDouble("selling_price"));
                car.setQuantityInStock(rs.getInt("quantity_in_stock"));
                car.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm xe theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return car;
    }

    // Hàm main để kiểm tra nhanh (bạn có thể giữ lại hoặc xóa đi tùy ý)
    public static void main(String[] args) {
        CarDAO carDAO = new CarDAO();

        System.out.println("Đang lấy danh sách xe từ CSDL...");
        List<Car> carList = carDAO.getAllCars();

        if (carList.isEmpty()) {
            System.out.println("Không tìm thấy xe nào trong CSDL. (Hãy chắc chắn bạn đã thêm dữ liệu mẫu)");
        } else {
            System.out.println("Tìm thấy " + carList.size() + " chiếc xe.");
            for (Car car : carList) {
                System.out.println(car.toString());
            }
        }

     
    }
}
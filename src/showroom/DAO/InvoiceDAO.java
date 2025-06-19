package showroom.DAO;

import showroom.model.Invoices; //
import showroom.model.InvoiceDetail; //
import showroom.model.Car; //
import showroom.model.Customers; //
import showroom.model.User; // Import User model
import showroom.util.DatabaseConnection; //

import java.sql.Connection; //
import java.sql.PreparedStatement; //
import java.sql.ResultSet; //
import java.sql.SQLException; //
import java.sql.Statement; //
import java.util.ArrayList; //
import java.util.List; //
import java.util.Date; //

public class InvoiceDAO {

    public boolean addInvoice(Invoices invoice) {
        String sqlInvoice = "INSERT INTO Invoices (customer_id, user_id, invoice_date, total_amount) VALUES (?, ?, ?, ?)"; // Đã thêm user_id
        String sqlInvoiceDetail = "INSERT INTO InvoiceDetail (invoice_id, car_id, quantity, price_at_sale) VALUES (?, ?, ?, ?)"; // Đã đổi unit_price thành price_at_sale
        // Giả sử bạn sẽ cập nhật số lượng xe trong một phương thức CarDAO riêng hoặc xử lý tại đây
        String sqlUpdateCarStock = "UPDATE Cars SET quantity_in_stock = quantity_in_stock - ? WHERE car_id = ?"; //

        Connection conn = null; //
        try {
            conn = DatabaseConnection.getConnection(); //
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Chèn hóa đơn
            try (PreparedStatement pstmtInvoice = conn.prepareStatement(sqlInvoice, Statement.RETURN_GENERATED_KEYS)) { //
                pstmtInvoice.setInt(1, invoice.getCustomerId()); //
                pstmtInvoice.setInt(2, invoice.getUserId()); // Đặt UserId
                pstmtInvoice.setTimestamp(3, new java.sql.Timestamp(invoice.getInvoiceDate().getTime())); //
                pstmtInvoice.setDouble(4, invoice.getTotalAmount()); //

                int affectedRows = pstmtInvoice.executeUpdate(); //
                if (affectedRows == 0) { //
                    throw new SQLException("Tạo hóa đơn thất bại, không có hàng nào bị ảnh hưởng."); //
                }

                try (ResultSet generatedKeys = pstmtInvoice.getGeneratedKeys()) { //
                    if (generatedKeys.next()) { //
                        invoice.setInvoiceId(generatedKeys.getInt(1)); //
                    } else {
                        throw new SQLException("Tạo hóa đơn thất bại, không lấy được ID."); //
                    }
                }
            }

            // 2. Chèn chi tiết hóa đơn và cập nhật số lượng xe
            for (InvoiceDetail detail : invoice.getInvoiceDetails()) { //
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlInvoiceDetail)) { //
                    pstmtDetail.setInt(1, invoice.getInvoiceId()); //
                    pstmtDetail.setInt(2, detail.getCarId()); //
                    pstmtDetail.setInt(3, detail.getQuantity()); //
                    pstmtDetail.setDouble(4, detail.getPriceAtSale()); // Đã thay đổi getUnitPrice()
                    pstmtDetail.executeUpdate(); //
                }

                // Cập nhật số lượng xe trong kho
                try (PreparedStatement pstmtUpdateCar = conn.prepareStatement(sqlUpdateCarStock)) { //
                    pstmtUpdateCar.setInt(1, detail.getQuantity()); //
                    pstmtUpdateCar.setInt(2, detail.getCarId()); //
                    pstmtUpdateCar.executeUpdate(); //
                }
            }

            conn.commit(); // Hoàn tất giao dịch
            return true; //

        } catch (SQLException e) { //
            if (conn != null) { //
                try {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) { //
                    ex.printStackTrace(); //
                }
            }
            e.printStackTrace(); //
        } finally {
            if (conn != null) { //
                try {
                    conn.setAutoCommit(true); // Trả lại auto-commit
                    conn.close(); //
                } catch (SQLException e) { //
                    e.printStackTrace(); //
                }
            }
        }
        return false; //
    }

    public List<Invoices> getAllInvoices() {
        List<Invoices> invoices = new ArrayList<>(); //
        // Nối với bảng Customers và Users để lấy đầy đủ chi tiết
        String sql = "SELECT i.invoice_id, i.customer_id, i.user_id, i.invoice_date, i.total_amount, " +
                     "cust.full_name AS customer_full_name, cust.address, cust.phone_number, cust.email, " +
                     "u.username AS user_username, u.full_name AS user_full_name " +
                     "FROM Invoices i " +
                     "JOIN Customers cust ON i.customer_id = cust.customer_id " +
                     "JOIN Users u ON i.user_id = u.user_id " + // Nối với bảng Users
                     "ORDER BY i.invoice_date DESC"; //

        String sqlDetail = "SELECT id.invoice_detail_id, id.car_id, id.quantity, id.price_at_sale, " + // Đã đổi unit_price
                           "c.car_name, c.manufacturer, c.year_of_manufacture, c.color, c.model_type, c.selling_price " + // Lấy tất cả chi tiết xe
                           "FROM InvoiceDetail id JOIN Cars c ON id.car_id = c.car_id WHERE id.invoice_id = ?"; //

        try (Connection conn = DatabaseConnection.getConnection(); //
             Statement stmt = conn.createStatement(); //
             ResultSet rs = stmt.executeQuery(sql)) { //

            while (rs.next()) { //
                Invoices invoice = new Invoices(); //
                invoice.setInvoiceId(rs.getInt("invoice_id")); //
                invoice.setCustomerId(rs.getInt("customer_id")); //
                invoice.setUserId(rs.getInt("user_id"));
                invoice.setInvoiceDate(rs.getTimestamp("invoice_date")); //
                invoice.setTotalAmount(rs.getDouble("total_amount")); //

                // Đặt đối tượng Customer
                Customers customer = new Customers(); //
                customer.setCustomerId(rs.getInt("customer_id")); //
                customer.setFullName(rs.getString("customer_full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setEmail(rs.getString("email"));
                invoice.setCustomer(customer); // Đặt đối tượng khách hàng vào hóa đơn

                // Đặt đối tượng User (người xử lý hóa đơn)
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_username"));
                user.setFullName(rs.getString("user_full_name"));
                // invoice.setUser(user); // Nếu bạn thêm phương thức setUser vào mô hình Invoices

                // Tải chi tiết hóa đơn
                List<InvoiceDetail> details = new ArrayList<>(); //
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail)) { //
                    pstmtDetail.setInt(1, invoice.getInvoiceId()); //
                    try (ResultSet rsDetail = pstmtDetail.executeQuery()) { //
                        while (rsDetail.next()) { //
                            InvoiceDetail detail = new InvoiceDetail(); //
                            detail.setInvoiceDetailId(rsDetail.getInt("invoice_detail_id")); //
                            detail.setInvoiceId(invoice.getInvoiceId()); //
                            detail.setCarId(rsDetail.getInt("car_id")); //
                            detail.setQuantity(rsDetail.getInt("quantity")); //
                            detail.setPriceAtSale(rsDetail.getDouble("price_at_sale")); // Đã đổi unit_price

                            // Đặt đối tượng Car cho chi tiết hóa đơn
                            Car car = new Car();
                            car.setId(rsDetail.getInt("car_id"));
                            car.setCarName(rsDetail.getString("car_name"));
                            car.setManufacturer(rsDetail.getString("manufacturer"));
                            car.setYearOfManufacture(rsDetail.getInt("year_of_manufacture"));
                            car.setColor(rsDetail.getString("color"));
                            car.setModelType(rsDetail.getString("model_type"));
                            car.setSellingPrice(rsDetail.getDouble("selling_price"));
                            detail.setCar(car); // Giả định mô hình InvoiceDetail có setter cho đối tượng Car

                            details.add(detail); //
                        }
                    }
                }
                invoice.setInvoiceDetails(details); //
                invoices.add(invoice); //
            }
        } catch (SQLException e) { //
            e.printStackTrace(); //
        }
        return invoices; //
    }

    public boolean updateInvoice(Invoices invoice) {
        // Cập nhật bảng Invoices
        String sqlUpdateInvoice = "UPDATE Invoices SET customer_id = ?, user_id = ?, invoice_date = ?, total_amount = ? WHERE invoice_id = ?"; //
        // Xóa tất cả các chi tiết cũ và thêm lại các chi tiết mới
        String sqlDeleteDetails = "DELETE FROM InvoiceDetail WHERE invoice_id = ?"; //
        String sqlInsertDetail = "INSERT INTO InvoiceDetail (invoice_id, car_id, quantity, price_at_sale) VALUES (?, ?, ?, ?)"; // Đã đổi unit_price

        Connection conn = null; //
        try {
            conn = DatabaseConnection.getConnection(); //
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật thông tin hóa đơn chính
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateInvoice)) { //
                pstmt.setInt(1, invoice.getCustomerId()); //
                pstmt.setInt(2, invoice.getUserId());
                pstmt.setTimestamp(3, new java.sql.Timestamp(invoice.getInvoiceDate().getTime())); //
                pstmt.setDouble(4, invoice.getTotalAmount()); //
                pstmt.setInt(5, invoice.getInvoiceId()); //
                pstmt.executeUpdate(); //
            }

            // Xóa các chi tiết hóa đơn cũ
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteDetails)) { //
                pstmt.setInt(1, invoice.getInvoiceId()); //
                pstmt.executeUpdate(); //
            }

            // Thêm lại các chi tiết hóa đơn mới
            for (InvoiceDetail detail : invoice.getInvoiceDetails()) { //
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertDetail)) { //
                    pstmt.setInt(1, invoice.getInvoiceId()); //
                    pstmt.setInt(2, detail.getCarId()); //
                    pstmt.setInt(3, detail.getQuantity()); //
                    pstmt.setDouble(4, detail.getPriceAtSale()); // Đã đổi getUnitPrice()
                    pstmt.executeUpdate(); //
                }
            }

            conn.commit(); // Hoàn tất giao dịch
            return true; //
        } catch (SQLException e) { //
            if (conn != null) { //
                try {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) { //
                    ex.printStackTrace(); //
                }
            }
            e.printStackTrace(); //
        } finally {
            if (conn != null) { //
                try {
                    conn.setAutoCommit(true); //
                    conn.close(); //
                } catch (SQLException e) { //
                    e.printStackTrace(); //
                }
            }
        }
        return false; //
    }

    public boolean deleteInvoice(int invoiceId) {
        // Xóa chi tiết hóa đơn trước, sau đó xóa hóa đơn chính
        String sqlDeleteDetails = "DELETE FROM InvoiceDetail WHERE invoice_id = ?"; //
        String sqlDeleteInvoice = "DELETE FROM Invoices WHERE invoice_id = ?"; //

        Connection conn = null; //
        try {
            conn = DatabaseConnection.getConnection(); //
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa chi tiết hóa đơn
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteDetails)) { //
                pstmt.setInt(1, invoiceId); //
                pstmt.executeUpdate(); //
            }

            // Xóa hóa đơn chính
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteInvoice)) { //
                pstmt.setInt(1, invoiceId); //
                pstmt.executeUpdate(); //
            }

            conn.commit(); // Hoàn tất giao dịch
            return true; //
        } catch (SQLException e) { //
            if (conn != null) { //
                try {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException ex) { //
                    ex.printStackTrace(); //
                }
            }
            e.printStackTrace(); //
        } finally {
            if (conn != null) { //
                try {
                    conn.setAutoCommit(true); //
                    conn.close(); //
                } catch (SQLException e) { //
                    e.printStackTrace(); //
                }
            }
        }
        return false; //
    }
}
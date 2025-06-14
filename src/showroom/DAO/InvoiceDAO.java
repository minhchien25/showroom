package showroom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import showroom.model.InvoiceDetail;
import showroom.model.Invoices;
import showroom.util.DatabaseConnection;

public class InvoiceDAO {

    /**
     * Phương thức cốt lõi để tạo một hóa đơn mới.
     * Nó bao gồm 3 hành động trong một Transaction:
     * 1. Thêm một dòng vào bảng `invoices`.
     * 2. Thêm các dòng chi tiết vào bảng `invoice_details`.
     * 3. Cập nhật lại số lượng xe trong bảng `cars`.
     * @param invoice Thông tin hóa đơn chung.
     * @param details Danh sách các chi tiết hóa đơn (các xe được bán).
     * @return true nếu tất cả thành công, false nếu có lỗi xảy ra.
     */
    public boolean createInvoice(Invoices invoice, List<InvoiceDetail> details) {
        String sqlInvoice = "INSERT INTO invoices(customer_id, user_id, invoice_date, total_amount) VALUES(?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO invoice_details(invoice_id, car_id, quantity, price_at_sale) VALUES(?, ?, ?, ?)";
        String sqlUpdateCarStock = "UPDATE cars SET quantity_in_stock = quantity_in_stock - ? WHERE car_id = ?";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            // BẮT ĐẦU TRANSACTION: Tắt chế độ tự động commit
            conn.setAutoCommit(false);

            // --- HÀNH ĐỘNG 1: THÊM HÓA ĐƠN ---
            int generatedInvoiceId = 0;
            try (PreparedStatement pstmtInvoice = conn.prepareStatement(sqlInvoice, Statement.RETURN_GENERATED_KEYS)) {
                pstmtInvoice.setInt(1, invoice.getCustomerId());
                pstmtInvoice.setInt(2, invoice.getUserId());
                pstmtInvoice.setTimestamp(3, new java.sql.Timestamp(invoice.getInvoiceDate().getTime()));
                pstmtInvoice.setDouble(4, invoice.getTotalAmount());
                
                pstmtInvoice.executeUpdate();
                
                // Lấy ID của hóa đơn vừa được tạo
                try (ResultSet generatedKeys = pstmtInvoice.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedInvoiceId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Tạo hóa đơn thất bại, không nhận được ID.");
                    }
                }
            }

            // --- HÀNH ĐỘNG 2: THÊM CHI TIẾT HÓA ĐƠN ---
            try (PreparedStatement pstmtDetail = conn.prepareStatement(sqlDetail)) {
                for (InvoiceDetail detail : details) {
                    pstmtDetail.setInt(1, generatedInvoiceId); // Dùng ID hóa đơn vừa tạo
                    pstmtDetail.setInt(2, detail.getCarId());
                    pstmtDetail.setInt(3, detail.getQuantity());
                    pstmtDetail.setDouble(4, detail.getPriceAtSale());
                    pstmtDetail.addBatch(); // Thêm vào lô để thực thi cùng lúc
                }
                pstmtDetail.executeBatch();
            }

            // --- HÀNH ĐỘNG 3: CẬP NHẬT SỐ LƯỢNG XE TRONG KHO ---
            try (PreparedStatement pstmtUpdateCar = conn.prepareStatement(sqlUpdateCarStock)) {
                for (InvoiceDetail detail : details) {
                    pstmtUpdateCar.setInt(1, detail.getQuantity()); // Số lượng xe bán ra
                    pstmtUpdateCar.setInt(2, detail.getCarId());
                    pstmtUpdateCar.addBatch();
                }
                pstmtUpdateCar.executeBatch();
            }

            // Nếu tất cả hành động trên thành công, COMMIT TRANSACTION
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Transaction bị lỗi! Đang rollback...");
            e.printStackTrace();
            try {
                if (conn != null) {
                    // Nếu có lỗi, ROLLBACK TRANSACTION (hủy bỏ tất cả thay đổi)
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    // Luôn luôn trả lại chế độ auto commit và đóng kết nối
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
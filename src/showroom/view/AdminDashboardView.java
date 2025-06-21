
package showroom.view;
import java.awt.Image;
import java.net.URL;
import showroom.view.ThongKe;


import showroom.model.Car;
import showroom.DAO.CarDAO;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AdminDashboardView extends javax.swing.JFrame {

    private CarDAO carDAO;
    private DefaultTableModel tableModel;
   // HÃY COPY TOÀN BỘ ĐOẠN CODE NÀY
// Trong constructor của file AdminDashboardView.java

public AdminDashboardView() {
        initComponents();
        setupUIComponents(); // Gọi phương thức cài đặt giao diện
        loadDataToTable();
    }
    
    /**
     * Phương thức tập trung cho việc cài đặt giao diện và tải tài nguyên.
     */
    private void setupUIComponents() {
        this.setLocationRelativeTo(null);
        carDAO = new CarDAO();
        tableModel = (DefaultTableModel) tblCars.getModel();
        tableModel.setColumnIdentifiers(new Object[]{
            "ID", "Tên Xe", "Hãng Sản Xuất", "Năm SX", "Màu Sắc", "Kiểu Dáng", "Giá Bán", "Số Lượng Tồn", "Mô Tả",
        });

        // Đặt biểu tượng cho các nút với kích thước tùy chỉnh
        int iconSize = 24; // Kích thước chung cho các biểu tượng
        
        btnAddCar.setIcon(createScaledIcon("/showroom/resources/icons/add.png", iconSize, iconSize));
        btnEditCar.setIcon(createScaledIcon("/showroom/resources/icons/editing.png", iconSize, iconSize));
        btnDeleteCar.setIcon(createScaledIcon("/showroom/resources/icons/x-mark.png", iconSize, iconSize));
        btnQuanLyNguoiDung.setIcon(createScaledIcon("/showroom/resources/icons/profile.png", iconSize, iconSize));
        btnThongKe.setIcon(createScaledIcon("/showroom/resources/icons/progress.png", iconSize, iconSize));
    }

    /**
     * Phương thức trợ giúp để tải và thay đổi kích thước của một ImageIcon.
     * @param path Đường dẫn đến tệp biểu tượng trong classpath.
     * @param width Chiều rộng mong muốn.
     * @param height Chiều cao mong muốn.
     * @return một ImageIcon đã được thay đổi kích thước, hoặc null nếu không tìm thấy tệp.
     */
    private ImageIcon createScaledIcon(String path, int width, int height) {
        URL iconURL = getClass().getResource(path);
        if (iconURL != null) {
            ImageIcon originalIcon = new ImageIcon(iconURL);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Không tìm thấy tệp tài nguyên: " + path);
            return null;
        }
    }
private void loadDataToTable() {
    try {
        // Xóa tất cả các hàng cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách xe từ CSDL
        List<Car> carList = carDAO.getAllCars();

        // Duyệt qua danh sách và thêm từng xe vào bảng
        for (Car car : carList) {
            tableModel.addRow(new Object[]{
                car.getId(),
                car.getCarName(),
                car.getManufacturer(),
                car.getYearOfManufacture(),
                car.getColor(),
                car.getModelType(),
                car.getSellingPrice(),
                car.getQuantityInStock(),
                car.getDescription()
            });
        }
    } catch (Exception e) {
        // In lỗi ra console nếu có vấn đề
        e.printStackTrace();
    }
}
    
    

    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPanel1 = new javax.swing.JScrollPane();
        tblCars = new javax.swing.JTable();
        btnDeleteCar = new javax.swing.JButton();
        btnAddCar = new javax.swing.JButton();
        btnEditCar = new javax.swing.JButton();
        btnQuanLyNguoiDung = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("                                                              PHẦN MỀM QUẢN LÍ SHOWROOM Ô TÔ (ADMIN)");

        tblCars.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPanel1.setViewportView(tblCars);

        btnDeleteCar.setText("Xoá Xe");
        btnDeleteCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCarActionPerformed(evt);
            }
        });

        btnAddCar.setText("Thêm Xe");
        btnAddCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCarActionPerformed(evt);
            }
        });

        btnEditCar.setText("Sửa Xe");
        btnEditCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCarActionPerformed(evt);
            }
        });

        btnQuanLyNguoiDung.setText("Quản lí người dùng");
        btnQuanLyNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyNguoiDungActionPerformed(evt);
            }
        });

        btnThongKe.setText("Thống kê");
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        jLabel2.setText("CHỨC NĂNG");

        jLabel3.setText("KHU VỰC HIỂN THỊ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnDeleteCar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditCar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddCar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnQuanLyNguoiDung))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnThongKe)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddCar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditCar)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteCar)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuanLyNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThongKe))
                    .addComponent(jScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCarActionPerformed
         AddCarDialog addCarDialog = new AddCarDialog(this, true);
        addCarDialog.setVisible(true);
        loadDataToTable();
    }//GEN-LAST:event_btnAddCarActionPerformed
    
    

    
    
    private void btnDeleteCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCarActionPerformed
        int selectedRow = tblCars.getSelectedRow();

    // 2. Kiểm tra xem người dùng đã chọn hàng nào chưa
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chiếc xe để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return; // Không làm gì cả nếu chưa chọn
    }

    // 3. Hiển thị hộp thoại xác nhận trước khi xóa
    int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa chiếc xe này không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

    // 4. Nếu người dùng không đồng ý (chọn NO hoặc đóng hộp thoại) thì dừng lại
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        // 5. Lấy ID của xe từ cột đầu tiên (cột 0) của hàng đã chọn
        //    Lưu ý: Cột ID phải là cột đầu tiên trong JTable của bạn
        int carIdToDelete = (int) tblCars.getValueAt(selectedRow, 0);

        // 6. Gọi phương thức deleteCar từ CarDAO (carDAO đã được khởi tạo)
        carDAO.deleteCar(carIdToDelete); //

        // 7. Thông báo thành công và tải lại dữ liệu mới lên bảng
        JOptionPane.showMessageDialog(this, "Đã xóa xe thành công!");
        loadDataToTable(); // Gọi lại hàm để làm mới bảng

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa xe: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // In lỗi ra console để debug
    }
    }//GEN-LAST:event_btnDeleteCarActionPerformed

    private void btnEditCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCarActionPerformed
      int selectedRow = tblCars.getSelectedRow();

    // BƯỚC 2: Kiểm tra xem có hàng nào được chọn không
    if (selectedRow == -1) {
        // NẾU KHÔNG CHỌN, HIỆN THÔNG BÁO NÀY
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chiếc xe để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return; // và kết thúc
    }

    try {
        // BƯỚC 3: Lấy ID xe từ hàng đã chọn
        int carIdToEdit = Integer.parseInt(tblCars.getValueAt(selectedRow, 0).toString());

        // BƯỚC 4: Tìm xe trong database bằng ID
        Car carToEdit = carDAO.getCarById(carIdToEdit);

        // BƯỚC 5: Kiểm tra xem có tìm thấy xe không
        if (carToEdit != null) {
            // NẾU TÌM THẤY, HIỆN DIALOG SỬA XE
            EditCarDialog editDialog = new EditCarDialog(this, true);
            editDialog.loadCarData(carToEdit);
            editDialog.setVisible(true);
            loadDataToTable();
        } else {
            // NẾU KHÔNG TÌM THẤY, HIỆN THÔNG BÁO NÀY
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin xe để sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception e) {
        // NẾU CÓ LỖI BẤT KỲ, HIỆN THÔNG BÁO NÀY
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi chuẩn bị sửa xe: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // và in lỗi đỏ ra console
    }
    }//GEN-LAST:event_btnEditCarActionPerformed

    private void btnQuanLyNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyNguoiDungActionPerformed
        
        // Tạo một cửa sổ (Frame) mới để chứa panel quản lý người dùng
    JFrame userManagementFrame = new JFrame("Quản Lý Người Dùng");
    
    // Thêm UserManagementPanel vào cửa sổ
    userManagementFrame.add(new UserManagementPanel());
    
    // Thiết lập các thuộc tính cho cửa sổ
    userManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng cửa sổ này
    userManagementFrame.pack(); // Tự động điều chỉnh kích thước
    userManagementFrame.setLocationRelativeTo(this); // Hiển thị ở giữa Dashboard
    userManagementFrame.setVisible(true); // Hiển thị cửa sổ
    }//GEN-LAST:event_btnQuanLyNguoiDungActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
    ThongKe thongKeDialog = new ThongKe(this, true);
    thongKeDialog.setVisible(true);
    }//GEN-LAST:event_btnThongKeActionPerformed
    
   
    
    public void refreshCarTableData() {
    loadDataToTable();
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboardView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCar;
    private javax.swing.JButton btnDeleteCar;
    private javax.swing.JButton btnEditCar;
    private javax.swing.JButton btnQuanLyNguoiDung;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPanel1;
    private javax.swing.JTable tblCars;
    // End of variables declaration//GEN-END:variables
}

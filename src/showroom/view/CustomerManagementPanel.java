
package showroom.view;


import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import showroom.DAO.CustomerDAO;
import showroom.model.Customers;



public class CustomerManagementPanel extends javax.swing.JPanel {

      private CustomerDAO customerDAO;
    private DefaultTableModel tableModel;
    
    
    
    public CustomerManagementPanel() {
        initComponents();
        
        customerDAO = new CustomerDAO();
        tableModel = (DefaultTableModel) tblKhachHang.getModel();
        
        tableModel.setColumnIdentifiers(new Object[]{
            "ID Khách hàng", "Họ và Tên", "Địa chỉ", "Số điện thoại", "Email"
        });
        
        loadCustomersToTable();
    }

    
    private void loadCustomersToTable() {
        tableModel.setRowCount(0);
        List<Customers> list = customerDAO.getAllCustomers();
        for (Customers kh : list) {
            tableModel.addRow(new Object[]{
                kh.getCustomerId(),
                kh.getFullName(),
                kh.getAddress(),
                kh.getPhoneNumber(),
                kh.getEmail()
            });
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnThemKhachHang = new javax.swing.JButton();
        btnSuaKhachHang = new javax.swing.JButton();
        btnXoaKhachHang = new javax.swing.JButton();

        lblTitle.setText("   QUẢN LÝ KHÁCH HÀNG");

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblKhachHang);

        btnThemKhachHang.setText("Thêm Khách Hàng");
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        btnSuaKhachHang.setText("Sửa Thông Tin");
        btnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhachHangActionPerformed(evt);
            }
        });

        btnXoaKhachHang.setText("Xoá Khách Hàng");
        btnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(btnXoaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKhachHang)
                    .addComponent(btnSuaKhachHang)
                    .addComponent(btnXoaKhachHang))
                .addContainerGap(51, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
       JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddEditCustomerDialog dialog = new AddEditCustomerDialog(parentFrame, true);
        dialog.setVisible(true);
        
        // Tải lại bảng sau khi dialog đóng
        loadCustomersToTable();
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhachHangActionPerformed
      int selectedRow = tblKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int customerId = (int) tblKhachHang.getValueAt(selectedRow, 0);
        // Lấy thông tin khách hàng từ DAO (chúng ta sẽ cần thêm phương thức này)
        // Tạm thời, chúng ta sẽ tạo đối tượng từ thông tin trên bảng
        Customers customerToEdit = new Customers(
            customerId,
            (String) tblKhachHang.getValueAt(selectedRow, 1),
            (String) tblKhachHang.getValueAt(selectedRow, 2),
            (String) tblKhachHang.getValueAt(selectedRow, 3),
            (String) tblKhachHang.getValueAt(selectedRow, 4)
        );

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddEditCustomerDialog dialog = new AddEditCustomerDialog(parentFrame, true, customerToEdit);
        dialog.setVisible(true);

        // Tải lại bảng sau khi dialog đóng
        loadCustomersToTable();
    }//GEN-LAST:event_btnSuaKhachHangActionPerformed

    private void btnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhachHangActionPerformed
        int selectedRow = tblKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int customerId = (int) tblKhachHang.getValueAt(selectedRow, 0);
            if (customerDAO.deleteCustomer(customerId)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công.");
                loadCustomersToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXoaKhachHangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuaKhachHang;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnXoaKhachHang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblKhachHang;
    // End of variables declaration//GEN-END:variables
}

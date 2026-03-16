
package add;

import admin.admindashboard;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import laundry.login;
import staff.staffdashboard;


public class view extends javax.swing.JFrame {
private int hoveredRow = -1;

    private String caller;       
    private String loggedUser;   
    private Object admin;

    
 
    
public view(String caller, String loggedUser) {
    initComponents();
    this.caller = caller;
    this.loggedUser = loggedUser;

    // HEADER COLOR
    jTable1.getTableHeader().setBackground(new java.awt.Color(0,153,153));
    jTable1.getTableHeader().setForeground(java.awt.Color.BLACK);
    jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

    // ROW HEIGHT
    jTable1.setRowHeight(25);

    // CLICK SELECTION COLOR
    jTable1.setSelectionBackground(new java.awt.Color(153,204,255));
    jTable1.setSelectionForeground(java.awt.Color.BLACK);

    // HOVER EFFECT
    jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {
            int row = jTable1.rowAtPoint(e.getPoint());
            if(row != hoveredRow){
                hoveredRow = row;
                jTable1.repaint();
            }
        }
    });

    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            hoveredRow = -1;
            jTable1.repaint();
        }
    });

    // CUSTOM RENDERER
    jTable1.setDefaultRenderer(Object.class,new javax.swing.table.DefaultTableCellRenderer(){
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,Object value,boolean isSelected,
            boolean hasFocus,int row,int column){

        java.awt.Component c = super.getTableCellRendererComponent(
                table,value,isSelected,hasFocus,row,column);

        if(row == hoveredRow){
            c.setBackground(new java.awt.Color(180,200,255)); // hover light blue
        }else{
            if(row % 2 == 0){
                c.setBackground(new java.awt.Color(220,220,220)); // light gray
            }else{
                c.setBackground(new java.awt.Color(169,169,169)); // dark gray
            }
        }

        return c;
    }
});

    loadCustomers();
}

    public view() {
    throw new UnsupportedOperationException("Use view(String caller, String loggedUser) instead");
}

    
    private void setupTableListener() {
    config cfg = new config();

    jTable1.getModel().addTableModelListener(e -> {
        int row = e.getFirstRow();
        int column = e.getColumn();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        
        if (row < 0 || row >= model.getRowCount() || column < 0 || column >= model.getColumnCount()) {
            return; 
        }

        Object idObj = model.getValueAt(row, 0);
        if(idObj == null) return; 

        int customerId = (int) idObj; 
        Object newValue = model.getValueAt(row, column);

        String columnName = model.getColumnName(column);
        String dbColumn = "";
        switch(columnName){
            case "Full Name": dbColumn = "full_name"; break;
            case "Contact": dbColumn = "contact_number"; break;
            case "Address": dbColumn = "address"; break;
        }

        if(!dbColumn.isEmpty()){
            String sql = "UPDATE customers SET " + dbColumn + " = ? WHERE c_id = ?";
            cfg.updateRecord(sql, newValue.toString(), customerId);
        }
    });
}


    
    private void loadCustomers() {
    Connection conn = config.connectDB();
    if (conn == null) {
        System.out.println("Connection failed!");
        return;
    }

    String sql = "SELECT * FROM customers";

    try {
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear table first

        while(rs.next()) {
            Object[] row = new Object[] {
                rs.getInt("c_id"),
                rs.getString("full_name"),
                rs.getString("contact_number"),
                rs.getString("address"),
                rs.getString("date_added")   // ✅ add this
            };
            model.addRow(row);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel5.setBackground(new java.awt.Color(192, 237, 232));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/laundryyy-removebg-preview__1_-removebg-preview.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel6.setText("VIEW CUSTOMERS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(203, 203, 203)
                .addComponent(jLabel6)
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel6)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "c_id", "Full Name", "Contact", "Address", "Date Added"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setBackground(new java.awt.Color(192, 237, 232));
        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(192, 237, 232));
        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("DELETE");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(192, 237, 232));
        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("SEARCH");

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Back");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(29, 29, 29)
                        .addComponent(jButton3)
                        .addGap(26, 26, 26)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton4)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
                                   
    String searchText = jTextField2.getText();

    Connection conn = config.connectDB();
    if (conn == null) return;

    String sql = "SELECT * FROM customers WHERE full_name LIKE ?";

    try {
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, "%" + searchText + "%");

        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while(rs.next()) {
            Object[] row = new Object[] {
                rs.getInt("c_id"),
                rs.getString("full_name"),
                rs.getString("contact_number"),
                rs.getString("address"),
                rs.getString("date_added")   // ✅ add this too
            };
            model.addRow(row);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
                                       
     if ("admin".equals(caller)) {
        new admindashboard(loggedUser).setVisible(true);
    } else if ("staff".equals(caller)) {
        new staffdashboard(loggedUser).setVisible(true);
    }
    this.dispose();
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

    int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
        return;
    }

    // Get data from selected row
    int id = (int) jTable1.getValueAt(selectedRow, 0);
    String fullName = jTable1.getValueAt(selectedRow, 1).toString();
    String contact = jTable1.getValueAt(selectedRow, 2).toString();
    String address = jTable1.getValueAt(selectedRow, 3).toString();

    // Open Update Form and pass data
    admin.updatecustomer editForm =
            new admin.updatecustomer(id, fullName, contact, address, caller, loggedUser);

    editForm.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
                                     
    int selectedRow = jTable1.getSelectedRow();
    if(selectedRow == -1){
        JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
        return;
    }

    // Get customer ID from the first column
    int customerId = (int) jTable1.getValueAt(selectedRow, 0);

    // Confirm deletion
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this customer?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

    if(confirm == JOptionPane.YES_OPTION){
        config cfg = new config();
        String sql = "DELETE FROM customers WHERE c_id = ?";
        boolean success = cfg.deleteRecord(sql, customerId);

        if(success){
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
            // Remove from table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete customer.");
        }
    }


    }//GEN-LAST:event_jButton3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    java.awt.EventQueue.invokeLater(() -> {

    String currentCaller = blocking.PageLauncher.getCaller();
    String currentUser = blocking.PageLauncher.getLoggedUser();

    if(currentCaller != null && currentUser != null) {
        new view(currentCaller, currentUser).setVisible(true);
    } else {
        JOptionPane.showMessageDialog(null, "Please login first.");
        new login().setVisible(true); // open login form
    }
});
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables


}

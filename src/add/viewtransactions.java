/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package add;

import admin.admindashboard;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import staff.staffdashboard;

/**
 *
 * @author Administrator
 */
public class viewtransactions extends javax.swing.JFrame {
private int hoveredRow = -1;

    private String caller;
    private String loggedUser;

     public viewtransactions(String caller, String loggedUser) {
    this.caller = caller;
    this.loggedUser = loggedUser;
    initComponents();

    // HEADER COLOR
    jTable1.getTableHeader().setBackground(new java.awt.Color(0,153,153));
    jTable1.getTableHeader().setForeground(java.awt.Color.BLACK);
    jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

    // ROW HEIGHT
    jTable1.setRowHeight(25);

    // SELECTION COLOR
    jTable1.setSelectionBackground(new java.awt.Color(153,204,255));
    jTable1.setSelectionForeground(java.awt.Color.BLACK);

    // HOVER DETECTOR
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

    // CUSTOM RENDERER (ROW COLORS)
// CUSTOM RENDERER (Gray + Dark Gray alternating rows)
jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        java.awt.Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            c.setBackground(new java.awt.Color(153, 204, 255)); // selected row
        } else if (row == hoveredRow) {
            c.setBackground(new java.awt.Color(180, 200, 255)); // hover row
        } else {
            if (row % 2 == 0) {
                c.setBackground(new java.awt.Color(220, 220, 220)); // light gray
            } else {
                c.setBackground(new java.awt.Color(169, 169, 169)); // dark gray
            }
        }

        return c;
    }
});
    // JTable model
    jTable1.setModel(new DefaultTableModel(
        new Object[][] {},
        new String[] {
            "Transaction ID","Customer Name","Staff Name",
            "Service Name","Weight (kg)","Total Amount","Date Created"
        }
    ){
        @Override
        public boolean isCellEditable(int row,int column){
            return false;
        }
    });

    loadTransactions();
}
   
private void loadTransactions() {

    Connection conn = config.connectDB();
    if (conn == null) {
        JOptionPane.showMessageDialog(this, "Database connection failed!");
        return;
    }

    // SQL query: join customers, services, users to get all info
    String sql = "SELECT l.l_id, c.full_name AS customer_name, " +
                 "u.full_name AS staff_name, s.service_name, " +
                 "l.weight, l.total_amount, l.date_created " +
                 "FROM laundry l " +
                 "LEFT JOIN customers c ON l.c_id = c.c_id " +
                 "LEFT JOIN services s ON l.s_id = s.s_id " +
                 "LEFT JOIN users u ON l.u_id = u.user_id " +
                 "ORDER BY l.l_id ASC";

    try {
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = new DefaultTableModel(
            new String[]{
                "Transaction ID",
                "Customer Name",
                "Staff Name",
                "Service Name",
                "Weight (kg)",
                "Total Amount",
                "Date Created"
            }, 0
        );

        while (rs.next()) {
            // Override staff name if staff is logged in
            String staffName = rs.getString("staff_name");
            if ("staff".equals(caller)) {
                staffName = loggedUser;
            }

            model.addRow(new Object[]{
                rs.getInt("l_id"),
                rs.getString("customer_name"),
                staffName,  // show logged-in staff if caller is staff
                rs.getString("service_name"),
                rs.getDouble("weight"),
                rs.getDouble("total_amount"),
                rs.getString("date_created")
            });
        }

        jTable1.setModel(model);

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading transactions: " + ex.getMessage());
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel3.setBackground(new java.awt.Color(192, 237, 232));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/laundryyy-removebg-preview__1_-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setText("VIEW TRANSACTIONS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(185, 185, 185)
                .addComponent(jLabel2)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(192, 237, 232));
        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("SEARCH");

        jTable1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "l_id", "c_id", "u_id", "s_id", "total_amount", "notes", "payment_method", "payment_status", "laundry_status", "date_created"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Back");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
                                    
    String searchText = jTextField2.getText();

    Connection conn = config.connectDB();
    if (conn == null) return;

    String sql = "SELECT l.l_id, c.full_name AS customer_name, " +
                 "u.full_name AS staff_name, s.service_name, " +
                 "l.weight, l.total_amount, l.date_created " +
                 "FROM laundry l " +
                 "LEFT JOIN customers c ON l.c_id = c.c_id " +
                 "LEFT JOIN services s ON l.s_id = s.s_id " +
                 "LEFT JOIN users u ON l.u_id = u.user_id " +
                 "WHERE l.l_id LIKE ? OR c.full_name LIKE ? OR u.full_name LIKE ? " +
                 "OR s.service_name LIKE ? OR l.total_amount LIKE ? OR l.date_created LIKE ?";

    try {
        PreparedStatement pst = conn.prepareStatement(sql);

        for (int i = 1; i <= 6; i++) {
            pst.setString(i, "%" + searchText + "%");
        }

        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            String staffName = rs.getString("staff_name");
            if ("staff".equals(caller)) {
                staffName = loggedUser;
            }

            Object[] row = new Object[] {
                rs.getInt("l_id"),
                rs.getString("customer_name"),
                staffName, // override with logged-in staff
                rs.getString("service_name"),
                rs.getDouble("weight"),
                rs.getDouble("total_amount"),
                rs.getString("date_created")
            };
            model.addRow(row);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error searching transactions: " + ex.getMessage());
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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

    // Launch the frame through PageLauncher so login is enforced
    java.awt.EventQueue.invokeLater(() -> {
    blocking.PageLauncher.launch(new viewtransactions("admin", "admin"));
});
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

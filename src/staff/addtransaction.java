/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import add.addcustomer;
import config.UserSession;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import laundry.BubblePanel;
import staff.staffdashboard;
import staff.staffdashboard;

/**
 *
 * @author Administrator
 */
public class addtransaction extends javax.swing.JFrame {

    private int customerId;

    public addtransaction() {
    initComponents();
    loadCustomers();
    loadServices();
    setupListeners(); 
    initPrintButton(); // sa constructor
}


public addtransaction(int customerId) {
    initComponents();
    this.customerId = customerId;
    loadCustomers();
    loadServices(); 
    setupListeners(); 
    initPrintButton();
    
   
}

private void showReceiptSimple(String receipt) {
    // JTextArea para sa receipt
    JTextArea textArea = new JTextArea(receipt);
    textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
    textArea.setEditable(false);
    textArea.setBackground(null); // transparent background effect // walang box
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(null); // walang scroll pane border
    scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

    // Print button sa panel
    JButton printButton = new JButton("Print");
    printButton.addActionListener(e -> {
        try {
            textArea.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage());
        }
    });

    JPanel panel = new JPanel(new java.awt.BorderLayout());
    panel.add(scrollPane, java.awt.BorderLayout.CENTER);
    panel.add(printButton, java.awt.BorderLayout.SOUTH);

    // Show as a simple dialog
    JOptionPane.showMessageDialog(this, panel, "Receipt", JOptionPane.PLAIN_MESSAGE);
}

private void printReceipt() {
    // Build receipt
    int customerRow = jTable3.getSelectedRow();
    int serviceRow = jTable2.getSelectedRow();
    if(customerRow < 0 || serviceRow < 0) return;

    String customerName = jTable3.getValueAt(customerRow, 1).toString();
    String contact = jTable3.getValueAt(customerRow, 2).toString();
    String serviceName = jTable2.getValueAt(serviceRow, 1).toString();
    double weight = Double.parseDouble(jTextField1.getText());
    double price = Double.parseDouble(jTable2.getValueAt(serviceRow, 2).toString());
    double totalAmount = Double.parseDouble(jTextField2.getText());

    String receipt = "======= LAUNDRY RECEIPT =======\n";
    receipt += "Customer: " + customerName + "\n";
    receipt += "Contact: " + contact + "\n";
    receipt += "Service: " + serviceName + "\n";
    receipt += "Weight: " + weight + " kg\n";
    receipt += "Price per kg: " + price + "\n";
    receipt += "Total: " + totalAmount + "\n";
    receipt += "===============================\n";

    showReceiptPreview(receipt);
}
    
    private void loadCustomers() {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:laundry.db")) {
        String sql = "SELECT c.c_id, c.full_name, c.contact_number, c.address, c.weight, c.date_added, " +
                     "CASE WHEN l.c_id IS NOT NULL THEN 1 ELSE 0 END AS has_transaction " +
                     "FROM customers c " +
                     "LEFT JOIN laundry l ON c.c_id = l.c_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0); // clear existing rows

        while (rs.next()) {
            Object[] row = {
                rs.getInt("c_id"),
                rs.getString("full_name"),
                rs.getString("contact_number"),
                rs.getString("address"),
                rs.getDouble("weight"),
                rs.getString("date_added"),
                rs.getInt("has_transaction") // 1 = already has transaction
            };
            model.addRow(row);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Failed to load customers: " + e.getMessage());
    }
}

    private void loadServices() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:laundry.db")) {
            String sql = "SELECT s_id, service_name, price_per_kg FROM services";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // clear existing rows
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("s_id"),
                    rs.getString("service_name"),
                    rs.getDouble("price_per_kg")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load services: " + e.getMessage());
        }
    }

    private void setupListeners() {
        // Customer selection updates weight
        jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = jTable3.getSelectedRow();
                if (row >= 0) {
                    double weight = Double.parseDouble(jTable3.getValueAt(row, 4).toString());
                    jTextField1.setText(String.valueOf(weight));
                    updateTotalAmount();
                }
            }

        });

        // Service selection updates total
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateTotalAmount();
            }
        });
    }

private void updateTotalAmount() {
    int customerRow = jTable3.getSelectedRow();
    int serviceRow = jTable2.getSelectedRow();

    if (customerRow >= 0 && serviceRow >= 0) {
        try {
            Object weightObj = jTable3.getValueAt(customerRow, 4);
            Object priceObj = jTable2.getValueAt(serviceRow, 2);

            double weight = Double.parseDouble(weightObj.toString().trim());
            double price = Double.parseDouble(priceObj.toString().trim());

            double total = weight * price;
            jTextField2.setText(String.format("%.2f", total));
        } catch (NumberFormatException ex) {
            jTextField2.setText("0.00");
        }
    }
}
   
    
   private javax.swing.JButton jButtonPrint; // field

private void initPrintButton() {
    jButtonPrint = new JButton("Print Receipt");
    jButtonPrint.addActionListener(e -> {
        int customerRow = jTable3.getSelectedRow();
        int serviceRow = jTable2.getSelectedRow();
        if (customerRow >= 0 && serviceRow >= 0) {
            printReceipt(); // call your existing method
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer and service.");
        }
    });
    jPanel1.add(jButtonPrint);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPanel1 = new BubblePanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel3.setBackground(new java.awt.Color(192, 237, 232));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/laundryyy-removebg-preview__1_-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setText("ADD TRANSACTION");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(185, 185, 185)
                .addComponent(jLabel2)
                .addContainerGap(227, Short.MAX_VALUE))
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

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Customer :");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Services :");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Weight :");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Total Amount :");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton1.setText("Add Transaction");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "s_id", "service_name", "price_per_kg"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "c_id", "full_name", "contact_number", "address", "weight", "date_added"
            }
        ));
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
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

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        addcustomer lf = new addcustomer();
        lf.setVisible(true);
        this.dispose();
      
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
                                     
    int customerRow = jTable3.getSelectedRow();
    int serviceRow = jTable2.getSelectedRow();

    if (customerRow < 0 || serviceRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a customer and a service.");
        return;
    }

    int c_id = (int) jTable3.getValueAt(customerRow, 0);
    int s_id = (int) jTable2.getValueAt(serviceRow, 0);
    double weight = Double.parseDouble(jTextField1.getText().trim());
    double totalAmount = Double.parseDouble(jTextField2.getText().trim());
    int u_id = UserSession.getInstance().getUserId();

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:laundry.db")) {

        // 1️⃣ Save transaction sa database
        String sql = "INSERT INTO laundry (c_id, s_id, u_id, weight, total_amount) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, c_id);
        ps.setInt(2, s_id);
        ps.setInt(3, u_id);
        ps.setDouble(4, weight);
        ps.setDouble(5, totalAmount);
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        int l_id = 0;
        if (generatedKeys.next()) {
            l_id = generatedKeys.getInt(1);
        }

        // 2️⃣ Kunin customer info
        String sqlCust = "SELECT full_name, contact_number FROM customers WHERE c_id=?";
        PreparedStatement psCust = conn.prepareStatement(sqlCust);
        psCust.setInt(1, c_id);
        ResultSet rsCust = psCust.executeQuery();
        String customerName = "", contact = "";
        if (rsCust.next()) {
            customerName = rsCust.getString("full_name");
            contact = rsCust.getString("contact_number");
        }

        // 3️⃣ Kunin service info
        String sqlService = "SELECT service_name, price_per_kg FROM services WHERE s_id=?";
        PreparedStatement psService = conn.prepareStatement(sqlService);
        psService.setInt(1, s_id);
        ResultSet rsService = psService.executeQuery();
        String serviceName = "";
        double price = 0;
        if (rsService.next()) {
            serviceName = rsService.getString("service_name");
            price = rsService.getDouble("price_per_kg");
        }

        // 4️⃣ Save sa receipts table
        String sqlReceipt = "INSERT INTO receipts (l_id, printed_at) VALUES (?, datetime('now'))";
        PreparedStatement psReceipt = conn.prepareStatement(sqlReceipt);
        psReceipt.setInt(1, l_id);
        psReceipt.executeUpdate();

        // 5️⃣ Build receipt string
        String receipt = "======= LAUNDRY RECEIPT =======\n";
        receipt += "Customer: " + customerName + "\n";
        receipt += "Contact: " + contact + "\n";
        receipt += "Service: " + serviceName + "\n";
        receipt += "Weight: " + weight + " kg\n";
        receipt += "Price per kg: " + price + "\n";
        receipt += "Total: " + totalAmount + "\n";
        receipt += "===============================\n";

        // 6️⃣ Create receipt panel
        JTextArea textArea = new JTextArea(receipt);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
        scrollPane.setBorder(null);

        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            try {
                textArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage());
            }
        });

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            // Close dialog and return to staff dashboard
            Window w = SwingUtilities.getWindowAncestor(okButton);
            if (w != null) w.dispose();

            // Open staff dashboard
            staffdashboard dashboard = new staffdashboard(UserSession.getInstance().getUsername());
            dashboard.setVisible(true);
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(okButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 7️⃣ Centered dialog
        JDialog dialog = new JDialog((java.awt.Frame) null, "Receipt", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // center sa screen
        dialog.setVisible(true);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
        java.util.logging.Logger.getLogger(staffdashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    /* Launch the page via PageLauncher to enforce login first */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            blocking.PageLauncher.launch(new staffdashboard(null)); 
            // null dito, PageLauncher na ang magpasa ng logged-in username
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    private void showReceiptPreview(String receipt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

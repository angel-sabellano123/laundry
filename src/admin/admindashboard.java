
package admin;


import add.addcustomer;
import add.addservices;
import add.adduser;
import add.reports;
import add.view;
import add.viewtransactions;
import static com.sun.jndi.toolkit.dir.DirSearch.search;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import laundry.login;
import staff.staffprofile;

/**
 *
 * @author Administrator
 */ 

        
public class admindashboard extends javax.swing.JFrame {

    
    private String adminUsername;

    /**
     * Creates new form admindashboard
     */ 
    
    public admindashboard() {
    initComponents();
    loadUserTable(); // auto show users
    setupTableDesign(); 
}

    public admindashboard(String username) {
    initComponents();
    this.adminUsername = username;
    setupTableDesign();
    loadUserTable(); // auto show users
}


   
    
    void displayUser(){
        config con = new config ();
        String sql = "SELECT * FROM users";
        con.displayData(sql, jTable1);
        
    }
        
   private DefaultTableModel model; // class-level
   private int hoveredRow = -1;

private void loadUserTable() {
    try {
        config con = new config();
        Connection conn = con.connectDB();

        String sql = "SELECT user_id, full_name, username, contact, role FROM users";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        // Class-level model with only ID column non-editable
        model = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // ALL columns not editable
    }
};

        model.addColumn("ID");          // column 0
        model.addColumn("Full Name");   // column 1
        model.addColumn("Username");    // column 2
        model.addColumn("Contact");     // column 3
        model.addColumn("Role");        // column 4

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("user_id"),
                rs.getString("full_name"),
                rs.getString("username"),
                rs.getString("contact"),
                rs.getString("role")
            });
        }

        rs.close();
        pst.close();
        conn.close();

        jTable1.setModel(model);
        jTable1.setFillsViewportHeight(true);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
    }
}

    private void setupTableDesign(){

    // HEADER STYLE
    jTable1.getTableHeader().setBackground(new java.awt.Color(0,153,153));
    jTable1.getTableHeader().setForeground(java.awt.Color.BLACK);
    jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

    // ROW HEIGHT
    jTable1.setRowHeight(25);

    // CLICK COLOR
    jTable1.setSelectionBackground(new java.awt.Color(153,204,255));
    jTable1.setSelectionForeground(java.awt.Color.BLACK);

    // HOVER DETECTION
jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        int row = jTable1.rowAtPoint(e.getPoint());
        if (row != hoveredRow) { // use class-level hoveredRow
            hoveredRow = row;
            jTable1.repaint();
        }
    }
});

jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        hoveredRow = -1; // reset hover
        jTable1.repaint();
    }
});

    // CUSTOM COLOR RENDERER
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
            c.setBackground(new java.awt.Color(200, 230, 255)); // hover row
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
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel3.setBackground(new java.awt.Color(192, 237, 232));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/laundryyy-removebg-preview__1_-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setText("ADMIN DASHBOARD");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(185, 185, 185)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel2.setBackground(new java.awt.Color(192, 237, 232));

        jButton5.setBackground(new java.awt.Color(192, 237, 232));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setText("Log Out");
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SERVICES");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("VIEW CUSTOMERS");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("VIEW TRANSACTIONS");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("USER MANAGEMENT");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("PROFILE");
        jLabel7.setToolTipText("");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("RECEIPTS");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("REPORTS");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("REPORTS");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(66, 66, 66)
                            .addComponent(jLabel9))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(192, 237, 232));
        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("ADD");
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

        jButton3.setBackground(new java.awt.Color(192, 237, 232));
        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("DELETE");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(192, 237, 232));
        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("SEARCH");

        jTextField3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(jButton3)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Confirm Log Out",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        // User clicked YES → proceed to logout
        login lf = new login();
        lf.setVisible(true);
        this.dispose();
    } else {
        // User clicked NO → do nothing
        // Optional: you can show a message or just ignore
    }
    }//GEN-LAST:event_jButton5MouseClicked

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
          config conf = new config();
    String searchText = jTextField3.getText().trim();

    // kapag empty → clear table
    if(searchText.equals("")){
        javax.swing.table.DefaultTableModel emptyModel =
                new javax.swing.table.DefaultTableModel();
        
        emptyModel.addColumn("ID");
        emptyModel.addColumn("Full Name");
        emptyModel.addColumn("Username");
        emptyModel.addColumn("Contact");
        emptyModel.addColumn("Role");

        jTable1.setModel(emptyModel);
        return;
    }

    // search query
        String sql = "SELECT user_id, full_name, username, contact, role FROM users " +
             "WHERE user_id LIKE ? OR full_name LIKE ? OR username LIKE ? OR contact LIKE ? OR role LIKE ?";

         conf.displayData(sql, jTable1,
        "%" + searchText + "%",
        "%" + searchText + "%",
        "%" + searchText + "%",
        "%" + searchText + "%",
        "%" + searchText + "%");
    
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        addservices lf = new addservices();
        lf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        adduser lf = new adduser();
        lf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
                                     
    view customerView = new view("admin", this.adminUsername); 
    customerView.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        viewtransactions lf = new viewtransactions("admin", this.adminUsername);
        lf.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
                                     
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a user to delete.");
        return;
    }

    String id = jTable1.getValueAt(selectedRow, 0).toString(); // use ID as key
    String username = jTable1.getValueAt(selectedRow, 2).toString(); // just for message

    int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete user: " + username + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        config cfg = new config();
        String sql = "DELETE FROM users WHERE user_id = ?";
        boolean deleted = cfg.deleteRecord(sql, Integer.parseInt(id)); // id as int

        if (deleted) {
            JOptionPane.showMessageDialog(null, "User deleted successfully!");
            loadUserTable(); // refresh table
        } else {
            JOptionPane.showMessageDialog(null, "Delete failed. Please try again.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
    }


    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
 
    int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user to edit.");
        return;
    }

    // Kunin data sa selected row
    String id = jTable1.getValueAt(selectedRow, 0).toString();
    String fullName = jTable1.getValueAt(selectedRow, 1).toString();
    String username = jTable1.getValueAt(selectedRow, 2).toString();
    String contact = jTable1.getValueAt(selectedRow, 3).toString();
    String role = jTable1.getValueAt(selectedRow, 4).toString();

    // Open Edit Form and pass data
    edituser editForm = new edituser(id, fullName, username, contact, role);
    editForm.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        loadUserTable();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
            adminprofile lf = new adminprofile();
            lf.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
            receipts lf = new receipts();
            lf.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
            reports lf = new reports();
            lf.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(admindashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form using PageLauncher */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // ✅ Dito na tawagin ang PageLauncher para siguraduhin na may login muna
            blocking.PageLauncher.launch(new admindashboard("admin123")); // pass test username
        }
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import java.security.MessageDigest;

public class config {

    // Correct database connection
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:laundry.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public static Connection connect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Remove this method or update it to use connectDB
    // public static Connection connect() {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }

    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }
    
    public String authenticate(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("type");
            }
        }
    } catch (SQLException e) {
        System.out.println("Login Error: " + e.getMessage());
    }
    return null;
}
 
    public void displayData(String sql, javax.swing.JTable table) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        // This line automatically maps the Resultset to your JTable
        table.setModel(DbUtils.resultSetToTableModel(rs));
        
    } catch (SQLException e) {
        System.out.println("Error displaying data: " + e.getMessage());
    }
}
    public void displayData(String sql, javax.swing.JTable table, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Set the parameters for the search
        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            // Automatically maps the filtered ResultSet to your JTable
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        
    } catch (SQLException e) {
        System.out.println("Error filtering data: " + e.getMessage());
    }
}

public boolean updateRecord(String sql, Object... params) {
    try (Connection conn = connectDB();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        for (int i = 0; i < params.length; i++) {
            pst.setObject(i + 1, params[i]);
        }

        int affectedRows = pst.executeUpdate(); // returns number of rows updated
        return affectedRows > 0; // true if at least 1 row updated

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}




public boolean deleteRecord(String sql, int id) {
    try (Connection conn = connectDB();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, id); 
        int affectedRows = pst.executeUpdate();

        return affectedRows > 0; 
    } catch (Exception e) {
        e.printStackTrace();
        return false; 
    }
}



    public boolean insertRecord(String sql, Object... params) {
    try {
        Connection conn = connectDB();
        PreparedStatement pst = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pst.setObject(i + 1, params[i]);
        }

        int rows = pst.executeUpdate();

        pst.close();
        conn.close();

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean duplicateCheck(String username) {

    boolean exists = false;

    try {
        Connection conn = connectDB();
        String sql = "SELECT username FROM users WHERE username = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, username);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            exists = true;
        }

    } catch (Exception e) {
        System.out.println("Duplicate Check Error: " + e.getMessage());
    }

    return exists;
}

    public static String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
   
}

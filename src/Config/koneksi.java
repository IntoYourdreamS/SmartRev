/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class koneksi {

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                try {
                    String url = "jdbc:mysql://localhost:3306/smart";
                    String user = "root";
                    String pass = "";
                    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                    conn = (Connection) DriverManager.getConnection(url, user, pass);
                } catch (Exception e) {
                    Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}

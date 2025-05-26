/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import smart.*;
import Config.koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author acer
 */
public class dataexpired extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public dataexpired() {
        initComponents();
        
        // Custom header table
        javax.swing.table.JTableHeader header = tb_expired.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBackground(Color.BLACK); // Warna background header
                setForeground(Color.WHITE); // Warna teks putih
                setFont(new Font("Segoe UI", Font.BOLD, 12)); // Font lebih tebal
                setHorizontalAlignment(JLabel.CENTER); // Posisi teks di tengah
                
                return this;
            }
        });
        
        makeButtonTransparent(kembali);
        loadExpiredData();
    }
    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
    private void loadExpiredData() {
        try {
            // Gunakan koneksi dari class koneksi
            conn = koneksi.getConnection();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(this, 
                    "Gagal terhubung ke database!", 
                    "Error Database", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Bersihkan tabel terlebih dahulu
            DefaultTableModel model = (DefaultTableModel) tb_expired.getModel();
            model.setRowCount(0);
            
            // Query untuk mengambil data produk yang expired atau akan expired dalam 7 hari
            String query = "SELECT p.id_produk, p.nama_produk, b.kode_barcode, b.tgl_expired " +
                          "FROM barcode b " +
                          "INNER JOIN produk p ON b.id_produk = p.id_produk " +
                          "WHERE b.tgl_expired <= DATE_ADD(CURDATE(), INTERVAL 7 DAY) " +
                          "ORDER BY b.tgl_expired ASC";
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            // Format tanggal
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            // Tambahkan data ke tabel
            while (rs.next()) {
                String idProduk = rs.getString("id_produk");
                String namaProduk = rs.getString("nama_produk");
                String barcode = rs.getString("kode_barcode");
                Date tanggalExpired = rs.getDate("tgl_expired");
                
                String tanggalExpiredStr = "";
                if (tanggalExpired != null) {
                    tanggalExpiredStr = dateFormat.format(tanggalExpired);
                }
                
                // Tambahkan baris ke tabel
                model.addRow(new Object[]{
                    idProduk,
                    namaProduk,
                    barcode,
                    tanggalExpiredStr
                });
            }
            
            // Jika tidak ada data expired
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{
                    "Tidak ada data",
                    "Tidak ada produk expired",
                    "-",
                    "-"
                });
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading expired data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data expired: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            // Tutup resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    // Method untuk refresh data
    public void refreshData() {
        loadExpiredData();
    }
    
    // Method untuk menutup koneksi database
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_expired = new javax.swing.JTable();
        kembali = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_expired.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id Produk", "Nama Produk", "Barcode", "Tanggal Expired"
            }
        ));
        jScrollPane1.setViewportView(tb_expired);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 1100, 470));

        kembali.setBorder(null);
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 660, 110, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data Expired (1).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
         new restok().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_kembaliActionPerformed

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
            java.util.logging.Logger.getLogger(dataexpired.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataexpired.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataexpired.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataexpired.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataexpired().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton kembali;
    private javax.swing.JTable tb_expired;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.Session;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.management.Query.value;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import popup.dataexpired;
import popup.datasupplier;
import popup.datareturn;
import popup.tambahbarang;
import popup.tambahsupplier;
import popup.inputreturn;
import popup.tambahkaryawan;
import popup.validasitambahbarang;

/**
 *
 * @author Muhammad Shobri
 */
public class restok extends javax.swing.JFrame {
 private Connection conn;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smart";
    private static final String DB_USER = "root"; // sesuaikan dengan username DB Anda
    private static final String DB_PASS = ""; // sesuaikan dengan password DB Anda

    public restok() {
        initComponents();
        
        // Custom header table
        javax.swing.table.JTableHeader header = tablerestock.getTableHeader();
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
        
        makeButtonTransparent(dashboard);
        makeButtonTransparent(transaksi);
        makeButtonTransparent(laporan);
        makeButtonTransparent(karyawan);
     
        makeButtonTransparent(inputbarang);
      
        makeButtonTransparent(datasupplier);
        makeButtonTransparent(datareturn);
        makeButtonTransparent(dataexpired);
     
        
        // Koneksi database dan load data
        connectToDatabase();
        loadRestockData();
    }
    
private void connectToDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadRestockData() {
        DefaultTableModel model = (DefaultTableModel) tablerestock.getModel();
        model.setRowCount(0); // Kosongkan tabel terlebih dahulu
        
        try {
            // Query untuk mengambil data produk yang perlu restok
            String query = "SELECT p.id_produk, p.nama_produk, p.harga, p.stok, s.nama_supplier, p.kategori " +
                          "FROM produk p " +
                          "LEFT JOIN supplier s ON p.id_supplier = s.id_supplier " +
                          "WHERE p.stok < 5"; // Ambang batas restok
            
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            int no = 1;
            while (rs.next()) {
                String idProduk = rs.getString("id_produk");
                String namaBarang = rs.getString("nama_produk");
                int hargaBeli = rs.getInt("harga");
                int hargaJual = (int) (hargaBeli * 1.2); // Harga jual +20%
                int jumlah = rs.getInt("stok");
                String kategori = rs.getString("kategori");
                String supplier = rs.getString("nama_supplier");
                
                if (supplier == null) {
                    supplier = "Tidak ada supplier";
                }
                
                model.addRow(new Object[]{
                    no++,
                    namaBarang,
                    hargaBeli,
                    hargaJual,
                    jumlah,
                    kategori,
                    supplier
                });
            }
            
            rs.close();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data restok: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboard = new javax.swing.JButton();
        transaksi = new javax.swing.JButton();
        laporan = new javax.swing.JButton();
        karyawan = new javax.swing.JButton();
        inputbarang = new javax.swing.JButton();
        datasupplier = new javax.swing.JButton();
        dataexpired = new javax.swing.JButton();
        datareturn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablerestock = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 140, 50));

        transaksi.setBorder(null);
        transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 130, 40));

        laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanActionPerformed(evt);
            }
        });
        getContentPane().add(laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 130, 50));

        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 140, 40));

        inputbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputbarangActionPerformed(evt);
            }
        });
        getContentPane().add(inputbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 80, 120, 30));

        datasupplier.setBorder(null);
        datasupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datasupplierActionPerformed(evt);
            }
        });
        getContentPane().add(datasupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 200, 160, 40));

        dataexpired.setBorder(null);
        dataexpired.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataexpiredActionPerformed(evt);
            }
        });
        getContentPane().add(dataexpired, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 530, 160, 60));

        datareturn.setBorder(null);
        datareturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datareturnActionPerformed(evt);
            }
        });
        getContentPane().add(datareturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 360, 130, 40));

        tablerestock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama  Barang", "Harga Beli", "Harga Jual", "Jumlah", "Kategori", "Supplier"
            }
        ));
        jScrollPane1.setViewportView(tablerestock);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 750, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Restock (3).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
          new dashboard().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_dashboardActionPerformed

    private void transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiActionPerformed
        // TODO add your handling code here:
          new transaksi().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_transaksiActionPerformed

    private void laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanActionPerformed
        // TODO add your handling code here:
          new laporanpenjualan().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_laporanActionPerformed

    private void karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karyawanActionPerformed
        // TODO add your handling code here:
          new karyawan().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_karyawanActionPerformed

    private void inputbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputbarangActionPerformed
        validasitambahbarang popup = new validasitambahbarang();
                popup.setVisible(true);  
    }//GEN-LAST:event_inputbarangActionPerformed

    private void datasupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datasupplierActionPerformed
        // TODO add your handling code here:
         new datasupplier().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_datasupplierActionPerformed

    private void dataexpiredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataexpiredActionPerformed
        // TODO add your handling code here:
         new dataexpired().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_dataexpiredActionPerformed

    private void datareturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datareturnActionPerformed
        // TODO add your handling code here:
         new datareturn().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_datareturnActionPerformed

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
            java.util.logging.Logger.getLogger(restok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(restok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(restok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(restok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new restok().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard;
    private javax.swing.JButton dataexpired;
    private javax.swing.JButton datareturn;
    private javax.swing.JButton datasupplier;
    private javax.swing.JButton inputbarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton laporan;
    private javax.swing.JTable tablerestock;
    private javax.swing.JButton transaksi;
    // End of variables declaration//GEN-END:variables


}

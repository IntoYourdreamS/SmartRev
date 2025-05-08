/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import popup.notifberhasilkrw;
import popup.tambahkaryawan;
import popup.ubahkaryawan;
import popup.tambahkaryawan;

/**
 *
 * @author acer
 */
public class karyawan2 extends javax.swing.JFrame {

    public static int getSelectedRow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static Object getValueAt(int selectedRow, int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static Object getModel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Creates new form login
     */
    public karyawan2() {
        initComponents();
         loadDataToTable();
         customizeTable();
         makeButtonTransparent(karyawan);
          makeButtonTransparent(dashboard);
           makeButtonTransparent(transaksi);
            makeButtonTransparent(restock);
             makeButtonTransparent(laporan);
          
              makeButtonTransparent(hapus);
    }
    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
     private void customizeTable() {
          JTableHeader header = tbkaryawan2.getTableHeader();
           header.setFont(new Font("Inter", Font.BOLD, 11));
           header.setForeground(Color.WHITE);
            header.setOpaque(false);
            tbkaryawan2.setFont(new Font("Arial", Font.PLAIN, 10));
            tbkaryawan2.setRowHeight(30); 
            tbkaryawan2.setShowGrid(true); 
            tbkaryawan2.setIntercellSpacing(new java.awt.Dimension(0, 0));
            tbkaryawan2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

     }
    
   

private void loadDataToTable() {
    // Model tabel dengan kolom yang diinginkan
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"No Karyawan", "Nama Karyawan", "No HP", "Role", "Jam Masuk"}, 
        0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Membuat tabel tidak bisa di-edit
        }
    };
    
    tbkaryawan2.setModel(model);

    try (Connection conn = koneksi.getConnection()) {
        // Query untuk mendapatkan semua data karyawan beserta semua jam masuk
        String query = "SELECT k.id_karyawan, k.nama_karyawan, k.no_telp, k.role, " +
                     "dk.tanggal_masuk as jam_masuk " +
                     "FROM karyawan k " +
                     "LEFT JOIN detail_karyawan dk ON k.id_karyawan = dk.id_karyawan " +
                     "ORDER BY k.id_karyawan, dk.tanggal_masuk DESC"; // Urutkan berdasarkan id dan jam masuk terbaru

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Format tanggal
                String jamMasukFormatted = "Belum ada data";
                java.sql.Timestamp jamMasuk = rs.getTimestamp("jam_masuk");
                if (jamMasuk != null && !rs.wasNull()) {
                    jamMasukFormatted = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamMasuk);
                }
                
                // Tambahkan semua baris tanpa peduli duplikat id_karyawan
                model.addRow(new Object[]{
                    rs.getString("id_karyawan"),
                    rs.getString("nama_karyawan"),
                    rs.getString("no_telp"),
                    rs.getString("role"),
                    jamMasukFormatted
                });
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Gagal memuat data: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    
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

        laporan = new javax.swing.JButton();
        restock = new javax.swing.JButton();
        transaksi = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkaryawan2 = new javax.swing.JTable();
        karyawan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        laporan.setBorder(null);
        laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanActionPerformed(evt);
            }
        });
        getContentPane().add(laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 150, 40));

        restock.setBorder(null);
        restock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restockActionPerformed(evt);
            }
        });
        getContentPane().add(restock, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 150, 40));

        transaksi.setBorder(null);
        transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 160, 40));

        dashboard.setBorder(null);
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 160, 40));

        hapus.setBorder(null);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 90, 90, 40));

        tbkaryawan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No karyawan", "Nama karyawan", "No hp", "Role", "Jam  masuk"
            }
        ));
        jScrollPane2.setViewportView(tbkaryawan2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 1040, 440));

        karyawan.setBorder(null);
        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 180, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/karyawan (3).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

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

    private void restockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restockActionPerformed
        // TODO add your handling code here:
          new restok().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_restockActionPerformed

    private void laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanActionPerformed
        // TODO add your handling code here:
          new laporanpenjualan().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_laporanActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
     // TODO add your handling code here:
int selectedRow = tbkaryawan2.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
    return;
}

String idKaryawan = tbkaryawan2.getValueAt(selectedRow, 0).toString(); 

int confirm = JOptionPane.showConfirmDialog(this,
        "Apakah Anda yakin ingin menghapus data ini?",
        "Konfirmasi Hapus",
        JOptionPane.YES_NO_OPTION);

if (confirm == JOptionPane.YES_OPTION) {
    String query = "DELETE FROM karyawan WHERE id_karyawan=?";

    try (Connection conn = koneksi.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
        pst.setString(1, idKaryawan);

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            DefaultTableModel model = (DefaultTableModel) tbkaryawan2.getModel();
            model.removeRow(selectedRow);

            
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


    }//GEN-LAST:event_hapusActionPerformed

    private void karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karyawanActionPerformed
        // TODO add your handling code here:
        // Menambahkan aksi ke tombol "Tambah"
  new karyawan().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_karyawanActionPerformed

    
     

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
            java.util.logging.Logger.getLogger(karyawan2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(karyawan2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(karyawan2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(karyawan2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new karyawan2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton laporan;
    private javax.swing.JButton restock;
    private javax.swing.JTable tbkaryawan2;
    private javax.swing.JButton transaksi;
    // End of variables declaration//GEN-END:variables

    private void loadTableData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}


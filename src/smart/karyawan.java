/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import popup.ubahkaryawan;
import popup.tambahkaryawan;

/**
 *
 * @author acer
 */
public class karyawan extends javax.swing.JFrame {

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
    public karyawan() {
        initComponents();

        // Custom header table
        javax.swing.table.JTableHeader header = tbkaryawan.getTableHeader();
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
        tbkaryawan.setDefaultEditor(Object.class, null);

        loadDataToTable();
        customizeTable();
        makeButtonTransparent(tambah);
        makeButtonTransparent(dashboard);
        makeButtonTransparent(transaksi);
        makeButtonTransparent(restock);
        makeButtonTransparent(laporan);
        makeButtonTransparent(ubah);
        makeButtonTransparent(hapus);
        makeButtonTransparent(presensi);
    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private void customizeTable() {
        JTableHeader header = tbkaryawan.getTableHeader();
        header.setFont(new Font("Inter", Font.BOLD, 11));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        tbkaryawan.setFont(new Font("Arial", Font.PLAIN, 10));
        tbkaryawan.setRowHeight(30);
        tbkaryawan.setShowGrid(true);
        tbkaryawan.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbkaryawan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    private void loadDataToTable() {
        // Definisikan model tabel dengan header kolom sesuai dengan data karyawan
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No Karyawan", "Nama Karyawan", "No HP", "Password", "Role", "RFID"}, 0
        );
        tbkaryawan.setModel(model); // Set model ke JTable (asumsi tb_karyawan adalah nama JTable)

        try (Connection conn = koneksi.getConnection(); Statement stmt = conn.createStatement()) {
            // Query untuk mengambil data karyawan
            String query = "SELECT id_karyawan, nama_karyawan, no_telp, password, role, RFID FROM karyawan";

            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    // Ambil data dari ResultSet sesuai dengan nama kolom tabel karyawan
                    String idKaryawan = rs.getString("id_karyawan");
                    String namaKaryawan = rs.getString("nama_karyawan");
                    String noTelp = rs.getString("no_telp");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String rfid = rs.getString("RFID");

                    // Tambahkan data ke model tabel
                    model.addRow(new Object[]{idKaryawan, namaKaryawan, noTelp, password, role, rfid});
                }
            }
        } catch (SQLException e) {
            // Tampilkan pesan kesalahan jika terjadi SQLException
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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

        tambah = new javax.swing.JButton();
        laporan = new javax.swing.JButton();
        restock = new javax.swing.JButton();
        transaksi = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkaryawan = new javax.swing.JTable();
        presensi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tambah.setBorder(null);
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        getContentPane().add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 90, 60, 40));

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

        ubah.setBorder(null);
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });
        getContentPane().add(ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 90, 60, 40));

        hapus.setBorder(null);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 90, 60, 40));

        tbkaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No karyawan", "Nama karyawan", "No hp", "Password", "Role", "RFID"
            }
        ));
        jScrollPane2.setViewportView(tbkaryawan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 1040, 440));

        presensi.setBorder(null);
        presensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presensiActionPerformed(evt);
            }
        });
        getContentPane().add(presensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 160, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/karyawan (1).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
        // Menambahkan aksi ke tombol "Tambah"

        tambahkaryawan popup = new tambahkaryawan();
        popup.setVisible(true);

    }//GEN-LAST:event_tambahActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        dashboard dash = new dashboard();
        dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
        dash.setVisible(true);
        this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_dashboardActionPerformed

    private void transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiActionPerformed
        transaksi dash = new transaksi();
        dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
        dash.setVisible(true);
        this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_transaksiActionPerformed

    private void restockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restockActionPerformed
        restok dash = new restok();
        dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
        dash.setVisible(true);
        this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_restockActionPerformed

    private void laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanActionPerformed
        // TODO add your handling code here:
        laporanpenjualan dash = new laporanpenjualan();
        dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
        dash.setVisible(true);
        this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_laporanActionPerformed

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
        ubahkaryawan popup = new ubahkaryawan();
        popup.setVisible(true);

    }//GEN-LAST:event_ubahActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbkaryawan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idKaryawan = tbkaryawan.getValueAt(selectedRow, 0).toString();

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

                    DefaultTableModel model = (DefaultTableModel) tbkaryawan.getModel();
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

    private void presensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presensiActionPerformed
        // TODO add your handling code here:
        new karyawan2().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_presensiActionPerformed

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
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton laporan;
    private javax.swing.JButton presensi;
    private javax.swing.JButton restock;
    private javax.swing.JButton tambah;
    private javax.swing.JTable tbkaryawan;
    private javax.swing.JButton transaksi;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables

    private void loadTableData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

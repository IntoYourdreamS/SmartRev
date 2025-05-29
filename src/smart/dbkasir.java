/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.Session;
import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.sql.PreparedStatement;
import javax.swing.table.JTableHeader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class dbkasir extends javax.swing.JFrame {

    public dbkasir() {
        initComponents();
        loadDataToTable();
        loadData();
        displayLoggedInEmployeeName();
        jTextField5.setOpaque(false);
        jTextField5.setBackground(new Color(0, 0, 0, 0));
        customizeTable();
        makeButtonTransparent(bttntransaksi);
        makeButtonTransparent(logout);
        makeButtonTransparent(txdepan);
        setTableData();

    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private void loadDataToTable() {
        // Definisikan model tabel dengan header kolom
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Kode Barang", "Nama Barang", "Stok"}
        );
        tbexpired.setModel(model); // Set model ke JTable (asumsi jTable1 adalah nama JTable)

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart", "root", ""); Statement stmt = conn.createStatement()) {

            // Query untuk mengambil data produk dengan stok < 10
            String query = "SELECT id_produk, nama_produk, stok FROM produk WHERE stok < 10 ORDER BY stok ASC";

            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    // Ambil data dari ResultSet
                    String kodeBarang = rs.getString("id_produk");
                    String namaBarang = rs.getString("nama_produk");
                    int stok = rs.getInt("stok");

                    // Tambahkan data ke model tabel
                    model.addRow(new Object[]{kodeBarang, namaBarang, stok});
                }
            }

        } catch (SQLException e) {
            // Tampilkan pesan kesalahan
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data produk: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setTableData() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"BRG001", "Teh Hijau", 10},
                    {"BRG002", "Teh Hitam", 5},
                    {"BRG003", "Teh Oolong", 2},
                    {"BRG004", "Teh Herbal", 12}
                },
                new String[]{"Kode Barang", "Nama Barang", "Stok"}
        );

        jTable1.setModel(model);
    }

    private void loadData() {
        // Definisikan model tabel dengan header kolom
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Kode Produk", "Nama Produk", "Total Terjual", "Kategori"}
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Set proper column classes for sorting
                switch (columnIndex) {
                    case 0:
                        return String.class;  // Kode Produk
                    case 1:
                        return String.class;  // Nama Produk
                    case 2:
                        return Integer.class; // Total Terjual
                    case 3:
                        return String.class; // Kategori
                    default:
                        return Object.class;
                }
            }
        };

        tbpenjualanterlaris.setModel(model); // Set model ke JTable

        // Query SQL untuk ambil data penjualan terlaris
        String query = """
    SELECT detail_penjualan.id_produk, 
           COALESCE(produk.nama_produk) AS nama_produk,
           SUM(detail_penjualan.jumlah) AS total_terjual,
           COALESCE(detail_penjualan.kategori, 'Tidak Diketahui') AS kategori
    FROM detail_penjualan
    LEFT JOIN produk ON detail_penjualan.id_produk = produk.id_produk
    GROUP BY detail_penjualan.id_produk, produk.nama_produk, detail_penjualan.kategori
    ORDER BY total_terjual DESC
""";

        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?useUnicode=true&characterEncoding=UTF-8", "root", ""); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String kodeProduk = rs.getString("id_produk");
                String namaProduk = rs.getString("nama_produk");
                int totalTerjual = rs.getInt("total_terjual");
                String kategori = rs.getString("kategori");

                // Tambahkan ke tabel
                model.addRow(new Object[]{
                    kodeProduk != null ? kodeProduk : "N/A",
                    namaProduk,
                    totalTerjual,
                    kategori
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data penjualan terlaris: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Timer greetingTimer;
    private String namaKaryawan;

    private void displayLoggedInEmployeeName() {
        try {
            String idKaryawan = Session.getKode();

            if (idKaryawan != null && !idKaryawan.isEmpty()) {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/smart",
                        "root",
                        "");

                String sql = "SELECT nama_karyawan FROM karyawan WHERE id_karyawan = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, idKaryawan);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    namaKaryawan = rs.getString("nama_karyawan");
                    startGreetingCycle();
                } else {
                    jTextField5.setText("Halo Karyawan!");
                }

                rs.close();
                pstmt.close();
                conn.close();
            } else {
                jTextField5.setText("Halo Pengguna!");
            }
        } catch (Exception e) {
            jTextField5.setText("Halo!");
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void startGreetingCycle() {
        // Hentikan timer sebelumnya jika ada
        if (greetingTimer != null && greetingTimer.isRunning()) {
            greetingTimer.stop();
        }

        // Buat timer baru
        greetingTimer = new Timer(3000, e -> {
            String currentText = jTextField5.getText();

            if (currentText.startsWith("Halo")) {
                // Ganti ke "Selamat bertransaksi!" selama 5 detik
                jTextField5.setText("Selamat bekerja!");
                greetingTimer.setDelay(5000);
            } else {
                // Ganti kembali ke "Halo [Nama]" selama 3 detik
                jTextField5.setText("Halo " + namaKaryawan + "!");
                greetingTimer.setDelay(3000);
            }
        });

        // Mulai dengan menampilkan sapaan
        jTextField5.setText("Halo " + namaKaryawan + "!");
        greetingTimer.setRepeats(true);
        greetingTimer.start();
    }

    private void customizeTable() {
        JTableHeader header = jTable1.getTableHeader();
        JTableHeader header2 = tbpenjualanterlaris.getTableHeader();
        JTableHeader header3 = tbexpired.getTableHeader();

        header.setFont(new Font("Inter", Font.BOLD, 11));
        header2.setFont(new Font("Inter", Font.BOLD, 11));
        header3.setFont(new Font("Inter", Font.BOLD, 11));

        header.setForeground(Color.WHITE);
        header2.setForeground(Color.WHITE);
        header3.setForeground(Color.WHITE);

        header.setOpaque(false);
        header2.setOpaque(false);
        header3.setOpaque(false);

        jTable1.setFont(new Font("Arial", Font.PLAIN, 10));
        tbpenjualanterlaris.setFont(new Font("Arial", Font.PLAIN, 10));
        tbexpired.setFont(new Font("Arial", Font.PLAIN, 10));

        jTable1.setRowHeight(30);
        tbpenjualanterlaris.setRowHeight(30);
        tbexpired.setRowHeight(30);

        jTable1.setShowGrid(true);
        tbpenjualanterlaris.setShowGrid(true);
        tbexpired.setShowGrid(true);

        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbpenjualanterlaris.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbexpired.setIntercellSpacing(new java.awt.Dimension(0, 0));

        jTable1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbpenjualanterlaris.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbexpired.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        tbpenjualanterlaris.setSelectionBackground(new Color(25, 25, 25));
        tbpenjualanterlaris.setSelectionForeground(Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tbexpired = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbpenjualanterlaris = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bttntransaksi = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        txdepan = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbexpired.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode barang", "Nama barang", "Stok"
            }
        ));
        tbexpired.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane3.setViewportView(tbexpired);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 550, 250, 140));

        tbpenjualanterlaris.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode produk", "Nama produk", "No penjualan", "Kategori"
            }
        ));
        jScrollPane2.setViewportView(tbpenjualanterlaris);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 380, 140));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode barang", "Nama barang", "Stok"
            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 550, 310, 140));

        bttntransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttntransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(bttntransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 170, 50));

        logout.setBorder(null);
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 140, 50));

        txdepan.setBorder(null);
        getContentPane().add(txdepan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 450, 190, 30));

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setBorder(null);
        jTextField5.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jTextField5.setEnabled(false);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 20, 160, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbkasir.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1372, 768));

        jButton2.setBackground(new java.awt.Color(85, 85, 85));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 150, 40));

        jButton3.setBackground(new java.awt.Color(85, 85, 85));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 150, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bttntransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttntransaksiActionPerformed
        transaksikasir dash = new transaksikasir();
        dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
        dash.setVisible(true);
        this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_bttntransaksiActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart", "root", "");
            String idKaryawan = Session.getKode();

            if (!idKaryawan.isEmpty()) {
                // Query untuk update record terakhir yang belum memiliki tanggal_keluar
                String query = "UPDATE detail_karyawan SET tanggal_keluar = CURRENT_TIMESTAMP() "
                        + "WHERE id_karyawan = ? AND tanggal_masuk = ("
                        + "   SELECT max_masuk FROM ("
                        + "       SELECT MAX(tanggal_masuk) AS max_masuk "
                        + "       FROM detail_karyawan "
                        + "       WHERE id_karyawan = ? AND tanggal_keluar IS NULL"
                        + "   ) AS temp"
                        + ")";

                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, idKaryawan);
                pstmt.setString(2, idKaryawan);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Logout time updated successfully");
                } else {
                    System.out.println("No record found to update");
                }

            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating logout time: " + e.getMessage());
        }

// Tampilkan form login
        login dash = new login();
        dash.setLocationRelativeTo(null);
        dash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

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
            java.util.logging.Logger.getLogger(dbkasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dbkasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dbkasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dbkasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dbkasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttntransaksi;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton logout;
    private javax.swing.JTable tbexpired;
    private javax.swing.JTable tbpenjualanterlaris;
    private javax.swing.JButton txdepan;
    // End of variables declaration//GEN-END:variables
}

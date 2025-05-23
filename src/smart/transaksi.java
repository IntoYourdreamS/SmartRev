/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.Session;
import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class transaksi extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    private static int counter = 1;
    private PreparedStatement stat;
    private ResultSet rs;
    private DefaultTableModel model = null;

    public transaksi() {
        initComponents();
        SwingUtilities.invokeLater(() -> txt_noBarang.requestFocusInWindow());
        refreshTable();
        makeButtonTransparent(bttndashboard);
        makeButtonTransparent(restok);
        makeButtonTransparent(karyawan);
        makeButtonTransparent(laporan);
        makeButtonTransparent(btn_simpan);
        makeButtonTransparent(btn_hapus);
        makeButtonTransparent(btn_bayar);
        
        txt_bayar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formatBayarField();
            }
        });
        customizeTable();
        txt_qty.setOpaque(false);
        txt_qty.setBackground(new Color(0, 0, 0, 0));
        txt_namabrg.setOpaque(false);
        txt_namabrg.setBackground(new Color(0, 0, 0, 0));
        txt_noBarang.setOpaque(false);
        txt_noBarang.setBackground(new Color(0, 0, 0, 0));
        txt_harga.setOpaque(false);
        txt_harga.setBackground(new Color(0, 0, 0, 0));
        txt_kategori.setOpaque(false);
        txt_kategori.setBackground(new Color(0, 0, 0, 0));
        txt_kembalian.setOpaque(false);
        txt_kembalian.setBackground(new Color(0, 0, 0, 0));
        txt_totalharga.setOpaque(false);
        txt_totalharga.setBackground(new Color(0, 0, 0, 0));
        txt_bayar.setOpaque(false);
        txt_bayar.setBackground(new Color(0, 0, 0, 0));

//        No_nota.setText(generateNota());
    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private String generateNota() {
        return "Nota-" + String.format("%04d", counter++);
    }

    public static String generateKodeTransaksi() {
        Connection conn = koneksi.getConnection();
        String kodeTransaksi = "TR001";

        if (conn != null) {
            try {
                Statement statement = conn.createStatement();
                String query = "SELECT id_penjualan FROM penjualan ORDER BY id_penjualan DESC LIMIT 1";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String lastKode = resultSet.getString("id_penjualan");
                    int kodeNum = Integer.parseInt(lastKode.substring(3)) + 1;
                    kodeTransaksi = String.format("TR%03d", kodeNum);
                }

                resultSet.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kodeTransaksi;
    }

    private double parseFormattedNumber(String formattedNumber) {
        try {
            return Double.parseDouble(formattedNumber.replace(".", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void refreshTable() {
        model = new DefaultTableModel();
        model.addColumn("Kode Produk");
        model.addColumn("Nama Produk");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Sub Total");
        model.addColumn("Kategori");
        jTable1.setModel(model);
    }

    private void customizeTable() {
        JTableHeader header = jTable1.getTableHeader();

        header.setFont(new Font("Inter", Font.BOLD, 11));

        header.setForeground(Color.WHITE);

        header.setOpaque(false);

        jTable1.setFont(new Font("Arial", Font.PLAIN, 10));

        jTable1.setRowHeight(30);

        jTable1.setShowGrid(true);

        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));

        jTable1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jTable1.setSelectionBackground(new Color(25, 25, 25));
        jTable1.setSelectionForeground(Color.WHITE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bttndashboard = new javax.swing.JButton();
        txt_kategori = new javax.swing.JTextField();
        txt_namabrg = new javax.swing.JTextField();
        btn_bayar = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_harga = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        txt_kembalian = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        txt_noBarang = new javax.swing.JTextField();
        txt_totalharga = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txt_qty = new javax.swing.JTextField();
        laporan = new javax.swing.JButton();
        restok = new javax.swing.JButton();
        karyawan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bttndashboard.setBorder(null);
        bttndashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttndashboardActionPerformed(evt);
            }
        });
        getContentPane().add(bttndashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 180, 40));

        txt_kategori.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        txt_kategori.setBorder(null);
        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });
        getContentPane().add(txt_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 320, 40));

        txt_namabrg.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        txt_namabrg.setBorder(null);
        txt_namabrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabrgActionPerformed(evt);
            }
        });
        getContentPane().add(txt_namabrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 320, 40));

        btn_bayar.setBorder(null);
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 640, 180, 50));

        btn_hapus.setBorder(null);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 650, 100, 40));

        txt_harga.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        txt_harga.setBorder(null);
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 320, 40));

        btn_simpan.setBorder(null);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 650, 400, 40));

        txt_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_kembalian.setBorder(null);
        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });
        getContentPane().add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 650, 280, 40));

        txt_bayar.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_bayar.setBorder(null);
        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });
        getContentPane().add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 570, 290, 40));

        txt_noBarang.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        txt_noBarang.setBorder(null);
        txt_noBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noBarangActionPerformed(evt);
            }
        });
        getContentPane().add(txt_noBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 320, 40));

        txt_totalharga.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_totalharga.setBorder(null);
        txt_totalharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalhargaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_totalharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 570, 280, 40));

        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                "id barang", "Nama barang", "Harga barang", "QTY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 620, -1));

        txt_qty.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        txt_qty.setBorder(null);
        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qtyKeyReleased(evt);
            }
        });
        getContentPane().add(txt_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 320, 30));

        laporan.setBorder(null);
        laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanActionPerformed(evt);
            }
        });
        getContentPane().add(laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 150, 40));

        restok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restokActionPerformed(evt);
            }
        });
        getContentPane().add(restok, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 170, 50));

        karyawan.setBorder(null);
        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 170, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Transaksiiiiiiiii.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_qtyActionPerformed

    private void prosesTransaksi(String kodeTransaksi, String idKaryawan, String[] idProduk, int[] jumlahProduk, String[] Kategori, int[] hargaS, int[] SubTotal, Double bayar, int total) throws SQLException {
        try (Connection conn = koneksi.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // Insert into penjualan
                String insertTransaksi = "INSERT INTO penjualan VALUES (?, ?, NOW(), ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertTransaksi)) {
                    ps.setString(1, kodeTransaksi);
                    ps.setString(2, idKaryawan);
                    ps.setInt(3, total);
                    ps.setDouble(4, bayar);
                    ps.executeUpdate();
                }

                // Insert into detail_penjualan and update stok
                for (int j = 0; j < idProduk.length; j++) {
                    String insertDetailTransaksi = "INSERT INTO detail_penjualan VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement DTps = conn.prepareStatement(insertDetailTransaksi)) {
                        DTps.setString(1, kodeTransaksi);
                        DTps.setString(2, idProduk[j]);
                        DTps.setString(3, Kategori[j]);
                        DTps.setInt(4, jumlahProduk[j]);
                        DTps.setInt(5, hargaS[j]);
                        DTps.setInt(6, SubTotal[j]);
                        DTps.executeUpdate();
                    }

                    // Update stok
                    String updateStokMenu = "UPDATE produk SET stok = stok - ? WHERE id_produk = ?";
                    try (PreparedStatement updateSM = conn.prepareStatement(updateStokMenu)) {
                        updateSM.setInt(1, jumlahProduk[j]);
                        updateSM.setString(2, idProduk[j]);
                        updateSM.executeUpdate();
                    }
                }

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw e;
            }
        }
    }

    private String formatNumber(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        formatter.setGroupingUsed(true);
        return formatter.format(number);
    }

    private void hitungTotalHarga() {
        int totalHarga = 0;

        if (model != null) {
            for (int i = 0; i < model.getRowCount(); i++) {
                totalHarga += Integer.parseInt(model.getValueAt(i, 4).toString().replace(".", ""));
            }
        }
        txt_totalharga.setText(formatNumber(totalHarga));
    }

    private void formatBayarField() {
        String input = txt_bayar.getText().trim().replace(".", "");
        if (!input.isEmpty()) {
            try {
                double nilai = Double.parseDouble(input);
                txt_bayar.setText(formatNumber(nilai));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                txt_bayar.requestFocus();
            }
        }
    }

    private void txt_noBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noBarangActionPerformed
        // TODO add your handling code here:

        String kode_bahan = txt_noBarang.getText();
        txt_namabrg.setText(kode_bahan);
        try (Connection conn = koneksi.getConnection(); PreparedStatement pst = conn.prepareStatement("SELECT * FROM produk WHERE barcode = ?")) {
            pst.setString(1, txt_noBarang.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String namaProduk = rs.getString("nama_produk");
                String harga = rs.getString("harga");
                String kategori = rs.getString("kategori");
                String stok = rs.getString("stok");

                txt_namabrg.setText(namaProduk);
                txt_harga.setText(harga);
                txt_kategori.setText(kategori);
                txt_qty.setText("1");
                btn_simpan.doClick();
            } else {
                JOptionPane.showMessageDialog(this, "Kode bahan tidak ditemukan", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                txt_namabrg.setText("");
                txt_harga.setText("");
                txt_kategori.setText("");
                txt_qty.setText("");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Kesalahan: " + e.getMessage(),
                    "Kesalahan Database",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace(); // Cetak error untuk debugging
        }
    }//GEN-LAST:event_txt_noBarangActionPerformed

    private void txt_namabrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabrgActionPerformed

    private void txt_totalhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalhargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalhargaActionPerformed

    private void bttndashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttndashboardActionPerformed
        // TODO add your handling code here:
        new dashboard().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bttndashboardActionPerformed

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

    private void restokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restokActionPerformed
        // TODO add your handling code here:
        new restok().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_restokActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        try {
            // Periksa apakah semua input telah diisi
            if (txt_noBarang.getText().trim().isEmpty()
                    || txt_namabrg.getText().trim().isEmpty()
                    || txt_qty.getText().trim().isEmpty()
                    || txt_harga.getText().trim().isEmpty()
                    || txt_kategori.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Harap isi semua kolom sebelum menyimpan.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int qty = Integer.parseInt(txt_qty.getText().trim());
            int harga = Integer.parseInt(txt_harga.getText().trim());

            if (qty <= 0 || harga <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah dan harga harus lebih dari 0.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String checkStockSQL = "SELECT stok FROM produk WHERE barcode = ?";
            try (Connection conn = koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(checkStockSQL)) {

                ps.setString(1, txt_noBarang.getText().trim());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int stokTersedia = rs.getInt("stok");
                    txt_noBarang.requestFocus();

                    if (stokTersedia < qty) {
                        JOptionPane.showMessageDialog(null,
                                "Stok tidak cukup!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Produk tidak ditemukan dalam database.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Terjadi kesalahan saat mengecek stok: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int total = qty * harga;

            if (model == null) {
                refreshTable();
            }

            model.addRow(new Object[]{
                txt_noBarang.getText().trim(),
                txt_namabrg.getText().trim(),
                qty,
                harga,
                total,
                txt_kategori.getText().trim()
            });

            hitungTotalHarga();

            txt_noBarang.setText("");
            txt_namabrg.setText("");
            txt_qty.setText("");
            txt_harga.setText("");
            txt_kategori.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harap masukkan angka yang valid untuk jumlah dan harga.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int pilihan = JOptionPane.showConfirmDialog(null, "Konfirmasi Hapus?", "", JOptionPane.YES_NO_OPTION);
            if (pilihan == JOptionPane.YES_OPTION) {
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
                txt_noBarang.setText("");
                txt_namabrg.setText("");
                txt_qty.setText("");
                txt_harga.setText("");
                txt_kategori.setText("");
                txt_totalharga.setText("");
                txt_bayar.setText("");
                txt_kembalian.setText("");
            } else if (pilihan == JOptionPane.NO_OPTION) {
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Belum ada item yang ditambahkan!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (txt_bayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nominal pembayaran harus diisi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double total = parseFormattedNumber(txt_totalharga.getText());
            double bayar = parseFormattedNumber(txt_bayar.getText());
            double kembalian = bayar - total;
            int integerKembalian = (int) Math.ceil(kembalian);

            if (bayar < total) {
                JOptionPane.showMessageDialog(this,
                        "Nominal pembayaran kurang!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Proses transaksi
            String kodeTransaksi = generateKodeTransaksi();
            String idKaryawan = Session.getKode();

            // Simpan data transaksi ke database
            if (prosesTransaksiKeDatabase(kodeTransaksi, idKaryawan, total, bayar)) {
                // Update UI
                txt_kembalian.setText(formatNumber(kembalian));

                // Siapkan data untuk struk
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                String[][] itemsForStruk = new String[model.getRowCount()][4];

                for (int i = 0; i < model.getRowCount(); i++) {
                    itemsForStruk[i][0] = model.getValueAt(i, 1).toString(); // Nama
                    itemsForStruk[i][1] = model.getValueAt(i, 2).toString(); // Qty
                    itemsForStruk[i][2] = model.getValueAt(i, 3).toString(); // Harga
                    itemsForStruk[i][3] = model.getValueAt(i, 4).toString(); // Subtotal
                }

                // Cetak struk dengan konfirmasi
                boolean cetakBerhasil = StrukPrinter.printStrukDenganKonfirmasi(
                        kodeTransaksi,
                        itemsForStruk,
                        total,
                        bayar,
                        kembalian,
                        Session.getKode()
                );

                // Reset form setelah transaksi
                model.setRowCount(0);
                txt_totalharga.setText("");
                txt_bayar.setText("");

                if (cetakBerhasil) {
                    JOptionPane.showMessageDialog(this,
                            "Transaksi berhasil dan struk telah dicetak!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Transaksi berhasil (struk tidak dicetak)",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Format nominal tidak valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean prosesTransaksiKeDatabase(String kodeTransaksi, String idKaryawan,
            double total, double bayar) {
        // Implementasi penyimpanan ke database
        // Return true jika berhasil
        return true;
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void txt_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_qtyKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton bttndashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton laporan;
    private javax.swing.JButton restok;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_kategori;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_namabrg;
    private javax.swing.JTextField txt_noBarang;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_totalharga;
    // End of variables declaration//GEN-END:variables
}

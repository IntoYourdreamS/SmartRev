    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import Config.koneksi;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import smart.karyawan;
import smart.restok;
import java.sql.PreparedStatement;

/**
 *
 * @author ASUS
 */
public class tambahbarang extends javax.swing.JFrame {

    private Connection conn;

    public tambahbarang() {
        initComponents();
        id_barang.setText(generateCode());
 populateSupplierComboBox();
        conn = koneksi.getConnection();
        makeButtonTransparent(kembali);
         makeButtonTransparent(tambah);
         makeButtonTransparent(ubah);
         makeButtonTransparent(hapus);
        id_barang.setOpaque(false);
        id_barang.setBackground(new Color(0, 0, 0, 0));
        nama_barang.setOpaque(false);
        nama_barang.setBackground(new Color(0, 0, 0, 0));
        harga_jual.setOpaque(false);
        harga_jual.setBackground(new Color(0, 0, 0, 0));
        harga_beli.setOpaque(false);
        harga_beli.setBackground(new Color(0, 0, 0, 0));
        jumlah.setOpaque(false);
        jumlah.setBackground(new Color(0, 0, 0, 0));
        kategori.setOpaque(false);
        kategori.setBackground(new Color(0, 0, 0, 0));
        id_supplier.setOpaque(false);
        id_supplier.setBackground(new Color(0, 0, 0, 0));
        barcode.setOpaque(false);
        barcode.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0));
     
    }

private void populateSupplierComboBox() {
    jComboBox1.removeAllItems();
    String query = "SELECT id_supplier, nama_supplier FROM supplier ORDER BY id_supplier";
    
    try (Connection conn = koneksi.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        while (rs.next()) {
            String id = rs.getString("id_supplier");
            String nama = rs.getString("nama_supplier");
            jComboBox1.addItem(id + " - " + nama);
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data supplier: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    
    
     private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    class Coba extends JFrame {
    public Coba() {
        setTitle("Popup Frame - Coba");
        setSize(200, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
    
    private String generateCode() {
        String prefix = "PR";
        String query = "SELECT id_produk FROM produk ORDER BY id_produk DESC LIMIT 1";
        String newCode = "";

        try (Connection conn = koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String lastId = rs.getString("id_produk");
                int number = Integer.parseInt(lastId.substring(2));
                number++;
                newCode = prefix + String.format("%03d", number);
            } else {
                newCode = prefix + "001";
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat ID produk baru: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return newCode;
    }
    
        private String generateCode1() {
        String prefix = "PB";
      String query = "SELECT id_pembelian FROM pembelian ORDER BY id_pembelian DESC LIMIT 1";

        String newCode = "";

        try (Connection conn = koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String lastId = rs.getString("id_pembelian");
                int number = Integer.parseInt(lastId.substring(2));
                number++;
                newCode = prefix + String.format("%03d", number);
            } else {
                newCode = prefix + "001";
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat ID produk baru: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return newCode;
    }
    
    private boolean validateInput() {
        // Validasi field tidak kosong
         if (nama_barang.getText().trim().isEmpty()) {
            showError("Nama barang harus diisi!");
            nama_barang.requestFocus();
            return false;
        }
        
        if (harga_jual.getText().trim().isEmpty()) {
            showError("Harga jual harus diisi!");
            harga_jual.requestFocus();
            return false;
        }
        
        if (harga_beli.getText().trim().isEmpty()) {
            showError("Harga beli harus diisi!");
            harga_beli.requestFocus();
            return false;
        }
        
        if (jumlah.getText().trim().isEmpty()) {
            showError("Jumlah harus diisi!");
            jumlah.requestFocus();
            return false;
        }
        
        if (kategori.getText().trim().isEmpty()) {
            showError("Kategori harus diisi!");
            kategori.requestFocus();
            return false;
        }
        
        if (id_supplier.getText().trim().isEmpty()) {
            showError("ID Supplier harus diisi!");
            id_supplier.requestFocus();
            return false;
        }
        
        if (barcode.getText().trim().isEmpty()) {
            showError("Barcode harus diisi!");
            barcode.requestFocus();
            return false;
        }
        
        if (tgl_exp.getDate() == null) {
            showError("Tanggal expired harus diisi!");
            return false;
        }

        // Validasi format angka
        try {
            int hargaJual = Integer.parseInt(harga_jual.getText());
            int hargaBeli = Integer.parseInt(harga_beli.getText());
            int jumlahStok = Integer.parseInt(jumlah.getText());
            
            if (hargaJual <= 0 || hargaBeli <= 0 || jumlahStok <= 0) {
                showError("Harga dan jumlah harus lebih besar dari 0!");
                return false;
            }
            
            if (hargaJual < hargaBeli) {
                showError("Harga jual harus lebih besar dari harga beli!");
                return false;
            }
            
        } catch (NumberFormatException e) {
            showError("Harga dan jumlah harus berupa angka!");
            return false;
        }

        return true;
    }



    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ubah = new javax.swing.JButton();
        id_barang = new javax.swing.JTextField();
        nama_barang = new javax.swing.JTextField();
        harga_jual = new javax.swing.JTextField();
        harga_beli = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        kategori = new javax.swing.JTextField();
        tambah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        barcode = new javax.swing.JTextField();
        kembali = new javax.swing.JButton();
        tgl_exp = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        id_supplier = new javax.swing.JTextField();

        ubah.setBorder(null);
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_barang.setFont(new java.awt.Font("Futura Md BT", 1, 12)); // NOI18N
        id_barang.setForeground(new java.awt.Color(116, 77, 6));
        id_barang.setBorder(null);
        id_barang.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        id_barang.setEnabled(false);
        id_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_barangActionPerformed(evt);
            }
        });
        getContentPane().add(id_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 170, 30));

        nama_barang.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        nama_barang.setBorder(null);
        getContentPane().add(nama_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 170, 30));

        harga_jual.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        harga_jual.setBorder(null);
        harga_jual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harga_jualActionPerformed(evt);
            }
        });
        getContentPane().add(harga_jual, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 260, 30));

        harga_beli.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        harga_beli.setBorder(null);
        getContentPane().add(harga_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 180, 30));

        jumlah.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jumlah.setBorder(null);
        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        getContentPane().add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 260, 40));

        kategori.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        kategori.setBorder(null);
        kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kategoriActionPerformed(evt);
            }
        });
        getContentPane().add(kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 170, 30));

        tambah.setBorder(null);
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        getContentPane().add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 160, 40));

        hapus.setBorder(null);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        getContentPane().add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 390, 160, 40));

        barcode.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        barcode.setBorder(null);
        getContentPane().add(barcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 170, 40));

        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 50, 40));

        tgl_exp.setBackground(new java.awt.Color(164, 107, 9));
        tgl_exp.setForeground(new java.awt.Color(116, 77, 6));
        tgl_exp.setFont(new java.awt.Font("Futura Md BT", 0, 16)); // NOI18N
        getContentPane().add(tgl_exp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 190, 40));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 252, 190, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Tambah Barang (5).png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        id_supplier.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        id_supplier.setBorder(null);
        getContentPane().add(id_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 260, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void harga_jualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harga_jualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_harga_jualActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoriActionPerformed

    private void id_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_barangActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
Connection conn = null;
PreparedStatement statProduk = null;
PreparedStatement statDetail = null;
PreparedStatement statPembelian = null;
PreparedStatement statBarcode = null;

try {
    // Validasi input wajib (tgl_exp tetap wajib diisi karena dibutuhkan untuk barcode)
    if (id_barang.getText().trim().isEmpty() || 
        nama_barang.getText().trim().isEmpty() || 
        harga_jual.getText().trim().isEmpty() || 
        harga_beli.getText().trim().isEmpty() || 
        jumlah.getText().trim().isEmpty() || 
        kategori.getText().trim().isEmpty() ||
        jComboBox1.getSelectedItem() == null ||
        tgl_exp.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ambil nilai dari form
    String kode = id_barang.getText().trim();
    String namaBarang = nama_barang.getText().trim();
    String Hargajual = harga_jual.getText().trim();
    String Hargabeli = harga_beli.getText().trim();
    String Jumlah = jumlah.getText().trim();
    String Kategori = kategori.getText().trim();
    String selectedSupplier = (String) jComboBox1.getSelectedItem();
    String Nosupplier = selectedSupplier.split(" - ")[0];
    String barcodeManual = barcode.getText().trim();

    // Validasi barcode hanya angka
    if (!barcodeManual.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Barcode hanya boleh berisi angka!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalExp = sdf.format(tgl_exp.getDate());
    String idPembelian = generateCode1(); // Generate ID pembelian baru
    String idKaryawan = "KR001"; // Ganti sesuai karyawan login

    // Validasi format angka untuk harga dan jumlah
    int hargaJualInt, hargaBeliInt, jumlahInt;
    try {
        hargaJualInt = Integer.parseInt(Hargajual);
        hargaBeliInt = Integer.parseInt(Hargabeli);
        jumlahInt = Integer.parseInt(Jumlah);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Harga dan jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Buat koneksi database
    conn = koneksi.getConnection();
    if (conn == null || conn.isClosed()) {
        JOptionPane.showMessageDialog(this, "Gagal terhubung ke database", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Cek apakah ID produk sudah ada
    String checkQuery = "SELECT COUNT(*) FROM produk WHERE id_produk = ?";
    try (PreparedStatement checkStat = conn.prepareStatement(checkQuery)) {
        checkStat.setString(1, kode);
        ResultSet rs = checkStat.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "ID Produk sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Cek apakah barcode sudah ada
    String checkBarcodeQuery = "SELECT COUNT(*) FROM barcode WHERE kode_barcode = ?";
    try (PreparedStatement checkBarcode = conn.prepareStatement(checkBarcodeQuery)) {
        checkBarcode.setString(1, barcodeManual);
        ResultSet rs = checkBarcode.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "Barcode sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Simpan ke tabel PEMBELIAN
    String queryPembelian = "INSERT INTO pembelian (id_pembelian, id_karyawan, id_supplier, tanggal) VALUES (?, ?, ?, CURDATE())";
    statPembelian = conn.prepareStatement(queryPembelian);
    statPembelian.setString(1, idPembelian);
    statPembelian.setString(2, idKaryawan);
    statPembelian.setString(3, Nosupplier);
    int resultPembelian = statPembelian.executeUpdate();
    if (resultPembelian == 0) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data pembelian", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Simpan ke tabel PRODUK (tanpa tgl_expired)
    String queryProduk = "INSERT INTO produk (id_produk, nama_produk, harga, stok, kategori, id_supplier) VALUES (?, ?, ?, ?, ?, ?)";
    statProduk = conn.prepareStatement(queryProduk);
    statProduk.setString(1, kode);
    statProduk.setString(2, namaBarang);
    statProduk.setInt(3, hargaJualInt);
    statProduk.setInt(4, jumlahInt);
    statProduk.setString(5, Kategori);
    statProduk.setString(6, Nosupplier);
    int resultProduk = statProduk.executeUpdate();
    if (resultProduk == 0) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data produk", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Simpan ke tabel BARCODE (tgl_expired disimpan di sini saja)
    String queryBarcode = "INSERT INTO barcode (id_produk, kode_barcode, tgl_expired) VALUES (?, ?, ?)";
    statBarcode = conn.prepareStatement(queryBarcode);
    statBarcode.setString(1, kode);
    statBarcode.setString(2, barcodeManual);
    statBarcode.setString(3, tanggalExp);
    int resultBarcode = statBarcode.executeUpdate();
    if (resultBarcode == 0) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data barcode", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Simpan ke tabel DETAIL_PEMBELIAN
    String queryDetailPembelian = "INSERT INTO detail_pembelian (id_pembelian, id_produk, jumlah, sub_total, harga_beli, kategori) VALUES (?, ?, ?, ?, ?, ?)";
    statDetail = conn.prepareStatement(queryDetailPembelian);
    statDetail.setString(1, idPembelian);
    statDetail.setString(2, kode);
    statDetail.setInt(3, jumlahInt);
    statDetail.setInt(4, hargaBeliInt * jumlahInt);
    statDetail.setInt(5, hargaBeliInt);
    statDetail.setString(6, Kategori);
    int resultDetail = statDetail.executeUpdate();
    if (resultDetail == 0) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan detail pembelian", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Sukses
    JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    resetForm();
    id_barang.setText(generateCode());

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

    }



    }//GEN-LAST:event_tambahActionPerformed

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
  
    }//GEN-LAST:event_ubahActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
    
    }//GEN-LAST:event_hapusActionPerformed

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new restok().setVisible(true);
    }//GEN-LAST:event_kembaliActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
       
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(tambahbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tambahbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tambahbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tambahbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tambahbarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barcode;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga_beli;
    private javax.swing.JTextField harga_jual;
    private javax.swing.JTextField id_barang;
    private javax.swing.JTextField id_supplier;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField kategori;
    private javax.swing.JButton kembali;
    private javax.swing.JTextField nama_barang;
    private javax.swing.JButton tambah;
    private com.toedter.calendar.JDateChooser tgl_exp;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables
}

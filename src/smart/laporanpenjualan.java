/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;
import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import com.sun.jdi.connect.spi.Connection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author sobri
 */
public class laporanpenjualan extends javax.swing.JFrame {


    public laporanpenjualan()  {
     try {
        initComponents();
         javax.swing.table.JTableHeader header = tbpenjualan.getTableHeader();
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
        customizeTable();
        makeButtonTransparent(dashboard);
        makeButtonTransparent(transaksi);
        makeButtonTransparent(restok);
        makeButtonTransparent(karyawan);
        makeButtonTransparent(pembelian);
        loadDataPenjualan();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error inisialisasi: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }
private void loadDataPenjualan() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No Nota");
    model.addColumn("Nama Produk");
    model.addColumn("Jumlah");
    model.addColumn("Harga");
    model.addColumn("Total");
    model.addColumn("Kategori");
    model.addColumn("Nama Karyawan");
    model.addColumn("Tanggal");

    // Create DecimalFormat for currency formatting
    DecimalFormat rupiahFormat = new DecimalFormat("Rp #,###.00");

    String sql = "SELECT " +
             "p.id_penjualan, " +
             "pr.nama_produk, " +
             "dp.jumlah, " +
             "dp.harga_satuan, " +
             "dp.subtotal, " +
             "dp.kategori, " +
             "k.nama_karyawan, " +
             "p.tanggal " +
             "FROM detail_penjualan dp " +
             "JOIN penjualan p ON dp.id_penjualan = p.id_penjualan " +
             "LEFT JOIN produk pr ON dp.id_produk = pr.id_produk " +
             "LEFT JOIN karyawan k ON p.id_karyawan = k.id_karyawan";

    java.sql.Connection conn = null;
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;

    try {
        conn = koneksi.getConnection();
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_penjualan"),
                rs.getString("nama_produk"),
                rs.getInt("jumlah"),
                rupiahFormat.format(rs.getDouble("harga_satuan")), // Formatted
                rupiahFormat.format(rs.getDouble("subtotal")),      // Formatted
                rs.getString("kategori"),
                rs.getString("nama_karyawan"),
                rs.getDate("tanggal")
            });
        }

        tbpenjualan.setModel(model);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal load data penjualan:\n" + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
     private void customizeTable() {
          JTableHeader header = tbpenjualan.getTableHeader();
           header.setFont(new Font("Inter", Font.BOLD, 11));
           header.setForeground(Color.black);
            header.setOpaque(false);
            tbpenjualan.setFont(new Font("Arial", Font.PLAIN, 10));
            tbpenjualan.setRowHeight(30); 
            tbpenjualan.setShowGrid(true); 
            tbpenjualan.setIntercellSpacing(new java.awt.Dimension(0, 0));
            tbpenjualan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
        restok = new javax.swing.JButton();
        karyawan = new javax.swing.JButton();
        pembelian = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbpenjualan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard.setBorder(null);
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 150, 50));

        transaksi.setBorder(null);
        transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 140, 50));

        restok.setBorder(null);
        restok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restokActionPerformed(evt);
            }
        });
        getContentPane().add(restok, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 140, 40));

        karyawan.setBorder(null);
        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 140, 40));

        pembelian.setBorder(null);
        pembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pembelianActionPerformed(evt);
            }
        });
        getContentPane().add(pembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 160, 40));

        tbpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No nota", "Nama produk", "Jumlah", "Harga", "Total", "Kategori", "Nama karyawan", "Tanggal"
            }
        ));
        jScrollPane2.setViewportView(tbpenjualan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 1040, 460));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/laporan penjualan (4).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiActionPerformed
        // TODO add your handling code here:
          new transaksi().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_transaksiActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        // TODO add your handling code here:
          new dashboard().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_dashboardActionPerformed

    private void restokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restokActionPerformed
        // TODO add your handling code here:
          new restok().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_restokActionPerformed

    private void karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karyawanActionPerformed
        // TODO add your handling code here:
          new karyawan().setVisible(true);
        this.setVisible(false);   
    }//GEN-LAST:event_karyawanActionPerformed

    private void pembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pembelianActionPerformed
        // TODO add your handling code here:
         new laporanpembelian().setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_pembelianActionPerformed

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
            java.util.logging.Logger.getLogger(laporanpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporanpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporanpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporanpenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporanpenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton pembelian;
    private javax.swing.JButton restok;
    private javax.swing.JTable tbpenjualan;
    private javax.swing.JButton transaksi;
    // End of variables declaration//GEN-END:variables

    private void makeButtonTransparent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

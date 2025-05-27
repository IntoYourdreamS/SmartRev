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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author acer
 */
public class laporanpembelian extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public laporanpembelian() {
        initComponents();
        javax.swing.table.JTableHeader header = tbpembelian.getTableHeader();
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
        makeButtonTransparent(penjualan);
        makeButtonTransparent(reset);
        makeButtonTransparent(export);
        customizeDateChooser(tanggal_awal);
        customizeDateChooser(tanggal_akhir);
        loadDataPembelian();

    }

    private void customizeDateChooser(com.toedter.calendar.JDateChooser dateChooser) {
        // Transparansi untuk panel utama
        dateChooser.setOpaque(false);

        try {
            // Akses komponen text field melalui DateEditor
            Component editorComponent = dateChooser.getDateEditor().getUiComponent();

            if (editorComponent instanceof JTextField) {
                JTextField textField = (JTextField) editorComponent;
                textField.setOpaque(false); // Background transparan
                textField.setBackground(new Color(0, 0, 0, 0));
                textField.setForeground(Color.BLACK); // Warna teks
                textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error customizing date chooser: " + e.getMessage());
        }

        // Hapus border
        dateChooser.setBorder(BorderFactory.createEmptyBorder());
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Laporan_Pembelian.xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Laporan Penjualan");

                // Create header style
                XSSFCellStyle headerStyle = workbook.createCellStyle();
                XSSFFont headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short) 12);
                headerStyle.setFont(headerFont);

                // Set header background color
                headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

                // Create header row
                XSSFRow headerRow = sheet.createRow(0);
                for (int i = 0; i < tbpembelian.getColumnCount(); i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(tbpembelian.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // Add data rows
                for (int i = 0; i < tbpembelian.getRowCount(); i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    for (int j = 0; j < tbpembelian.getColumnCount(); j++) {
                        Object value = tbpembelian.getValueAt(i, j);
                        XSSFCell cell = row.createCell(j);

                        if (value != null) {
                            // Handle different data types
                            if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else if (value instanceof Date) {
                                cell.setCellValue((Date) value);

                                // Create date style
                                XSSFCellStyle dateStyle = workbook.createCellStyle();
                                dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy"));
                                cell.setCellStyle(dateStyle);
                            } else {
                                cell.setCellValue(value.toString());
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                // Auto-size columns
                for (int i = 0; i < tbpembelian.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                // Save the file
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(this,
                            "File berhasil diekspor ke: " + filePath,
                            "Ekspor Berhasil",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal ekspor file: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Terjadi kesalahan: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void loadDataPembelian() {
        DefaultTableModel model = (DefaultTableModel) tbpembelian.getModel();
        model.setRowCount(0); // Clear existing data

        // Create DecimalFormat for Indonesian Rupiah currency
        DecimalFormat rupiahFormat = new DecimalFormat("Rp #,###");

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = koneksi.getConnection();

            String query = "SELECT p.id_pembelian, pr.nama_produk, dp.jumlah, dp.harga_beli, "
                    + "(dp.jumlah * dp.harga_beli) as total, pr.kategori, "
                    + "k.nama_karyawan, p.tanggal "
                    + "FROM pembelian p "
                    + "JOIN detail_pembelian dp ON p.id_pembelian = dp.id_pembelian "
                    + "JOIN produk pr ON dp.id_produk = pr.id_produk "
                    + "LEFT JOIN karyawan k ON p.id_karyawan = k.id_karyawan "
                    + "ORDER BY p.tanggal DESC";

            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_pembelian"),
                    rs.getString("nama_produk"),
                    rs.getInt("jumlah"),
                    rupiahFormat.format(rs.getInt("harga_beli")), // Formatted
                    rupiahFormat.format(rs.getInt("total")), // Formatted
                    rs.getString("kategori"),
                    rs.getString("nama_karyawan"),
                    rs.getDate("tanggal")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data pembelian: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

    }

    private void customizeTable() {
        JTableHeader header = tbpembelian.getTableHeader();
        header.setFont(new Font("Inter", Font.BOLD, 11));
        header.setForeground(Color.black);
        header.setOpaque(false);
        tbpembelian.setFont(new Font("Arial", Font.PLAIN, 10));
        tbpembelian.setRowHeight(30);
        tbpembelian.setShowGrid(true);
        tbpembelian.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbpembelian.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tanggal_awal = new com.toedter.calendar.JDateChooser();
        tanggal_akhir = new com.toedter.calendar.JDateChooser();
        reset = new javax.swing.JButton();
        export = new javax.swing.JButton();
        penjualan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbpembelian = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(tanggal_awal, new org.netbeans.lib.awtextra.AbsoluteConstraints(758, 88, 210, 30));
        getContentPane().add(tanggal_akhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(994, 88, 210, 30));

        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 80, 40, 50));

        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });
        getContentPane().add(export, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 80, 50, 50));

        penjualan.setBorder(null);
        penjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                penjualanActionPerformed(evt);
            }
        });
        getContentPane().add(penjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 170, 40));

        tbpembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbpembelian);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 1050, 470));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/laporan pembelian.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void penjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penjualanActionPerformed
        // TODO add your handling code here:
        new laporanpenjualan().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_penjualanActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        tanggal_awal.setDate(null);
        tanggal_akhir.setDate(null);

        // Load semua data tanpa filter
        loadDataPembelian();
    }//GEN-LAST:event_resetActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        // TODO add your handling code here:
        // Buat dialog pilihan export
        Object[] options = {"Export ke Excel", "Batal"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Pilih format export:",
                "Export Data",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            //            exportToCSV();
            exportToExcel();
            //    } else if (choice == 1) {
            //        exportToPDF();
            //    }
        }
    }//GEN-LAST:event_exportActionPerformed

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
    private javax.swing.JButton export;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton penjualan;
    private javax.swing.JButton reset;
    private com.toedter.calendar.JDateChooser tanggal_akhir;
    private com.toedter.calendar.JDateChooser tanggal_awal;
    private javax.swing.JTable tbpembelian;
    // End of variables declaration//GEN-END:variables
}

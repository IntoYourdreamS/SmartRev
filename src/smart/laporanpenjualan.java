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
import java.awt.Desktop;
import java.awt.Font;
import java.io.BufferedWriter;
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
import java.util.Calendar;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author sobri
 */
public class laporanpenjualan extends javax.swing.JFrame {

    public laporanpenjualan() {
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
            makeButtonTransparent(reset);
            makeButtonTransparent(export);
            customizeDateChooser(tanggal_awal);
            customizeDateChooser(tanggal_akhir);

            loadDataPenjualan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inisialisasi: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        // Tambahkan action listener untuk tanggal
        tanggal_awal.addPropertyChangeListener("date", evt -> filterDataByDate());
        tanggal_akhir.addPropertyChangeListener("date", evt -> filterDataByDate());

        // Tambahkan action listener untuk tombol reset
        reset.addActionListener(this::resetActionPerformed);

        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
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

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
        fileChooser.setSelectedFile(new File("Laporan_Penjualan.csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(filePath); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

                // Write header
                for (int i = 0; i < tbpenjualan.getColumnCount(); i++) {
                    bufferedWriter.write(tbpenjualan.getColumnName(i));
                    if (i < tbpenjualan.getColumnCount() - 1) {
                        bufferedWriter.write(",");
                    }
                }
                bufferedWriter.newLine();

                // Write data
                for (int i = 0; i < tbpenjualan.getRowCount(); i++) {
                    for (int j = 0; j < tbpenjualan.getColumnCount(); j++) {
                        Object value = tbpenjualan.getValueAt(i, j);
                        String cellValue = (value != null) ? value.toString() : "";

                        // Escape commas and quotes in CSV
                        if (cellValue.contains(",") || cellValue.contains("\"")) {
                            cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                        }

                        bufferedWriter.write(cellValue);
                        if (j < tbpenjualan.getColumnCount() - 1) {
                            bufferedWriter.write(",");
                        }
                    }
                    bufferedWriter.newLine();
                }

                JOptionPane.showMessageDialog(this,
                        "File CSV berhasil diekspor ke: " + filePath,
                        "Ekspor Berhasil",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal ekspor file: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Laporan_Penjualan.xlsx"));

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
                for (int i = 0; i < tbpenjualan.getColumnCount(); i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(tbpenjualan.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // Add data rows
                for (int i = 0; i < tbpenjualan.getRowCount(); i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    for (int j = 0; j < tbpenjualan.getColumnCount(); j++) {
                        Object value = tbpenjualan.getValueAt(i, j);
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
                for (int i = 0; i < tbpenjualan.getColumnCount(); i++) {
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

    private void filterDataByDate() {
        Date startDate = tanggal_awal.getDate();
        Date endDate = tanggal_akhir.getDate();

        if (startDate != null && endDate != null) {
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this,
                        "Tanggal awal tidak boleh lebih besar dari tanggal akhir",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tambahkan 1 hari ke endDate untuk mencakup seluruh hari terakhir
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DATE, 1);
            Date adjustedEndDate = cal.getTime();

            loadDataPenjualanByDate(startDate, adjustedEndDate);
        } else if (startDate != null || endDate != null) {
            JOptionPane.showMessageDialog(this,
                    "Harap pilih kedua tanggal untuk filter",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

//    private void loadDataPenjualan() {
//    loadDataPenjualanByDate(null, null);
//}
    private void loadDataPenjualanByDate(Date startDate, Date endDate) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No Nota");
        model.addColumn("Nama Produk");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total");
        model.addColumn("Kategori");
        model.addColumn("Nama Karyawan");
        model.addColumn("Tanggal");

        DecimalFormat rupiahFormat = new DecimalFormat("Rp #,###.00");

        String sql = "SELECT "
                + "p.id_penjualan, "
                + "pr.nama_produk, "
                + "dp.jumlah, "
                + "dp.harga_satuan, "
                + "dp.subtotal, "
                + "dp.kategori, "
                + "k.nama_karyawan, "
                + "p.tanggal "
                + "FROM detail_penjualan dp "
                + "JOIN penjualan p ON dp.id_penjualan = p.id_penjualan "
                + "LEFT JOIN produk pr ON dp.id_produk = pr.id_produk "
                + "LEFT JOIN karyawan k ON p.id_karyawan = k.id_karyawan "
                + "WHERE p.tanggal BETWEEN ? AND ?";

        java.sql.Connection conn = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;

        try {
            conn = koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));
            rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_penjualan"),
                    rs.getString("nama_produk"),
                    rs.getInt("jumlah"),
                    rupiahFormat.format(rs.getDouble("harga_satuan")),
                    rupiahFormat.format(rs.getDouble("subtotal")),
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        String sql = "SELECT "
                + "p.id_penjualan, "
                + "pr.nama_produk, "
                + "dp.jumlah, "
                + "dp.harga_satuan, "
                + "dp.subtotal, "
                + "dp.kategori, "
                + "k.nama_karyawan, "
                + "p.tanggal "
                + "FROM detail_penjualan dp "
                + "JOIN penjualan p ON dp.id_penjualan = p.id_penjualan "
                + "LEFT JOIN produk pr ON dp.id_produk = pr.id_produk "
                + "LEFT JOIN karyawan k ON p.id_karyawan = k.id_karyawan";

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
                    rupiahFormat.format(rs.getDouble("subtotal")), // Formatted
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
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

        export = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        tanggal_awal = new com.toedter.calendar.JDateChooser();
        tanggal_akhir = new com.toedter.calendar.JDateChooser();
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

        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });
        getContentPane().add(export, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 80, 50, 50));

        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 80, 40, 50));
        getContentPane().add(tanggal_awal, new org.netbeans.lib.awtextra.AbsoluteConstraints(758, 88, 210, 30));
        getContentPane().add(tanggal_akhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(994, 88, 210, 30));

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/laporan penjualan.png"))); // NOI18N
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

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        tanggal_awal.setDate(null);
        tanggal_akhir.setDate(null);

        // Load semua data tanpa filter
        loadDataPenjualan();
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
    private javax.swing.JButton dashboard;
    private javax.swing.JButton export;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton pembelian;
    private javax.swing.JButton reset;
    private javax.swing.JButton restok;
    private com.toedter.calendar.JDateChooser tanggal_akhir;
    private com.toedter.calendar.JDateChooser tanggal_awal;
    private javax.swing.JTable tbpenjualan;
    private javax.swing.JButton transaksi;
    // End of variables declaration//GEN-END:variables

    private void makeButtonTransparent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

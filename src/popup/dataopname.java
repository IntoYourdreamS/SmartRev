/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import Config.koneksi;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import smart.*;

/**
 *
 * @author acer
 */
public class dataopname extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public dataopname() {
        initComponents();
        javax.swing.table.JTableHeader header = tbdataopname.getTableHeader();
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
         makeButtonTransparent(reset);
          makeButtonTransparent(export);
        this.setBackground(new Color(0, 0, 0, 0));
        loadDataToTable();
         customizeDateChooser(tanggal_awal);
        customizeDateChooser(tanggal_akhir);
        // Add property change listeners for date filtering
        tanggal_awal.addPropertyChangeListener("date", evt -> filterDataByDate());
        tanggal_akhir.addPropertyChangeListener("date", evt -> filterDataByDate());
    }
    
    
    
    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
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
    
    private void customizeTable() {
        JTableHeader header = tbdataopname.getTableHeader();
        header.setFont(new Font("Inter", Font.BOLD, 11));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        tbdataopname.setFont(new Font("Arial", Font.PLAIN, 10));
        tbdataopname.setRowHeight(30); 
        tbdataopname.setShowGrid(true); 
        tbdataopname.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbdataopname.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

            // Add 1 day to endDate to include the entire last day
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(java.util.Calendar.DATE, 1);
            Date adjustedEndDate = cal.getTime();

            loadDataToTableByDate(startDate, adjustedEndDate);
        } else if (startDate != null || endDate != null) {
            JOptionPane.showMessageDialog(this,
                    "Harap pilih kedua tanggal untuk filter",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
  private void loadDataToTableByDate(Date startDate, Date endDate) {
     DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Barang", "Nama Barang", "Stok Sistem", "Stok Fisik", "Selisih", "Catatan", "Tanggal"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Nonaktifkan edit untuk semua sel
        }
    };
    tbdataopname.setModel(model);

    try (Connection conn = koneksi.getConnection()) {
        String query = "SELECT kd_barang, namaBarang, stokSistem, stokFisik, selisih, catatan, create_at FROM stok_opname " +
                       "WHERE create_at BETWEEN ? AND ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idBarang = rs.getString("kd_barang");
                    String namaBarang = rs.getString("namaBarang");
                    int stokSistem = rs.getInt("stokSistem");
                    int stokFisik = rs.getInt("stokFisik");
                    String selisih = rs.getString("selisih");
                    String catatan = rs.getString("catatan");
                    java.sql.Date tanggal = rs.getDate("create_at");

                    model.addRow(new Object[]{idBarang, namaBarang, stokSistem, stokFisik, selisih, catatan, tanggal});
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Gagal memuat data opname: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    // Center alignment
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < tbdataopname.getColumnCount(); i++) {
        tbdataopname.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}

  private void loadDataToTable() {
   DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID Barang", "Nama Barang", "Stok Sistem", "Stok Fisik", "Selisih", "Catatan", "Tanggal"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Nonaktifkan edit untuk semua sel
        }
    };
    tbdataopname.setModel(model);

    try (Connection conn = koneksi.getConnection(); 
         Statement stmt = conn.createStatement()) {

        String query = "SELECT kd_barang, namaBarang, stokSistem, stokFisik, selisih, catatan, create_at FROM stok_opname";

        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String idBarang = rs.getString("kd_barang");
                String namaBarang = rs.getString("namaBarang");
                int stokSistem = rs.getInt("stokSistem");
                int stokFisik = rs.getInt("stokFisik");
                String selisih = rs.getString("selisih");
                String catatan = rs.getString("catatan");
                java.sql.Date tanggal = rs.getDate("create_at");  // ✅ Ganti dari Timestamp ke Date

                model.addRow(new Object[]{idBarang, namaBarang, stokSistem, stokFisik, selisih, catatan, tanggal});
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Gagal memuat data opname: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    // Center alignment
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    for (int i = 0; i < tbdataopname.getColumnCount(); i++) {
        tbdataopname.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}


    
    
    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Laporan_Stok_Opname.xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Laporan Stok Opname");

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
                for (int i = 0; i < tbdataopname.getColumnCount(); i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(tbdataopname.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // Add data rows
                for (int i = 0; i < tbdataopname.getRowCount(); i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    for (int j = 0; j < tbdataopname.getColumnCount(); j++) {
                        Object value = tbdataopname.getValueAt(i, j);
                        XSSFCell cell = row.createCell(j);

                        if (value != null) {
                            if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else {
                                cell.setCellValue(value.toString());
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                // Auto-size columns
                for (int i = 0; i < tbdataopname.getColumnCount(); i++) {
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

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal ekspor file: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
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

        kembali = new javax.swing.JButton();
        tanggal_akhir = new com.toedter.calendar.JDateChooser();
        reset = new javax.swing.JButton();
        export = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbdataopname = new javax.swing.JTable();
        tanggal_awal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kembali.setBorder(null);
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 660, 130, 30));
        getContentPane().add(tanggal_akhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 70, 210, 30));

        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 60, 40, 50));

        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });
        getContentPane().add(export, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 60, 50, 50));

        tbdataopname.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id barang", "Nama barang", "Stok sistem", "Stok fisik", "Selisih", "Catatan", "Tanggal"
            }
        ));
        jScrollPane2.setViewportView(tbdataopname);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 1140, 500));
        getContentPane().add(tanggal_awal, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 210, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data laporan opname11.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
         new stokopname().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_kembaliActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
          tanggal_awal.setDate(null);
        tanggal_akhir.setDate(null);

        // Load semua data tanpa filter
        loadDataToTable();
    }//GEN-LAST:event_resetActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
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
            exportToExcel();
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
            java.util.logging.Logger.getLogger(dataopname.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataopname.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataopname.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataopname.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new dataopname().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton export;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton kembali;
    private javax.swing.JButton reset;
    private com.toedter.calendar.JDateChooser tanggal_akhir;
    private com.toedter.calendar.JDateChooser tanggal_awal;
    private javax.swing.JTable tbdataopname;
    // End of variables declaration//GEN-END:variables
}

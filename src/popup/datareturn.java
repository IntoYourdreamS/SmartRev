/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import javax.swing.JButton;
import smart.*;
import Config.koneksi;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author acer
 */
public class datareturn extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public datareturn() {
        initComponents();
        makeButtonTransparent(kembali);
        makeButtonTransparent(inputreturn);
        makeButtonTransparent(reset);
        makeButtonTransparent(export);
        loadTableRetur();
        tbldatareturn.setDefaultEditor(Object.class, null);
    
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

            loadTableReturByDate(startDate, adjustedEndDate);
        } else if (startDate != null || endDate != null) {
            JOptionPane.showMessageDialog(this,
                    "Harap pilih kedua tanggal untuk filter",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void loadTableReturByDate(Date startDate, Date endDate) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No retur");
        model.addColumn("No Barang");
        model.addColumn("Nama");
        model.addColumn("Tanggal retur");
        model.addColumn("Alasan");

        try {
            String sql = "SELECT id_retur_penjualan, id_produk, nama_produk, tanggal_retur, alasan " +
                         "FROM retur_penjualan " +
                         "WHERE tanggal_retur BETWEEN ? AND ?";
            
            Connection conn = koneksi.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_retur_penjualan"),
                    rs.getString("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getString("tanggal_retur"),
                    rs.getString("alasan")
                });
            }

            tbldatareturn.setModel(model);
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    
    private void loadTableRetur() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No retur");
        model.addColumn("No Barang");
        model.addColumn("Nama");
        model.addColumn("Tanggal retur");
        model.addColumn("Alasan");

        try {
            String sql = "SELECT id_retur_penjualan, id_produk, nama_produk, tanggal_retur, alasan FROM retur_penjualan";
            Connection conn = koneksi.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_retur_penjualan"),
                    rs.getString("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getString("tanggal_retur"),
                    rs.getString("alasan")
                });
            }

            tbldatareturn.setModel(model);
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    
    private void loadDataToTable() {
        loadTableRetur();
    }
    
    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Laporan_Retur_Penjualan.xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Laporan Retur Penjualan");

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
                for (int i = 0; i < tbldatareturn.getColumnCount(); i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(tbldatareturn.getColumnName(i));
                    cell.setCellStyle(headerStyle);
                }

                // Add data rows
                for (int i = 0; i < tbldatareturn.getRowCount(); i++) {
                    XSSFRow row = sheet.createRow(i + 1);
                    for (int j = 0; j < tbldatareturn.getColumnCount(); j++) {
                        Object value = tbldatareturn.getValueAt(i, j);
                        XSSFCell cell = row.createCell(j);

                        if (value != null) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                // Auto-size columns
                for (int i = 0; i < tbldatareturn.getColumnCount(); i++) {
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

        inputreturn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatareturn = new javax.swing.JTable();
        kembali = new javax.swing.JButton();
        tanggal_awal = new com.toedter.calendar.JDateChooser();
        tanggal_akhir = new com.toedter.calendar.JDateChooser();
        reset = new javax.swing.JButton();
        export = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputreturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputreturnActionPerformed(evt);
            }
        });
        getContentPane().add(inputreturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 650, 200, 40));

        tbldatareturn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No retur", "No Barang", "Nama", "Tanggal return", "Alasan"
            }
        ));
        jScrollPane1.setViewportView(tbldatareturn);
        if (tbldatareturn.getColumnModel().getColumnCount() > 0) {
            tbldatareturn.getColumnModel().getColumn(1).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 1100, 480));

        kembali.setBorder(null);
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 660, 150, 30));
        getContentPane().add(tanggal_awal, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 210, 30));
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data laporan return (1)0.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1390, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
         new restok().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_kembaliActionPerformed

    private void inputreturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputreturnActionPerformed
        // TODO add your handling code here:
        inputreturn popup = new inputreturn();
                popup.setVisible(true);  
    }//GEN-LAST:event_inputreturnActionPerformed

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
            java.util.logging.Logger.getLogger(datareturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datareturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datareturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datareturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new datareturn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton export;
    private javax.swing.JButton inputreturn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton kembali;
    private javax.swing.JButton reset;
    private com.toedter.calendar.JDateChooser tanggal_akhir;
    private com.toedter.calendar.JDateChooser tanggal_awal;
    private javax.swing.JTable tbldatareturn;
    // End of variables declaration//GEN-END:variables
}

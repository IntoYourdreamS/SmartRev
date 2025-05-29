/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import Config.koneksi;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Timestamp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Calendar;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

        // Custom header table
        javax.swing.table.JTableHeader header = tbkaryawan2.getTableHeader();
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

        loadDataToTable();
        customizeTable();
        makeButtonTransparent(karyawan);
        makeButtonTransparent(dashboard);
        makeButtonTransparent(transaksi);
        makeButtonTransparent(restock);
        makeButtonTransparent(laporan);
        makeButtonTransparent(export);
        makeButtonTransparent(reset);

    }

    private void filterDataByName(String nama) {
        // Model tabel dengan kolom yang diinginkan
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No Karyawan", "Nama Karyawan", "No HP", "Role", "Jam Masuk", "Jam Keluar", "Keterangan"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat tabel tidak bisa di-edit
            }
        };

        tbkaryawan2.setModel(model);

        try (Connection conn = koneksi.getConnection()) {
            // Query untuk mencari karyawan berdasarkan nama
            String query = "SELECT k.id_karyawan, k.nama_karyawan, k.no_telp, k.role, "
                    + "dk.tanggal_masuk, dk.tanggal_keluar "
                    + "FROM karyawan k "
                    + "LEFT JOIN detail_karyawan dk ON k.id_karyawan = dk.id_karyawan "
                    + "WHERE k.nama_karyawan LIKE ? "
                    + "ORDER BY k.id_karyawan, dk.tanggal_masuk DESC";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + nama + "%"); // Gunakan wildcard untuk pencarian partial

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Ambil timestamp jam masuk dan keluar
                        java.sql.Timestamp jamMasuk = rs.getTimestamp("tanggal_masuk");
                        java.sql.Timestamp jamKeluar = rs.getTimestamp("tanggal_keluar");

                        // Lewati baris jika tidak ada data jam masuk dan keluar
                        if (jamMasuk == null && jamKeluar == null) {
                            continue;
                        }

                        // Format tanggal masuk
                        String jamMasukFormatted = (jamMasuk != null)
                                ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamMasuk) : "Belum ada data";
                        // Format tanggal keluar
                        String jamKeluarFormatted = (jamKeluar != null)
                                ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamKeluar) : "Belum keluar";

                        // Tentukan keterangan berdasarkan timestamp
                        String keterangan = "Tepat Waktu"; // Default

                        if (jamMasuk != null && jamKeluar != null) {
                            // Buat batas jam masuk dan keluar (09:00 dan 17:00)
                            String tanggalMasukStr = new SimpleDateFormat("yyyy-MM-dd").format(jamMasuk) + " 09:00:00";
                            String tanggalKeluarStr = new SimpleDateFormat("yyyy-MM-dd").format(jamKeluar) + " 17:00:00";

                            Timestamp batasJamMasuk = Timestamp.valueOf(tanggalMasukStr);
                            Timestamp batasJamKeluar = Timestamp.valueOf(tanggalKeluarStr);

                            // Logika untuk menentukan keterangan
                            if (jamMasuk.after(batasJamMasuk)) {
                                keterangan = "Telat";
                            }
                            if (jamKeluar.before(batasJamKeluar)) {
                                keterangan = "Pulang Dulu";
                            }
                        }

                        // Tambahkan baris ke model
                        model.addRow(new Object[]{
                            rs.getString("id_karyawan"),
                            rs.getString("nama_karyawan"),
                            rs.getString("no_telp"),
                            rs.getString("role"),
                            jamMasukFormatted,
                            jamKeluarFormatted,
                            keterangan
                        });
                    }
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

        // Set default values untuk tahun dan bulan saat ini
        Calendar cal = Calendar.getInstance();
        tahun.setYear(cal.get(Calendar.YEAR));
        bulan.setMonth(cal.get(Calendar.MONTH));

        // Add listeners untuk filter otomatis
        setupDateFilters();

    }

    private void loadDataToTable() {
        // Model tabel dengan kolom yang diinginkan
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No Karyawan", "Nama Karyawan", "No HP", "Role", "Jam Masuk", "Jam Keluar", "Keterangan"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat tabel tidak bisa di-edit
            }
        };

        tbkaryawan2.setModel(model);

        try (Connection conn = koneksi.getConnection()) {
            // Query untuk mendapatkan semua data karyawan beserta jam masuk dan keluar
            String query = "SELECT k.id_karyawan, k.nama_karyawan, k.no_telp, k.role, "
                    + "dk.tanggal_masuk, dk.tanggal_keluar "
                    + "FROM karyawan k "
                    + "LEFT JOIN detail_karyawan dk ON k.id_karyawan = dk.id_karyawan "
                    + "ORDER BY k.id_karyawan, dk.tanggal_masuk DESC";

            try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    // Ambil timestamp jam masuk dan keluar
                    java.sql.Timestamp jamMasuk = rs.getTimestamp("tanggal_masuk");
                    java.sql.Timestamp jamKeluar = rs.getTimestamp("tanggal_keluar");

                    // Lewati baris jika tidak ada data jam masuk dan keluar
                    if (jamMasuk == null && jamKeluar == null) {
                        continue;
                    }

                    // Format tanggal masuk
                    String jamMasukFormatted = (jamMasuk != null) ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamMasuk) : "Belum ada data";
                    // Format tanggal keluar
                    String jamKeluarFormatted = (jamKeluar != null) ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamKeluar) : "Belum keluar";

                    // Tentukan keterangan berdasarkan timestamp
                    String keterangan = "Tepat Waktu"; // Default

                    if (jamMasuk != null && jamKeluar != null) {
                        // Buat batas jam masuk dan keluar (09:00 dan 17:00)
                        String tanggalMasukStr = new SimpleDateFormat("yyyy-MM-dd").format(jamMasuk) + " 09:00:00";
                        String tanggalKeluarStr = new SimpleDateFormat("yyyy-MM-dd").format(jamKeluar) + " 17:00:00";

                        Timestamp batasJamMasuk = Timestamp.valueOf(tanggalMasukStr);
                        Timestamp batasJamKeluar = Timestamp.valueOf(tanggalKeluarStr);

                        // Logika untuk menentukan keterangan
                        if (jamMasuk.after(batasJamMasuk)) {
                            keterangan = "Telat";
                        }
                        if (jamKeluar.before(batasJamKeluar)) {
                            keterangan = "Pulang Dulu";
                        }
                    }

                    // Tambahkan baris ke model
                    model.addRow(new Object[]{
                        rs.getString("id_karyawan"),
                        rs.getString("nama_karyawan"),
                        rs.getString("no_telp"),
                        rs.getString("role"),
                        jamMasukFormatted,
                        jamKeluarFormatted,
                        keterangan
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + e.getMessage(),
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

    private void setupDateFilters() {
        // Listener untuk JYearChooser
        tahun.addPropertyChangeListener("year", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterDataByDate();
            }
        });

        // Listener untuk JMonthChooser
        bulan.addPropertyChangeListener("month", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterDataByDate();
            }
        });
    }

    private void filterDataByDate() {
        int selectedYear = tahun.getYear();
        int selectedMonth = bulan.getMonth() + 1; // JMonthChooser menggunakan 0-based indexing

        loadDataToTable(selectedYear, selectedMonth);
    }

    // Method untuk load data dengan filter tahun dan bulan
    private void loadDataToTable(int filterYear, int filterMonth) {
        // Model tabel dengan kolom yang diinginkan
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No Karyawan", "Nama Karyawan", "No HP", "Role", "Jam Masuk", "Jam Keluar", "Keterangan"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat tabel tidak bisa di-edit
            }
        };

        tbkaryawan2.setModel(model);

        try (Connection conn = koneksi.getConnection()) {
            // Query dasar
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT k.id_karyawan, k.nama_karyawan, k.no_telp, k.role, ")
                    .append("dk.tanggal_masuk, dk.tanggal_keluar ")
                    .append("FROM karyawan k ")
                    .append("LEFT JOIN detail_karyawan dk ON k.id_karyawan = dk.id_karyawan ");

            // Tambahkan filter jika diperlukan
            if (filterYear > 0 && filterMonth > 0) {
                queryBuilder.append("WHERE YEAR(dk.tanggal_masuk) = ? AND MONTH(dk.tanggal_masuk) = ? ");
            } else if (filterYear > 0) {
                queryBuilder.append("WHERE YEAR(dk.tanggal_masuk) = ? ");
            } else if (filterMonth > 0) {
                queryBuilder.append("WHERE MONTH(dk.tanggal_masuk) = ? ");
            }

            queryBuilder.append("ORDER BY k.id_karyawan, dk.tanggal_masuk DESC");

            String query = queryBuilder.toString();

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                // Set parameter untuk prepared statement
                int paramIndex = 1;
                if (filterYear > 0 && filterMonth > 0) {
                    stmt.setInt(paramIndex++, filterYear);
                    stmt.setInt(paramIndex++, filterMonth);
                } else if (filterYear > 0) {
                    stmt.setInt(paramIndex++, filterYear);
                } else if (filterMonth > 0) {
                    stmt.setInt(paramIndex++, filterMonth);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Ambil timestamp jam masuk dan keluar
                        java.sql.Timestamp jamMasuk = rs.getTimestamp("tanggal_masuk");
                        java.sql.Timestamp jamKeluar = rs.getTimestamp("tanggal_keluar");

                        // Lewati baris jika tidak ada data jam masuk dan keluar (untuk filter)
                        if (jamMasuk == null && jamKeluar == null && (filterYear > 0 || filterMonth > 0)) {
                            continue;
                        }

                        // Format tanggal masuk
                        String jamMasukFormatted = (jamMasuk != null) ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamMasuk) : "Belum ada data";
                        // Format tanggal keluar
                        String jamKeluarFormatted = (jamKeluar != null) ? new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(jamKeluar) : "Belum keluar";

                        // Tentukan keterangan berdasarkan timestamp
                        String keterangan = "Tepat Waktu"; // Default

                        if (jamMasuk != null && jamKeluar != null) {
                            // Buat batas jam masuk dan keluar (09:00 dan 17:00)
                            String tanggalMasukStr = new SimpleDateFormat("yyyy-MM-dd").format(jamMasuk) + " 09:00:00";
                            String tanggalKeluarStr = new SimpleDateFormat("yyyy-MM-dd").format(jamKeluar) + " 17:00:00";

                            Timestamp batasJamMasuk = Timestamp.valueOf(tanggalMasukStr);
                            Timestamp batasJamKeluar = Timestamp.valueOf(tanggalKeluarStr);

                            // Logika untuk menentukan keterangan
                            if (jamMasuk.after(batasJamMasuk)) {
                                keterangan = "Telat";
                            }
                            if (jamKeluar.before(batasJamKeluar)) {
                                keterangan = "Pulang Dulu";
                            }
                        }

                        // Tambahkan baris ke model
                        model.addRow(new Object[]{
                            rs.getString("id_karyawan"),
                            rs.getString("nama_karyawan"),
                            rs.getString("no_telp"),
                            rs.getString("role"),
                            jamMasukFormatted,
                            jamKeluarFormatted,
                            keterangan
                        });
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + e.getMessage(),
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tahun = new com.toedter.calendar.JYearChooser();
        bulan = new com.toedter.calendar.JMonthChooser();
        laporan = new javax.swing.JButton();
        restock = new javax.swing.JButton();
        transaksi = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        export = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkaryawan2 = new javax.swing.JTable();
        karyawan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(tahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 90, -1, 40));
        getContentPane().add(bulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, -1, 40));

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

        export.setBorder(null);
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });
        getContentPane().add(export, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 80, 70, 50));

        reset.setBorder(null);
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 90, 40, 40));

        search.setBorder(null);
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        getContentPane().add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 170, 40));

        tbkaryawan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No karyawan", "Nama karyawan", "No hp", "Role", "Jam  masuk", "Jam keluar", "Keterangan"
            }
        ));
        jScrollPane2.setViewportView(tbkaryawan2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 1040, 520));

        karyawan.setBorder(null);
        karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 180, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/karyawan (1)_1.png"))); // NOI18N
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

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai Excel");

        // Set default file name based on filters
        String defaultFileName;
        int selectedYear = tahun.getYear();
        int selectedMonth = bulan.getMonth() + 1; // JMonthChooser uses 0-based index

        if (selectedYear > 0 && selectedMonth > 0) {
            defaultFileName = String.format("Laporan_Karyawan_%02d-%04d.xlsx", selectedMonth, selectedYear);
        } else if (selectedYear > 0) {
            defaultFileName = String.format("Laporan_Karyawan_Tahun_%04d.xlsx", selectedYear);
        } else if (selectedMonth > 0) {
            defaultFileName = String.format("Laporan_Karyawan_Bulan_%02d.xlsx", selectedMonth);
        } else {
            defaultFileName = "Laporan_Karyawan_Semua.xlsx";
        }

        fileChooser.setSelectedFile(new File(defaultFileName));

        // Filter for Excel files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);

        // Show save dialog
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Ensure the file has .xlsx extension
            if (!fileToSave.getName().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try {
                // Create Excel workbook
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Data Karyawan");

                // Get table model
                DefaultTableModel model = (DefaultTableModel) tbkaryawan2.getModel();

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col));
                }

                // Create data rows
                for (int row = 0; row < model.getRowCount(); row++) {
                    Row dataRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Cell cell = dataRow.createCell(col);
                        Object value = model.getValueAt(row, col);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                // Auto-size columns
                for (int col = 0; col < model.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                // Write to file
                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    workbook.write(outputStream);
                    workbook.close();

                    // Show success message
                    JOptionPane.showMessageDialog(this,
                            "Data berhasil diekspor ke Excel!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal mengekspor data: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_exportActionPerformed

    private void karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_karyawanActionPerformed
        // TODO add your handling code here:
        // Menambahkan aksi ke tombol "Tambah"
        new karyawan().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_karyawanActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        // Reset search field
        search.setText("");

        // Reset year and month filters to current date
        Calendar cal = Calendar.getInstance();
        tahun.setYear(cal.get(Calendar.YEAR));
        bulan.setMonth(cal.get(Calendar.MONTH));

        // Reload all data without filters
        loadDataToTable();

        // Optional: Show confirmation message
//    JOptionPane.showMessageDialog(this,
//        "Semua filter telah direset",
//        "Reset Berhasil",
//        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_resetActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String searchText = search.getText().trim(); // Ambil teks dari search field

        if (searchText.isEmpty()) {
            // Jika search kosong, load semua data
            loadDataToTable();
        } else {
            // Jika ada teks pencarian, filter data
            filterDataByName(searchText);
        }
    }//GEN-LAST:event_searchActionPerformed

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
    private com.toedter.calendar.JMonthChooser bulan;
    private javax.swing.JButton dashboard;
    private javax.swing.JButton export;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton karyawan;
    private javax.swing.JButton laporan;
    private javax.swing.JButton reset;
    private javax.swing.JButton restock;
    private javax.swing.JTextField search;
    private com.toedter.calendar.JYearChooser tahun;
    private javax.swing.JTable tbkaryawan2;
    private javax.swing.JButton transaksi;
    // End of variables declaration//GEN-END:variables

    private void loadTableData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

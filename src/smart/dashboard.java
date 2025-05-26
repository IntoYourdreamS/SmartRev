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
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class dashboard extends javax.swing.JFrame {
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public dashboard() {
        initComponents();
        loadDataToTable();
        loadData();
        loadExpiredData();
        tampilkanTotalHarga();
        tampilkanTotalHargaExpired();
        tampilkanPendapatanBersih();
        displayLoggedInEmployeeName();
        jTextField5.setOpaque(false);
        jTextField5.setBackground(new Color(0, 0, 0, 0));
        customizeTable();
        makeButtonTransparent(jButton1);
        makeButtonTransparent(bttnlaporan);
        makeButtonTransparent(bttntransaksi);
        makeButtonTransparent(bttnkaryawan);
        makeButtonTransparent(logout);
        makeButtonTransparent(txdepan);
        
//        setTableData();
        initSalesChart();
    
    }
    


private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
private void loadDataToTable()  {
    // Definisikan model tabel dengan header kolom
    DefaultTableModel model = new DefaultTableModel(
      new Object[][]{}, 
        new String[]{"Kode Barang", "Nama Barang", "Stok"}
    );
    tbhmpirhbis.setModel(model); // Set model ke JTable (asumsi jTable1 adalah nama JTable)

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart", "root", "");
         Statement stmt = conn.createStatement()) {

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


    private void loadExpiredData() {
        try {
            // Gunakan koneksi dari class koneksi
            conn = koneksi.getConnection();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(this, 
                    "Gagal terhubung ke database!", 
                    "Error Database", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Bersihkan tabel terlebih dahulu
            DefaultTableModel model = (DefaultTableModel) expiredtb.getModel();
            model.setRowCount(0);
            
            // Query untuk mengambil data produk yang expired atau akan expired dalam 7 hari
            String query = "SELECT p.id_produk, p.nama_produk, b.kode_barcode, b.tgl_expired " +
                          "FROM barcode b " +
                          "INNER JOIN produk p ON b.id_produk = p.id_produk " +
                          "WHERE b.tgl_expired <= DATE_ADD(CURDATE(), INTERVAL 7 DAY) " +
                          "ORDER BY b.tgl_expired ASC";
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            
            // Format tanggal
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            // Tambahkan data ke tabel
            while (rs.next()) {
                String idProduk = rs.getString("id_produk");
                String namaProduk = rs.getString("nama_produk");
                String barcode = rs.getString("kode_barcode");
                Date tanggalExpired = rs.getDate("tgl_expired");
                
                String tanggalExpiredStr = "";
                if (tanggalExpired != null) {
                    tanggalExpiredStr = dateFormat.format(tanggalExpired);
                }
                
                // Tambahkan baris ke tabel
                model.addRow(new Object[]{
                    idProduk,
                    namaProduk,
                    barcode,
                    tanggalExpiredStr
                });
            }
            
            // Jika tidak ada data expired
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{
                    "Tidak ada data",
                    "Tidak ada produk expired",
                    "-",
                    "-"
                });
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading expired data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data expired: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            // Tutup resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    // Method untuk refresh data
    public void refreshData() {
        loadExpiredData();
    }
    
    // Method untuk menutup koneksi database
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
  private void loadData() {
    DefaultTableModel model = new DefaultTableModel(
        new Object[][]{},
        new String[]{"Kode Produk", "Nama Produk", "Total Terjual", "Kategori"}
    ) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return String.class;
                case 1: return String.class;
                case 2: return Integer.class;
                case 3: return String.class;
                default: return Object.class;
            }
        }
    };
    
    tbpenjualanterlaris.setModel(model);

    // Query yang diperbaiki dengan COALESCE untuk menangani nilai NULL
    String query = """
        SELECT 
            dp.id_produk, 
            COALESCE(p.nama_produk, 'Produk Tidak Dikenal') AS nama_produk,
            SUM(dp.jumlah) AS total_terjual,
            COALESCE(dp.kategori, 'Tidak Diketahui') AS kategori
        FROM detail_penjualan dp
        LEFT JOIN produk p ON dp.id_produk = p.id_produk
        GROUP BY dp.id_produk, p.nama_produk, dp.kategori
        ORDER BY total_terjual DESC
    """;

    try (
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/smart?useUnicode=true&characterEncoding=UTF-8", 
            "root", 
            "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)
    ) {
        while (rs.next()) {
            String kodeProduk = rs.getString("id_produk");
            String namaProduk = rs.getString("nama_produk");
            int totalTerjual = rs.getInt("total_terjual");
            String kategori = rs.getString("kategori");

            model.addRow(new Object[]{
                kodeProduk,
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
  
  private void tampilkanTotalHargaExpired() {
    try {
        Connection conn = koneksi.getConnection();
        if (conn != null) {
            // Query: jumlahkan harga produk yang barcode-nya sudah expired
            String query = "SELECT SUM(p.harga) AS total_expired " +
                           "FROM barcode b " +
                           "JOIN produk p ON b.id_produk = p.id_produk " +
                           "WHERE b.tgl_expired < CURDATE()";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                double totalExpired = rs.getDouble("total_expired");

                // Format ke dalam format Rupiah
                DecimalFormat df = new DecimalFormat("Rp #,##0.00");

                // Tampilkan di jTextField3
                jTextField3.setText(rs.wasNull() ? "Rp 0" : df.format(totalExpired));
            } else {
                jTextField3.setText("Rp 0");
            }

            rs.close();
            stmt.close();
            conn.close();
        } else {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal!");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

  
  private void tampilkanTotalHarga() {
    try {
        Connection conn = koneksi.getConnection();
        if (conn != null) {
            // Query untuk menghitung total subtotal dari detail_penjualan
            String query = "SELECT SUM(subtotal) AS total_harga FROM detail_penjualan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Ambil total harga
                double totalHarga = rs.getDouble("total_harga");
                
                // Format ke Rupiah
                DecimalFormat df = new DecimalFormat("Rp #,##0.00");
                
                // Tampilkan ke jTextField1
                jTextField2.setText(rs.wasNull() ? "Rp.0" : df.format(totalHarga));
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
            }

            // Tutup koneksi
            rs.close();
            stmt.close();
            conn.close();
        } else {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal!");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
  
  private void tampilkanPendapatanBersih() {
    try {
        Connection conn = koneksi.getConnection();
        if (conn != null) {
            String query = "SELECT SUM(subtotal) AS total_harga FROM detail_penjualan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                double totalHarga = rs.getDouble("total_harga");
                double pendapatanBersih = totalHarga * 0.35;
                
                DecimalFormat df = new DecimalFormat("Rp #,##0.00");
                jTextField4.setText(rs.wasNull() ? "Rp 0" : df.format(pendapatanBersih));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
        JTableHeader header = expiredtb.getTableHeader();
        JTableHeader header2 = tbpenjualanterlaris.getTableHeader();
        JTableHeader header3 = tbhmpirhbis.getTableHeader();
        
        header.setFont(new Font("Inter", Font.BOLD, 11));
        header2.setFont(new Font("Inter", Font.BOLD, 11));
        header3.setFont(new Font("Inter", Font.BOLD, 11));

        header.setForeground(Color.WHITE); 
        header2.setForeground(Color.WHITE);
        header3.setForeground(Color.WHITE);

        header.setOpaque(false);
        header2.setOpaque(false);
        header3.setOpaque(false);

        expiredtb.setFont(new Font("Arial", Font.PLAIN, 10));
        tbpenjualanterlaris.setFont(new Font("Arial", Font.PLAIN, 10));
        tbhmpirhbis.setFont(new Font("Arial", Font.PLAIN, 10));
        
        expiredtb.setRowHeight(30); 
        tbpenjualanterlaris.setRowHeight(30);
        tbhmpirhbis.setRowHeight(30);
        
        expiredtb.setShowGrid(true); 
        tbpenjualanterlaris.setShowGrid(true); 
        tbhmpirhbis.setShowGrid(true); 
        
        expiredtb.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbpenjualanterlaris.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbhmpirhbis.setIntercellSpacing(new java.awt.Dimension(0, 0));
        
        expiredtb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbpenjualanterlaris.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbhmpirhbis.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        tbpenjualanterlaris.setSelectionBackground(new Color(25, 25, 25)); 
        tbpenjualanterlaris.setSelectionForeground(Color.WHITE); 
    }
    
private class SalesChartPanel extends JPanel {
    private int[] salesData = new int[12];
    private String[] months = {
        "Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
        "Jul", "Agu", "Sep", "Okt", "Nov", "Des"
    };
    
    // Define fonts
    private final Font titleFont = new Font("Segoe UI Semibold", Font.BOLD, 16);
    private final Font axisLabelFont = new Font("Segoe UI Semibold", Font.PLAIN, 12);
    private final Font valueFont = new Font("Segoe UI Semibold", Font.PLAIN, 10);
    private final Font monthFont = new Font("Segoe UI Semibold", Font.PLAIN, 10);
    private final Font yAxisFont = new Font("Segoe UI Semibold", Font.PLAIN, 10);
    
    public SalesChartPanel() {
        setOpaque(false);
        fetchSalesDataFromDatabase();
    }
    
    private void fetchSalesDataFromDatabase() {
        // Initialize with zeros
        for (int i = 0; i < salesData.length; i++) {
            salesData[i] = 0;
        }

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart", 
                "root", 
                "");

            String sql = "SELECT MONTH(tanggal) as bulan, SUM(total_harga) as total " +
                         "FROM penjualan " +
                         "WHERE YEAR(tanggal) = YEAR(CURDATE()) " +
                         "GROUP BY MONTH(tanggal) " +
                         "ORDER BY MONTH(tanggal)";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int month = rs.getInt("bulan");
                int total = rs.getInt("total");
                if (month >= 1 && month <= 12) {
                    salesData[month - 1] = total;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw chart border
     //   g2d.setColor(Color.BLACK);
      //  g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // Draw title with Segoe UI Semibold
        g2d.setFont(titleFont);
        String title = "Grafik Penjualan Tahunan";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, (getWidth() - titleWidth) / 20, 30);
        
        int width = getWidth() - 40;
        int height = getHeight() - 80;
        int barWidth = width / salesData.length;
        
        // Fixed scale 0-4 million
        final int MAX_SCALE = 4000000;
        
        // Draw bars
        for (int i = 0; i < salesData.length; i++) {
            int barHeight = (int) ((double) salesData[i] / MAX_SCALE * height);
            barHeight = Math.max(0, barHeight); // Ensure not negative
            
            int x = 20 + i * barWidth;
            int y = getHeight() - 60 - barHeight;
            
            // Draw bar
            g2d.setColor(new Color(41, 128, 185));
            g2d.fillRect(x + 5, y, barWidth - 10, barHeight);
            
            // Draw value label with Segoe UI Semibold
            g2d.setColor(Color.BLACK);
            g2d.setFont(valueFont);
            String value = String.format("%,d", salesData[i]);
            int labelWidth = g2d.getFontMetrics().stringWidth(value);
            g2d.drawString(value, x + (barWidth - labelWidth)/2, y - 5);
            
            // Draw month label with Segoe UI Semibold
            g2d.setFont(monthFont);
            String month = months[i];
            int monthWidth = g2d.getFontMetrics().stringWidth(month);
            g2d.drawString(month, x + (barWidth - monthWidth)/2, getHeight() - 40);
        }
        
        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(20, getHeight() - 60, getWidth() - 20, getHeight() - 60); // X-axis
        g2d.drawLine(20, getHeight() - 60, 20, 40); // Y-axis
        
        // Draw X-axis label with Segoe UI Semibold
        g2d.setFont(axisLabelFont);
        String xLabel = "Bulan";
        int xLabelWidth = g2d.getFontMetrics().stringWidth(xLabel);
        g2d.drawString(xLabel, getWidth()/2 - xLabelWidth/2, getHeight() - 20);

        // Draw Y-axis labels (fixed 0-4 million) with Segoe UI Semibold
        g2d.setFont(yAxisFont);
        for (int i = 0; i <= 3; i++) {
            int value = i * 1000000;
            int yPos = getHeight() - 60 - (int)((double)value / MAX_SCALE * height);
            String label = i + " jt";
            g2d.drawString(label, 19 - g2d.getFontMetrics().stringWidth(label), yPos + 3);
        }
    }
}

private void initSalesChart() {
    SalesChartPanel chartPanel = new SalesChartPanel();
    chartPanel.setVisible(true);
    getContentPane().add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 240, 700, 230));
    getContentPane().setComponentZOrder(chartPanel, 0);
    chartPanel.repaint();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tbhmpirhbis = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbpenjualanterlaris = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        expiredtb = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        bttnkaryawan = new javax.swing.JButton();
        bttntransaksi = new javax.swing.JButton();
        bttnlaporan = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        txdepan = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbhmpirhbis.setModel(new javax.swing.table.DefaultTableModel(
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
        tbhmpirhbis.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane3.setViewportView(tbhmpirhbis);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 540, 250, 130));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 540, 380, 130));

        expiredtb.setModel(new javax.swing.table.DefaultTableModel(
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
                "Id Produk", "Nama Produk", "Barcode", "Tanggal Expired"
            }
        ));
        expiredtb.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane1.setViewportView(expiredtb);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 540, 320, 130));

        jButton1.setBackground(new java.awt.Color(85, 85, 85));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 170, 50));

        bttnkaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnkaryawanActionPerformed(evt);
            }
        });
        getContentPane().add(bttnkaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 170, 40));

        bttntransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttntransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(bttntransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 170, 50));

        bttnlaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnlaporanActionPerformed(evt);
            }
        });
        getContentPane().add(bttnlaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 170, 40));

        logout.setBorder(null);
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 140, 50));

        txdepan.setBorder(null);
        txdepan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txdepanActionPerformed(evt);
            }
        });
        getContentPane().add(txdepan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 430, 190, 50));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jTextField2.setBorder(null);
        jTextField2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField2.setEnabled(false);
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 320, 50));

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jTextField3.setBorder(null);
        jTextField3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField3.setEnabled(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 120, 320, 50));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jTextField4.setBorder(null);
        jTextField4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField4.setEnabled(false);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 310, 50));

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Dashboard OWNER.png"))); // NOI18N
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     restok dash = new restok();
dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
dash.setVisible(true);
this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bttnlaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnlaporanActionPerformed
     laporanpenjualan dash = new laporanpenjualan();
dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
dash.setVisible(true);
this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_bttnlaporanActionPerformed

    private void bttntransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttntransaksiActionPerformed
      transaksi dash = new transaksi();
dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
dash.setVisible(true);
this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_bttntransaksiActionPerformed

    private void bttnkaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnkaryawanActionPerformed
      karyawan dash = new karyawan();
dash.setLocationRelativeTo(null); // Optional: pusatkan jendela baru
dash.setVisible(true);
this.dispose(); // Menutup form login sepenuhnya tanpa efek flicker

    }//GEN-LAST:event_bttnkaryawanActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
try {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart", "root", "");
    String idKaryawan = Session.getKode();
    
    if(!idKaryawan.isEmpty()) {
        // Query untuk update record terakhir yang belum memiliki tanggal_keluar
        String query = "UPDATE detail_karyawan SET tanggal_keluar = CURRENT_TIMESTAMP() " +
                      "WHERE id_karyawan = ? AND tanggal_masuk = (" +
                      "   SELECT max_masuk FROM (" +
                      "       SELECT MAX(tanggal_masuk) AS max_masuk " +
                      "       FROM detail_karyawan " +
                      "       WHERE id_karyawan = ? AND tanggal_keluar IS NULL" +
                      "   ) AS temp" +
                      ")";
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, idKaryawan);
        pstmt.setString(2, idKaryawan);
        int rowsAffected = pstmt.executeUpdate();
        
        if(rowsAffected > 0) {
            System.out.println("Logout time updated successfully");
        } else {
            System.out.println("No record found to update");
        }
        
        // Reset session
       // Session.clear();
    //    pstmt.close();
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

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void txdepanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txdepanActionPerformed
        // TODO add your handling code here:
            new stokopname().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_txdepanActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

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
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnkaryawan;
    private javax.swing.JButton bttnlaporan;
    private javax.swing.JButton bttntransaksi;
    private javax.swing.JTable expiredtb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton logout;
    private javax.swing.JTable tbhmpirhbis;
    private javax.swing.JTable tbpenjualanterlaris;
    private javax.swing.JButton txdepan;
    // End of variables declaration//GEN-END:variables
}

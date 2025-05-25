/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smart;

import Config.Session;
import static Config.Session.getKode;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class StrukPrinter implements Printable {
    private static final int PAPER_WIDTH = (int) (70 * 72 / 25.4); // 70mm width
    private static final int PAPER_HEIGHT = (int) (200 * 72 / 25.4);
    private static final int MARGIN = 5;
    private static final int LINE_HEIGHT = 12;
    private final DecimalFormat currencyFormat = new DecimalFormat("Rp#,##0");
    private final DecimalFormat quantityFormat = new DecimalFormat("#");
    
    private String namaToko, alamatToko, teleponToko, noTransaksi, tanggal, namaKasir;
    private List<String[]> items;
    private double total, bayar, kembalian;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g = (Graphics2D) graphics;
        g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Font settings
        Font fontBold = new Font("Courier New", Font.BOLD, 9);
        Font fontRegular = new Font("Courier New", Font.PLAIN, 9);
        Font fontSmall = new Font("Courier New", Font.PLAIN, 8);

        int y = MARGIN;

        // Header
        g.setFont(fontBold);
        centerText(g, namaToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        
        g.setFont(fontSmall);
        centerText(g, alamatToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, teleponToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT + 5;

        drawLine(g, y);
        y += 10;

        // Transaction Info
        g.setFont(fontRegular);
        printLeftRight(g, "No. Transaksi", noTransaksi, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Tanggal", tanggal, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Kasir", namaKasir, y); // Now shows employee name
        y += LINE_HEIGHT + 5;

        drawLine(g, y);
        y += 10;

        // Item Header
        g.setFont(fontBold);
        String header = String.format("%-12s %2s %8s %9s", "NAMA", "QTY", "HARGA", "SUBTOTAL");
        g.drawString(header, MARGIN, y);
        y += LINE_HEIGHT;

        // Items
        g.setFont(fontRegular);
        for (String[] item : items) {
            String nama = truncate(item[0], 12);
            String qty = quantityFormat.format(Integer.parseInt(item[1]));
            String harga = formatCurrency(Double.parseDouble(item[2]));
            String subtotal = formatCurrency(Double.parseDouble(item[3]));
            
            String itemLine = String.format("%-12s %2s %9s %8s", nama, qty, harga, subtotal);
            g.drawString(itemLine, MARGIN, y);
            y += LINE_HEIGHT;
        }

        drawLine(g, y);
        y += 10;

        // Totals
        g.setFont(fontBold);
        printLeftRight(g, "TOTAL", formatCurrency(total), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "TUNAI", formatCurrency(bayar), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "KEMBALI", formatCurrency(kembalian), y);
        y += LINE_HEIGHT + 10;

        // Footer
        g.setFont(fontSmall);
        centerText(g, "Terima kasih atas kunjungan Anda", y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, "Barang yang sudah dibeli tidak dapat", y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, "ditukar/dikembalikan", y, PAPER_WIDTH);

        return PAGE_EXISTS;
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 2) + "..";
    }

    private void centerText(Graphics g, String text, int y, int width) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (width - textWidth) / 2;
        g.drawString(text, x, y);
    }

    private void drawLine(Graphics g, int y) {
        g.drawLine(MARGIN, y, PAPER_WIDTH - MARGIN, y);
    }

    private void printLeftRight(Graphics g, String left, String right, int y) {
        FontMetrics fm = g.getFontMetrics();
        int rightWidth = fm.stringWidth(right);
        g.drawString(left, MARGIN, y);
        g.drawString(right, PAPER_WIDTH - MARGIN - rightWidth, y);
    }

    // Setter methods
    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }

    public void setTeleponToko(String teleponToko) {
        this.teleponToko = teleponToko;
    }

    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public void setItems(List<String[]> items) {
        this.items = items;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }

    public static boolean printStrukDenganKonfirmasi(String kodeTransaksi, String[][] items,
            double total, double bayar, double kembalian, String idKasir) {
        try {
            StrukPrinter printer = new StrukPrinter();
            
            // Get employee name from database
            String namaKasir = getEmployeeName(idKasir);
            
            // Set store info
            printer.setNamaToko("TOKO SEMBAKO BU SITI");
            printer.setAlamatToko("Desa Bangunsari-Kec. Songgon");
            printer.setTeleponToko("Telp: 081332053238");

            // Set transaction info with employee name
            printer.setNoTransaksi(kodeTransaksi);
            printer.setTanggal(new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date()));
            printer.setNamaKasir(namaKasir);

            // Convert items
            List<String[]> itemList = new ArrayList<>();
            for (String[] item : items) {
                itemList.add(item);
            }
            printer.setItems(itemList);

            printer.setTotal(total);
            printer.setBayar(bayar);
            printer.setKembalian(kembalian);

            // Setup printing for 70mm width
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pageFormat = job.defaultPage();
            pageFormat.setOrientation(PageFormat.PORTRAIT);

            Paper paper = new Paper();
            paper.setSize(PAPER_WIDTH, PAPER_HEIGHT);
            paper.setImageableArea(0, 0, PAPER_WIDTH, PAPER_HEIGHT);
            pageFormat.setPaper(paper);

            job.setPrintable(printer, pageFormat);

            if (job.printDialog()) {
                job.print();
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat mencetak struk: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static String getEmployeeName(String employeeId) {
        String employeeName = Session.getKode(); // Default to ID if query fails
        
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart", "root", "");
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT nama_karyawan FROM karyawan WHERE id_karyawan = ?")) {
            
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                employeeName = rs.getString("nama_karyawan");
            } else {
                System.err.println("Employee with ID " + employeeId + " not found");
            }
        } catch (SQLException e) {
            System.err.println("Error getting employee name:");
            e.printStackTrace();
        }
        
        return employeeName;
    }
}
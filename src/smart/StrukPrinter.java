/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smart;



import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;

public class StrukPrinter implements Printable {
    // Konstanta untuk ukuran struk 80mm
    private static final int PAPER_WIDTH = 226; // 80mm dalam points (1mm = 2.83 points)
    private static final int PAPER_HEIGHT = 1000; // Panjang kertas
    private static final int MARGIN = 5; // Reduced margin
    private static final int LINE_HEIGHT = 12;
    
    private String noTransaksi;
    private String tanggal;
    private String[][] items;
    private double total;
    private double bayar;
    private double kembalian;
    private String namaKasir;
    private String namaToko;
    private String alamatToko;
    private String teleponToko;

    private static final DecimalFormat currencyFormat = new DecimalFormat("Rp#,###");
    private static final DecimalFormat quantityFormat = new DecimalFormat("#,###");

    public StrukPrinter(String noTransaksi, String[][] items, double total, 
                      double bayar, double kembalian, String namaKasir) {
        this.noTransaksi = noTransaksi;
        this.tanggal = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        this.items = items;
        this.total = total;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.namaKasir = namaKasir;
        this.namaToko = "TOKO SEMBAKO BU SITI";
        this.alamatToko = "Desa Bangunsari-Kecamatan Songgon";
        this.teleponToko = "Telp: 081332053238";
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g = (Graphics2D) graphics;
        g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Setup font
        Font fontBold = new Font("Courier New", Font.BOLD, 9); // Reduced font size
        Font fontRegular = new Font("Courier New", Font.PLAIN, 8);
        Font fontSmall = new Font("Courier New", Font.PLAIN, 7);
        
        int y = MARGIN;

        // Header Toko
        g.setFont(fontBold);
        centerText(g, namaToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        
        g.setFont(fontSmall);
        centerText(g, alamatToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, teleponToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT + 5;

        // Garis pemisah
        drawLine(g, y);
        y += LINE_HEIGHT;

        // Info Transaksi
        g.setFont(fontRegular);
        printLeftRight(g, "No. Transaksi", noTransaksi, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Tanggal", tanggal, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Kasir", namaKasir, y);
        y += LINE_HEIGHT + 5;

        // Garis pemisah
        drawLine(g, y);
        y += LINE_HEIGHT;

        // Header Tabel Item
        g.setFont(fontBold);
        String format = "%-18s %3s %8s %8s"; // Adjusted column widths
        g.drawString(String.format(format, "NAMA BARANG", "QTY", "HARGA", "SUBTOTAL"), MARGIN, y);
        y += LINE_HEIGHT;
        
        drawLine(g, y);
        y += LINE_HEIGHT;

        // Daftar Item
        g.setFont(fontRegular);
        for (String[] item : items) {
            String nama = truncate(item[0], 18); // Adjusted truncation length
            String qty = quantityFormat.format(Integer.parseInt(item[1]));
            String harga = currencyFormat.format(Double.parseDouble(item[2]));
            String subtotal = currencyFormat.format(Double.parseDouble(item[3]));
            
            g.drawString(String.format(format, nama, qty, harga, subtotal), MARGIN, y);
            y += LINE_HEIGHT;
        }

        // Garis pemisah
        drawLine(g, y);
        y += LINE_HEIGHT;

        // Total Pembayaran
        g.setFont(fontBold);
        printLeftRight(g, "TOTAL", currencyFormat.format(total), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "TUNAI", currencyFormat.format(bayar), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "KEMBALI", currencyFormat.format(kembalian), y);
        y += LINE_HEIGHT + 10;

        // Footer
        g.setFont(fontSmall);
        centerText(g, "Terima kasih atas kunjungan Anda", y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, "Barang yang sudah dibeli tidak dapat", y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, "ditukar/dikembalikan", y, PAPER_WIDTH);
        y += LINE_HEIGHT + 5;

        // Add space at the end
       // drawLine(g, y);
       // y += LINE_HEIGHT;
       // centerText(g, " ", y, PAPER_WIDTH);

        return PAGE_EXISTS;
    }

    private void centerText(Graphics g, String text, int y, int width) {
        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    private void printLeftRight(Graphics g, String left, String right, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(left, MARGIN, y);
        g.drawString(right, PAPER_WIDTH - MARGIN - fm.stringWidth(right), y);
    }

    private void drawLine(Graphics g, int y) {
        g.drawLine(MARGIN, y, PAPER_WIDTH - MARGIN, y);
    }

    private String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }

    // Cetak otomatis tanpa konfirmasi
    public static void printStrukOtomatis(String noTransaksi, String[][] items, 
                                        double total, double bayar, double kembalian, 
                                        String namaKasir) {
        try {
            StrukPrinter struk = new StrukPrinter(noTransaksi, items, total, bayar, kembalian, namaKasir);
            PrinterJob job = PrinterJob.getPrinterJob();
            
            PageFormat pf = job.defaultPage();
            Paper paper = pf.getPaper();
            paper.setSize(PAPER_WIDTH, PAPER_HEIGHT);
            paper.setImageableArea(0, 0, PAPER_WIDTH, PAPER_HEIGHT);
            pf.setPaper(paper);
            
            job.setPrintable(struk, pf);
            job.print();
            
        } catch (PrinterException e) {
            System.err.println("Gagal mencetak struk: " + e.getMessage());
        }
    }

    // Cetak dengan dialog konfirmasi
    public static boolean printStrukDenganKonfirmasi(String noTransaksi, String[][] items, 
                                                   double total, double bayar, double kembalian,
                                                   String namaKasir) {
        try {
            StrukPrinter struk = new StrukPrinter(noTransaksi, items, total, bayar, kembalian, namaKasir);
            PrinterJob job = PrinterJob.getPrinterJob();
            
            PageFormat pf = job.defaultPage();
            Paper paper = pf.getPaper();
            paper.setSize(PAPER_WIDTH, PAPER_HEIGHT);
            paper.setImageableArea(0, 0, PAPER_WIDTH, PAPER_HEIGHT);
            pf.setPaper(paper);
            
            job.setPrintable(struk, pf);
            
            if (job.printDialog()) {
                job.print();
                return true;
            }
            return false;
            
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, 
                "Gagal mencetak struk:\n" + e.getMessage(), 
                "Error Cetak", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
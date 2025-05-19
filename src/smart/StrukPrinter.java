/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smart;

// Include necessary imports above
import java.awt.*;
import java.awt.print.*;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.List;





public class StrukPrinter implements Printable {
    private static final int PAPER_WIDTH = (int) (70 * 72 / 25.4); // Changed to 70mm
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

        // Slightly smaller fonts for narrower paper
        Font fontBold = new Font("Courier New", Font.BOLD, 9);
        Font fontRegular = new Font("Courier New", Font.PLAIN, 9);
        Font fontSmall = new Font("Courier New", Font.PLAIN, 8);

        int y = MARGIN;

        // Header - Centered but with narrower width
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

        // Transaction Info - Adjusted for narrower paper
        g.setFont(fontRegular);
        printLeftRight(g, "No. Transaksi", noTransaksi, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Tanggal", tanggal, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Kasir", namaKasir, y);
        y += LINE_HEIGHT + 5;

        drawLine(g, y);
        y += 10;

        // Item Header - Adjusted column widths
        g.setFont(fontBold);
        String header = String.format("%-12s %2s %8s %9s", "NAMA", "QTY", "HARGA", "SUBTOTAL");
        g.drawString(header, MARGIN, y);
        y += LINE_HEIGHT;

       // drawLine(g, y);
        //y += 5;

        // Items - Narrower columns
        g.setFont(fontRegular);
        for (String[] item : items) {
            String nama = truncate(item[0], 12); // Shorter name field
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

        // Footer - Center aligned within 70mm
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

            // Set store info
            printer.setNamaToko("TOKO SEMBAKO BU SITI");
            printer.setAlamatToko("Desa Bangunsari-Kec. Songgon");
            printer.setTeleponToko("Telp: 081332053238");

            // Set transaction info
            printer.setNoTransaksi(kodeTransaksi);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yy HH:mm");
            printer.setTanggal(sdf.format(new java.util.Date()));
            printer.setNamaKasir(idKasir);

            // Convert items
            List<String[]> itemList = new java.util.ArrayList<>();
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
            double width = 70 * 72 / 25.4; // 70mm width
            double height = 200 * 72 / 25.4;
            paper.setSize(width, height);
            paper.setImageableArea(0, 0, width, height);
            pageFormat.setPaper(paper);

            job.setPrintable(printer, pageFormat);

            if (job.printDialog()) {
                job.print();
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
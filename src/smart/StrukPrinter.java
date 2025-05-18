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
    private static final int PAPER_WIDTH = (int) (80 * 72 / 25.4);
    private static final int PAPER_HEIGHT = (int) (80 * 72 / 25.4);
    private static final int MARGIN = 2;
    private static final int LINE_HEIGHT = 10;
    private final DecimalFormat currencyFormat = new DecimalFormat("Rp#,###");
    private final DecimalFormat quantityFormat = new DecimalFormat("#");
    
    private String namaToko, alamatToko, teleponToko, noTransaksi, tanggal, namaKasir;
    private List<String[]> items;
    private double total, bayar, kembalian;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g = (Graphics2D) graphics;
        g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        Font fontBold = new Font("Courier New", Font.BOLD, 8);
        Font fontRegular = new Font("Courier New", Font.PLAIN, 8);
        Font fontSmall = new Font("Courier New", Font.PLAIN, 7);

        int y = MARGIN + 10;

        // Header
        g.setFont(fontBold);
        centerText(g, namaToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        g.setFont(fontSmall);
        centerText(g, alamatToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT;
        centerText(g, teleponToko, y, PAPER_WIDTH);
        y += LINE_HEIGHT + 2;

        drawLine(g, y);
        y += 5;

        // Transaction Info
        g.setFont(fontRegular);
        printLeftRight(g, "No. Transaksi", noTransaksi, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Tanggal", tanggal, y);
        y += LINE_HEIGHT;
        printLeftRight(g, "Kasir", namaKasir, y);
        y += LINE_HEIGHT + 2;

        drawLine(g, y);
        y += 5;

        // Item Header
        g.setFont(fontBold);
        String header = "NAMA        QTY  HARGA   SUBTOTAL";
        g.drawString(header, MARGIN, y);
        y += LINE_HEIGHT;

       // drawLine(g, y);
       // y += 5;

        // Items
        g.setFont(fontRegular);
        for (String[] item : items) {
            String nama = truncate(item[0], 10);
            String qty = quantityFormat.format(Integer.parseInt(item[1]));
            String harga = formatCurrency(Double.parseDouble(item[2]));
            String subtotal = formatCurrency(Double.parseDouble(item[3]));
            String itemLine = String.format("%-10s %3s %6s %7s", nama, qty, harga, subtotal);
            g.drawString(itemLine, MARGIN, y);
            y += LINE_HEIGHT;
        }

        drawLine(g, y);
        y += 5;

        // Totals
        g.setFont(fontBold);
        printLeftRight(g, "TOTAL", formatCurrency(total), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "TUNAI", formatCurrency(bayar), y);
        y += LINE_HEIGHT;
        printLeftRight(g, "KEMBALI", formatCurrency(kembalian), y);
        y += LINE_HEIGHT + 5;

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

  


    // Format mata uang dalam Rupiah, tanpa Rp supaya muat lebih baik
    

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

    /**
     * Method static untuk mencetak struk dengan konfirmasi dialog
     * Dipanggil dari halaman transaksi
     *
     * @param kodeTransaksi Kode transaksi yang sudah digenerate
     * @param items Array items yang akan dicetak [nama, qty, harga, subtotal]
     * @param total Total harga pembelian
     * @param bayar Jumlah yang dibayarkan
     * @param kembalian Jumlah kembalian
     * @param idKasir ID kasir yang melakukan transaksi
     * @return true jika struk berhasil dicetak, false jika tidak
     */
    public static boolean printStrukDenganKonfirmasi(String kodeTransaksi, String[][] items,
            double total, double bayar, double kembalian, String idKasir) {
        try {
            // Buat instance printer
            StrukPrinter printer = new StrukPrinter();

            // Setup data toko (dari konfigurasi aplikasi/properties)
            printer.setNamaToko("TOKO SEMBAKO BU SITI");
            printer.setAlamatToko("Desa Bangunasri-Kecamatan Songgon");
            printer.setTeleponToko("Telp: 081332053238");

            // Setup data transaksi dari parameter
            printer.setNoTransaksi(kodeTransaksi);

            // Format tanggal saat ini
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yy HH:mm");
            String tanggal = sdf.format(new java.util.Date());
            printer.setTanggal(tanggal);

            // Setup kasir
            printer.setNamaKasir(idKasir);

            // Konversi items dari array ke List
            List<String[]> itemList = new java.util.ArrayList<>();
            for (String[] item : items) {
                itemList.add(item);
            }
            printer.setItems(itemList);

            // Set total, bayar, kembalian
            printer.setTotal(total);
            printer.setBayar(bayar);
            printer.setKembalian(kembalian);

            // Proses cetak
            PrinterJob job = PrinterJob.getPrinterJob();

            // Atur page format untuk thermal printer 80mm x 80mm
            PageFormat pageFormat = job.defaultPage();
            pageFormat.setOrientation(PageFormat.PORTRAIT);

            Paper paper = new Paper();
            double width = 80 * 72 / 25.4;  // 80mm dalam point
            double height = 80 * 72 / 25.4; // 80mm dalam point
            double margin = 0.0;
            paper.setSize(width, height);
            paper.setImageableArea(margin, margin, width - (2 * margin), height - (2 * margin));
            pageFormat.setPaper(paper);

            job.setPrintable(printer, pageFormat);

            // Show print dialog (optional)
            boolean doPrint = job.printDialog();

            if (doPrint) {
                System.out.println("Mencetak struk...");
                job.print();
                System.out.println("Pencetakan selesai!");
                return true;
            } else {
                System.out.println("Pencetakan dibatalkan.");
                return false;
            }

        } catch (PrinterException pe) {
            System.err.println("Error saat mencetak: " + pe.getMessage());
            pe.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Error umum: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
    

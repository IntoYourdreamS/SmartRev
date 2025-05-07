/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
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
import java.util.Calendar;
import javax.swing.JOptionPane;

public class dashboard extends javax.swing.JFrame {

    public dashboard() {
        initComponents();

        customizeTable();
        makeButtonTransparent(jButton1);
        makeButtonTransparent(bttnlaporan);
        makeButtonTransparent(bttntransaksi);
        makeButtonTransparent(bttnkaryawan);
        makeButtonTransparent(logout);
        makeButtonTransparent(txdepan);
        
        setTableData();
        initSalesChart();
    }

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private void setTableData() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"BRG001", "Teh Hijau", 10},
                    {"BRG002", "Teh Hitam", 5},
                    {"BRG003", "Teh Oolong", 2},
                    {"BRG004", "Teh Herbal", 12}
                },
                new String[]{"Kode Barang", "Nama Barang", "Stok"}
        );

        jTable1.setModel(model);
    }

    private void customizeTable() {
        JTableHeader header = jTable1.getTableHeader();
        JTableHeader header2 = tbpenjualanterlaris.getTableHeader();
        JTableHeader header3 = tbexpired.getTableHeader();
        
        header.setFont(new Font("Inter", Font.BOLD, 11));
        header2.setFont(new Font("Inter", Font.BOLD, 11));
        header3.setFont(new Font("Inter", Font.BOLD, 11));

        header.setForeground(Color.WHITE); 
        header2.setForeground(Color.WHITE);
        header3.setForeground(Color.WHITE);

        header.setOpaque(false);
        header2.setOpaque(false);
        header3.setOpaque(false);

        jTable1.setFont(new Font("Arial", Font.PLAIN, 10));
        tbpenjualanterlaris.setFont(new Font("Arial", Font.PLAIN, 10));
        tbexpired.setFont(new Font("Arial", Font.PLAIN, 10));
        
        jTable1.setRowHeight(30); 
        tbpenjualanterlaris.setRowHeight(30);
        tbexpired.setRowHeight(30);
        
        jTable1.setShowGrid(true); 
        tbpenjualanterlaris.setShowGrid(true); 
        tbexpired.setShowGrid(true); 
        
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbpenjualanterlaris.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tbexpired.setIntercellSpacing(new java.awt.Dimension(0, 0));
        
        jTable1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbpenjualanterlaris.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tbexpired.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
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
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
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
    getContentPane().add(chartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 250, 700, 220));
    getContentPane().setComponentZOrder(chartPanel, 0);
    chartPanel.repaint();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tbexpired = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbpenjualanterlaris = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        bttnkaryawan = new javax.swing.JButton();
        bttntransaksi = new javax.swing.JButton();
        bttnlaporan = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        txdepan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbexpired.setModel(new javax.swing.table.DefaultTableModel(
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
        tbexpired.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane3.setViewportView(tbexpired);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 550, 250, 130));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 550, 380, 140));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setSelectionBackground(new java.awt.Color(25, 25, 25));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 550, 310, 140));

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
        getContentPane().add(txdepan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 450, 190, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Dashboard kasir (2).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 1372, 768));

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
       new restok().setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bttnlaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnlaporanActionPerformed
      new laporanpenjualan().setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_bttnlaporanActionPerformed

    private void bttntransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttntransaksiActionPerformed
       new transaksi().setVisible(true);
        this.setVisible(false); 
        dispose();
    }//GEN-LAST:event_bttntransaksiActionPerformed

    private void bttnkaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnkaryawanActionPerformed
        new karyawan().setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_bttnkaryawanActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
          new login().setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_logoutActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton logout;
    private javax.swing.JTable tbexpired;
    private javax.swing.JTable tbpenjualanterlaris;
    private javax.swing.JButton txdepan;
    // End of variables declaration//GEN-END:variables
}

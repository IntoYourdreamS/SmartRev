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

/**
 *
 *
 * @author acer
 */
public class dashboard extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public dashboard() {
        initComponents();


        customizeTable();
        makeButtonTransparent(jButton1);
        makeButtonTransparent(bttnlaporan);
        makeButtonTransparent(bttntransaksi);
        makeButtonTransparent(bttnkaryawan);
        makeButtonTransparent(logout);
        
        setTableData();
     
      

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
        // Mengubah warna header tabel
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
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 150, 40));

        bttnkaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnkaryawanActionPerformed(evt);
            }
        });
        getContentPane().add(bttnkaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 150, 40));

        bttntransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttntransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(bttntransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 160, 40));

        bttnlaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnlaporanActionPerformed(evt);
            }
        });
        getContentPane().add(bttnlaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 150, 40));

        logout.setBorder(null);
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 610, 120, 50));

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
    // End of variables declaration//GEN-END:variables
}

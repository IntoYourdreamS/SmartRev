/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import Config.koneksi;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import smart.*;

/**
 *
 * @author acer
 */
public class datasupplier extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public datasupplier() {
        initComponents();
        javax.swing.table.JTableHeader header = tbkaryawan.getTableHeader();
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
             makeButtonTransparent(suplier);
             loadDataToTable();
             tbkaryawan.setDefaultEditor(Object.class, null);
    
    }
    
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
     private void customizeTable() {
          JTableHeader header = tbkaryawan.getTableHeader();
           header.setFont(new Font("Inter", Font.BOLD, 11));
           header.setForeground(Color.WHITE);
            header.setOpaque(false);
            tbkaryawan.setFont(new Font("Arial", Font.PLAIN, 10));
            tbkaryawan.setRowHeight(30); 
            tbkaryawan.setShowGrid(true); 
            tbkaryawan.setIntercellSpacing(new java.awt.Dimension(0, 0));
            tbkaryawan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

     }
     
  
    private void loadDataToTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Supplier", "Nama Supplier", "Telepon", "Alamat"}, 0);
        tbkaryawan.setModel(model);

        try (Connection conn = koneksi.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Perhatikan: di database kolomnya 'no_telp' bukan 'no_hp'
            String query = "SELECT id_supplier, nama_supplier, telepon, alamat FROM supplier";
            
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String idSupplier = rs.getString("id_supplier");
                    String namaSupplier = rs.getString("nama_supplier");
                    String telepon = rs.getString("telepon");
                    String alamat = rs.getString("alamat");

                    model.addRow(new Object[]{idSupplier, namaSupplier, telepon, alamat});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data supplier: " + e.getMessage(),
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

        kembali = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkaryawan = new javax.swing.JTable();
        suplier = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kembali.setBorder(null);
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 660, 130, 30));

        tbkaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No Supplier", "Nama Supplier", "No Hp", "Alamat"
            }
        ));
        jScrollPane2.setViewportView(tbkaryawan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 1120, 500));

        suplier.setBorder(null);
        suplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suplierActionPerformed(evt);
            }
        });
        getContentPane().add(suplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 660, 240, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Data Supplier (1)0.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
         new restok().setVisible(true);
        this.setVisible(false);  
    }//GEN-LAST:event_kembaliActionPerformed

    private void suplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suplierActionPerformed
        // TODO add your handling code here:
        tambahsupplier popup = new tambahsupplier();
        popup.setVisible(true);
    }//GEN-LAST:event_suplierActionPerformed

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
            java.util.logging.Logger.getLogger(datasupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datasupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datasupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datasupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datasupplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton kembali;
    private javax.swing.JButton suplier;
    private javax.swing.JTable tbkaryawan;
    // End of variables declaration//GEN-END:variables
}

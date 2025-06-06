/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smart;

import java.awt.Color;
import Config.koneksi;
import Config.Session;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.sql.SQLException;
import popup.rfid;

/**
 *
 * @author acer
 */
public class login extends JFrame {

    /**
     * Creates new form login
     */
    public login() {
        initComponents();
        Password.setOpaque(false);
        Password.setBackground(new Color(0, 0, 0, 0));
        FieldUsername.setOpaque(false);
        FieldUsername.setBackground(new Color(0, 0, 0, 0));
        makeButtonTransparent(login);
        makeButtonTransparent(rfid);
        FieldUsername.requestFocus();

        hide_pasword1.setVisible(false);
        hide_pasword1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });

    }
    Connection conn = koneksi.getConnection();

    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        show_pasword = new javax.swing.JLabel();
        hide_pasword1 = new javax.swing.JLabel();
        FieldUsername = new javax.swing.JTextField();
        Password = new javax.swing.JPasswordField();
        login = new javax.swing.JButton();
        rfid = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        RFIDInput = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        show_pasword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        show_pasword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eye.png"))); // NOI18N
        show_pasword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_paswordMouseClicked(evt);
            }
        });
        getContentPane().add(show_pasword, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 410, 50, 50));

        hide_pasword1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hide_pasword1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eye-slash.png"))); // NOI18N
        hide_pasword1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hide_pasword1MouseClicked(evt);
            }
        });
        getContentPane().add(hide_pasword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 410, 50, 50));

        FieldUsername.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        FieldUsername.setBorder(null);
        FieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldUsernameActionPerformed(evt);
            }
        });
        FieldUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldUsernameKeyTyped(evt);
            }
        });
        getContentPane().add(FieldUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 310, 290, 50));

        Password.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        Password.setBorder(null);
        Password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PasswordMouseClicked(evt);
            }
        });
        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });
        getContentPane().add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 410, 290, 50));

        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        getContentPane().add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 530, 340, 50));

        rfid.setBorder(null);
        rfid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfidActionPerformed(evt);
            }
        });
        getContentPane().add(rfid, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 480, 300, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loginn.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 730));

        RFIDInput.setUI(null);
        getContentPane().add(RFIDInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 260, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUsernameActionPerformed

    private void show_paswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_paswordMouseClicked
        show_pasword.setVisible(false);  // Sembunyikan tombol show
        hide_pasword1.setVisible(true);  // Tampilkan tombol hide
        Password.setEchoChar((char) 0);

        show_pasword.getParent().revalidate();
        show_pasword.getParent().repaint();
    }//GEN-LAST:event_show_paswordMouseClicked

    private void hide_pasword1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hide_pasword1MouseClicked
        show_pasword.setVisible(true);  // Tampilkan tombol show
        hide_pasword1.setVisible(false); // Sembunyikan tombol hide
        Password.setEchoChar('*'); // Kembalikan karakter password ke bentuk tersembunyi (bintang)

        hide_pasword1.getParent().revalidate();
        hide_pasword1.getParent().repaint();

    }//GEN-LAST:event_hide_pasword1MouseClicked

    private long lastTime = 0;
    private String buffer = "";
    private final int RFID_THRESHOLD = 30;

    private void FieldUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldUsernameKeyTyped
        // TODO add your handling code here:
        long currentTime = System.currentTimeMillis();
        char c = evt.getKeyChar();

        // Cek apakah inputnya terlalu cepat (mungkin RFID)
        if (lastTime != 0 && (currentTime - lastTime) > RFID_THRESHOLD) {
            buffer = "";
        }

        buffer += c;
        lastTime = currentTime;

        if (c == '\n' || c
                == '\r') {
            if (buffer.length() >= 9) {
                RFIDInput.setText(buffer);
                FieldUsername.setText("");
                ambilData(RFIDInput.getText().trim());
                System.out.println("Scan RFID Terdeteksi: " + buffer);

            } else {
                System.out.println("Input Manual: " + buffer);
            }
            buffer = "";
        }
    }//GEN-LAST:event_FieldUsernameKeyTyped

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed
        // TODO add your handling code here:
        login.doClick();
    }//GEN-LAST:event_PasswordActionPerformed

    private void PasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PasswordMouseClicked
        show_pasword.setVisible(true);   // Tampilkan tombol show
        hide_pasword1.setVisible(false);
        Password.setEchoChar('*');

        hide_pasword1.getParent().revalidate();
        hide_pasword1.getParent().repaint();
    }//GEN-LAST:event_PasswordMouseClicked

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        // TODO add your handling code here:
        String userInput = FieldUsername.getText();
        char[] passwordInputChar = Password.getPassword();
        String passwordInput = new String(passwordInputChar);

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtInsert = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart", "root", "");

            // Cek login di tabel karyawan
            String sql = "SELECT * FROM karyawan WHERE nama_karyawan = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userInput);
            pstmt.setString(2, passwordInput);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Ambil data
                String idKaryawan = rs.getString("id_karyawan");
                String role = rs.getString("role");
                String rfid = rs.getString("RFID");

                // Simpan session
                Session.setKode(idKaryawan);
                Session.setRole(role);

                // Insert ke detail_karyawan
                String insertSql = "INSERT INTO detail_karyawan (id_karyawan, RFID, tanggal_masuk) VALUES (?, ?, NOW())";
                pstmtInsert = conn.prepareStatement(insertSql);
                pstmtInsert.setString(1, idKaryawan);
                pstmtInsert.setString(2, rfid);
                pstmtInsert.executeUpdate();

                // Tampilkan sukses dan arahkan berdasarkan role
                JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + role + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                if ("Owner".equalsIgnoreCase(role)) {
                    new dashboard().setVisible(true);
                } else if ("Karyawan".equalsIgnoreCase(role)) {
                    new dbkasir().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Peran tidak dikenal: " + role, "Error", JOptionPane.ERROR_MESSAGE);
                }

                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Login gagal. Username atau password salah.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (pstmtInsert != null) {
                    pstmtInsert.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }//GEN-LAST:event_loginActionPerformed

    private void rfidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfidActionPerformed
        // TODO add your handling code here:

        rfid popup = new rfid();
        popup.setVisible(true);
    }//GEN-LAST:event_rfidActionPerformed

    private void ambilData(String rfid) {
        // Validasi format RFID terlebih dahulu
        if (rfid == null || rfid.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "RFID tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM karyawan WHERE RFID = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, rfid);
            ResultSet hasil = ps.executeQuery();

            if (hasil.next()) {
                String idKaryawan = hasil.getString("id_karyawan");
                String role = hasil.getString("role");

                // Verifikasi kesesuaian RFID dengan karyawan
                if (!rfid.equals(hasil.getString("RFID"))) {
                    JOptionPane.showMessageDialog(this, "RFID tidak sesuai dengan karyawan", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Set session data
                Session.setKode(idKaryawan);
                Session.setRole(role);

                // Insert data login dengan transaction handling
                try {
                    conn.setAutoCommit(false); // Mulai transaction

                    // 1. Update RFID di tabel karyawan jika belum sesuai (optional)
                    String updateQuery = "UPDATE karyawan SET RFID = ? WHERE id_karyawan = ? AND (RFID IS NULL OR RFID != ?)";
                    try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                        updatePs.setString(1, rfid);
                        updatePs.setString(2, idKaryawan);
                        updatePs.setString(3, rfid);
                        updatePs.executeUpdate();
                    }

                    // 2. Insert data login ke detail_karyawan
                    String insertQuery = "INSERT INTO detail_karyawan (id_karyawan, RFID, tanggal_masuk) VALUES (?, ?, NOW())";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {
                        insertPs.setString(1, idKaryawan);
                        insertPs.setString(2, rfid);
                        int affectedRows = insertPs.executeUpdate();

                        if (affectedRows == 0) {
                            throw new SQLException("Gagal menyimpan data login");
                        }
                    }

                    conn.commit(); // Commit transaction jika semua sukses

                    JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + role, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    new dashboard().setVisible(true);
                    this.dispose();

                } catch (SQLException e) {
                    conn.rollback(); // Rollback jika ada error
                    throw e;
                } finally {
                    conn.setAutoCommit(true); // Kembalikan auto-commit
                }

            } else {
                JOptionPane.showMessageDialog(this, "RFID Tidak Terdaftar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FlatLightLaf.setup();
        UIManager.put("TableHeader.background", Color.BLACK);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FieldUsername;
    private javax.swing.JPasswordField Password;
    private javax.swing.JTextField RFIDInput;
    private javax.swing.JLabel hide_pasword1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton login;
    private javax.swing.JButton rfid;
    private javax.swing.JLabel show_pasword;
    // End of variables declaration//GEN-END:variables
}

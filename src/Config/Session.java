package Config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author YourdreamS
 */
public class Session {
    public static String id_karyawan = "";
    public static String role = "kasir";
    
    public static String getKode(){
        return id_karyawan;
    }
    public static void setKode(String kode){
        Session.id_karyawan = kode;
    }
    public static String getRole(){
        return role;
    }
    public static void setRole(String roles){
        Session.role= roles;
    }

    public static void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

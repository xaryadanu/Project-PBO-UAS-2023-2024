package KONEKSIDB;

import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class KONEKSI {
    private static Connection KONEKSI;
    public Connection conn;
    Statement stm;
    
    public KONEKSI(){}
    
   public Connection openkoneksi() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            KONEKSI = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_program_warung", "root", "");
            return KONEKSI;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Tidak ada koneksi yang terbuka.");
            return null;
        }
    }
    
    public void closekoneksi() throws SQLException{
        try{
            if(KONEKSI != null){
                System.out.print("Tutup Koneksi\n");
            }
        }catch(Exception ex){
        }
    } 

    public com.mysql.jdbc.Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
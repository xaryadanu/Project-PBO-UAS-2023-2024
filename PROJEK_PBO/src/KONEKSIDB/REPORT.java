package KONEKSIDB;

import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.*;

import java.io.File;
import javax.swing.JOptionPane;

import java.sql.*;
import net.sf.jasperreports.engine.util.JRLoader;

public class REPORT {
    public REPORT(){}
    
    public REPORT(String filename, Connection conn){
        try {
            File report = new File(filename);
            JasperReport jreprt = (JasperReport)JRLoader.loadObject(report);
            JasperPrint jprintt = JasperFillManager.fillReport(jreprt,null, conn);
            JasperViewer.viewReport(jprintt,false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan","Cetak Laporan",JOptionPane.ERROR_MESSAGE);
        }
    
    }
}

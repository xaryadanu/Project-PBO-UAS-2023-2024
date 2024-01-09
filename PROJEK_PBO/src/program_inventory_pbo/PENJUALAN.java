package program_inventory_pbo;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author arya
 */
public class PENJUALAN extends javax.swing.JInternalFrame {
    private DefaultTableModel TabModel;
    Connection conn;
    Statement st;
    ResultSet rs;

    /**
     * Creates new form test
     */
    public PENJUALAN() {
        initComponents();
        txstok.setVisible(false);
        txsubtotal.setVisible(false);
        SiapIsi(false);
        TombolNormal();
        
        Object header[]={"KODE BARANG","NAMA BARANG","HARGA","JUMLAH","SUBTOTAL"};
        TabModel=new DefaultTableModel(null, header);
    }
    
    public Connection setKoneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/db_program_warung","root","");
            st=conn.createStatement();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Koneksi Gagal:"+e);
        }
        return conn;
    }
    
    private void SiapIsi(boolean a){
        txkodetransaksi.setEnabled(a);
        txkodebarang.setEnabled(a);
        txnamabarang.setEnabled(a);
        txharga.setEnabled(a);
        txjumlah.setEnabled(a);
        txsubtotal.setEnabled(a);
        txtotal.setEnabled(a);
        txbayar.setEnabled(a);
        txkembalian.setEnabled(a);
        txstok.setEnabled(a);
        
    }
    
    private void TombolNormal(){
        bttambah.setEnabled(true);
        btaddchart.setEnabled(false);
        btbayar.setEnabled(false);        
        bttransaksi.setEnabled(true);
        btpilohbarang.setEnabled(false);
        bthapuschart.setEnabled(false);
        
    }
    
    private void bersih(){
        txkodetransaksi.setText("");
        txkodebarang.setText("");
        txnamabarang.setText("");
        txharga.setText("");
        txjumlah.setText("");
        txsubtotal.setText("0");
        txtotal.setText("0");
        txbayar.setText("0");
        txkembalian.setText("");
        txstok.setText("");
        
    }
    
    private void kodetransaksi(){
       try{
           setKoneksi();
           String sql="select right(kodetransaksi,2)+1 from tb_penjualan";
           ResultSet rs=st.executeQuery(sql);
           if(rs.next()){
           rs.last();
           String no=rs.getString(1);
           while (no.length()<3){
               no="0"+no;
               txkodetransaksi.setText("TR"+no);}
       }
           else
           {
                   txkodetransaksi.setText("TR001");
       }
       } catch (Exception e)
       {
    }
    }
    
    
    
    private void bayar(){
        setKoneksi();
        try{
           Date skrg=new Date();
           SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
           String tanggal=frm.format(skrg); 
 
            int t = tabelchart.getRowCount();
             for(int i=0;i<t;i++)    
            {
            String kodebarang=tabelchart.getValueAt(i, 0).toString();
            String namabarang=tabelchart.getValueAt(i, 1).toString();
            int harga= Integer.parseInt(tabelchart.getValueAt(i, 2).toString());
            int jml= Integer.parseInt(tabelchart.getValueAt(i, 3).toString());            
            int subtot= Integer.parseInt(tabelchart.getValueAt(i, 4).toString());
         
            String sql ="insert into tb_penjualan values('"+txkodetransaksi.getText()
                    +"','"+kodebarang+"','"
                    +namabarang+"','"
                    +tanggal+"','"
                    +harga+"','"
                    +jml+"','"
                    +subtot+"','"
                    +txtotal.getText()+"','"
                    +txbayar.getText()+"','"
                    +txkembalian.getText()+"')";
            
             st.executeUpdate(sql);
             
            }         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SIMPAN TRANSAKSI PENJUALAN GAGAL");
        }
        
    }
    
    
    public void tabeltransaksi(){
        Object header[]={"KTR","KDB","NAMA BARANG","TANGGAL","HARGA","JUMLAH","SUBTOTAL","TOTAL","BAYAR","KEMBALIAN"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="select*from tb_penjualan";
        try {
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
    
    public void tb_barang(){
        Object header[]={"KODE BARANG","NAMA BARANG","HARGA","STOK"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_barang.setModel(data);
        setKoneksi();
        String sql="select*from tb_barang";
        try {
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
    
    public void hitungstok(){
        int jumlahbeli=Integer.parseInt(txjumlah.getText());
        int stok=Integer.parseInt(txstok.getText());
        
        int total=jumlahbeli-stok;
        txstok.setText(Integer.toString(total));
    }
    
    public void ambildata() {
        try {
            tabelchart.setModel(TabModel);
                String kolom1 = txkodebarang.getText();
                String kolom2 = txnamabarang.getText();
                String kolom3 = txharga.getText();
                String kolom4 = txjumlah.getText();
                String kolom5 = txsubtotal.getText();
                String[] kolom = {kolom1, kolom2, kolom3, kolom4, kolom5};
                TabModel.addRow(kolom);
          }
          catch (Exception ex) {
              JOptionPane.showMessageDialog(null, "Data gagal disimpan");
          }     
    }
    
    public void nota(){
        try {
            String NamaFile = "src/report/CetakNota.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection setKoneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_program_warung","root","");
            HashMap param = new HashMap();
            param.put("ptrans",txkodetransaksi.getText());
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, param, conn);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak!","Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusdatadaritabel() {
        int a = tabelchart.getSelectedRow();
        if(a == -1){
        }TabModel.removeRow(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_barang = new javax.swing.JTable();
        txpencarianbarang = new javax.swing.JTextField();
        jDialog2 = new javax.swing.JDialog();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();
        txpencariantransaksi = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txkodetransaksi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txkodebarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txnamabarang = new javax.swing.JTextField();
        txharga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txjumlah = new javax.swing.JTextField();
        txsubtotal = new javax.swing.JTextField();
        txstok = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        bttambah = new javax.swing.JButton();
        btpilohbarang = new javax.swing.JButton();
        btaddchart = new javax.swing.JButton();
        bthapuschart = new javax.swing.JButton();
        bttransaksi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelchart = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txbayar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txkembalian = new javax.swing.JTextField();
        btbayar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setBackground(new java.awt.Color(0, 0, 51));
        jDialog1.setMinimumSize(new java.awt.Dimension(900, 400));
        jDialog1.setModal(true);
        jDialog1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDialog1MouseClicked(evt);
            }
        });

        jInternalFrame1.setTitle("TABEL BARANG");
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(900, 400));
        jInternalFrame1.setVisible(true);
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame1MouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        tb_barang.setAutoCreateRowSorter(true);
        tb_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tb_barang.setForeground(new java.awt.Color(51, 51, 51));
        tb_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_barangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_barang);

        txpencarianbarang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txpencarianbarang.setForeground(new java.awt.Color(51, 51, 51));
        txpencarianbarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencarianbarang.setText("KETIK KODE BARANG ATAU NAMA BARANG UNTUK MELAKUKAN PENCARIAN LALU ENTER");
        txpencarianbarang.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencarianbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencarianbarangActionPerformed(evt);
            }
        });
        txpencarianbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencarianbarangKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencarianbarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencarianbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog2.setBackground(new java.awt.Color(0, 0, 51));
        jDialog2.setMinimumSize(new java.awt.Dimension(1020, 400));
        jDialog2.setModal(true);
        jDialog2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDialog2MouseClicked(evt);
            }
        });

        jInternalFrame2.setTitle("TABEL TRANSAKSI");
        jInternalFrame2.setPreferredSize(new java.awt.Dimension(1020, 400));
        jInternalFrame2.setVisible(true);
        jInternalFrame2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame2MouseClicked(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        tabeltransaksi.setAutoCreateRowSorter(true);
        tabeltransaksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tabeltransaksi.setForeground(new java.awt.Color(51, 51, 51));
        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltransaksiMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabeltransaksi);

        txpencariantransaksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txpencariantransaksi.setForeground(new java.awt.Color(51, 51, 51));
        txpencariantransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencariantransaksi.setText("KETIK KODE TRANSAKSI UNTUK MELAKUKAN PENCARIAN LALU ENTER");
        txpencariantransaksi.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencariantransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencariantransaksiActionPerformed(evt);
            }
        });
        txpencariantransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencariantransaksiKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencariantransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencariantransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("FORM PENJUALAN");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("KODE TRANSAKSI");

        txkodetransaksi.setEditable(false);
        txkodetransaksi.setBackground(new java.awt.Color(255, 255, 204));
        txkodetransaksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txkodetransaksi.setForeground(new java.awt.Color(51, 51, 51));
        txkodetransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("KODE BARANG");

        txkodebarang.setEditable(false);
        txkodebarang.setBackground(new java.awt.Color(255, 255, 204));
        txkodebarang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txkodebarang.setForeground(new java.awt.Color(51, 51, 51));
        txkodebarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("NAMA BARANG");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("HARGA");

        txnamabarang.setEditable(false);
        txnamabarang.setBackground(new java.awt.Color(255, 255, 204));
        txnamabarang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txnamabarang.setForeground(new java.awt.Color(51, 51, 51));
        txnamabarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txnamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnamabarangActionPerformed(evt);
            }
        });

        txharga.setEditable(false);
        txharga.setBackground(new java.awt.Color(255, 255, 204));
        txharga.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txharga.setForeground(new java.awt.Color(51, 51, 51));
        txharga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txhargaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("JUMLAH");

        txjumlah.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txjumlah.setForeground(new java.awt.Color(51, 51, 51));
        txjumlah.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txsubtotal.setBackground(new java.awt.Color(204, 204, 204));
        txsubtotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txsubtotal.setForeground(new java.awt.Color(51, 51, 51));
        txsubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txstok.setBackground(new java.awt.Color(204, 204, 204));
        txstok.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txstok.setForeground(new java.awt.Color(51, 51, 51));
        txstok.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txkodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txharga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txnamabarang)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txstok, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txkodebarang, txkodetransaksi});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txstok, txsubtotal});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txkodebarang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        bttambah.setBackground(new java.awt.Color(204, 204, 204));
        bttambah.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        bttambah.setForeground(new java.awt.Color(51, 51, 51));
        bttambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/TAMBAH.png"))); // NOI18N
        bttambah.setText("TAMBAH");
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        btpilohbarang.setBackground(new java.awt.Color(204, 204, 204));
        btpilohbarang.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btpilohbarang.setForeground(new java.awt.Color(51, 51, 51));
        btpilohbarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/tbbuku.png"))); // NOI18N
        btpilohbarang.setText("PILIH BARANG");
        btpilohbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btpilohbarangActionPerformed(evt);
            }
        });

        btaddchart.setBackground(new java.awt.Color(204, 204, 204));
        btaddchart.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btaddchart.setForeground(new java.awt.Color(51, 51, 51));
        btaddchart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/addchart.png"))); // NOI18N
        btaddchart.setText("ADD CHART");
        btaddchart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaddchartActionPerformed(evt);
            }
        });

        bthapuschart.setBackground(new java.awt.Color(204, 204, 204));
        bthapuschart.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        bthapuschart.setForeground(new java.awt.Color(51, 51, 51));
        bthapuschart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/hapuschart.png"))); // NOI18N
        bthapuschart.setText("HAPUS CHART");
        bthapuschart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapuschartActionPerformed(evt);
            }
        });

        bttransaksi.setBackground(new java.awt.Color(204, 204, 204));
        bttransaksi.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        bttransaksi.setForeground(new java.awt.Color(51, 51, 51));
        bttransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/tbtransaksi.png"))); // NOI18N
        bttransaksi.setText("TB TRANSAKSI");
        bttransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(bttambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btpilohbarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btaddchart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bthapuschart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bttransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btaddchart, bthapuschart, btpilohbarang, bttambah, bttransaksi});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttambah)
                    .addComponent(btpilohbarang)
                    .addComponent(btaddchart)
                    .addComponent(bthapuschart)
                    .addComponent(bttransaksi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelchart.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tabelchart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "KODE BARANG", "NAMA BARANG", "HARGA", "JUMLAH", "SUBTOTAL"
            }
        ));
        jScrollPane1.setViewportView(tabelchart);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("TOTAL :");

        txtotal.setEditable(false);
        txtotal.setBackground(new java.awt.Color(255, 255, 204));
        txtotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtotal.setForeground(new java.awt.Color(255, 0, 0));
        txtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("BAYAR :");

        txbayar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txbayar.setForeground(new java.awt.Color(255, 0, 0));
        txbayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txbayarKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("KEMBALIAN :");

        txkembalian.setEditable(false);
        txkembalian.setBackground(new java.awt.Color(255, 255, 204));
        txkembalian.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txkembalian.setForeground(new java.awt.Color(255, 0, 0));
        txkembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btbayar.setBackground(new java.awt.Color(0, 0, 0));
        btbayar.setForeground(new java.awt.Color(204, 204, 204));
        btbayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/SIMPAN.png"))); // NOI18N
        btbayar.setText("BAYAR");
        btbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbayarActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("\"TEKAN ENTER SETELAH ISI BAYAR, LALU TEKAN TOMBOL BAYAR UNTUK MENYIMPAN TRANSAKSI\"");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btbayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btbayar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(220, 60, 910, 530);
    }// </editor-fold>//GEN-END:initComponents

    private void txnamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnamabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txnamabarangActionPerformed

    private void txhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txhargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txhargaActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        if(bttambah.getText().equalsIgnoreCase("TAMBAH")){
            bttambah.setText("REFRESH");
            bersih();
            SiapIsi(true);

            kodetransaksi();
            btaddchart.setEnabled(true);
            bttransaksi.setEnabled(false);
            bttambah.setEnabled(true);
            btbayar.setEnabled(true);
            btpilohbarang.setEnabled(true);
            bthapuschart.setEnabled(true);

        } else{
            bttambah.setText("TAMBAH");
            bersih();
            SiapIsi(false);
            TombolNormal();
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
        }
    }//GEN-LAST:event_bttambahActionPerformed

    private void btpilohbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btpilohbarangActionPerformed
        // TODO add your handling code here:        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        tb_barang();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btpilohbarangActionPerformed

    private void btaddchartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaddchartActionPerformed
        // TODO add your handling code here:
        int harga=Integer.parseInt(txharga.getText());
        int jml=Integer.parseInt(txjumlah.getText());
        int stok=Integer.parseInt(txstok.getText());
        int total=Integer.parseInt(txtotal.getText());

        if(jml>stok){
            JOptionPane.showMessageDialog(null, "Stok barang tidak mencukupi");
        }else{

            int subtot=harga*jml;
            txsubtotal.setText(Integer.toString(subtot));

            int hasilstok=stok-jml;
            txstok.setText(Integer.toString(hasilstok));

            int totbay=total+(harga*jml);
            txtotal.setText(Integer.toString(totbay));

            ambildata();

        }
    }//GEN-LAST:event_btaddchartActionPerformed

    private void bthapuschartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapuschartActionPerformed
        // TODO add your handling code here:
        int baris = tabelchart.getSelectedRow();
        int jml=Integer.parseInt(tabelchart.getModel().getValueAt(baris, 2).toString());
        int total=Integer.parseInt(txtotal.getText());
        int harga=Integer.parseInt(tabelchart.getModel().getValueAt(baris, 3).toString());

        int totbay=total-(harga*jml);
        txtotal.setText(Integer.toString(totbay));
        hapusdatadaritabel();
    }//GEN-LAST:event_bthapuschartActionPerformed

    private void bttransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttransaksiActionPerformed
        // TODO add your handling code here:
        jDialog2.setLocationRelativeTo(null);
        tabeltransaksi();
        jDialog2.setVisible(true);
    }//GEN-LAST:event_bttransaksiActionPerformed

    private void txbayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbayarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            int total=Integer.parseInt(txtotal.getText());
            int bayar=Integer.parseInt(txbayar.getText());
            if(bayar<total){
                JOptionPane.showMessageDialog(null, "Jumlah bayar tidak mencukupi");
                txbayar.requestFocus();
            } else{
                int kembali=bayar-total;
                txkembalian.setText(Integer.toString(kembali));
            }
        }
    }//GEN-LAST:event_txbayarKeyPressed

    private void btbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbayarActionPerformed
        // TODO add your handling code here:
        if(txkodetransaksi.getText().equals("")
            ||txkodebarang.getText().equals("")
            ||txnamabarang.getText().equals("")
            ||txharga.getText().equals("")
            ||txjumlah.getText().equals("")
            ||txsubtotal.getText().equals("")
            ||txtotal.getText().equals("")
            ||txbayar.getText().equals("")
            ||txkembalian.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Lengkapi inputan penjualan barang");
        } else{
            bayar();
            int pesan=JOptionPane.showConfirmDialog(null, "Print Out Nota?","Print",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(pesan==JOptionPane.YES_OPTION){
                nota();
            }else {
                JOptionPane.showMessageDialog(null, "Simpan Transaksi Berhasil");
            }
            bersih();
            SiapIsi(false);
            TombolNormal();
            TabModel.getDataVector().removeAllElements();
            TabModel.fireTableDataChanged();
            bttambah.setText("TAMBAH");
        }
    }//GEN-LAST:event_btbayarActionPerformed

    private void tb_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_barangMouseClicked
        // TODO add your handling code here:
        int baris = tb_barang.getSelectedRow();
        txkodebarang.setText(tb_barang.getModel().getValueAt(baris, 0).toString());
        txnamabarang.setText(tb_barang.getModel().getValueAt(baris, 1).toString());
        txharga.setText(tb_barang.getModel().getValueAt(baris, 2).toString());
        txstok.setText(tb_barang.getModel().getValueAt(baris, 3).toString());
        jDialog1.dispose();
    }//GEN-LAST:event_tb_barangMouseClicked

    private void txpencarianbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencarianbarangActionPerformed
        // TODO add your handling code here:
        Object header[]={"KODE BARANG","NAMA BARANG","HARGA","STOK"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_barang.setModel(data);
        setKoneksi();
        String sql="Select * from tb_barang where kodebarang like '%" + txpencarianbarang.getText() + "%'" + "or namabarang like '%" + txpencarianbarang.getText()+"%'";
        try {
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);

                String kolom[]={kolom1,kolom2,kolom3,kolom4};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencarianbarangActionPerformed

    private void txpencarianbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencarianbarangKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txpencarianbarangKeyPressed

    private void jInternalFrame1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1MouseClicked

    private void jDialog1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialog1MouseClicked

    private void tabeltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltransaksiMouseClicked
        // TODO add your handling code here:
        int baris = tabeltransaksi.getSelectedRow();
        txkodetransaksi.setText(tabeltransaksi.getModel().getValueAt(baris, 0).toString());
        jDialog2.dispose();
        nota();
    }//GEN-LAST:event_tabeltransaksiMouseClicked

    private void txpencariantransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencariantransaksiActionPerformed
        // TODO add your handling code here:
        Object header[]={"KTR","KDB","NAMA BARANG","TANGGAL","HARGA","JUMLAH","SUBTOTAL","TOTAL","BAYAR","KEMBALIAN"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="Select * from tb_penjualan where kodetransaksi like '%" + txpencariantransaksi.getText() + "%'" + "or namabarang like '%" + txpencariantransaksi.getText()+"%'";
        try {
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencariantransaksiActionPerformed

    private void txpencariantransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencariantransaksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpencariantransaksiKeyPressed

    private void jInternalFrame2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame2MouseClicked

    private void jDialog2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialog2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btaddchart;
    private javax.swing.JButton btbayar;
    private javax.swing.JButton bthapuschart;
    private javax.swing.JButton btpilohbarang;
    private javax.swing.JButton bttambah;
    private javax.swing.JButton bttransaksi;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabelchart;
    private javax.swing.JTable tabeltransaksi;
    private javax.swing.JTable tb_barang;
    private javax.swing.JTextField txbayar;
    private javax.swing.JTextField txharga;
    private javax.swing.JTextField txjumlah;
    private javax.swing.JTextField txkembalian;
    private javax.swing.JTextField txkodebarang;
    private javax.swing.JTextField txkodetransaksi;
    private javax.swing.JTextField txnamabarang;
    private javax.swing.JTextField txpencarianbarang;
    private javax.swing.JTextField txpencariantransaksi;
    private javax.swing.JTextField txstok;
    private javax.swing.JTextField txsubtotal;
    private javax.swing.JTextField txtotal;
    // End of variables declaration//GEN-END:variables
}

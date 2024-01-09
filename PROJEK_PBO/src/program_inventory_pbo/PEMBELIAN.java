package program_inventory_pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arya
 */
public class PEMBELIAN extends javax.swing.JInternalFrame {
    Connection conn;
    Statement stm;
    ResultSet rs;

    /**
     * Creates new form test
     */
    public PEMBELIAN() {
        initComponents();
        siapIsi(false);
        tombolNormal();
        tb_transaksi();
    }
    
    public Connection setKoneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/db_program_warung","root","");
            stm=conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Koneksi Gagal :" +e);
        }
       return conn; 
    }
    
        private void siapIsi(boolean a){
        txkodetransaksi.setEnabled(a);
        txkodebarang.setEnabled(a);
        txnamabarang.setEnabled(a);
        txsuplier.setEnabled(a);
        txjumlah.setEnabled(a);
        txhargabeli.setEnabled(a);
        txhargajual.setEnabled(a);
        
    }
    
    private void tombolNormal(){
        bttambah.setEnabled(true);
        btsimpan.setEnabled(false);
        bthapus.setEnabled(false);
        btbarang.setEnabled(false); 
        btsuplier.setEnabled(false);
    }
    
    private void bersih(){
        txkodetransaksi.setText("");
        txkodebarang.setText("");
        txnamabarang.setText("");
        txsuplier.setText("");
        txjumlah.setText("");
        txhargabeli.setText("");
        txhargajual.setText("");    
    }
    
    private void kodetransaksi(){
        try {
            setKoneksi();
            String sql="select right (kodetransaksi,2)+1 from tb_pembelian ";
            ResultSet rs=stm.executeQuery(sql);
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
    private void simpan(){
        try{
            Date skrg=new Date();
           SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
           String tanggal=frm.format(skrg); 
           
            setKoneksi();
            String sql="insert into tb_pembelian values('"+txkodetransaksi.getText()
                    +"','"+txkodebarang.getText()
                    +"','"+txnamabarang.getText()
                    +"','"+txsuplier.getText()
                    +"','"+tanggal
                    +"','"+txjumlah.getText()
                    +"','"+txhargabeli.getText()
                    +"','"+txhargajual.getText() +"')";
            stm.executeUpdate(sql); 
            JOptionPane.showMessageDialog(null,"Simpan Data Berhasil");
            }
            catch (Exception e) {
        }
        tb_transaksi();
       
    }
    
    
    private void hapus(){
        try{
            String sql="delete from tb_pembelian where kodetransaksi='"+ txkodetransaksi.getText() +"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil ");
            }
            catch (Exception e) {
            }
        tb_transaksi();
    }
    
    public void tb_transaksi(){
        Object header[]={"KODE TRANSAKSI","KODE BARANG","NAMA BARANG","SUPLIER","TANGGAL","JUMLAH","HARGA BELI","HARGA JUAL"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_transaksi.setModel(data);
        setKoneksi();
        String sql="select*from tb_pembelian";
        try {
            ResultSet rs=stm.executeQuery(sql);
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
                      
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8};
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
            ResultSet rs=stm.executeQuery(sql);
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
    
    public void tb_suplier(){
        Object header[]={"KODE SUPLIER","NAMA SUPLIER","NO TLP","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_suplier.setModel(data);
        setKoneksi();
        String sql="select*from tb_supplier";
        try {
            ResultSet rs=stm.executeQuery(sql);
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
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_barang = new javax.swing.JTable();
        txpencarianbarang = new javax.swing.JTextField();
        jDialog2 = new javax.swing.JDialog();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_suplier = new javax.swing.JTable();
        txpencariansuplier = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txkodetransaksi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txkodebarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txnamabarang = new javax.swing.JTextField();
        txjumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txhargabeli = new javax.swing.JTextField();
        txhargajual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txsuplier = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txpencariantransaksi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_transaksi = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        bttambah = new javax.swing.JButton();
        btbarang = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        btsuplier = new javax.swing.JButton();

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

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

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
        txpencarianbarang.setText("KETIK KODE BARANG ATAU NAMA BARANG UNTUK MELAKUKAN PENCARI AN LALU ENTER");
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencarianbarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jDialog2.setMinimumSize(new java.awt.Dimension(900, 400));
        jDialog2.setModal(true);
        jDialog2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDialog2MouseClicked(evt);
            }
        });

        jInternalFrame2.setTitle("TABEL SUPLIER");
        jInternalFrame2.setPreferredSize(new java.awt.Dimension(900, 400));
        jInternalFrame2.setVisible(true);
        jInternalFrame2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame2MouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        tb_suplier.setAutoCreateRowSorter(true);
        tb_suplier.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tb_suplier.setForeground(new java.awt.Color(51, 51, 51));
        tb_suplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_suplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_suplierMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_suplier);

        txpencariansuplier.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txpencariansuplier.setForeground(new java.awt.Color(51, 51, 51));
        txpencariansuplier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencariansuplier.setText("KETIK KODE SUPLIER ATAU NAMA SUPLIER UNTUK MELAKUKAN PENCARIAN LALU ENTER");
        txpencariansuplier.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencariansuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencariansuplierActionPerformed(evt);
            }
        });
        txpencariansuplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencariansuplierKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencariansuplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencariansuplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("FORM PEMBELIAN");

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
        jLabel4.setText("JUMLAH");

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

        txjumlah.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txjumlah.setForeground(new java.awt.Color(51, 51, 51));
        txjumlah.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txjumlahActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("HARGA BELI");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("HARGA JUAL");

        txhargabeli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txhargabeli.setForeground(new java.awt.Color(51, 51, 51));
        txhargabeli.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txhargajual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txhargajual.setForeground(new java.awt.Color(51, 51, 51));
        txhargajual.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("SUPLIER");

        txsuplier.setEditable(false);
        txsuplier.setBackground(new java.awt.Color(255, 255, 204));
        txsuplier.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txsuplier.setForeground(new java.awt.Color(51, 51, 51));
        txsuplier.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txkodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txsuplier)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txnamabarang))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txhargabeli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txhargajual, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txhargabeli, txhargajual});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel7});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txnamabarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txhargabeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txkodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txhargajual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txsuplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txpencariantransaksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txpencariantransaksi.setForeground(new java.awt.Color(51, 51, 51));
        txpencariantransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencariantransaksi.setText("KETIK KODE TRANSAKSI UNTUK MELAKUKAN PENCARIAN LALU ENTER");
        txpencariantransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txpencariantransaksiMouseClicked(evt);
            }
        });
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

        tb_transaksi.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        tb_transaksi.setForeground(new java.awt.Color(51, 51, 51));
        tb_transaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_transaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_transaksi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencariantransaksi)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencariantransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btbarang.setBackground(new java.awt.Color(204, 204, 204));
        btbarang.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btbarang.setForeground(new java.awt.Color(51, 51, 51));
        btbarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/tbbuku.png"))); // NOI18N
        btbarang.setText("PILIH BARANG");
        btbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbarangActionPerformed(evt);
            }
        });

        btsimpan.setBackground(new java.awt.Color(204, 204, 204));
        btsimpan.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btsimpan.setForeground(new java.awt.Color(51, 51, 51));
        btsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/SIMPAN.png"))); // NOI18N
        btsimpan.setText("SIMPAN");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        bthapus.setBackground(new java.awt.Color(204, 204, 204));
        bthapus.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        bthapus.setForeground(new java.awt.Color(51, 51, 51));
        bthapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/HAPUS.png"))); // NOI18N
        bthapus.setText("HAPUS");
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });

        btsuplier.setBackground(new java.awt.Color(204, 204, 204));
        btsuplier.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btsuplier.setForeground(new java.awt.Color(51, 51, 51));
        btsuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconn/suplier.png"))); // NOI18N
        btsuplier.setText("PILIH SUPLIER");
        btsuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsuplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(bttambah)
                .addGap(18, 18, 18)
                .addComponent(btsuplier)
                .addGap(18, 18, 18)
                .addComponent(btbarang)
                .addGap(18, 18, 18)
                .addComponent(btsimpan)
                .addGap(18, 18, 18)
                .addComponent(bthapus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btbarang, bthapus, btsimpan, btsuplier, bttambah});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttambah)
                    .addComponent(btbarang)
                    .addComponent(btsimpan)
                    .addComponent(bthapus)
                    .addComponent(btsuplier))
                .addContainerGap())
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txjumlahActionPerformed

    private void txpencariantransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txpencariantransaksiMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_txpencariantransaksiMouseClicked

    private void txpencariantransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencariantransaksiActionPerformed
        // TODO add your handling code here:
        Object header[]={"KODE TRANSAKSI","KODE BARANG","NAMA BARANG","SUPLIER","TANGGAL","JUMLAH","HARGA BELI","HARGA JUAL"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_transaksi.setModel(data);
        setKoneksi();
        String sql="Select * from tb_pembelian where kodetransaksi like '%" + txpencariantransaksi.getText() + "%'" + "or namabarang like '%" + txpencariantransaksi.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
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

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom8};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencariantransaksiActionPerformed

    private void txpencariantransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencariantransaksiKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txpencariantransaksiKeyPressed

    private void tb_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_transaksiMouseClicked
        // TODO add your handling code here:
        int baris = tb_transaksi.getSelectedRow();
        txkodetransaksi.setText(tb_transaksi.getModel().getValueAt(baris, 0).toString());
        txkodebarang.setText(tb_transaksi.getModel().getValueAt(baris, 1).toString());
        txnamabarang.setText(tb_transaksi.getModel().getValueAt(baris, 2).toString());
        txsuplier.setText(tb_transaksi.getModel().getValueAt(baris, 3).toString());
        txjumlah.setText(tb_transaksi.getModel().getValueAt(baris, 5).toString());
        txhargabeli.setText(tb_transaksi.getModel().getValueAt(baris, 6).toString());
        txhargajual.setText(tb_transaksi.getModel().getValueAt(baris, 7).toString());
        bthapus.setEnabled(true);
    }//GEN-LAST:event_tb_transaksiMouseClicked

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        if(bttambah.getText().equalsIgnoreCase("TAMBAH")){
            bttambah.setText("REFRESH");
            bersih();
            siapIsi(true);
            kodetransaksi();

            txkodebarang.setEnabled(true);
            bttambah.setEnabled(true);
            btsimpan.setEnabled(true);
            bthapus.setEnabled(false);
            btbarang.setEnabled(true);
            btsuplier.setEnabled(true);
        } else{
            bttambah.setText("TAMBAH");
            bersih();
            siapIsi(false);
            tombolNormal();
            tb_transaksi();
        }
    }//GEN-LAST:event_bttambahActionPerformed

    private void btbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbarangActionPerformed
        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        tb_barang();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btbarangActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
        if(txkodetransaksi.getText().isEmpty()
            ||txkodebarang.getText().isEmpty()
            ||txnamabarang.getText().isEmpty()
            ||txjumlah.getText().isEmpty()
            ||txhargabeli.getText().isEmpty()
            ||txhargajual.getText().isEmpty()){

            JOptionPane.showMessageDialog(null, "Mohon Lengkapi Inputan Data!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{

            if(bttambah.getText().equalsIgnoreCase("REFRESH")){
                if(bttambah.getText().equalsIgnoreCase("REFRESH")){
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }

            }
            bersih();
            siapIsi(false);
            bttambah.setText("TAMBAH");
            tombolNormal();

        }
    }//GEN-LAST:event_btsimpanActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(pesan==JOptionPane.YES_OPTION){
            if(pesan==JOptionPane.YES_OPTION){
                hapus();
                bersih();
                siapIsi(false);
                tombolNormal();
            } else{
                JOptionPane.showMessageDialog(null, "HAPUS DATA GAGAL :(");
            }

        }
    }//GEN-LAST:event_bthapusActionPerformed

    private void btsuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsuplierActionPerformed
        // TODO add your handling code here:
        jDialog2.setLocationRelativeTo(null);
        tb_suplier();
        jDialog2.setVisible(true);
    }//GEN-LAST:event_btsuplierActionPerformed

    private void tb_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_barangMouseClicked
        // TODO add your handling code here:
        int baris = tb_barang.getSelectedRow();
        txkodebarang.setText(tb_barang.getModel().getValueAt(baris, 0).toString());
        txnamabarang.setText(tb_barang.getModel().getValueAt(baris, 1).toString());
        txhargajual.setText(tb_barang.getModel().getValueAt(baris, 2).toString());
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
            ResultSet rs=stm.executeQuery(sql);
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

    private void tb_suplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_suplierMouseClicked
        // TODO add your handling code here:
        int baris = tb_suplier.getSelectedRow();
        txsuplier.setText(tb_suplier.getModel().getValueAt(baris, 1).toString());
        jDialog2.dispose();
    }//GEN-LAST:event_tb_suplierMouseClicked

    private void txpencariansuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencariansuplierActionPerformed
        // TODO add your handling code here:
        Object header[]={"KODE SUPLIER","NAMA SUPLIER","NO TLP","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tb_suplier.setModel(data);
        setKoneksi();
        String sql="Select * from tb_supplier where kodesupplier like '%" + txpencariansuplier.getText() + "%'" + "or namasupplier like '%" + txpencariansuplier.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
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
    }//GEN-LAST:event_txpencariansuplierActionPerformed

    private void txpencariansuplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencariansuplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpencariansuplierKeyPressed

    private void jInternalFrame2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame2MouseClicked

    private void jDialog2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jDialog2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbarang;
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton btsuplier;
    private javax.swing.JButton bttambah;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tb_barang;
    private javax.swing.JTable tb_suplier;
    private javax.swing.JTable tb_transaksi;
    private javax.swing.JTextField txhargabeli;
    private javax.swing.JTextField txhargajual;
    private javax.swing.JTextField txjumlah;
    private javax.swing.JTextField txkodebarang;
    private javax.swing.JTextField txkodetransaksi;
    private javax.swing.JTextField txnamabarang;
    private javax.swing.JTextField txpencarianbarang;
    private javax.swing.JTextField txpencariansuplier;
    private javax.swing.JTextField txpencariantransaksi;
    private javax.swing.JTextField txsuplier;
    // End of variables declaration//GEN-END:variables
}

package java_app1;

import java.awt.CardLayout;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.HeadlessException;
/**
 *
 * @author A.T.T.A
 */
public class Main extends javax.swing.JFrame {

    public Connection conn;
    public ResultSet rs;
    public Statement stm;
    public PreparedStatement pst;
    String sql, sql2, nmbr,hrgjl,satuan,jenisbr,jenisst,save, jenissatuan, id_satuan, id_barang;
    int row;
    boolean update = false;
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        koneksi DB = new koneksi();
        DB.config();
        conn = DB.con;
        stm = DB.stm;
        update_tabelbarang();
        cbboxjenis();
        cbboxJenisSatuan();
        hilangkanPesanError();
    }
    
    private void hilangkanPesanError() {
        lblErrorNamaBarang.setVisible(false);
        lblErrorHargaJual.setVisible(false);
        lblErrorJenisBarang.setVisible(false);
        lblErrorJenisSatuan.setVisible(false);
        nmbarang.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(196, 196, 196), 1, true));
        hrgjual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(196, 196, 196), 1, true));
        cbjenisbarang.setBorder(new javax.swing.border.EmptyBorder(1, 1, 1, 1));
        cbjenissatuan.setBorder(new javax.swing.border.EmptyBorder(1, 1, 1, 1));
    }
    
    private void BersihkanLayarStok() {
        nmbarang.setText("  Masukkan nama barang");
        nmbarang.setForeground(new java.awt.Color(138, 138, 138));
        hrgjual.setText("  Masukkan harga jual");
        hrgjual.setForeground(new java.awt.Color(138, 138, 138));
        cbjenisbarang.setSelectedIndex(0);
        cbjenissatuan.setSelectedIndex(0);
        tfKelolaJenis.setText("Masukkan jenis barang baru");
    }
    
    private void  cbboxjenis(){
        sql = "select jenis_barang from jenis";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cbjenisbarang.addItem("Pilih Jenis Barang");
            while (rs.next()) {
                String jenis = rs.getString("Jenis_barang");
                cbjenisbarang.addItem(jenis);
            }
        } catch (Exception e) {
        }
    }
    
    private void cbboxJenisSatuan() {
        sql = "SELECT jenis_satuan FROM satuan";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cbjenissatuan.addItem("Pilih Jenis Satuan");
            while (rs.next()) {                
                String jenis = rs.getString("jenis_satuan");
                cbjenissatuan.addItem(jenis);
            }
        } catch (Exception e) {
            
        }
    }
        
    private String getIdJenis(String namaJenis){
        String id = "0";
        sql = "select id_jenis from jenis where jenis_barang='" + namaJenis + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getString("id_jenis");
            }
        } catch (Exception e) {
        }
        return id;
    }
    
    private String getIdSatuan (String namaSatuan) {
        String id = "0";
        sql = "select id_satuan from satuan where jenis_satuan='" + namaSatuan + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getString("id_satuan");
                System.out.println("id="+id);
            }
        } catch (Exception e) {
        }
        return id;
    }
        
        private void update_tabelbarang() {
        // membuat tampilan model tabel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Jenis");
        model.addColumn("Satuan");
        model.addColumn("Nama Barang");
        model.addColumn("Stok");
        model.addColumn("Harga Pokok");
        model.addColumn("Harga Jual");
        tblstok.setModel(model);
          // menampilkan data database ke dalam tabel
        try {
            rs = stm.executeQuery("SELECT b.id_barang,j.jenis_barang, s.jenis_satuan,"
                    + " b.nama_barang,b.stok,b.harga_pokok,b.harga_jual "
                    + "FROM barang b join jenis j on j.id_jenis=b.id_jenis "
                    + "join satuan s on s.id_satuan = b.id_satuan order by id_barang");
            while (rs.next()){
            Object[] data = new Object[7];
                data[0] = rs.getString("id_barang");
                data[1] = rs.getString("jenis_barang");
                data[2] = rs.getString("jenis_satuan");
                data[3] = rs.getString("nama_barang");
                data[4] = rs.getString("stok");
                data[5] = rs.getString("harga_pokok");
                data[6] = rs.getString("harga_jual");
                model.addRow(data);
                tblstok.setModel(model);
        }
            rs.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
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

        jPanel15 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        tabTransaksi = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlAktifTransaksi = new javax.swing.JPanel();
        tabStok = new javax.swing.JPanel();
        btnStok = new javax.swing.JLabel();
        pnlAktifStok = new javax.swing.JPanel();
        tabHutang = new javax.swing.JPanel();
        btnhutang = new javax.swing.JLabel();
        pnlAktifHutang = new javax.swing.JPanel();
        logout = new javax.swing.JLabel();
        Change = new javax.swing.JPanel();
        Transaksi = new javax.swing.JPanel();
        panelOverview = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        panelPenjualan = new javax.swing.JPanel();
        topPanel1 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        penjualanOnClick = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        pengeluaranOnClick = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        Hutang = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        paneltombol = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        CardPanelHutang = new javax.swing.JPanel();
        panelberi = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        paneltrima = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        topPanel2 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        stok = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lapbarang = new javax.swing.JPanel();
        btncetakstok = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblstok = new javax.swing.JTable();
        tmbhstok = new javax.swing.JButton();
        topPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnUbah = new javax.swing.JButton();
        tmbhbarang = new javax.swing.JPanel();
        nmbarang = new javax.swing.JTextField();
        hrgjual = new javax.swing.JTextField();
        cbjenisbarang = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblJenisSatuan = new javax.swing.JLabel();
        topPanel4 = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tfKelolaJenis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cbjenissatuan = new javax.swing.JComboBox<>();
        lblErrorNamaBarang = new javax.swing.JLabel();
        lblErrorHargaJual = new javax.swing.JLabel();
        lblErrorJenisBarang = new javax.swing.JLabel();
        lblErrorJenisSatuan = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1440, 1024));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setBackground(new java.awt.Color(0, 140, 255));
        sidebar.setPreferredSize(new java.awt.Dimension(200, 1024));
        sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/logo burung.png"))); // NOI18N
        jLabel14.setText("Burung");
        jLabel14.setIconTextGap(10);
        sidebar.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        tabTransaksi.setBackground(new java.awt.Color(204, 204, 255));
        tabTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/transaction logo.png"))); // NOI18N
        jLabel2.setText("Tansaksi");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.setIconTextGap(10);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        tabTransaksi.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 7, 160, 30));

        pnlAktifTransaksi.setBackground(new java.awt.Color(255, 188, 58));

        javax.swing.GroupLayout pnlAktifTransaksiLayout = new javax.swing.GroupLayout(pnlAktifTransaksi);
        pnlAktifTransaksi.setLayout(pnlAktifTransaksiLayout);
        pnlAktifTransaksiLayout.setHorizontalGroup(
            pnlAktifTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        pnlAktifTransaksiLayout.setVerticalGroup(
            pnlAktifTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        tabTransaksi.add(pnlAktifTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 5, -1));

        sidebar.add(tabTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 190, 45));

        tabStok.setBackground(new java.awt.Color(0, 140, 255));
        tabStok.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnStok.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        btnStok.setForeground(new java.awt.Color(255, 255, 255));
        btnStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/logo stok.png"))); // NOI18N
        btnStok.setText("  Stok");
        btnStok.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStok.setIconTextGap(10);
        btnStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStokMouseClicked(evt);
            }
        });
        tabStok.add(btnStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 7, 160, 30));

        pnlAktifStok.setBackground(new java.awt.Color(255, 188, 58));

        javax.swing.GroupLayout pnlAktifStokLayout = new javax.swing.GroupLayout(pnlAktifStok);
        pnlAktifStok.setLayout(pnlAktifStokLayout);
        pnlAktifStokLayout.setHorizontalGroup(
            pnlAktifStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        pnlAktifStokLayout.setVerticalGroup(
            pnlAktifStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        tabStok.add(pnlAktifStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 5, -1));
        pnlAktifStok.setVisible(false);

        sidebar.add(tabStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 190, 45));

        tabHutang.setBackground(new java.awt.Color(0, 140, 255));
        tabHutang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhutang.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        btnhutang.setForeground(new java.awt.Color(255, 255, 255));
        btnhutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/hutang logo.png"))); // NOI18N
        btnhutang.setText("Hutang");
        btnhutang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnhutang.setIconTextGap(10);
        btnhutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhutangMouseClicked(evt);
            }
        });
        tabHutang.add(btnhutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 7, 160, 30));

        pnlAktifHutang.setBackground(new java.awt.Color(255, 188, 58));

        javax.swing.GroupLayout pnlAktifHutangLayout = new javax.swing.GroupLayout(pnlAktifHutang);
        pnlAktifHutang.setLayout(pnlAktifHutangLayout);
        pnlAktifHutangLayout.setHorizontalGroup(
            pnlAktifHutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        pnlAktifHutangLayout.setVerticalGroup(
            pnlAktifHutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        tabHutang.add(pnlAktifHutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 5, -1));
        pnlAktifHutang.setVisible(false);

        sidebar.add(tabHutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 190, 45));

        logout.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/sign-out-alt.png"))); // NOI18N
        logout.setText("Logout");
        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.setIconTextGap(10);
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        sidebar.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 680, 160, 30));

        jPanel1.add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, -1));

        Change.setLayout(new java.awt.CardLayout());

        Transaksi.setLayout(new java.awt.CardLayout());

        panelOverview.setBackground(new java.awt.Color(234, 234, 234));
        panelOverview.setPreferredSize(new java.awt.Dimension(1240, 990));
        panelOverview.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel.setBackground(new java.awt.Color(255, 255, 255));
        topPanel.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel10.setText("Transaksi");
        topPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        panelOverview.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Pengeluaran");

        jLabel13.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 51));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 300, 130));

        jLabel11.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 140, 255));
        jLabel11.setText("Overview");
        panelOverview.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 68, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon laporan.png"))); // NOI18N
        jLabel18.setText("Laporan Keuangan");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon panah kiri.png"))); // NOI18N
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 15, -1, -1));

        panelOverview.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 920, 60));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 188, 58));
        jButton2.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jButton2.setText("+ Catat Transaksi");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(351, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        panelOverview.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 920, 410));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Penjualan");

        jLabel39.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 204, 51));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 300, 130));

        jPanel14.setBackground(new java.awt.Color(224, 255, 219));

        jLabel40.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(46, 231, 120));
        jLabel40.setText("Keuntungan");

        jLabel41.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 204, 51));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, -1, 130));

        Transaksi.add(panelOverview, "panelOverview");

        panelPenjualan.setBackground(new java.awt.Color(234, 234, 234));
        panelPenjualan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel1.setBackground(new java.awt.Color(255, 255, 255));
        topPanel1.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel31.setText("Catat Transaksi");
        topPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        panelPenjualan.add(topPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.CardLayout());

        penjualanOnClick.setBackground(new java.awt.Color(255, 255, 255));
        penjualanOnClick.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(46, 231, 120));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button.png"))); // NOI18N
        jLabel4.setText("Penjualan");
        jLabel4.setToolTipText("");
        jLabel4.setIconTextGap(10);
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        penjualanOnClick.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 440, 50));

        jPanel5.setBackground(new java.awt.Color(234, 234, 234));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(124, 124, 124));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button redup.png"))); // NOI18N
        jLabel19.setText("Pengeluaran");
        jLabel19.setToolTipText("");
        jLabel19.setIconTextGap(10);
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        penjualanOnClick.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(477, 15, 440, 50));

        jPanel2.add(penjualanOnClick, "penjualanOnClick");

        pengeluaranOnClick.setBackground(new java.awt.Color(255, 255, 255));
        pengeluaranOnClick.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(234, 234, 234));
        jPanel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(124, 124, 124));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button redup.png"))); // NOI18N
        jLabel42.setText("Penjualan");
        jLabel42.setToolTipText("");
        jLabel42.setIconTextGap(10);
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });
        jPanel18.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        pengeluaranOnClick.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 440, 50));

        jPanel19.setBackground(new java.awt.Color(255, 91, 91));
        jPanel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button2.png"))); // NOI18N
        jLabel43.setText("Pengeluaran");
        jLabel43.setToolTipText("");
        jLabel43.setIconTextGap(10);
        jPanel19.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        pengeluaranOnClick.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(477, 15, 440, 50));

        jPanel2.add(pengeluaranOnClick, "pengeluaranOnClick");

        panelPenjualan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 936, 340));

        Transaksi.add(panelPenjualan, "panelPenjualan");

        Change.add(Transaksi, "transaksi");

        jPanel12.setBackground(new java.awt.Color(234, 234, 234));
        jPanel12.setPreferredSize(new java.awt.Dimension(1250, 45));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paneltombol.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 0, 51));
        jPanel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Montserrat", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("BERIKAN");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(511, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(46, 231, 120));
        jPanel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Montserrat", 1, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("TERIMA");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button.png"))); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addContainerGap(536, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout paneltombolLayout = new javax.swing.GroupLayout(paneltombol);
        paneltombol.setLayout(paneltombolLayout);
        paneltombolLayout.setHorizontalGroup(
            paneltombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltombolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        paneltombolLayout.setVerticalGroup(
            paneltombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltombolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneltombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel12.add(paneltombol, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 63, -1, -1));

        CardPanelHutang.setBackground(new java.awt.Color(255, 255, 255));
        CardPanelHutang.setLayout(new java.awt.CardLayout());

        panelberi.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel26.setText("Memberikan ke :");

        jTextField2.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jTextField3.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jTextField4.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel28.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel28.setText("Jatuh Tempo");

        jLabel27.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel27.setText("Memberikan sejumlah :");

        jLabel25.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel25.setText("Informasi Optional");

        jLabel29.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel29.setText("Tanggal Hutang");

        jButton3.setBackground(new java.awt.Color(255, 188, 58));
        jButton3.setFont(new java.awt.Font("Montserrat SemiBold", 0, 28)); // NOI18N
        jButton3.setText("SIMPAN");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelberiLayout = new javax.swing.GroupLayout(panelberi);
        panelberi.setLayout(panelberiLayout);
        panelberiLayout.setHorizontalGroup(
            panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelberiLayout.createSequentialGroup()
                .addGroup(panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelberiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelberiLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel25))
                        .addGap(0, 1173, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelberiLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jTextField4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelberiLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jTextField2))
                    .addGroup(panelberiLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3)
                            .addGroup(panelberiLayout.createSequentialGroup()
                                .addGroup(panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel28)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(panelberiLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelberiLayout.setVerticalGroup(
            panelberiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelberiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        CardPanelHutang.add(panelberi, "cardterima");
        panelberi.getAccessibleContext().setAccessibleName("");

        paneltrima.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel30.setText("Menerima dari :");

        jTextField5.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N

        jTextField6.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Montserrat Medium", 0, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("Rp");

        jButton5.setBackground(new java.awt.Color(255, 188, 58));
        jButton5.setFont(new java.awt.Font("Montserrat SemiBold", 0, 28)); // NOI18N
        jButton5.setText("SIMPAN");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5jButton2ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        jLabel35.setText("Menerima Sejumlah");

        jLabel36.setFont(new java.awt.Font("Montserrat Medium", 0, 24)); // NOI18N
        jLabel36.setText("Sisa Hutang");

        jLabel37.setFont(new java.awt.Font("Montserrat Medium", 0, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 51, 51));
        jLabel37.setText("xxxx");

        jCheckBox1.setFont(new java.awt.Font("Montserrat Medium", 0, 24)); // NOI18N
        jCheckBox1.setText("Terima Pelunasan");
        jCheckBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneltrimaLayout = new javax.swing.GroupLayout(paneltrima);
        paneltrima.setLayout(paneltrimaLayout);
        paneltrimaLayout.setHorizontalGroup(
            paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltrimaLayout.createSequentialGroup()
                .addGroup(paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneltrimaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneltrimaLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel30)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paneltrimaLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5)
                            .addGroup(paneltrimaLayout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1097, Short.MAX_VALUE)
                                .addComponent(jLabel34)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel37)
                                .addGap(158, 158, 158))))
                    .addGroup(paneltrimaLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6)
                            .addGroup(paneltrimaLayout.createSequentialGroup()
                                .addGroup(paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        paneltrimaLayout.setVerticalGroup(
            paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltrimaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(paneltrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(49, 49, 49)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        CardPanelHutang.add(paneltrima, "cardberikan");

        jPanel12.add(CardPanelHutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 155, 1236, 654));

        topPanel2.setBackground(new java.awt.Color(255, 255, 255));
        topPanel2.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel32.setText("Catat Hutang");
        topPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        jPanel12.add(topPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        javax.swing.GroupLayout HutangLayout = new javax.swing.GroupLayout(Hutang);
        Hutang.setLayout(HutangLayout);
        HutangLayout.setHorizontalGroup(
            HutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1374, Short.MAX_VALUE)
        );
        HutangLayout.setVerticalGroup(
            HutangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        Change.add(Hutang, "hutang");

        jPanel3.setLayout(new java.awt.CardLayout());

        lapbarang.setBackground(new java.awt.Color(234, 234, 234));
        lapbarang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btncetakstok.setBackground(new java.awt.Color(255, 188, 58));
        btncetakstok.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        btncetakstok.setText("Cetak Laporan");
        btncetakstok.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncetakstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakstokActionPerformed(evt);
            }
        });
        lapbarang.add(btncetakstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 67, 166, 35));

        tblstok.setModel(new javax.swing.table.DefaultTableModel(
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
        tblstok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblstokMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblstok);

        lapbarang.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 120, 1099, 391));

        tmbhstok.setBackground(new java.awt.Color(255, 188, 58));
        tmbhstok.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        tmbhstok.setText("+Tambah Stok");
        tmbhstok.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tmbhstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhstokActionPerformed(evt);
            }
        });
        lapbarang.add(tmbhstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(968, 529, 166, 35));

        topPanel3.setBackground(new java.awt.Color(255, 255, 255));
        topPanel3.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel20.setText("Stok");
        topPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        lapbarang.add(topPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        btnUbah.setText("Ubah");
        btnUbah.setEnabled(false);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        lapbarang.add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, -1, -1));

        jPanel3.add(lapbarang, "lapbarang");

        tmbhbarang.setBackground(new java.awt.Color(234, 234, 234));
        tmbhbarang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nmbarang.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        nmbarang.setForeground(new java.awt.Color(138, 138, 138));
        nmbarang.setText("Masukkan nama barang");
        nmbarang.setActionCommand("<Not Set>");
        nmbarang.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(196, 196, 196), 1, true));
        nmbarang.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        nmbarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nmbarangFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nmbarangFocusLost(evt);
            }
        });
        tmbhbarang.add(nmbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 84, 1019, 40));

        hrgjual.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        hrgjual.setForeground(new java.awt.Color(138, 138, 138));
        hrgjual.setText("Masukkan harga jual");
        hrgjual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(196, 196, 196), 1, true));
        hrgjual.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        hrgjual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hrgjualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                hrgjualFocusLost(evt);
            }
        });
        hrgjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrgjualActionPerformed(evt);
            }
        });
        tmbhbarang.add(hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 1019, 40));

        cbjenisbarang.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        cbjenisbarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbjenisbarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbjenisbarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbjenisbarangFocusGained(evt);
            }
        });
        tmbhbarang.add(cbjenisbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 253, 1019, 40));

        jButton4.setBackground(new java.awt.Color(255, 188, 58));
        jButton4.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 12)); // NOI18N
        jButton4.setText("Simpan");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        tmbhbarang.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 1019, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Nama Barang");
        tmbhbarang.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 63, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Harga Jual");
        tmbhbarang.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        lblJenisSatuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblJenisSatuan.setText("Jenis Satuan");
        tmbhbarang.add(lblJenisSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        topPanel4.setBackground(new java.awt.Color(255, 255, 255));
        topPanel4.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudul.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        lblJudul.setText("Tambah Barang");
        topPanel4.add(lblJudul, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        tmbhbarang.add(topPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tfKelolaJenis.setForeground(new java.awt.Color(138, 138, 138));
        tfKelolaJenis.setText("Masukkan jenis barang baru");
        tfKelolaJenis.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        tfKelolaJenis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfKelolaJenisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfKelolaJenisFocusLost(evt);
            }
        });
        tfKelolaJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKelolaJenisActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel5.setText("Kelola Jenis");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/help icon 13x13.png"))); // NOI18N

        jButton1.setFont(new java.awt.Font("Montserrat Medium", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 140, 255));
        jButton1.setText("+ Tambah Jenis");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tfKelolaJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9)))
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfKelolaJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tmbhbarang.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 1019, 90));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Jenis Barang");
        tmbhbarang.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 235, -1, -1));

        cbjenissatuan.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        cbjenissatuan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbjenissatuan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbjenissatuanFocusGained(evt);
            }
        });
        tmbhbarang.add(cbjenissatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 1019, 40));

        lblErrorNamaBarang.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNamaBarang.setText("Please fill out this field.");
        tmbhbarang.add(lblErrorNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 125, -1, -1));

        lblErrorHargaJual.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorHargaJual.setText("Please fill out this field.");
        tmbhbarang.add(lblErrorHargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        lblErrorJenisBarang.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorJenisBarang.setText("Please fill out this field.");
        tmbhbarang.add(lblErrorJenisBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 294, -1, -1));

        lblErrorJenisSatuan.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorJenisSatuan.setText("Please fill out this field.");
        tmbhbarang.add(lblErrorJenisSatuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 381, -1, -1));

        jPanel3.add(tmbhbarang, "tmbhbarang");

        javax.swing.GroupLayout stokLayout = new javax.swing.GroupLayout(stok);
        stok.setLayout(stokLayout);
        stokLayout.setHorizontalGroup(
            stokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        stokLayout.setVerticalGroup(
            stokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stokLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 179, Short.MAX_VALUE))
        );

        Change.add(stok, "stokpnl");

        jPanel1.add(Change, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 1250, 990));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hrgjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hrgjualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hrgjualActionPerformed

    private void btncetakstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakstokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btncetakstokActionPerformed

    private void tmbhstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhstokActionPerformed
        lblJudul.setText("Tambah Barang");
        CardLayout clayout = (CardLayout) jPanel3.getLayout();
        clayout.show(jPanel3, "tmbhbarang");
        
        BersihkanLayarStok();
        update = false;
    }//GEN-LAST:event_tmbhstokActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // cek apakah seluruh field sudah diisi
        if (nmbarang.getText().isBlank() || nmbarang.getText().equals("  Masukkan nama barang") ||
                hrgjual.getText().isBlank() || hrgjual.getText().equals("  Masukkan harga jual") ||
                cbjenisbarang.getSelectedIndex() == 0 || cbjenissatuan.getSelectedIndex() == 0) {
            
            // apakah nama barang blm diisi
            if (nmbarang.getText().isBlank() || nmbarang.getText().equals("  Masukkan nama barang")) {
                lblErrorNamaBarang.setVisible(true);
                nmbarang.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 1, true));
            }
            
            // apakah harga jual blm diisi
            if (hrgjual.getText().isBlank() || hrgjual.getText().equals("  Masukkan harga jual")) {
                lblErrorHargaJual.setVisible(true);
                hrgjual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 1, true));
            }
            
            // apakah combobox jenis barang blm dipilih
            if (cbjenisbarang.getSelectedIndex() == 0) {
                cbjenisbarang.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 1, true));
                lblErrorJenisBarang.setVisible(true);
            }
            
            // apakah combobox jenis satuan blm dipilih
            if (cbjenissatuan.getSelectedIndex() == 0) {
                cbjenissatuan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 1, true));
                lblErrorJenisSatuan.setVisible(true);
            }
            
            return;
        }
        
        nmbr = nmbarang.getText();
        hrgjl = hrgjual.getText();
        jenisbr = cbjenisbarang.getSelectedItem().toString();
        jenissatuan = cbjenissatuan.getSelectedItem().toString();
        save = getIdJenis(jenisbr);
        id_satuan = getIdSatuan(jenissatuan);
        
        // update barang
        if (update == true) {
            try {
            sql = "UPDATE barang SET nama_barang='" + nmbr + "', "
                    + "harga_jual='" + hrgjl + "',"
                    + "id_jenis='" + save + "',"
                    + "id_satuan='" + id_satuan + "'"
                    + "WHERE id_barang='" + id_barang + "'";
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            CardLayout clayout = (CardLayout) jPanel3.getLayout();
            clayout.show(jPanel3, "lapbarang"); 
            update_tabelbarang();
            update = false;

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data gagal diperbarui" + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        } else {
            // tambah barang
        try {
            sql2 = "insert into barang(nama_barang, harga_jual, id_jenis, id_satuan) values ("
                    + "'" + nmbr + "',"
                    + "'" + hrgjl + "',"
                    + "'" + save + "',"
                    + "'" + id_satuan + "')";
            stm = conn.createStatement();
            stm.executeUpdate(sql2);
            
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
            CardLayout clayout = (CardLayout) jPanel3.getLayout();
            clayout.show(jPanel3, "lapbarang"); 
            update_tabelbarang();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data gagal disimpan"+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tfKelolaJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKelolaJenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKelolaJenisActionPerformed

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        dispose();
        new Login().setVisible(true); 
    }//GEN-LAST:event_logoutMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        lblJudul.setText("Transaksi");
        pnlAktifTransaksi.setVisible(true);
        pnlAktifHutang.setVisible(false);
        pnlAktifStok.setVisible(false);
        
        CardLayout clayout = (CardLayout) Change.getLayout();
        clayout.show(Change, "transaksi");
        
        CardLayout cl = (CardLayout) Transaksi.getLayout();
        cl.show(Transaksi, "panelOverview");
        
        tabTransaksi.setBackground(new java.awt.Color(204, 204, 255));
        tabHutang.setBackground(new java.awt.Color(0, 140, 255));
        tabStok.setBackground(new java.awt.Color(0, 140, 255));
        
        hilangkanPesanError();
        update = false;
    }//GEN-LAST:event_jLabel2MouseClicked

    private void btnhutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhutangMouseClicked
        // TODO add your handling code here:
        lblJudul.setText("Catat Hutang");
        pnlAktifTransaksi.setVisible(false);
        pnlAktifHutang.setVisible(true);
        pnlAktifStok.setVisible(false);
        
        CardLayout clayout = (CardLayout) Change.getLayout();
        clayout.show(Change, "hutang");
        
        tabTransaksi.setBackground(new java.awt.Color(0, 140, 255));
        tabHutang.setBackground(new java.awt.Color(204, 204, 255));
        tabStok.setBackground(new java.awt.Color(0, 140, 255));
        
        CardLayout panel = (CardLayout) (CardPanelHutang.getLayout());
        panel.show(CardPanelHutang, "cardterima");
        
        hilangkanPesanError();
        update = false;
    }//GEN-LAST:event_btnhutangMouseClicked

    private void nmbarangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nmbarangFocusGained
        // TODO add your handling code here:
        if (nmbarang.getText().equals("  Masukkan nama barang")) {
            nmbarang.setText("  ");
            nmbarang.setForeground(new java.awt.Color(0, 0, 0));
        }
        hilangkanPesanError();
    }//GEN-LAST:event_nmbarangFocusGained

    private void nmbarangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nmbarangFocusLost
        // TODO add your handling code here:
        if (nmbarang.getText().isBlank()) {
            nmbarang.setText("  Masukkan nama barang");
            nmbarang.setForeground(new java.awt.Color(138, 138, 138));
        }
    }//GEN-LAST:event_nmbarangFocusLost

    private void hrgjualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hrgjualFocusGained
        // TODO add your handling code here:
        if (hrgjual.getText().equals("  Masukkan harga jual")) {
            hrgjual.setText("  ");
            hrgjual.setForeground(new java.awt.Color(0, 0, 0));
        }
        hilangkanPesanError();
    }//GEN-LAST:event_hrgjualFocusGained

    private void hrgjualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hrgjualFocusLost
        // TODO add your handling code here:
        if (hrgjual.getText().isBlank()) {
            hrgjual.setText("  Masukkan harga jual");
            hrgjual.setForeground(new java.awt.Color(138, 138, 138));
        }
    }//GEN-LAST:event_hrgjualFocusLost

    private void tfKelolaJenisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfKelolaJenisFocusGained
        // TODO add your handling code here:
        if (tfKelolaJenis.getText().equals("Masukkan jenis barang baru")) {
            tfKelolaJenis.setText("");
            tfKelolaJenis.setForeground(new java.awt.Color(0, 0, 0));
        }
    }//GEN-LAST:event_tfKelolaJenisFocusGained

    private void tfKelolaJenisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfKelolaJenisFocusLost
        // TODO add your handling code here:
        if (tfKelolaJenis.getText().isBlank()) {
            tfKelolaJenis.setText("Masukkan jenis barang baru");
            tfKelolaJenis.setForeground(new java.awt.Color(138, 138, 138));
        }
    }//GEN-LAST:event_tfKelolaJenisFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (tfKelolaJenis.getText().equals("Masukkan jenis barang baru")) {
            JOptionPane.showMessageDialog(this, "Masukkan nama jenis barang!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String alamatMhs = tfKelolaJenis.getText();
            try {
                sql = "INSERT INTO jenis VALUES("
                        + "NULL,"
                        + "'" + alamatMhs + "'"
                        + ")";
                stm = conn.createStatement();
                stm.executeUpdate(sql);

                JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
                tfKelolaJenis.setText("Masukkan jenis barang baru");
                cbjenisbarang.removeAllItems(); // hapus seluruh isi combo box
                // refresh combo box
                cbboxjenis(); 
                cbboxJenisSatuan();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "Data gagal disimpan", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
        }  
    }//GEN-LAST:event_jButton1ActionPerformed


    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        CardLayout panel = (CardLayout) (CardPanelHutang.getLayout());
        panel.show(CardPanelHutang, "cardterima");
        
    }//GEN-LAST:event_jPanel16MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        // TODO add your handling code here:
        CardLayout panel = (CardLayout) (CardPanelHutang.getLayout());
        panel.show(CardPanelHutang, "cardberikan");
    }//GEN-LAST:event_jPanel17MouseClicked


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) Transaksi.getLayout();
        clayout.show(Transaksi, "panelPenjualan");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5jButton2ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "pengeluaranOnClick");
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "pengeluaranOnClick");
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "penjualanOnClick");
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "penjualanOnClick");
    }//GEN-LAST:event_jPanel18MouseClicked

    private void btnStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStokMouseClicked
        // TODO add your handling code here:
        lblJudul.setText("Stok");
        pnlAktifTransaksi.setVisible(false);
        pnlAktifHutang.setVisible(false);
        pnlAktifStok.setVisible(true);
        
        CardLayout clayout = (CardLayout) Change.getLayout();
        clayout.show(Change, "stokpnl");
        
        CardLayout cl = (CardLayout) jPanel3.getLayout();
        cl.show(jPanel3, "lapbarang");
        
        tabTransaksi.setBackground(new java.awt.Color(0, 140, 255));
        tabHutang.setBackground(new java.awt.Color(0, 140, 255));
        tabStok.setBackground(new java.awt.Color(204, 204, 255));
        
        hilangkanPesanError();
        update = false;
    }//GEN-LAST:event_btnStokMouseClicked

    private void cbjenisbarangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbjenisbarangFocusGained
        // TODO add your handling code here:
        hilangkanPesanError();
    }//GEN-LAST:event_cbjenisbarangFocusGained

    private void cbjenissatuanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbjenissatuanFocusGained
        // TODO add your handling code here:
        hilangkanPesanError();
    }//GEN-LAST:event_cbjenissatuanFocusGained

    private void tblstokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblstokMouseClicked
        btnUbah.setEnabled(true);
        int row = tblstok.getSelectedRow();
        id_barang = ((String) tblstok.getValueAt(row, 0));
        nmbarang.setText((String) tblstok.getValueAt(row, 3));
        nmbarang.setForeground(new java.awt.Color(0, 0, 0));
        hrgjual.setText("  "+(String) tblstok.getValueAt(row, 6));
        hrgjual.setForeground(new java.awt.Color(0, 0, 0));
        cbjenisbarang.setSelectedItem(tblstok.getValueAt(tblstok.getSelectedRow(), 1) + "");
        cbjenissatuan.setSelectedItem(tblstok.getValueAt(tblstok.getSelectedRow(), 2) + "");
    }//GEN-LAST:event_tblstokMouseClicked

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        CardLayout clayout = (CardLayout) jPanel3.getLayout();
        clayout.show(jPanel3, "tmbhbarang");
        lblJudul.setText("Ubah Barang");
        update = true;
    }//GEN-LAST:event_btnUbahActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardPanelHutang;
    private javax.swing.JPanel Change;
    private javax.swing.JPanel Hutang;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JLabel btnStok;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btncetakstok;
    private javax.swing.JLabel btnhutang;
    private javax.swing.JComboBox<String> cbjenisbarang;
    private javax.swing.JComboBox<String> cbjenissatuan;
    private javax.swing.JTextField hrgjual;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JPanel lapbarang;
    private javax.swing.JLabel lblErrorHargaJual;
    private javax.swing.JLabel lblErrorJenisBarang;
    private javax.swing.JLabel lblErrorJenisSatuan;
    private javax.swing.JLabel lblErrorNamaBarang;
    private javax.swing.JLabel lblJenisSatuan;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel logout;
    private javax.swing.JTextField nmbarang;
    private javax.swing.JPanel panelOverview;
    private javax.swing.JPanel panelPenjualan;
    private javax.swing.JPanel panelberi;
    private javax.swing.JPanel paneltombol;
    private javax.swing.JPanel paneltrima;
    private javax.swing.JPanel pengeluaranOnClick;
    private javax.swing.JPanel penjualanOnClick;
    private javax.swing.JPanel pnlAktifHutang;
    private javax.swing.JPanel pnlAktifStok;
    private javax.swing.JPanel pnlAktifTransaksi;
    private javax.swing.JPanel sidebar;
    private javax.swing.JPanel stok;
    private javax.swing.JPanel tabHutang;
    private javax.swing.JPanel tabStok;
    private javax.swing.JPanel tabTransaksi;
    private javax.swing.JTable tblstok;
    private javax.swing.JTextField tfKelolaJenis;
    private javax.swing.JPanel tmbhbarang;
    private javax.swing.JButton tmbhstok;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel topPanel1;
    private javax.swing.JPanel topPanel2;
    private javax.swing.JPanel topPanel3;
    private javax.swing.JPanel topPanel4;
    // End of variables declaration//GEN-END:variables
}

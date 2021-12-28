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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author A.T.T.A
 */
public class Main extends javax.swing.JFrame {

    public Connection conn;
    public ResultSet rs;
    public Statement stm;
    public PreparedStatement pst;
    String sql, sql2, nmbr,hrgjl,satuan,jenisbr, jltotal, jenisst,save, jenissatuan, id_satuan, id_barang, nama_barang, harga_beli, kd_barang, harga_barang, jumlah, subtotal, hrgBarangJual, stokbrg;
    int row, totaljual, totalbeli;
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
        tabeljual();
        tabelbeli();
        updateTabelHutang();
        selectKeranjang();
        selectjual();
        totalkeluar();
        totaljual();
        trjual();
        trDisplay();
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
    
    private void bersihkanTabelStok () {
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        // unselect row pada tabel stok
        tblstok.clearSelection();
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
    
    private void penjualan(){
            String total = "0";
            sql = "SELECT sum(total_harga) FROM transaksi_penjualan";
            
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    while (rs.next()) {
                        total = rs.getString("total_harga");
                        System.out.println("Rp. "+total);
                    }
                } catch (Exception e) {
            }
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

        private void tabeljual() {
        // membuat tampilan model tabel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Stok");
        model.addColumn("Satuan");
        model.addColumn("Harga Jual");
        tblbarangjual.setModel(model);
          // menampilkan data database ke dalam tabel
        try {
            rs = stm.executeQuery("SELECT b.id_barang, b.nama_barang, b.stok, s.jenis_satuan, b.harga_jual FROM barang b, satuan s WHERE b.id_satuan = s.id_satuan");
            while (rs.next()){
            Object[] data = new Object[5];
                data[0] = rs.getString("id_barang");
                data[1] = rs.getString("nama_barang");
                data[2] = rs.getString("stok");
                data[3] = rs.getString("jenis_satuan");
                data[4] = rs.getString("harga_jual");
                model.addRow(data);
                tblbarangjual.setModel(model);
        }
            rs.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } 
    
 }
        
        private void tabelbeli() {
        // membuat tampilan model tabel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Satuan");
        tblbarangbeli.setModel(model);
          // menampilkan data database ke dalam tabel
        try {
            rs = stm.executeQuery("SELECT b.id_barang, b.nama_barang, s.jenis_satuan FROM barang b, satuan s WHERE b.id_satuan = s.id_satuan");
            while (rs.next()){
            Object[] data = new Object[4];
                data[0] = rs.getString("id_barang");
                data[1] = rs.getString("nama_barang");
                data[2] = rs.getString("jenis_satuan");
                model.addRow(data);
                tblbarangbeli.setModel(model);
        }
            rs.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } 
    
 }
        private void updateTabelHutang(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jumlah Hutang");
        tblhutang.setModel(model);
        
        try{
            rs = stm.executeQuery("SELECT * FROM peminjaman_hutang");
            while(rs.next()){
                Object[] data = new Object[2];
                data[0] = rs.getString("nama_pelanggan");
                data[1] = rs.getString("nominal_peminjaman");
                model.addRow(data);
                tblhutang.setModel(model);
            }
            rs.close();
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void ClearTabelHutang(){
        txtberikan.setText("");
        txtnominal.setText("");
        txtcatatanhutang.setText("");
        tglhutang.setCalendar(null);
        tgljatuhtempo.setCalendar(null);
        }
        
        private void selectKeranjang(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Harga Satuan");
        model.addColumn("Jumlah Beli");
        model.addColumn("Subtotal");
        tblpengeluaran.setModel(model);
        
        try{
            rs = stm.executeQuery("SELECT * FROM temp_barang");
            while(rs.next()){
                Object[] data = new Object[4];
                data[0] = rs.getString("nama_barang");
                data[1] = rs.getString("harga_pokok");
                data[2] = rs.getString("jumlah");
                data[3] = rs.getString("subtotal");
                model.addRow(data);
                tblpengeluaran.setModel(model);
            }
            rs.close();
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
        private void selectjual(){
        DefaultTableModel models = new DefaultTableModel();
        models.addColumn("Nama Barang");
        models.addColumn("Harga Satuan");
        models.addColumn("Jumlah Beli");
        models.addColumn("Subtotal");
        tblJual.setModel(models);
        
        try{
            rs = stm.executeQuery("SELECT * FROM temp_jual");
            while(rs.next()){
                Object[] data = new Object[4];
                data[0] = rs.getString("nama_barang");
                data[1] = rs.getString("harga_jual");
                data[2] = rs.getString("jumlah_beli");
                data[3] = rs.getString("total");
                models.addRow(data);
                tblJual.setModel(models);
            }
            rs.close();
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
        private void totalkeluar(){
            sql = "SELECT SUM(tb.subtotal) AS jumlah FROM temp_barang tb";
            
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    while (rs.next()) {
                        int total = rs.getInt("jumlah");
                        String krtotal = Integer.toString(total);
                        
                        ttlpengeluaran.setText("Rp.   " + krtotal);
                    }
                } catch (Exception e) {
            }
    }
        private void totaljual(){
            sql = "SELECT SUM(total) AS totaljl FROM temp_jual";
            
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    while (rs.next()) {
                        int totaljual = rs.getInt("totaljl");
                        jltotal = Integer.toString(totaljual);
                        
                        ttlpenjualan.setText("Rp.   " + jltotal);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
            }
    }
        
        private void trDisplay(){
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tanggal Transaksi");
            model.addColumn("Pemasukan");
            model.addColumn("Pengeluaran");
            tbltransaksi.setModel(model);
            
            try{
            rs = stm.executeQuery("SELECT tj.tanggal_penjualan, tj.total_harga, tb.total_harga FROM transaksi_penjualan tj JOIN barang b "
                    + "ON tj.id_barang = b.id_barang JOIN transaksi_pembelian tb "
                    + "ON tb.id_barang = b.id_barang ORDER BY tj.tanggal_penjualan");
            while(rs.next()){
                Object[] data = new Object[3];
                data[0] = rs.getString("tj.tanggal_penjualan");
                data[1] = rs.getString("tj.total_harga");
                data[2] = rs.getString("tb.total_harga");
                model.addRow(data);
                tbltransaksi.setModel(model);
            }
            rs.close();
       
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
//        private void untung(){
//            sql = "SELECT SUM(tj.total_harga) AS jual FROM transaksi_penjualan tj "
//                    + "JOIN barang b ON  tj.id_barang = b.id_barang";
//            
//            try {
//                pst = conn.prepareStatement(sql);
//                rs = pst.executeQuery();
//                    while (rs.next()) {
//                        int totaljual = rs.getInt("jual");
////                        int totalbeli = rs.getInt("beli");
//                        int keuntungan = totaljual;
//                        jltotal = Integer.toString(keuntungan);
//                        
//                        trKeuntungan.setText("Rp.   " + jltotal);
//                    }
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e);
//            }
//        }
        
        private void trjual(){
            sql = "SELECT SUM(total_harga) AS total_harga FROM transaksi_penjualan";
            sql2 = "SELECT SUM(total_harga) AS total_harga FROM transaksi_pembelian";
            
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    while (rs.next()) {
                        totaljual = rs.getInt("total_harga");
                        jltotal = Integer.toString(totaljual);
                        
                        trPenjualan.setText("Rp.   " + jltotal);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
            }
            try {
                pst = conn.prepareStatement(sql2);
                rs = pst.executeQuery();
                    while (rs.next()) {
                        totalbeli = rs.getInt("total_harga");
                        jltotal = Integer.toString(totalbeli);
                        
                        trpengeluaran.setText("Rp.   " + jltotal);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
            }
            
            int untung = totaljual - totalbeli;
            jltotal = Integer.toString(untung);
                        
            trKeuntungan.setText("Rp.   " + jltotal);
    }
        
        private void balik(){
            CardLayout clayout = (CardLayout) Change.getLayout();
            clayout.show(Change, "transaksi");

            CardLayout cl = (CardLayout) Transaksi.getLayout();
            cl.show(Transaksi, "panelOverview");

            CardLayout cla = (CardLayout) jPanel2.getLayout();
            cla.show(jPanel2, "penjualanOnClick");
        }
        
        private void tranBeli(){
            sql = "SELECT * FROM temp_barang";
            String tglbeli = null;
            String catatan = null;
            
            if(dateBeli.getDate() == null && txtCatatan.getText() != null){
                JOptionPane.showMessageDialog(this, "Isi Kolom Tanggal", "Perhatian", JOptionPane.WARNING_MESSAGE);
            }else{
                if(dateBeli.getDate() != null){
                    SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
                    tglbeli = dcn.format(dateBeli.getDate());
                    catatan = txtCatatan.getText();
                        try {
                        pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                            while (rs.next()) {
                                int id_barang = rs.getInt("id_barang");
                                String nm_barang = rs.getString("nama_barang");
                                int hrg_pokok = rs.getInt("harga_pokok");
                                int jml = rs.getInt("jumlah");
                                int stotal = rs.getInt("subtotal");

                                stm.executeUpdate("INSERT INTO transaksi_pembelian (id_barang, jumlah_pembelian, harga_beli_satuan, tanggal, total_harga, catatan) VALUES ("+id_barang+", '"+jml+"', '"+hrg_pokok+"', '"+tglbeli+"','"+stotal+"', '"+catatan+"')");
                            }
                            
                            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
                             
                            balik();
                             stm.executeUpdate("TRUNCATE temp_barang");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
    }
        
        private void tranJual(){
            sql = "SELECT * FROM temp_jual";
            
            String tglbeli = null;
            String catatan = null;
            String ttlJual = "SELECT SUM(total) AS totaljl FROM temp_jual";
            
            if(dateBeli.getDate() == null && txtCatatan.getText() != null){
                JOptionPane.showMessageDialog(this, "Isi Kolom Tanggal", "Perhatian", JOptionPane.WARNING_MESSAGE);
            }else{
                if(dateBeli.getDate() != null){
                    SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
                    tglbeli = dcn.format(dateBeli.getDate());
                    catatan = txtCatatan.getText();
                    if(txtBayar.getText() == ttlJual){
                        try {
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                int kd_barang = rs.getInt("id_barang");
                                String nm_barang = rs.getString("nama_barang");
                                int jmlbeli = rs.getInt("jumlah_beli");
                                int harga = rs.getInt("harga_jual");
                                int totaljual = rs.getInt("total");
                                stm.executeUpdate("INSERT INTO transaksi_penjualan (id_barang, nama_barang, jumlah_beli, harga_jual, tanggal_penjualan, total_harga, status, catatan) VALUES ('"+kd_barang+"', '"+nm_barang+"', '"+jmlbeli+"', '"+harga+"', '"+tglbeli+"', '"+totaljual+"', 'Lunas', '"+catatan+"')");
                            }
                            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
                             
                            balik();
                             stm.executeUpdate("TRUNCATE temp_jual");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e);
                        }
                    }else{
                        try {
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                int kd_barang = rs.getInt("id_barang");
                                String nm_barang = rs.getString("nama_barang");
                                int jmlbeli = rs.getInt("jumlah_beli");
                                int harga = rs.getInt("harga_jual");
                                int totaljual = rs.getInt("total");
                                stm.executeUpdate("INSERT INTO transaksi_penjualan (id_barang, nama_barang, jumlah_beli, harga_jual, tanggal_penjualan, total_harga, status, catatan) VALUES ('"+kd_barang+"', '"+nama_barang+"', '"+jmlbeli+"', '"+harga+"', '"+tglbeli+"', '"+totaljual+"', 'Belum Lunas', '"+catatan+"')");
                            }
                             
                            balik();
                             stm.executeUpdate("TRUNCATE temp_jual");
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
            }
    }
        
        private void refreshtabel(){
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nama Barang");
            model.addColumn("Harga Pokok");
            model.addColumn("Jumlah Beli");
            model.addColumn("Subtotal");
            tblpengeluaran.setModel(model);
            ttlpengeluaran.setText("Rp.   0");
            
            try{
            rs = stm.executeQuery("SELECT * FROM temp_barang");
            while(rs.next()){
                Object[] data = new Object[4];
                data[0] = rs.getString("nama_barang");
                data[1] = rs.getString("harga_pokok");
                data[2] = rs.getString("jumlah");
                data[3] = rs.getString("subtotal");
                model.addRow(data);
                tblpengeluaran.setModel(model);
            }
            rs.close();
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

           DefaultTableModel models = new DefaultTableModel();
            models.addColumn("Nama Barang");
            models.addColumn("Harga Satuan");
            models.addColumn("Jumlah Beli");
            models.addColumn("Subtotal");
            tblJual.setModel(models);
            ttlpenjualan.setText("Rp.   0");
            
            try{
            rs = stm.executeQuery("SELECT * FROM temp_jual");
            while(rs.next()){
                Object[] data = new Object[4];
                data[0] = rs.getString("nama_barang");
                data[1] = rs.getString("harga_jual");
                data[2] = rs.getString("jumlah_beli");
                data[3] = rs.getString("total");
                models.addRow(data);
                tblJual.setModel(models);
            }
            rs.close();
       
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
            
            trjual();
            dateBeli.setCalendar(null);
            txtCatatan.setText(" ");
            txtBayar.setText(" ");
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
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbltransaksi = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        trPenjualan = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        trKeuntungan = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        trpengeluaran = new javax.swing.JLabel();
        panelPenjualan = new javax.swing.JPanel();
        topPanel1 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        penjualanOnClick = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblJual = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        ttlpenjualan = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        pengeluaranOnClick = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblpengeluaran = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        ttlpengeluaran = new javax.swing.JLabel();
        tblTransaksiPengeluaran = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblbarangbeli = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        subpembelian = new javax.swing.JLabel();
        txtJmlBeli = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        txtHrgBeli = new javax.swing.JTextField();
        tblTransaksiPenjualan = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblbarangjual = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblJual = new javax.swing.JLabel();
        txtJual = new javax.swing.JTextField();
        btnJual = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCatatan = new javax.swing.JTextField();
        cbxPelanggan = new javax.swing.JComboBox<>();
        dateBeli = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblPelanggan = new javax.swing.JLabel();
        simpanBeli = new javax.swing.JButton();
        PanelLaporan = new javax.swing.JPanel();
        topPanel2 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lppenjualan = new javax.swing.JLabel();
        lppengeluaran = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Hutang = new javax.swing.JPanel();
        panelLihatHutang = new javax.swing.JPanel();
        topPanel5 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblhutang = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        panelHutang = new javax.swing.JPanel();
        topPanel6 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        berikanOnClick = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel56 = new javax.swing.JLabel();
        txtberikan = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtnominal = new javax.swing.JTextField();
        txtcatatanhutang = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        tglhutang = new com.toedter.calendar.JDateChooser();
        tgljatuhtempo = new com.toedter.calendar.JDateChooser();
        jButton8 = new javax.swing.JButton();
        terimaOnClick = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel52 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton7 = new javax.swing.JButton();
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
        btnHapus = new javax.swing.JButton();
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

        jPanel1.add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 190, -1));

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

        jLabel11.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 140, 255));
        jLabel11.setText("Overview");
        panelOverview.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 68, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon laporan.png"))); // NOI18N
        jLabel18.setText("Laporan Keuangan");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon panah kiri.png"))); // NOI18N
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 15, -1, 30));

        panelOverview.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 1040, 60));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 188, 58));
        jButton2.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jButton2.setText("+ Catat Transaksi");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tbltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tanggal", "Pemasukan", "Pengeluaran"
            }
        ));
        tbltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbltransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbltransaksi);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(850, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(351, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(69, Short.MAX_VALUE)))
        );

        panelOverview.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 1040, 410));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Penjualan");

        jLabel39.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 204, 51));

        trPenjualan.setFont(new java.awt.Font("Montserrat Medium", 0, 20)); // NOI18N
        trPenjualan.setForeground(new java.awt.Color(46, 231, 120));
        trPenjualan.setText("Rp0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel38)
                            .addComponent(trPenjualan))))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(trPenjualan)
                .addGap(18, 18, 18)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 336, 130));

        jPanel14.setBackground(new java.awt.Color(224, 255, 219));

        jLabel40.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(46, 231, 120));
        jLabel40.setText("Keuntungan");

        jLabel41.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 204, 51));

        trKeuntungan.setFont(new java.awt.Font("Montserrat Medium", 0, 20)); // NOI18N
        trKeuntungan.setForeground(new java.awt.Color(46, 231, 120));
        trKeuntungan.setText("Rp0");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(trKeuntungan)
                        .addComponent(jLabel40)))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel40)
                .addGap(13, 13, 13)
                .addComponent(trKeuntungan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(733, 100, 336, 130));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel65.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(102, 102, 102));
        jLabel65.setText("Pengeluaran");

        jLabel66.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 204, 51));

        trpengeluaran.setFont(new java.awt.Font("Montserrat Medium", 0, 20)); // NOI18N
        trpengeluaran.setForeground(new java.awt.Color(255, 91, 91));
        trpengeluaran.setText("Rp0");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel65)
                            .addComponent(trpengeluaran))))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(trpengeluaran)
                .addGap(18, 18, 18)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelOverview.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 100, -1, 130));

        Transaksi.add(panelOverview, "panelOverview");

        panelPenjualan.setBackground(new java.awt.Color(234, 234, 234));
        panelPenjualan.setMinimumSize(new java.awt.Dimension(1140, 690));
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
        penjualanOnClick.setMinimumSize(new java.awt.Dimension(1140, 65));
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

        tblJual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Barang", "Harga satuan", "Jumlah", "Subtotal"
            }
        ));
        jScrollPane6.setViewportView(tblJual);

        penjualanOnClick.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 120, 890, 120));

        jLabel29.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel29.setText("Total");
        penjualanOnClick.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, -1, -1));

        ttlpenjualan.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        ttlpenjualan.setText("Rp0");
        penjualanOnClick.add(ttlpenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, -1, -1));

        jLabel32.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel32.setText("Bayar");
        penjualanOnClick.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 290, -1, -1));

        jLabel34.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel34.setText("Rp");
        penjualanOnClick.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, -1, -1));
        penjualanOnClick.add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 290, 180, 28));

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setForeground(new java.awt.Color(0, 140, 255));
        jButton10.setText("Tambah Barang");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        penjualanOnClick.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, -1, 30));

        jPanel2.add(penjualanOnClick, "penjualanOnClick");

        pengeluaranOnClick.setBackground(new java.awt.Color(255, 255, 255));
        pengeluaranOnClick.setMinimumSize(new java.awt.Dimension(1140, 65));
        pengeluaranOnClick.setPreferredSize(new java.awt.Dimension(1140, 65));
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
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button2.png"))); // NOI18N
        jLabel43.setText("Pengeluaran");
        jLabel43.setToolTipText("");
        jLabel43.setIconTextGap(10);
        jPanel19.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        pengeluaranOnClick.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(477, 15, 440, 50));

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setForeground(new java.awt.Color(0, 140, 255));
        jButton11.setText("Tambah Barang");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        pengeluaranOnClick.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, -1, 30));

        tblpengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Barang", "Harga satuan", "Jumlah", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tblpengeluaran);

        pengeluaranOnClick.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 120, 890, 170));

        jLabel35.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel35.setText("Total");
        pengeluaranOnClick.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 310, -1, -1));

        ttlpengeluaran.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        ttlpengeluaran.setText("Rp. 0");
        pengeluaranOnClick.add(ttlpengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, -1, -1));

        jPanel2.add(pengeluaranOnClick, "pengeluaranOnClick");

        tblTransaksiPengeluaran.setBackground(new java.awt.Color(255, 255, 255));

        tblbarangbeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama Barang", "Satuan"
            }
        ));
        tblbarangbeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbarangbeliMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblbarangbeli);

        jLabel28.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel28.setText("Jumlah Beli");

        jLabel70.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel70.setText("Subtotal");

        subpembelian.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        subpembelian.setText("Rp 0");

        txtJmlBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtJmlBeliFocusLost(evt);
            }
        });
        txtJmlBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJmlBeliKeyReleased(evt);
            }
        });

        jButton12.setForeground(new java.awt.Color(0, 140, 255));
        jButton12.setText("Tambah");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel64.setText("Harga Beli");

        txtHrgBeli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHrgBeliFocusLost(evt);
            }
        });
        txtHrgBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHrgBeliActionPerformed(evt);
            }
        });
        txtHrgBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHrgBeliKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout tblTransaksiPengeluaranLayout = new javax.swing.GroupLayout(tblTransaksiPengeluaran);
        tblTransaksiPengeluaran.setLayout(tblTransaksiPengeluaranLayout);
        tblTransaksiPengeluaranLayout.setHorizontalGroup(
            tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblTransaksiPengeluaranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblTransaksiPengeluaranLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tblTransaksiPengeluaranLayout.createSequentialGroup()
                                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel70)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel64))
                                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tblTransaksiPengeluaranLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(subpembelian)
                                            .addComponent(txtJmlBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblTransaksiPengeluaranLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHrgBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        tblTransaksiPengeluaranLayout.setVerticalGroup(
            tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblTransaksiPengeluaranLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtJmlBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtHrgBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(tblTransaksiPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(subpembelian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );

        jPanel2.add(tblTransaksiPengeluaran, "tblTransaksiPengeluaran");

        tblTransaksiPenjualan.setBackground(new java.awt.Color(255, 255, 255));

        tblbarangjual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Barang", "Nama Barang", "Stok", "Satuan", "Harga Jual"
            }
        ));
        tblbarangjual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbarangjualMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblbarangjual);

        jLabel17.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel17.setText("Jumlah Beli");

        jLabel26.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel26.setText("Subtotal");

        lblJual.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        lblJual.setText("Rp 0");

        txtJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJualKeyReleased(evt);
            }
        });

        btnJual.setForeground(new java.awt.Color(0, 140, 255));
        btnJual.setText("Tambah");
        btnJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tblTransaksiPenjualanLayout = new javax.swing.GroupLayout(tblTransaksiPenjualan);
        tblTransaksiPenjualan.setLayout(tblTransaksiPenjualanLayout);
        tblTransaksiPenjualanLayout.setHorizontalGroup(
            tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblTransaksiPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblTransaksiPenjualanLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnJual, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tblTransaksiPenjualanLayout.createSequentialGroup()
                                .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel17))
                                .addGap(25, 25, 25)
                                .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblJual)
                                    .addComponent(txtJual, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        tblTransaksiPenjualanLayout.setVerticalGroup(
            tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tblTransaksiPenjualanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tblTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblJual))
                .addGap(18, 18, 18)
                .addComponent(btnJual, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );

        jPanel2.add(tblTransaksiPenjualan, "tblTransaksiPenjualan");

        panelPenjualan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 936, 340));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setMinimumSize(new java.awt.Dimension(1140, 65));
        jPanel16.setPreferredSize(new java.awt.Dimension(1140, 68));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel1.setText("Informasi Optional");
        jPanel16.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, -1, -1));

        txtCatatan.setFont(new java.awt.Font("Montserrat Medium", 0, 14)); // NOI18N
        txtCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCatatanActionPerformed(evt);
            }
        });
        jPanel16.add(txtCatatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 65, 881, 40));

        cbxPelanggan.setFont(new java.awt.Font("Montserrat Medium", 0, 14)); // NOI18N
        cbxPelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nama Pelanggan" }));
        cbxPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPelangganActionPerformed(evt);
            }
        });
        jPanel16.add(cbxPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 135, 881, 40));

        dateBeli.setMaxSelectableDate(new java.util.Date(253370743306000L));
        jPanel16.add(dateBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 206, 213, 35));

        jLabel12.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel12.setText("Tanggal");
        jPanel16.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 187, -1, -1));

        jLabel13.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel13.setText("Catatan");
        jPanel16.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 45, -1, -1));

        lblPelanggan.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        lblPelanggan.setText("Nama Pelanggan");
        jPanel16.add(lblPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 115, -1, -1));

        panelPenjualan.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 936, 260));

        simpanBeli.setBackground(new java.awt.Color(255, 188, 58));
        simpanBeli.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        simpanBeli.setText("Simpan Transaksi");
        simpanBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBeliActionPerformed1(evt);
            }
        });
        panelPenjualan.add(simpanBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 690, 940, 40));

        Transaksi.add(panelPenjualan, "panelPenjualan");

        topPanel2.setBackground(new java.awt.Color(255, 255, 255));
        topPanel2.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel21.setText("Laporan Hutang");
        topPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        lppenjualan.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        lppenjualan.setForeground(new java.awt.Color(46, 231, 120));

        lppengeluaran.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        lppengeluaran.setForeground(new java.awt.Color(255, 0, 0));

        jLabel24.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(46, 231, 120));
        jLabel24.setText("Penjualan");

        jLabel25.setFont(new java.awt.Font("Montserrat Medium", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setText("Pengeluaran");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(lppenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 442, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lppengeluaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(68, 68, 68)))
                .addGap(125, 125, 125))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lppengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lppenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton5.setBackground(new java.awt.Color(255, 188, 58));
        jButton5.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/downloading.png"))); // NOI18N
        jButton5.setText("Unduh Laporan");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelLaporanLayout = new javax.swing.GroupLayout(PanelLaporan);
        PanelLaporan.setLayout(PanelLaporanLayout);
        PanelLaporanLayout.setHorizontalGroup(
            PanelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLaporanLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(topPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelLaporanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelLaporanLayout.setVerticalGroup(
            PanelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLaporanLayout.createSequentialGroup()
                .addComponent(topPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(PanelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLaporanLayout.createSequentialGroup()
                        .addGap(599, 599, 599)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelLaporanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 98, Short.MAX_VALUE))
        );

        Transaksi.add(PanelLaporan, "cetaklap");

        Change.add(Transaksi, "transaksi");

        Hutang.setLayout(new java.awt.CardLayout());

        panelLihatHutang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel5.setBackground(new java.awt.Color(255, 255, 255));
        topPanel5.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel33.setText("Informasi Hutang");
        topPanel5.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        panelLihatHutang.add(topPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setBackground(new java.awt.Color(255, 188, 58));
        jButton6.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jButton6.setText("+ Catat Hutang");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 340, -1, 35));

        tblhutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama Pelanggan", "Jumlah Hutang"
            }
        ));
        tblhutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhutangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblhutang);

        jPanel8.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1110, 310));

        panelLihatHutang.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 294, 1134, 400));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setFont(new java.awt.Font("Montserrat Medium", 1, 14)); // NOI18N
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon laporan.png"))); // NOI18N
        jLabel45.setText("Laporan Hutang");
        jPanel20.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/icon panah kiri.png"))); // NOI18N
        jPanel20.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1085, 14, -1, -1));

        panelLihatHutang.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 218, 1134, 60));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("Hutang Saya");
        jPanel21.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 36, -1, -1));

        jLabel47.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 204, 51));
        jPanel21.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 98, 140, 39));

        jLabel22.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(46, 231, 120));
        jLabel22.setText("Rp0");
        jPanel21.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 64, -1, -1));

        panelLihatHutang.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 72, 560, 130));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        jLabel62.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(102, 102, 102));
        jLabel62.setText("Hutang Pelanggan");

        jLabel63.setFont(new java.awt.Font("Montserrat Medium", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 204, 51));

        jLabel23.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 91, 91));
        jLabel23.setText("Rp0");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel62)
                            .addComponent(jLabel23)))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(212, 212, 212))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel62)
                .addGap(13, 13, 13)
                .addComponent(jLabel23)
                .addGap(11, 11, 11)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelLihatHutang.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 72, 560, 130));

        Hutang.add(panelLihatHutang, "panelLihatHutang");

        panelHutang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel6.setBackground(new java.awt.Color(255, 255, 255));
        topPanel6.setPreferredSize(new java.awt.Dimension(1250, 45));
        topPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel44.setText("Catat Hutang");
        topPanel6.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 15, -1, -1));

        panelHutang.add(topPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 56));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.CardLayout());

        berikanOnClick.setBackground(new java.awt.Color(255, 255, 255));
        berikanOnClick.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(234, 234, 234));
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(124, 124, 124));
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button redup.png"))); // NOI18N
        jLabel50.setText("Terima Pelunasan");
        jLabel50.setToolTipText("");
        jLabel50.setIconTextGap(10);
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        jPanel25.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        berikanOnClick.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 16, 543, 50));

        jPanel26.setBackground(new java.awt.Color(255, 91, 91));
        jPanel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button2.png"))); // NOI18N
        jLabel51.setText("Berikan Hutang");
        jLabel51.setToolTipText("");
        jLabel51.setIconTextGap(10);
        jPanel26.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        berikanOnClick.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 543, 50));
        berikanOnClick.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 82, 1140, -1));

        jLabel56.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel56.setText("Memberikan ke:");
        berikanOnClick.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 100, -1, -1));

        txtberikan.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        berikanOnClick.add(txtberikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 130, 1100, 40));

        jLabel57.setFont(new java.awt.Font("Montserrat Medium", 1, 16)); // NOI18N
        jLabel57.setText("Informasi Opsional");
        berikanOnClick.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 310, -1, -1));

        txtnominal.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        berikanOnClick.add(txtnominal, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 220, 1100, 40));

        txtcatatanhutang.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        berikanOnClick.add(txtcatatanhutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 380, 1100, 40));
        berikanOnClick.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 1140, -1));

        jLabel58.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel58.setText("Tanggal Hutang");
        berikanOnClick.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, -1, -1));

        jLabel59.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel59.setText("Memberikan sejumlah:");
        berikanOnClick.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 190, -1, -1));

        jLabel60.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel60.setText("Catatan:");
        berikanOnClick.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 350, -1, -1));

        jLabel61.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel61.setText("Jatuh Tempo");
        berikanOnClick.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, -1, -1));
        berikanOnClick.add(tglhutang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 590, 40));
        berikanOnClick.add(tgljatuhtempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 590, 40));

        jButton8.setBackground(new java.awt.Color(255, 188, 58));
        jButton8.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 12)); // NOI18N
        jButton8.setText("Simpan");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8jButton3ActionPerformed(evt);
            }
        });
        berikanOnClick.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 615, 1105, 40));

        jPanel9.add(berikanOnClick, "berikanOnClick");

        terimaOnClick.setBackground(new java.awt.Color(255, 255, 255));
        terimaOnClick.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(46, 231, 120));
        jPanel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button.png"))); // NOI18N
        jLabel48.setText("Terima Pelunasan");
        jLabel48.setToolTipText("");
        jLabel48.setIconTextGap(10);
        jPanel23.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        terimaOnClick.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 16, 543, 50));

        jPanel24.setBackground(new java.awt.Color(234, 234, 234));
        jPanel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel24MouseClicked(evt);
            }
        });
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(124, 124, 124));
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java_app1/images/Radio button redup.png"))); // NOI18N
        jLabel49.setText("Berikan Hutang");
        jLabel49.setToolTipText("");
        jLabel49.setIconTextGap(10);
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });
        jPanel24.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        terimaOnClick.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 543, 50));
        terimaOnClick.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 82, 1140, -1));

        jLabel52.setFont(new java.awt.Font("Montserrat Medium", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 0, 51));
        jLabel52.setText("Rp 0");
        terimaOnClick.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 200, -1, -1));

        jTextField7.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        terimaOnClick.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 130, 1100, 40));

        jLabel53.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel53.setText("Menerima dari:");
        terimaOnClick.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 100, -1, -1));

        jLabel54.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel54.setText("Sisa Hutang");
        terimaOnClick.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel55.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jLabel55.setText("Menerima sejumlah:");
        terimaOnClick.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jTextField8.setFont(new java.awt.Font("Montserrat Medium", 0, 28)); // NOI18N
        terimaOnClick.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 1100, 40));

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setFont(new java.awt.Font("Montserrat Medium", 0, 16)); // NOI18N
        jCheckBox3.setText(" Terima Pelunasan");
        jCheckBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        terimaOnClick.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jButton7.setBackground(new java.awt.Color(255, 188, 58));
        jButton7.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 12)); // NOI18N
        jButton7.setText("Simpan");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7jButton3ActionPerformed(evt);
            }
        });
        terimaOnClick.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 380, 1105, 40));

        jPanel9.add(terimaOnClick, "terimaOnClick");

        panelHutang.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 70, 1134, 680));

        Hutang.add(panelHutang, "panelHutang");

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

        btnUbah.setBackground(new java.awt.Color(255, 188, 58));
        btnUbah.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.setEnabled(false);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        lapbarang.add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 80, 70, 30));

        btnHapus.setBackground(new java.awt.Color(255, 188, 58));
        btnHapus.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setEnabled(false);
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        lapbarang.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 80, 80, 30));

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
        bersihkanTabelStok();
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
        
        CardLayout cla = (CardLayout) jPanel2.getLayout();
        cla.show(jPanel2, "penjualanOnClick");
        
        tabTransaksi.setBackground(new java.awt.Color(204, 204, 255));
        tabHutang.setBackground(new java.awt.Color(0, 140, 255));
        tabStok.setBackground(new java.awt.Color(0, 140, 255));
        
        hilangkanPesanError();
        bersihkanTabelStok();
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
        
        CardLayout panel = (CardLayout) (Hutang.getLayout());
        panel.show(Hutang, "panelLihatHutang");
        
        hilangkanPesanError();
        bersihkanTabelStok();
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



    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            CardLayout clayout = (CardLayout) Transaksi.getLayout();
            clayout.show(Transaksi, "panelPenjualan");

            refreshtabel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "pengeluaranOnClick");
        
        lblPelanggan.setEnabled(false);
        cbxPelanggan.setEnabled(false);
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "pengeluaranOnClick");
        
        lblPelanggan.setEnabled(false);
        cbxPelanggan.setEnabled(false);
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "penjualanOnClick");
        
        lblPelanggan.setEnabled(true);
        cbxPelanggan.setEnabled(true);
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "penjualanOnClick");
        
        lblPelanggan.setEnabled(true);
        cbxPelanggan.setEnabled(true);
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
        bersihkanTabelStok();
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
        btnHapus.setEnabled(true);
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
        bersihkanTabelStok();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked

    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        try {
            int opsi = JOptionPane.showConfirmDialog(null, "Semua data penjualan dan pembelian akan ikut terhapus. \nHapus data?",
                    "Hapus Data", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (opsi == JOptionPane.YES_OPTION) {
                sql = "DELETE FROM barang WHERE id_barang='" + id_barang + "'";
                stm = conn.createStatement();
                stm.executeUpdate(sql);
                CardLayout clayout = (CardLayout) jPanel3.getLayout();
                clayout.show(jPanel3, "lapbarang"); 
                update_tabelbarang();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                btnUbah.setEnabled(false);
                btnHapus.setEnabled(false);
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Data gagal dihapus", "Error", JOptionPane.ERROR_MESSAGE);
        }
        bersihkanTabelStok();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        CardLayout clayout = (CardLayout) Hutang.getLayout();
        clayout.show(Hutang, "panelHutang");
        
        CardLayout clayout2 = (CardLayout) jPanel9.getLayout();
        clayout2.show(jPanel9, "berikanOnClick");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        CardLayout clayout = (CardLayout) jPanel9.getLayout();
        clayout.show(jPanel9, "berikanOnClick");
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jPanel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel24MouseClicked
        CardLayout clayout = (CardLayout) jPanel9.getLayout();
        clayout.show(jPanel9, "berikanOnClick");
    }//GEN-LAST:event_jPanel24MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        CardLayout clayout = (CardLayout) jPanel9.getLayout();
        clayout.show(jPanel9, "terimaOnClick");
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        CardLayout clayout = (CardLayout) jPanel9.getLayout();
        clayout.show(jPanel9, "terimaOnClick");
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton7jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7jButton3ActionPerformed

    private void jButton8jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8jButton3ActionPerformed
        // TODO add your handling code here:
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String oranghutang = txtberikan.getText();
        String nominal = txtnominal.getText();
        String catatan = txtcatatanhutang.getText();
        String tanggalhutang = String.valueOf(fm.format(tglhutang.getDate()));
        String tempo = String.valueOf(fm.format(tgljatuhtempo.getDate()));
        if(!"".equals(oranghutang) & !"".equals(nominal) & !"".equals(catatan)){
           try {
                stm.executeUpdate("INSERT INTO peminjaman_hutang (nama_pelanggan, nominal_peminjaman, tanggal_hutang, jatuh_tempo, catatan) VALUES('"+oranghutang+"', '"+nominal+"', '"+tanggalhutang+"', "
                    + "'"+tempo+"', '"+catatan+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                updateTabelHutang();
                ClearTabelHutang();
                           }
           catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Semua data harus diisi terlebih dahulu!");
        }
    }//GEN-LAST:event_jButton8jButton3ActionPerformed

    private void txtCatatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCatatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCatatanActionPerformed

    private void cbxPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPelangganActionPerformed

    private void simpanBeliActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBeliActionPerformed1
        tranBeli();
        tranJual();
        trjual();
    }//GEN-LAST:event_simpanBeliActionPerformed1

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        CardLayout clayout = (CardLayout) Transaksi.getLayout();
        clayout.show(Transaksi, "cetaklap");
    }//GEN-LAST:event_jPanel7MouseClicked

    private void tbltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbltransaksiMouseClicked

    private void tblhutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhutangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblhutangMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "tblTransaksiPenjualan");
        
        txtJual.setText("");
        lblJual.setText("Rp. 0");
        
        simpanBeli.setEnabled(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        CardLayout clayout = (CardLayout) jPanel2.getLayout();
        clayout.show(jPanel2, "tblTransaksiPengeluaran");
        txtHrgBeli.setText("");
        txtJmlBeli.setText("");
        subpembelian.setText("");
        
        simpanBeli.setEnabled(false);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int harga_beli = Integer.parseInt(txtHrgBeli.getText());
        int jmlbeli = Integer.parseInt(txtJmlBeli.getText());
        int subtotal = jmlbeli * harga_beli;
        int row = tblbarangbeli.getSelectedRow();
        kd_barang = ((String) tblbarangbeli.getValueAt(row, 0));
        nama_barang = ((String) tblbarangbeli.getValueAt(row, 1));
         
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Harga Pokok");
        model.addColumn("Jumlah Beli");
        model.addColumn("Subtotal");
        tblpengeluaran.setModel(model);
        
        Object[] data = new Object[4];
                data[0] = nama_barang;
                data[1] = harga_beli;
                data[2] = jmlbeli;
                data[3] = subtotal;
                model.addRow(data);
                tblpengeluaran.setModel(model);
        if(!"".equals(nama_barang) & !"".equals(harga_beli) & !"".equals(jmlbeli)){
           try {
                stm.executeUpdate("INSERT INTO temp_barang (id_barang, nama_barang, harga_pokok, jumlah, subtotal) VALUES('"+kd_barang+"', '"+nama_barang+"', '"+harga_beli+"', '"+jmlbeli+"', "
                    + "'"+subtotal+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                selectKeranjang();
                totalkeluar();
                }
           catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        
        simpanBeli.setEnabled(true);
        CardLayout cla = (CardLayout) jPanel2.getLayout();
        cla.show(jPanel2, "pengeluaranOnClick");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJualActionPerformed
        int jmljual = Integer.parseInt(txtJual.getText());
        int harga = Integer.parseInt(hrgBarangJual);
        int totaljual = jmljual * harga;
        int row = tblbarangjual.getSelectedRow();
        kd_barang     = ((String) tblbarangjual.getValueAt(row, 0));
        nama_barang   = ((String) tblbarangjual.getValueAt(row, 1));
        satuan        = ((String) tblbarangjual.getValueAt(row, 3));
        hrgBarangJual = ((String) tblbarangjual.getValueAt(row, 4));
         
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah Barang");
        model.addColumn("Harga");
        model.addColumn("Subtotal");
        tblJual.setModel(model);
        
        Object[] data = new Object[4];
                data[0] = nama_barang;
                data[1] = jmljual;
                data[2] = harga;
                data[3] = totaljual;
                model.addRow(data);
                tblJual.setModel(model);
        if(!"".equals(nama_barang) & !"".equals(jmljual)){
           try {
                stm.executeUpdate("INSERT INTO temp_jual (id_barang, nama_barang, jumlah_beli, satuan, harga_jual, total) VALUES('"+kd_barang+"', '"+nama_barang+"', '"+jmljual+"', '"+satuan+"','"+harga+"', "
                    + "'"+totaljual+"')");
                JOptionPane.showMessageDialog(null, "Data Berhasil Diinput");
                selectjual();
                totaljual();
                }
           catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
        
        simpanBeli.setEnabled(true);
        CardLayout cla = (CardLayout) jPanel2.getLayout();
        cla.show(jPanel2, "penjualanOnClick");
    }//GEN-LAST:event_btnJualActionPerformed

    private void tblbarangbeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbarangbeliMouseClicked
        int row = tblbarangbeli.getSelectedRow();
        kd_barang = ((String) tblbarangbeli.getValueAt(row, 0));
        nama_barang = ((String) tblbarangbeli.getValueAt(row, 1));
    }//GEN-LAST:event_tblbarangbeliMouseClicked

    private void txtJmlBeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtJmlBeliFocusLost
//        int jmlbeli = Integer.parseInt(jTextField5.getText());
//        int subtotal = jmlbeli * Integer.parseInt(harga_satuan);
//        String subtotalstring = Integer.toString(subtotal);
//        
//        subpembelian.setText(subtotalstring);
    }//GEN-LAST:event_txtJmlBeliFocusLost

    private void txtJmlBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlBeliKeyReleased
        int harga_beli = Integer.parseInt(txtHrgBeli.getText());
        int jmlbeli = Integer.parseInt(txtJmlBeli.getText());
        int subtotal = jmlbeli * harga_beli;
        String subtotalstring = Integer.toString(subtotal);
        
        subpembelian.setText("Rp. " + subtotalstring);
    }//GEN-LAST:event_txtJmlBeliKeyReleased

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
//    txtTotal
    }//GEN-LAST:event_jPanel19MouseClicked

    private void txtHrgBeliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHrgBeliFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHrgBeliFocusLost

    private void txtHrgBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHrgBeliKeyReleased
        if(!txtHrgBeli.getText().isBlank()){
            int harga_beli = Integer.parseInt(txtHrgBeli.getText());
            int jmlbeli = Integer.parseInt(txtJmlBeli.getText());
            int subtotal = jmlbeli * harga_beli;
            String subtotalstring = Integer.toString(subtotal);

            subpembelian.setText("Rp. " + subtotalstring);
        }
            
        
        
    }//GEN-LAST:event_txtHrgBeliKeyReleased

    private void txtHrgBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHrgBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHrgBeliActionPerformed

    private void txtJualKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJualKeyReleased
        int jmljual = Integer.parseInt(txtJual.getText());
        int harga = Integer.parseInt(hrgBarangJual);
        int totaljual = jmljual * harga;
        String subtotaljual = Integer.toString(totaljual);
        
        lblJual.setText("Rp. " + totaljual);
    }//GEN-LAST:event_txtJualKeyReleased

    private void tblbarangjualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbarangjualMouseClicked
        int row = tblbarangjual.getSelectedRow();
        kd_barang     = ((String) tblbarangjual.getValueAt(row, 0));
        nama_barang   = ((String) tblbarangjual.getValueAt(row, 1));
        hrgBarangJual = ((String) tblbarangjual.getValueAt(row, 4));
    }//GEN-LAST:event_tblbarangjualMouseClicked

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
    private javax.swing.JPanel Change;
    private javax.swing.JPanel Hutang;
    private javax.swing.JPanel PanelLaporan;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JPanel berikanOnClick;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnJual;
    private javax.swing.JLabel btnStok;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btncetakstok;
    private javax.swing.JLabel btnhutang;
    private javax.swing.JComboBox<String> cbjenisbarang;
    private javax.swing.JComboBox<String> cbjenissatuan;
    private javax.swing.JComboBox<String> cbxPelanggan;
    private com.toedter.calendar.JDateChooser dateBeli;
    private javax.swing.JTextField hrgjual;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel lapbarang;
    private javax.swing.JLabel lblErrorHargaJual;
    private javax.swing.JLabel lblErrorJenisBarang;
    private javax.swing.JLabel lblErrorJenisSatuan;
    private javax.swing.JLabel lblErrorNamaBarang;
    private javax.swing.JLabel lblJenisSatuan;
    private javax.swing.JLabel lblJual;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblPelanggan;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel lppengeluaran;
    private javax.swing.JLabel lppenjualan;
    private javax.swing.JTextField nmbarang;
    private javax.swing.JPanel panelHutang;
    private javax.swing.JPanel panelLihatHutang;
    private javax.swing.JPanel panelOverview;
    private javax.swing.JPanel panelPenjualan;
    private javax.swing.JPanel pengeluaranOnClick;
    private javax.swing.JPanel penjualanOnClick;
    private javax.swing.JPanel pnlAktifHutang;
    private javax.swing.JPanel pnlAktifStok;
    private javax.swing.JPanel pnlAktifTransaksi;
    private javax.swing.JPanel sidebar;
    private javax.swing.JButton simpanBeli;
    private javax.swing.JPanel stok;
    private javax.swing.JLabel subpembelian;
    private javax.swing.JPanel tabHutang;
    private javax.swing.JPanel tabStok;
    private javax.swing.JPanel tabTransaksi;
    private javax.swing.JTable tblJual;
    private javax.swing.JPanel tblTransaksiPengeluaran;
    private javax.swing.JPanel tblTransaksiPenjualan;
    private javax.swing.JTable tblbarangbeli;
    private javax.swing.JTable tblbarangjual;
    private javax.swing.JTable tblhutang;
    private javax.swing.JTable tblpengeluaran;
    private javax.swing.JTable tblstok;
    private javax.swing.JTable tbltransaksi;
    private javax.swing.JPanel terimaOnClick;
    private javax.swing.JTextField tfKelolaJenis;
    private com.toedter.calendar.JDateChooser tglhutang;
    private com.toedter.calendar.JDateChooser tgljatuhtempo;
    private javax.swing.JPanel tmbhbarang;
    private javax.swing.JButton tmbhstok;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel topPanel1;
    private javax.swing.JPanel topPanel2;
    private javax.swing.JPanel topPanel3;
    private javax.swing.JPanel topPanel4;
    private javax.swing.JPanel topPanel5;
    private javax.swing.JPanel topPanel6;
    private javax.swing.JLabel trKeuntungan;
    private javax.swing.JLabel trPenjualan;
    private javax.swing.JLabel trpengeluaran;
    private javax.swing.JLabel ttlpengeluaran;
    private javax.swing.JLabel ttlpenjualan;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCatatan;
    private javax.swing.JTextField txtHrgBeli;
    private javax.swing.JTextField txtJmlBeli;
    private javax.swing.JTextField txtJual;
    private javax.swing.JTextField txtberikan;
    private javax.swing.JTextField txtcatatanhutang;
    private javax.swing.JTextField txtnominal;
    // End of variables declaration//GEN-END:variables
}

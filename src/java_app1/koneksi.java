package java_app1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class koneksi {

    Connection con;
    Statement stm;
    
    public void config(){
        try{
            String dbUrl    = "jdbc:mysql://localhost/ap1_bukuwarung";
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(dbUrl, username, password);
            stm = con.createStatement();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Koneksi gagal"+ e.getMessage());
        }
    }
    }
    


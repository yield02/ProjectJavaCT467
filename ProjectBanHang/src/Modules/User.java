package Modules;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class User {
	public static void main(String args[]) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/qldiem?" + "user=root");
			System.out.println("Noi ket thanh cong");
			
			String sql= "select * from sinhvien";
			Statement smt= conn.createStatement();
			ResultSet rs;
			
			try {
				rs= smt.executeQuery(sql);
				
				System.out.println("Truy van thanh cong");
				while (rs.next()) {
                    String id = rs.getString("mssv");
                    String ten = rs.getString("hoten");

                    System.out.println("ID: " + id + ", Ten: " + ten);
                }

               
                rs.close();
				
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (Exception ex) { 
			System.out.println("Noi ket khong thanh cong");
			ex.printStackTrace();
		}
	}
}

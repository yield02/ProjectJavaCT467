package DatabaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnect {
	public Connection conn;
	
	public DbConnect() {
		try {
//			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost/projectbanhang?" + "user=root");
//			System.out.println("Noi ket thanh cong");
		}
		catch(SQLException ex) {
			System.out.println("Noi ket khong thanh cong");
			System.out.println(ex.getMessage());
		}
	}
}

package Modules;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.Console;
import java.sql.*;

import DatabaseConnect.DbConnect;


public class User {
	private int id;
	private String name;
	private String password;
	private String phone;
	
	public User() {
		this.id = -1;
		this.name = null;
		this.password = null;
		this.phone = null;
	}
	
	public User(int id, String name, String password, String phone) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}
	
	public User(String name, String password) {
		this.id = -1;
		this.name = name;
		this.password = password;
		this.phone = null;
	}
	
	
	public int getId() {
		return this.id;
	}
	public void Show() {
		System.out.println("ID: "+ this.id);
		System.out.println("Name: " + this.name);
		System.out.println("Phone: " + this.phone);
	}
	
	public boolean checkUser(String username){
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String check;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("SELECT u_name FROM user WHERE u_name=?");
			stmt.setString(1, username);
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet(); 
		        while (rs.next()) {
                    check = rs.getString("u_name");
                    if(check!=null)
                    	return false;
                }
                return true;
		    } 
			else {
				return true;
			}
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage());
		    return false;
		}
	}
	
	
	public void SignUp() {
//		Đăng ký
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			String username;
			String password;
			String confirm_password;
			String phone;
			System.out.println("ĐĂNG KÝ");
			while(true){
				System.out.println("Nhập tên người dùng:");
				username = sc.next();
				if(!this.checkUser(username)) {
					System.out.println("Tài khoản tồn tại!!! Vui lòng nhập lại.");
				} else {
					break;
				}
			}
			System.out.print("Nhập số điện thoại:");
			phone = sc.next();
			while(true){
				System.out.print("Nhập mật khẩu:");
				password = sc.next();
				System.out.print("Nhập lại mật khẩu:");
				confirm_password = sc.next();
				if(!password.equals(confirm_password)) {
					System.out.println("Mật khẩu nhập lại không khớp!!! Vui lòng đăng ký lại.");
				} else {
					break;
				}
			}
			if(this.authencationSignUp(username, password, phone) == true) {
				break;
			}	
			
		}
	}
	
	
	
	public void Login() {
//		Đăng nhập
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			String username;
			String password;
			System.out.println("ĐĂNG NHẬP");
			System.out.print("Tài Khoản: ");
			username = sc.next();
			System.out.print("Mật khẩu: ");
			password = sc.next();
			if(this.authencation(username, password) == true) {
				break;
			}
			else {
				System.out.println("Đăng nhập thất bại!!! Vui lòng đăng nhập lại.");
			}
		}
		
		
//		DbConnect db = new DbConnect();
		
	}
	

	public void Logout() {
		this.id = -1;
		this.name = null;
		this.password = null;
		this.phone = null;
	}
	
	
	public void changePassword() {
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Nhập mật khẩu cũ: ");
			String oldpwd = sc.next();
			System.out.print("Nhập mật khẩu mới: ");
			String newpwd = sc.next();
			System.out.print("Xác nhận lại mật khẩu mới: ");
			String renewpwd = sc.next();
			
			if(!newpwd.equals(renewpwd)) {
				System.out.println("Mật khẩu xác thực sai vui lòng nhập lại!!");
				continue;
			}
			else {
				if(this.authencation(this.name, oldpwd) == true) {
					// Đổi mật khẩu;
					PreparedStatement stmt = null;
					DbConnect db = new DbConnect();
					try {
						stmt = db.conn.prepareStatement("update user set u_password=? WHERE u_name=? and u_password=?");
						stmt.setString(1, newpwd);
						stmt.setString(2, this.name);
						stmt.setString(3, oldpwd);
						stmt.executeUpdate();
						System.out.println("\n Đổi mật khẩu thành công!!!!");
						break;
					}
					catch (SQLException ex){    //xử lý ngoại lệ 
					    System.out.println("SQLException: " + ex.getMessage()); 
					    System.out.println("Đổi mật khẩu thất bại!!!!");
					    continue;
					}
				}
				else {
					System.out.println("Mật khẩu cũ không đúng!!!!");
				}
			}
		}
		
		
	}
	
	public void changeInformation() {
		Scanner sc = new Scanner(System.in);
		String newName;
		while(true) {
			System.out.print("Nhập tên mới: ");
			newName = sc.next();
			if(!this.checkUser(newName)) {
				System.out.println("Tài khoản đã tồn tại, hãy nhập tên khác!!");
				continue;
			}
			break;
		}
		
		System.out.print("Số điện thoại mới: ");
		String newPhone = sc.next();
		
		PreparedStatement stmt = null;
		DbConnect db = new DbConnect();
		try {
			stmt = db.conn.prepareStatement("update user set u_name=?, u_phone=? WHERE u_name=?");
			stmt.setString(1, newName);
			stmt.setString(2, newPhone);
			stmt.setString(3, this.name);
			stmt.executeUpdate();
			this.name = newName;
			this.phone = newPhone;
			System.out.println("\n Đổi thông tin thành công!!!! \n");
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		    System.out.println("\n Đổi thông tin thất bại!!!! \n");
		}
	}
	
	
	
	public boolean authencation(String name, String password) {
		this.name = name;
		this.password = password;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("SELECT * FROM user WHERE u_name=? and u_password=?");
			stmt.setString(1, name);
			stmt.setString(2, password);
			stmt.executeQuery();
			rs = stmt.getResultSet();
			if (rs.next()) {
                this.id = rs.getInt("u_id");
                this.phone = rs.getString("u_phone");
		        return true;
		    } 
			else {
				return false;
			}
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage());
		    return false;
		}
		finally { 
		    //giải phóng tài nguyên khi không sử dụng nữa 
		    if (rs != null) { 
		        try { 
		            rs.close(); 
		        } catch (SQLException sqlEx) { } //đoạn mã xử lý ng/lệ 
		 
		        rs = null; 
		    } 
		 
		    if (stmt != null) { 
		        try { 
		            stmt.close(); 
		        } catch (SQLException sqlEx) { } //đoạn mã xử lý ng/lệ 
		 
		        stmt = null; 
		    } 
		}
	}
	public boolean authencationSignUp(String name, String password, String phone) {
		this.name = name;
		this.password = password;
		this.phone =  phone;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("INSERT INTO user(u_name, u_password, u_phone) VALUES(?,?,?)");
			stmt.setString(1, name);
			stmt.setString(2, password);
			stmt.setString(3, phone);
			stmt.executeUpdate();
		    rs = stmt.getResultSet(); 
            System.out.println("Đăng ký thành công");
		    return true;
		
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
		finally { 
		    //giải phóng tài nguyên khi không sử dụng nữa 
		    if (rs != null) { 
		        try { 
		            rs.close(); 
		        } catch (SQLException sqlEx) { } //đoạn mã xử lý ng/lệ 
		 
		        rs = null; 
		    } 
		 
		    if (stmt != null) { 
		        try { 
		            stmt.close(); 
		        } catch (SQLException sqlEx) { } //đoạn mã xử lý ng/lệ 
		 
		        stmt = null; 
		    } 
		} 
		return false;
	}
	
	
}

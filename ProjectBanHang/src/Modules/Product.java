package Modules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DatabaseConnect.DbConnect;

public class Product {
	private int id;
	private String name;
	private double price;
	private String description;
	
	public Product() {
		this.id = -1;
		this.name = null;
		this.price = 0;
		this.description = null;
	}
	
	public Product(int id, String name, double price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public void Show() {
		System.out.println("----------------------------------------------------------------");
		System.out.println("Thông Tin Sản Phẩm");
		System.out.println("ID Sản phẩm: " + this.id);
		System.out.println("Tên sản phẩm: " + this.name);
		System.out.println("Giá sản phẩm: " + String.format("%,.0f", this.price) + " VND");
		System.out.println("Mô tả sản phẩm: " + this.description);
		System.out.println("----------------------------------------------------------------");
	}
	
	public ArrayList getAll() {
		
		ArrayList<Product> listProduct = new ArrayList<>();
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("SELECT * FROM product");
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        int length = 0;
		        while (rs.next()) {
		        	Product index = new Product(rs.getInt("p_id"), rs.getString("p_name"), rs.getDouble("p_price"), rs.getString("p_description"));
		        	listProduct.add(index);
		        	length++;
                }
		        return listProduct;
		    } 
			else {
				System.out.println("Đăng nhập thất bại");
				return listProduct;
			}
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
		return listProduct; 
	}
	
}

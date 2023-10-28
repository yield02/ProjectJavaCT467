package Modules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import DatabaseConnect.DbConnect;

public class DetailOrder {
	private int id;
	private int orderId;
	private int productId;
	private int amount;
	
	
	public DetailOrder() {
		this.id = -1;
		this.orderId = -1;
		this.productId = -1;
		this.amount = 0;
	}
	
	public DetailOrder(int id, int orderId, int productId, int amount) {
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.amount = amount;
	}
	
	public void handle(int id_order) {
		Scanner sc = new Scanner(System.in);
		boolean c= true;
		while(c == true) {
			System.out.println("");
			System.out.println("Thực hiện: ");
			System.out.println("1. Thêm sản phẩm vào đơn hàng. ");
			System.out.println("2. Xoá sản phẩm khỏi đơn hàng. ");
			System.out.println("3. Xem tất cả sản phẩm của giỏ hàng.");
			System.out.println("4. Thoát.");
			System.out.println("Vui lòng chọn thao tác:");
			int action = sc.nextInt();
			switch(action) {
				case 1:
					this.addProductToOrder(id_order);
					break;
				case 2:
					this.deleteProductFromOrder(id_order);
					break;
				case 3:
					this.showDetail(id_order);
					break;
				case 4:
					c = false;
					break;
				default:
					System.out.println("Số "+ action +" không có trong bảng chức năng này!. Vui lòng chọn lại");
					this.handle(id_order);
					break;
			}
		}
	}
	
	public void addProductToOrder(int id_order) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Scanner sc = new Scanner(System.in);
		int amount=0;
		int id_product=-1;
		
		int count=0;
		while(count==0) {
			try {
				System.out.println("Nhập mã số sản phẩm:");
				id_product = sc.nextInt();
				DbConnect db = new DbConnect();
				PreparedStatement stmt1 = db.conn.prepareStatement("select * from product where p_id = ?;");
				stmt1.setInt(1, id_product);
				
				if (stmt1.executeQuery() != null) { 
					ResultSet rs1 = stmt1.getResultSet();
					while (rs1.next()) {
						count++;
					}
				}
				if(count==0) System.out.println("Id sản phẩm không tồn tại!!");
			}
			catch (SQLException ex){    //xử lý ngoại lệ 
				System.out.println("SQLException: " + ex.getMessage()); 
			}	
		}
		while(amount<=0) {
			System.out.println("Nhập số lượng sản phẩm:");
			amount = sc.nextInt();
			if(amount<=0) System.out.println("Số lượng sản phẩm phải lớn hơn 0!!");
		}
		
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("INSERT INTO detailorder(d_order, d_product, d_amount) VALUES(?,?,?)");
			stmt.setInt(1, id_order);
			stmt.setInt(2, id_product);
			stmt.setInt(3, amount);
			stmt.executeUpdate();
		    rs = stmt.getResultSet(); 
            System.out.println("Thêm sản phẩm vào đơn hàng thành công");
		    return ;
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
	
	public void showDetail(int id_order) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int stt = 1;
		int count=0;
		float sum= 0;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * from DetailOrder JOIN Product ON d_product= p_id WHERE d_order= ?; ");
			stmt.setInt(1, id_order);
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Chi tiết đơn hàng");
		        System.out.println("----------------------------------------------------------------------------------------------------------");
		        while (rs.next()) {
		        	count++;
	                String product = rs.getString("p_name");
	                double price = rs.getDouble("p_pri"
	                		+ "ce");
	                int sl= rs.getInt("d_amount");
	                int maso= rs.getInt("d_id");
	                System.out.println("STT: " + stt + ", Mã số: " + maso + ", product: " + product +", giá: "+ String.format("%,.0f", price) + " VND"+  ", số lượng: "+ sl);
	                sum+= price*sl;
	                stt++;
	            }
		        if(count==0) {
		        	System.out.println("Chưa có sản phẩm nào cả!!!");
		        }else System.out.println("Tổng tất cả: "+ String.format("%,.0f", sum) + " VND");
		        System.out.println("----------------------------------------------------------------------------------------------------------");
			    return ;
		    } else {
		    	System.out.println("Chưa có đơn hàng nào cả!!!");
		    }
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
	
	public void deleteProductFromOrder(int id_order) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		DbConnect db = new DbConnect();
		Scanner sc = new Scanner(System.in);
		int count=0;
		int check=1;
		int maso=-1;
		
		while(count==0) {
			System.out.println("Nhập mã số sản phẩm muốn xoá (hoặc 0 để thoát):");
			maso = sc.nextInt();
			if(maso!=0) {
				try {
					PreparedStatement stmt0 = db.conn.prepareStatement("select * from DetailOrder JOIN Product ON d_product= p_id WHERE d_order= ? and d_id=?; ");
					stmt0.setInt(1, id_order);
					stmt0.setInt(2, maso);
					if (stmt0.executeQuery() != null) { 
						ResultSet rs0 = stmt0.getResultSet();
						while (rs0.next()) {
							count++;
						}
					}
				} catch (SQLException ex){    //xử lý ngoại lệ 
					System.out.println("SQLException: " + ex.getMessage()); 
				}
				if(count==0) {
					System.out.println("Không có sản phẩm với mã số bạn nhập");
				}
			}else return;
		}
		
		
		System.out.println("Bạn có chắc chắn muốn xoá? (1 để tiếp tục, 0 thoát)");
		check = sc.nextInt();
		
		if(check==1) {
			try {
				
				stmt = db.conn.prepareStatement("delete from DetailOrder where d_id= ?;");
				stmt.setInt(1, maso);
				stmt.executeUpdate();
			    rs = stmt.getResultSet(); 
	            System.out.println("Xoá sản phẩm khỏi đơn hàng thành công");
			    return ;
			}
			catch (SQLException ex){    //xử lý ngoại lệ 
			    System.out.println("SQLException: " + ex.getMessage()); 
			}
		}else return ;
	}
}

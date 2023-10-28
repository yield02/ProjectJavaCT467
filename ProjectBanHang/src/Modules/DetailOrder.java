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
					this.deleteProductFromOrder();
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
		Scanner sc = new Scanner(System.in);
		int amount=1;
		int id_product;
		System.out.println("Nhập mã số sản phẩm:");
		id_product = sc.nextInt();
		System.out.println("Nhập số lượng sản phẩm:");
		amount = sc.nextInt();
		ResultSet rs = null;
		PreparedStatement stmt = null;
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
		float sum= 0;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * from DetailOrder JOIN Product ON d_product= p_id WHERE d_order= ?; ");
			stmt.setInt(1, id_order);
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Chi tiết đơn hàng");
		        System.out.println("---------------------------------------------------------------------------");
		        while (rs.next()) {
	                String product = rs.getString("p_name");
	                double price = rs.getDouble("p_price");
	                int sl= rs.getInt("d_amount");
	                int maso= rs.getInt("d_id");
	                System.out.println("STT: " + stt + ", Mã số: " + maso + ", product: " + product +", giá: "+ String.format("%,.0f", price) + " VND"+  ", số lượng: "+ sl);
	                sum+= price*sl;
	                stt++;
	            }
		        System.out.println("Tổng tất cả: "+ String.format("%,.0f", sum) + " VND");
		        System.out.println("---------------------------------------------------------------------------");
			    return ;
		    } else {
		    	System.out.println("Chưa có đơn hàng nào cả!!!");
		    }
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
	
	public void deleteProductFromOrder() {
		Scanner sc = new Scanner(System.in);
		int check=1;
		int maso;
		System.out.println("Nhập mã số sản phẩm muốn xoá:");
		maso = sc.nextInt();
		System.out.println("Bạn có chắc chắn muốn xoá? (1 để tiếp tục, 0 thoát)");
		check = sc.nextInt();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(check==1) {
			try {
				DbConnect db = new DbConnect();
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

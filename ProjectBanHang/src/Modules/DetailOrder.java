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
		while(c= true) {
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
//					this.deleteProductFromOrder();
					break;
				case 3:
					this.showDetail();
					break;
				case 4:
					Order order = new Order();
					User user = new User();
					order.action(user);
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
	
	public void showDetail() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int stt = 1;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * from DetailOrder JOIN Product ON d_product= p_id; ");
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Tất cả detail");
		        while (rs.next()) {
	                int id = rs.getInt("d_id");
	                String product = rs.getString("p_name");
	                double price = rs.getDouble("p_price");
	                int sl= rs.getInt("d_amount");
	                System.out.println("--------Đơn hàng thứ "+ stt + "---------");
	                System.out.println("ID: " + id + ", product: " + product +", giá: "+ price+  ", số lượng: "+ sl);
	                System.out.println("-------------------------------");
	                stt++;
	            }
			    return ;
		    } else {
		    	System.out.println("Chưa có đơn hàng nào cả!!!");
		    }
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
}

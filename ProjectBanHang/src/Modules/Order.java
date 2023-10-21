package Modules;
import java.util.ArrayList;
import java.util.Scanner;
import DatabaseConnect.DbConnect;
import java.io.Console;
import java.sql.*;
import java.sql.ResultSet;


public class Order {
	private int id;
	private int userId;
	
	public Order() {
		this.id = -1;
		this.userId = -1;
	}
	
	public Order(int id, int userId) {
		this.id = id;
		this.userId = userId;
	}
	
	public void action(User user) {
		boolean c = true;
		while(c == true) {
			System.out.println("");
			System.out.println("Bảng Chức Năng Giỏ Hàng:");
			System.out.println("1. Xem tất cả đơn hàng của bạn. ");
			System.out.println("2. Xem chi tiết đơn hàng. ");
			System.out.println("3. Thêm một đơn hàng mới.");
			System.out.println("4. Xoá một đơn hàng");
			System.out.println("5. Thoát khỏi giỏ hàng.");
			System.out.println("Vui lòng chọn thao tác:");
			Scanner sc = new Scanner(System.in);
			int action = sc.nextInt();
			switch(action) {
				case 1:
					this.showAll();
					break;
				case 2:
					this.showOrder();
					break;
				case 3:
					this.addNewOrder(user);
					break;
				case 4:
					this.deleteOrder();
					break;
				case 5:
					c = false;
					return ;
				default:
					System.out.println("Số "+ action +" không có trong bảng chức năng này!. Vui lòng chọn lại");
					this.action(user);
					break;
			}
		}
	}
	
	public void showAll() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int stt = 1;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * FROM `order`");
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Tất cả đơn hàng");
		        while (rs.next()) {
	                int id = rs.getInt("o_id");
	                int idUser = rs.getInt("o_user");
	                System.out.println("-------Đơn hàng thứ "+ stt + "-------");
	                System.out.println("ID: " + id + ", user: " + idUser);
	                System.out.println("----------------------------");
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

	public void showOrder() {
		Scanner sc = new Scanner(System.in);
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int stt = 1;
		System.out.println("Nhập id của đơn hàng: ");
		int id_order = sc.nextInt();
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * FROM `order` WHERE o_id= ?");
			stmt.setInt(1, id_order);
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Đơn hàng của bạn: ");
		        while (rs.next()) {
	                int id = rs.getInt("o_id");
	                int idUser = rs.getInt("o_user");
	                System.out.println("----------------------------");
	                System.out.println("ID: " + id + ", user: " + idUser);
	                System.out.println("----------------------------");
	                stt++;
	            }
		        
//		        Xử lý tiếp ở DetailOrder
		        
		        DetailOrder dtorder = new DetailOrder();
		        dtorder.handle(id_order);
		        return ;
		    } else {
		    	System.out.println("Không tìm thấy đơn hàng!!!");
		    }
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
	
	public void addNewOrder(User user) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("INSERT INTO `order`(o_user) VALUES(?)");
			stmt.setInt(1, user.getId());
			stmt.executeUpdate();
		    rs = stmt.getResultSet(); 
            System.out.println("Thêm đơn hàng thành công");
		    return ;
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}
	
	public void deleteOrder() {
		
	}




	
}

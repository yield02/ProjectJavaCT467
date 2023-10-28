package Modules;
import java.util.ArrayList;
import java.util.Scanner;
import DatabaseConnect.DbConnect;
import java.io.Console;
import java.sql.*;


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
					this.showAll(user);
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
					break;
				default:
					System.out.println("Số "+ action +" không có trong bảng chức năng này!. Vui lòng chọn lại");
					this.action(user);
					break;
			}
		}
	}
	
	public void showAll(User userin) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int stt = 1;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * FROM `order` JOIN user ON o_user = u_id where o_user =?;");
			stmt.setInt(1, userin.getId());
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Tất cả đơn hàng");
		        while (rs.next()) {
	                int id = rs.getInt("o_id");
	                String user = rs.getString("u_name");
	                System.out.println("-------Đơn hàng thứ "+ stt + "-------");
	                System.out.println("ID: " + id + ", user: " + user);
	                System.out.println("----------------------------");
	                stt++;
	            }
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
			stmt = db.conn.prepareStatement("select * FROM `order` JOIN user ON o_user = u_id WHERE o_id= ?");
			stmt.setInt(1, id_order);
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Đơn hàng của bạn: ");
		        while (rs.next()) {
	                int id = rs.getInt("o_id");
	                String user = rs.getString("u_name");
	                System.out.println("----------------------------");
	                System.out.println("ID: " + id + ", user: " + user);
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
		Scanner sc = new Scanner(System.in);
		int check=1;
		int maso;
		System.out.println("Nhập id đơn hàng muốn xoá:");
		maso = sc.nextInt();
		System.out.println("Bạn có chắc chắn muốn xoá? (1 để tiếp tục, 0 thoát)");
		check = sc.nextInt();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(check==1) {
//			try {
//				DbConnect db = new DbConnect();
//				stmt = db.conn.prepareStatement("START TRANSACTION;\r\n"
//						+ "DELETE FROM detailorder WHERE d_order = ?;\r\n"
//						+ "DELETE FROM `order` WHERE o_id = ?;\r\n"
//						+ "COMMIT;");
//				stmt.setInt(1, maso);
//				stmt.setInt(2, maso);
//				stmt.executeUpdate();
//			    rs = stmt.getResultSet(); 
//	            System.out.println("Xoá đơn hàng thành công");
//			    return ;
//			}
//			catch (SQLException ex){    //xử lý ngoại lệ 
//			    System.out.println("SQLException: " + ex.getMessage()); 
//			}
			Connection conn = null;
	        try {
	            DbConnect db = new DbConnect();
	            conn = db.conn;
	            conn.setAutoCommit(false); // Bắt đầu giao dịch

	            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM detailorder WHERE d_order = ?;");
	            stmt1.setInt(1, maso);
	            stmt1.executeUpdate();

	            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM `order` WHERE o_id = ?;");
	            stmt2.setInt(1, maso);
	            stmt2.executeUpdate();

	            conn.commit(); // Kết thúc giao dịch
	            System.out.println("Xoá đơn hàng thành công");
	        } catch (SQLException ex) {
	            try {
	                if (conn != null) {
	                    conn.rollback(); // Rollback giao dịch nếu có lỗi
	                }
	            } catch (SQLException rollbackEx) {
	                System.out.println("Lỗi khi rollback giao dịch: " + rollbackEx.getMessage());
	            }
	            System.out.println("SQLException: " + ex.getMessage());
	        } finally {
	            try {
	                if (conn != null) {
	                    conn.setAutoCommit(true); // Trả lại trạng thái mặc định
	                    conn.close();
	                }
	            } catch (SQLException closeEx) {
	                System.out.println("Lỗi khi đóng kết nối: " + closeEx.getMessage());
	            }
	        }
		}else return ;
	}




	
}

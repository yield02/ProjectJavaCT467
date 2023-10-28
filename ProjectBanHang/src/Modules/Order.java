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
					this.showOrder(user);
					break;
				case 3:
					this.addNewOrder(user);
					break;
				case 4:
					this.deleteOrder(user);
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
		int count=0;
		try {
			DbConnect db = new DbConnect();
			stmt = db.conn.prepareStatement("select * FROM `order` JOIN user ON o_user = u_id where o_user =?;");
			stmt.setInt(1, userin.getId());
			if (stmt.executeQuery() != null) { 
		        rs = stmt.getResultSet();
		        System.out.println("Tất cả đơn hàng");
		        while (rs.next()) {
		        	count++;
	                int id = rs.getInt("o_id");
	                String user = rs.getString("u_name");
	                System.out.println("-------Đơn hàng thứ "+ stt + "-------");
	                System.out.println("ID: " + id + ", user: " + user);
	                System.out.println("----------------------------");
	                stt++;
	            }
		        if(count==0) {
		        	System.out.println("Bạn chưa có đơn hàng nào cả!!!");
		        }
		    } else {
		    	System.out.println("Chưa có đơn hàng nào cả!!!");
		    }
		}
		catch (SQLException ex){    //xử lý ngoại lệ 
		    System.out.println("SQLException: " + ex.getMessage()); 
		}
	}

	public void showOrder(User userid) {
		Scanner sc = new Scanner(System.in);
		ResultSet rs0 = null;
		ResultSet rs = null;
		PreparedStatement stmt0 = null;
		PreparedStatement stmt = null;
		int stt = 1;
		System.out.println("Nhập id của đơn hàng: ");
		int id_order = sc.nextInt();
		int count=0;
		float sum= 0;
		try {
			DbConnect db = new DbConnect();
			stmt0 = db.conn.prepareStatement("select * FROM `order` JOIN user ON o_user = u_id where o_user =? and o_id=?;");
			stmt0.setInt(1, userid.getId());
			stmt0.setInt(2, id_order);
			if (stmt0.executeQuery() != null) { 
		        rs0 = stmt0.getResultSet();
		        while (rs0.next()) {
		        	count++;
	            }
		        if(count==0) {
		        	System.out.println("Đơn hàng không tồn tại!!!");
		        }else {
		        	stmt = db.conn.prepareStatement("select * from DetailOrder JOIN Product ON d_product= p_id WHERE d_order= ?; ");
					stmt.setInt(1, id_order);
					
					if (stmt.executeQuery() != null) { 
				        rs = stmt.getResultSet();
				        int dem=0;
				        System.out.println("Chi tiết đơn hàng");
				        System.out.println("----------------------------------------------------------------------------------------------------------");
				        while (rs.next()) {
				        	dem++;
			                String product = rs.getString("p_name");
			                double price = rs.getDouble("p_pri"
			                		+ "ce");
			                int sl= rs.getInt("d_amount");
			                int maso= rs.getInt("d_id");
			                System.out.println("STT: " + stt + ", Mã số: " + maso + ", product: " + product +", giá: "+ String.format("%,.0f", price) + " VND"+  ", số lượng: "+ sl);
			                sum+= price*sl;
			                stt++;
			            }
				        if(dem==0) {
				        	System.out.println("Chưa có sản phẩm nào cả!!!");
				        }else System.out.println("Tổng tất cả: "+ String.format("%,.0f", sum) + " VND");
				        System.out.println("----------------------------------------------------------------------------------------------------------");
					    
				        DetailOrder dtorder = new DetailOrder();
				        dtorder.handle(id_order);
				        return ;
				    } else {
				    	System.out.println("Chưa có đơn hàng nào cả!!!");
				    }
		        }
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
	
	public void deleteOrder(User user) {
		DbConnect db = new DbConnect();
		Scanner sc = new Scanner(System.in);
		int check=1;
		int maso=-1;
		int count=0;
		
		while(count==0) {
			System.out.println("Nhập id đơn hàng muốn xoá (hoặc 0 để thoát):");
			maso = sc.nextInt();
			if(maso!=0) {
				try {
					PreparedStatement stmt0 = db.conn.prepareStatement("select * FROM `order` JOIN user ON o_user = u_id where o_user =? and o_id=?;");
					stmt0.setInt(1, user.getId());
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
					System.out.println("Không có đơn hàng với mã số bạn nhập");
				}
			}else return;
		}
		
		System.out.println("Bạn có chắc chắn muốn xoá? (1 để tiếp tục, 0 thoát)");
		check = sc.nextInt();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(check==1) {
			Connection conn = null;
	        try {
	            
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

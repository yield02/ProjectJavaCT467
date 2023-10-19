import java.util.ArrayList;
import java.util.Scanner;

import Modules.Product;
import Modules.User;

public class method {
//	Đăng ký đăng nhập;
	public void authencation(User user) {
		Scanner sc = new Scanner(System.in);
		int acc;
		while(true) {
			System.out.print("Bạn đã có tài khoản chưa?(0: Không/1: có)");
			acc = sc.nextInt();
			
			
			if(acc == 1) { 
				user.Login();
				break;
			}
			else if(acc == 0) {
				user.SignUp();			
				this.authencation(user);
				break;
			}
			else {
				System.out.println("Chỉ nhập giá trị 0 hoặc 1");
			}
		}
	}

//	Show menu
	
	public void showmenu(User user) {
		boolean c = true;
		while(c == true) {
			System.out.println("");
			System.out.println("Bảng Chức Năng:");
			System.out.println("1. Xem thông tin cá nhân.");
			System.out.println("2. Chỉnh sửa thông tin.");
			System.out.println("3. Đổi mật khẩu.");
			System.out.println("4. Xem danh sách sản phẩm.");
			System.out.println("5. Thêm sản phẩm vào giỏ.");
			System.out.println("6. Xem danh sách đơn hàng.");
			System.out.println("7. Giỏ hàng.");
			System.out.println("8. Đăng xuất.");
			System.out.println("Vui lòng chọn thao tác:");
			Scanner sc = new Scanner(System.in);
			int action = sc.nextInt();
			switch(action) {
				case 1:
					user.Show();
					break;
				case 2:
					user.changeInformation();
					break;
				case 3:
					user.changePassword();
					break;
				case 4:
					Product product = new Product();
					System.out.println("\n Danh Sách Sản Phẩm \n");
					ArrayList<Product> listProduct = product.getAll();
					for(int i = 0; i < listProduct.size(); i++) {
						listProduct.get(i).Show();
					}
					productAction(listProduct);
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					
					break;
				
				case 8:
					user.Logout();
					System.out.println("Đăng xuất thành công.");
					c = false;
					break;
					
				default:
					System.out.println("Số "+ action +" không có trong bảng chức năng này!. Vui lòng chọn lại");
					this.showmenu(user);
					break;
			}
		}
	}
	
	public void productAction(ArrayList<Product> listProduct) {
		boolean c = true;
		Scanner sc = new Scanner(System.in);
		while(c) {
			System.out.println("\n Bảng Chức Năng \n");
			System.out.println("1. Tìm kiếm sản phẩm theo tên.");
			System.out.println("2. Lọc sản phẩm theo giá.");
			System.out.println("3. Xem danh sách sản phẩm.");
			System.out.println("4. Thoát bảng chức năng.");
			System.out.println("Vui lòng chọn thao tác:");
			int action = sc.nextInt();
			switch(action) {
			 	case 1:
			 		boolean find = false;
			 		System.out.print("Nhập tên sản phẩm: ");
			 		String name = sc.next();
			 		for(int i = 0; i < listProduct.size(); i++) {
			 			if(listProduct.get(i).getName().contains(name)) {
			 				find = true;
			 				listProduct.get(i).Show();
			 			}
					}
			 		if(find == false) {
		 				System.out.println("Không tìm thấy sản phẩm!!");
		 			}
			 		break;
			 		
			 	case 2:
			 		find = false;
			 		System.out.print("Nhập giá bé nhất: ");
			 		int giabenhat = sc.nextInt();
			 		System.out.print("Nhập giá lớn nhất: ");
			 		int gialonnhat = sc.nextInt();
			 		for(int i = 0; i < listProduct.size(); i++) {
			 			if(listProduct.get(i).getPrice() <= gialonnhat && listProduct.get(i).getPrice() >= giabenhat) {
			 				find = true;
			 				listProduct.get(i).Show();
			 			}
					}
			 		if(find == false) {
		 				System.out.println("Không tìm thấy sản phẩm!!");
		 			}
			 		break;
			 	case 3:
			 		break;
			 	case 4:
			 		c = false;
			 		break;
			 	default:
			 		System.out.println("Số "+ action +" không có trong bảng chức năng này!. Vui lòng chọn lại");
					this.productAction(listProduct);
					break;
			}
		}
		
		
	}



}

import java.util.Scanner;

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
			System.out.println("5. Xem danh sách đơn hàng.");
			System.out.println("6. Giỏ hàng.");
			System.out.println("7. Đăng xuất.");
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
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
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



}

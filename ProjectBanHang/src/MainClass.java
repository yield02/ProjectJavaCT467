import java.util.Scanner;

public class MainClass {
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int acc;
		while(true) {
			System.out.print("Bạn đã có tài khoản chưa?(0: Không/1: có)");
			acc = sc.nextInt();
			if(acc == 1) { 
//				Đăng nhập
				String username;
				String password;
				System.out.println("ĐĂNG NHẬP");
				System.out.print("Tài Khoản:");
//				username = sc.nextString();
				
				break;
			}
			else if(acc == 0) {
//				Tạo tài khoản
				break;
			}
			else {
				System.out.println("Chỉ nhập giá trị 0 hoặc 1");
			}
		}
	}
}


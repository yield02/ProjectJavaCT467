import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import DatabaseConnect.DbConnect;
import Modules.Order;
import Modules.User;

public class MainClass {
	public static void main(String[] args){	
		method mt = new method();
		User user = new User();
		Order order = new Order();
//		Login/Sigup;
		
		mt.authencation(user);
//		Đăng nhập xong
//		Show menu action
		mt.showmenu(user, order);
	}
	
	
}


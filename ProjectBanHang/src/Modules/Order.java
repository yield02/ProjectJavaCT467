package Modules;

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
	
}

package Modules;

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
}

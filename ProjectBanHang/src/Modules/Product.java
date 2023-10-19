package Modules;

public class Product {
	private int id;
	private String name;
	private double price;
	private String description;
	
	public Product() {
		this.id = -1;
		this.name = null;
		this.price = 0;
		this.description = null;
	}
	
	public Product(int id, String name, double price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
}

package server;

public class Product {
	private int id;		
	private String name;					
	private int price;					
	private int quantity;
	private int owner_id;			
	private String description;
	

	public Product(int id, String name, int price, int quantity, int owner_id, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.owner_id = owner_id;
		this.description = description;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getOwnerId() {
		return owner_id;
	}
	public void setOwnerId(int owner_id) {
		this.owner_id = owner_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

package pointOfSale;

public class Item {
	private String name;
	private double price;
	private String description;
	
	public Item(String name, double price, String description) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public double getTotalPrice() {
		return this.getPrice();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void display() {
		System.out.printf("Price: %d, Name: %s, Description: %s", 
				this.getPrice(), this.name, this.description);
	}
}

/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

public class Item {
	private final String name;
	private final double price;
	private final String description;
	
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
	
	@Override
	public int hashCode() {
		return java.util.Objects.hash(name, price, description);
	}
}

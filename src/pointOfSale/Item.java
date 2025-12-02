/*
 * Author: John Cedeno & Jacob Eurglunes & John Cornett 
 * 
 */

package pointOfSale;

public class Item extends MenuComponent {
	private final double price;
	private final String description;
	
	public Item(String name, double price, String description) {
		super(name);
		this.description = description;
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	@Override
        public double getTotalPrice() {
        return price;
        }
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override
    public void display() {
        System.out.printf("Price: %.2f, Name: %s, Description: %s%n", getPrice(), name, description);
    }
	
	@Override
	public int hashCode() {
		return java.util.Objects.hash(name, price, description);
	}
	
	
	// Increases quantity of line item
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof Item)) return false;
	    Item other = (Item) obj;
	    return name.equals(other.name)
	            && price == other.price
	            && description.equals(other.description);
	}

}

/*
 * Author: John Cedeno & Jacob Eurglunes & John Cornett 
 * 
 */

package pointOfSale;

public class Item extends MenuComponent {
	private final double price;
	
	public Item(String name, double price) {
		super(name);
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
	
	@Override
    public void display() {
        System.out.printf(this.toString());
    }
	
	@Override
	public int hashCode() {
		return java.util.Objects.hash(name, price);
	}
	
	@Override
    public String toString() {
        return String.format("Price: %.2f, Name: %s", getPrice(), name);
    }
	
	// Increases quantity of line item
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof Item)) return false;
	    Item other = (Item) obj;
	    return name.equals(other.name)
	            && price == other.price;
	}

}

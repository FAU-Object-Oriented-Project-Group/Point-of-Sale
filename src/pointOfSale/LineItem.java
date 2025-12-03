package pointOfSale;

public class LineItem {
	private final Item item;
	private final Integer quantity;
	
	public LineItem(Item item, Integer quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public Item getItem() {
		return item;
	}
	
	public double getSubtotal() {
		return item.getTotalPrice() * getQuantity();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Quantity: ");
		sb.append(quantity);
		sb.append(" ");
		sb.append(item.toString());
		
		return sb.toString();
	}
}

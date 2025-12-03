package pointOfSale;

import java.util.*;

public class Category extends MenuComponent {
	private Set<Item> items;
	public Category(String name) {
		super(name);
		items = new HashSet<>();
	}

	public void add(Item item) {
		items.add(item);
	}
	
	public void remove(Item item) {
		items.remove(item);
	}
	@Override
	public double getTotalPrice() {
		double acc = 0;
		for (Item item : items) {
			acc += item.getPrice();
		}
		return acc;
	}

	@Override
	public void display() {
		for (Item item : items) {
			item.display();
		}
	}

}

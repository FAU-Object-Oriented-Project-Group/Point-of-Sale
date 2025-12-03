/*
 * Author: John Cedeno
 */

package pointOfSale;

import java.util.*;

public class Category extends MenuComponent {
	private List<MenuComponent> items;
	public Category(String name) {
		super(name);
		items = new ArrayList<>();
	}

	public void add(MenuComponent item) {
		items.add(item);
	}
	
	public void remove(MenuComponent item) {
		items.remove(item);
	}
	@Override
	public double getTotalPrice() {
		double acc = 0;
		for (MenuComponent item : items) {
			acc += item.getTotalPrice();
		}
		return acc;
	}

	@Override
	public void display() {
		for (MenuComponent item : items) {
			System.out.println(this.toString() + ":");
			item.display();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public MenuComponent getChild(int index) {
	    return items.get(index);
	}

	@Override
	public int getChildCount() {
	    return items.size();
	}
}

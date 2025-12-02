/*
 * Author: John Cedeno
 */

package pointOfSale;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ReceiptModel {
	private Date timestamp;
	private List<LineItem> items;
	private List<ReceiptObserver> observers;
	private int recieptNumber;
	private static int recieptCounter = 1000;
	
	public ReceiptModel() {
		this.timestamp = new Date();
		this.recieptNumber = recieptCounter++;
		this.items = new ArrayList<>();
		this.observers = new ArrayList<>();
	}
	
	public void addItem(Item item, int quantity) {
		if (item == null || quantity <= 0) {
			throw new IllegalArgumentException("Item cannot be null and quantity must be positive.");
		}
		
		for (LineItem lineItem : items) {
			if (lineItem.getItem().equals(item)) {
				items.remove(lineItem);
				items.add(new LineItem(item, lineItem.getQuantity() + quantity));
				notifyObservers();
				return;
			}
		}
		
		items.add(new LineItem(item, quantity));
		notifyObservers();
	}
	
	public void removeItem(int index) {
		if (index < 0 || index >= items.size()) {
			throw new IndexOutOfBoundsException("Invalid item index: " + index);
		}
		items.remove(index);
		notifyObservers();
	}
	
	public List<LineItem> getItems() {
		return new ArrayList<>(items);
	}
	
	public void clear() {
		items.clear();
		this.timestamp = new Date();
		this.recieptNumber = recieptCounter++;
		notifyObservers();
	}
	
	public void notifyObservers() {
		for (ReceiptObserver observer : observers) {
			observer.update(this);
		}
	}
	
	public double getTotal() {
		double total = 0.0;
		for (LineItem lineItem : items) {
			total += lineItem.getSubtotal();
		}
		return total;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public int getRecieptNumber() {
		return recieptNumber;
	}
	
	public void addObserver(ReceiptObserver observer) {
		if (observer != null && !observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	public void removeObserver(ReceiptObserver observer) {
		observers.remove(observer);
	}
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(getRecieptNumber()).append("\n");
        sb.append("Date: ").append(timestamp).append("\n");
        sb.append("Items:\n");
        for (LineItem lineItem : items) {
            sb.append("  ").append(lineItem.toString()).append("\n");
        }
        sb.append(String.format("Total: $%.2f", getTotal()));
        return sb.toString();
    }
}

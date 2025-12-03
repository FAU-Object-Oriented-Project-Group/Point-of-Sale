package pointOfSale;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class MenuComponent {
	protected String name;
	
	public MenuComponent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void add (MenuComponent component) {
		throw new UnsupportedOperationException("Cannot add to this component");
	}
	public void remove(MenuComponent component) {
		throw new UnsupportedOperationException("Cannot remove from this component");
	}
	
	public MenuComponent getChild(int index) {
		throw new UnsupportedOperationException("Cannot get child from this component");
	}
	
	public int getChildCount() {
		throw new UnsupportedOperationException("This component has no children");
	}
	
	public abstract double getTotalPrice();
	public abstract void display();
	
	public DefaultMutableTreeNode buildTreeNode() {
		// Recursive function to build a tree model out of our hierarchy.
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
		
		if (this instanceof Category) {
			Category category = (Category) this;
			try {
				int childCount = category.getChildCount();
				for (int i = 0; i < childCount; i++) {
					MenuComponent child = category.getChild(i);
					node.add(child.buildTreeNode());
				}
			} catch (UnsupportedOperationException e) {
				
			}
		}
		
		return node;
	}
}

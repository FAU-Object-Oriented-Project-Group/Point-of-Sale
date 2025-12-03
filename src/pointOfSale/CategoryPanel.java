/*
* Author: John Cedeno & John Cornett 
*
*/

package pointOfSale;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;

class CategoryPanel extends JPanel {
    private JTree categoryTree;
    private DefaultTreeModel treeModel;
    private JScrollPane scrollPane;
    private Category rootCategory;

    public CategoryPanel() {
    	rootCategory = new Category("Main");
    	
        setLayout(new BorderLayout());
        
        buildTree();
        categoryTree = new JTree(treeModel);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        categoryTree.setRootVisible(true);

        scrollPane = new JScrollPane(categoryTree);
        add(scrollPane, BorderLayout.CENTER);

        // Add button panel
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToReceiptBtn = new JButton("Add to Receipt");
        JButton addItemBtn = new JButton("Add Item");
        JButton addCategoryBtn = new JButton("Add Category");
        
        topButtonPanel.add(addItemBtn);
        topButtonPanel.add(addCategoryBtn);
        bottomButtonPanel.add(addToReceiptBtn);
        add(bottomButtonPanel, BorderLayout.SOUTH);
        add(topButtonPanel, BorderLayout.NORTH);
    }
    
    public void buildTree() {
    	Category beverages = new Category("Beverages");
    	beverages.add(new Item("Coffee", 2.50));
    	beverages.add(new Item("Tea", 2.00));
    	beverages.add(new Item("Soda", 1.50));
    	
    	Category food = new Category("Food");
    	food.add(new Item("Sandwich", 5.99));
    	food.add(new Item("Salad", 4.99));
    	food.add(new Item("Pizza Slice", 3.50));
    	
    	Category desserts = new Category("Desserts");
    	desserts.add(new Item("Cake", 3.99));
    	desserts.add(new Item("Ice cream", 2.99));
    	
    	rootCategory.add(beverages);
    	rootCategory.add(food);
    	rootCategory.add(desserts);
    	
    	DefaultMutableTreeNode treeRoot = rootCategory.buildTreeNode();
        treeModel = new DefaultTreeModel(treeRoot);
        categoryTree = new JTree(treeModel);
    	expandAllNodes();
    	treeModel.reload();
    }

    public Item getSelectedItem() {
        TreePath path = categoryTree.getSelectionPath();
        if (path == null) return null;
        
        DefaultMutableTreeNode node =
        		(DefaultMutableTreeNode) path.getLastPathComponent();
        Object userObject = node.getUserObject();
        
        if (userObject instanceof Item) {
        	return (Item) userObject;
        }
        return null;
    }
    
    public Category getSelectedCategory() {
    	TreePath path = categoryTree.getSelectionPath();
    	if (path == null) return null;
    	
    	DefaultMutableTreeNode node =
    			(DefaultMutableTreeNode) path.getLastPathComponent();
    	Object userObject = node.getUserObject();
    	
    	if (userObject instanceof Category) {
    		return (Category) userObject;
    	} else if (userObject instanceof Item) {
    		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
    		if (parent != null) {
    			return (Category) parent.getUserObject();
    		}
    	}
    	
    	return null;
    }

    // Attach listener to the Add button
    public void addAddToReceiptListener(ActionListener listener) {
        if (!(getLayout() instanceof BorderLayout)) return;
        BorderLayout bl = (BorderLayout) getLayout();

        Component southComp = bl.getLayoutComponent(BorderLayout.SOUTH);
        if (!(southComp instanceof JPanel)) return;
        JPanel buttonPanel = (JPanel) southComp;

        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton &&
                    ((JButton) comp).getText().equals("Add to Receipt")) {
                ((JButton) comp).addActionListener(listener);
                return;
            }
        }
    }
    
    // Attach listener to the Add Category button
    public void addCategoryListener(ActionListener listener) {
        if (!(getLayout() instanceof BorderLayout)) return;
        BorderLayout bl = (BorderLayout) getLayout();

        Component northComp = bl.getLayoutComponent(BorderLayout.NORTH);
        if (!(northComp instanceof JPanel)) return;
        JPanel buttonPanel = (JPanel) northComp;

        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton &&
                    ((JButton) comp).getText().equals("Add Category")) {
                ((JButton) comp).addActionListener(listener);
                return;
            }
        }
    }
    
    // Attach listener to the Add Item button
    public void addItemListener(ActionListener listener) {
        if (!(getLayout() instanceof BorderLayout)) return;
        BorderLayout bl = (BorderLayout) getLayout();

        Component northComp = bl.getLayoutComponent(BorderLayout.NORTH);
        if (!(northComp instanceof JPanel)) return;
        JPanel buttonPanel = (JPanel) northComp;

        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton &&
                    ((JButton) comp).getText().equals("Add Item")) {
                ((JButton) comp).addActionListener(listener);
                return;
            }
        }
    }
    
    public void addSelectionListener(TreeSelectionListener listener) {
    	categoryTree.addTreeSelectionListener(listener);
    }
    
    public void rebuildTree() {
    	DefaultMutableTreeNode treeRoot = rootCategory.buildTreeNode();
        treeModel.setRoot(treeRoot);
        expandAllNodes();
    }
    
    private void expandAllNodes() {
        for (int i = 0; i < categoryTree.getRowCount(); i++) {
            categoryTree.expandRow(i);
        }
    }
}

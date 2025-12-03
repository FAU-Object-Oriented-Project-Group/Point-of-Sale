/*
* Author: John Cedeno & John Cornett 
*
*/

package pointOfSale;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.ActionListener;

class CategoryPanel extends JPanel {
    private JTree categoryTree;
    private DefaultTreeModel treeModel;
    private JScrollPane scrollPane;
    private Category rootCategory;

    public CategoryPanel() {
    	rootCategory = new Category("Menu");
    	
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToReceiptBtn = new JButton("Add to Receipt");
        JButton addItemBtn = new JButton("Add Item");
        
        buttonPanel.add(addItemBtn);
        buttonPanel.add(addToReceiptBtn);
        add(buttonPanel, BorderLayout.SOUTH);
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
    	
    	rebuildTree();
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

    // Attach listener to the Add button
    public void addAddToReceiptListener(Object listener) {
        if (!(listener instanceof ActionListener)) return;
        ActionListener al = (ActionListener) listener;

        if (!(getLayout() instanceof BorderLayout)) return;
        BorderLayout bl = (BorderLayout) getLayout();

        Component southComp = bl.getLayoutComponent(BorderLayout.SOUTH);
        if (!(southComp instanceof JPanel)) return;
        JPanel buttonPanel = (JPanel) southComp;

        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton &&
                    ((JButton) comp).getText().equals("Add to Receipt")) {
                ((JButton) comp).addActionListener(al);
                return;
            }
        }
    }
    
    public void rebuildTree() {
    	DefaultMutableTreeNode treeRoot = rootCategory.buildTreeNode();
        treeModel = new DefaultTreeModel(treeRoot);
        categoryTree = new JTree(treeModel);
    	expandAllNodes();
    	treeModel.reload();
    }
    
    private void expandAllNodes() {
        for (int i = 0; i < categoryTree.getRowCount(); i++) {
            categoryTree.expandRow(i);
        }
    }
}

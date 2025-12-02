/*
* Author: John Cedeno & John Cornett
*
*/

package pointOfSale;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionListener;

class CategoryPanel extends JPanel {
    private JTree categoryTree;
    private DefaultTreeModel treeModel;
    private JScrollPane scrollPane;

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initComponents();
        buildSampleTree();
    }

    private void initComponents() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        treeModel = new DefaultTreeModel(root);
        categoryTree = new JTree(treeModel);
        categoryTree.setShowsRootHandles(true);
        categoryTree.setRootVisible(true);

        scrollPane = new JScrollPane(categoryTree);
        add(scrollPane, BorderLayout.CENTER);

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToReceiptBtn = new JButton("Add to Receipt");
        buttonPanel.add(addToReceiptBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void buildSampleTree() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

        // Sample categories
        DefaultMutableTreeNode beverages = new DefaultMutableTreeNode("Beverages");
        beverages.add(new DefaultMutableTreeNode("Coffee - $2.50"));
        beverages.add(new DefaultMutableTreeNode("Tea - $2.00"));
        beverages.add(new DefaultMutableTreeNode("Soda - $1.50"));

        DefaultMutableTreeNode food = new DefaultMutableTreeNode("Food");
        food.add(new DefaultMutableTreeNode("Sandwich - $5.99"));
        food.add(new DefaultMutableTreeNode("Salad - $4.99"));
        food.add(new DefaultMutableTreeNode("Pizza Slice - $3.50"));

        DefaultMutableTreeNode desserts = new DefaultMutableTreeNode("Desserts");
        desserts.add(new DefaultMutableTreeNode("Cake - $3.99"));
        desserts.add(new DefaultMutableTreeNode("Ice Cream - $2.99"));

        root.add(beverages);
        root.add(food);
        root.add(desserts);

        treeModel.reload();

        for (int i = 0; i < categoryTree.getRowCount(); i++) {
            categoryTree.expandRow(i);
        }
    }
    
    public void buildTree(Object menuComponent) {
        // Will be implemented with actual MenuComponent
    }

    public Object getSelectedItem() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                categoryTree.getLastSelectedPathComponent();
        return node != null ? node.getUserObject() : null;
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
}

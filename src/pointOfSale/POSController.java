/*
 * Author: Jacob Eurglunes & John Cedeno
 * 
 */

package pointOfSale;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class POSController {

    private final ReceiptModel receiptModel;
    private final POSView view;
    private final Object menuRoot;         
    private InvoiceStrategy invoiceStrategy;
    private CategoryPanel categoryPanel;

    public POSController(ReceiptModel receiptModel,
                         InvoiceStrategy invoiceStrategy,
                         POSView view,
                         Object menuRoot) {

        if (receiptModel == null) {
            throw new IllegalArgumentException("receiptModel cannot be null");
        }
        if (view == null) {
            throw new IllegalArgumentException("view cannot be null");
        }

        this.receiptModel = receiptModel;
        this.view = view;
        this.menuRoot = menuRoot;
        this.invoiceStrategy = (invoiceStrategy != null)
                ? invoiceStrategy
                : new TextFileStrategy();

        if (view instanceof POSFrame) {
            ((POSFrame) view).setController(this);
        }
    }

    public void handleItemSelection(Item item) {
        if (item == null) {
            return;
        }
        receiptModel.addItem(item, 1);
    }


    public void handleRemoveItem(int index) {
        if (index < 0 || index >= receiptModel.getItems().size()) {
            return; // out of bounds, ignore
        }
        receiptModel.removeItem(index);
    }

    public void handleGenerateInvoice(ActionEvent e) {
        String invoiceText = receiptModel.toString();
        view.showInvoice(invoiceText);
    }

    public void handleChangeInvoiceFormat(String format) {
        if (format == null) {
            return;
        }

        String f = format.trim().toLowerCase();

        if (f.equals("pdf")) {
            invoiceStrategy = new PDFFileStrategy();
        } else if (f.equals("text") || f.equals("txt")) {
            invoiceStrategy = new TextFileStrategy();
        }
    }

    public void handleClearReceipt() {
        receiptModel.clear();
    }
    
    public void setCategoryPanel(CategoryPanel categoryPanel) {
    	this.categoryPanel = categoryPanel;
    	 categoryPanel.addItemListener(e -> handleAddItem());
         
         // Add Category button handler
         categoryPanel.addCategoryListener(e -> handleAddCategory());
    }

    public void initialize() {
        view.setReceiptModel(receiptModel);
    }

    public InvoiceStrategy getInvoiceStrategy() {
        return invoiceStrategy;
    }

    public void saveInvoiceToFile(java.io.File file) {
        if (file == null || invoiceStrategy == null) {
            return;
        }
        invoiceStrategy.generate(receiptModel, file);
    }
    
    public void handleAddItem() {
    	Category selectedCategory = categoryPanel.getSelectedCategory();
    	if (selectedCategory == null) {
    		JOptionPane.showMessageDialog(null,
    				"Please select a category first",
    				"No Category Selected",
    				JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
        // Show dialog to get item details
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Item Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, 
            "Add New Item", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                
                Item newItem = new Item(name, price);
                selectedCategory.add(newItem);
                
                // Refresh the tree
                categoryPanel.rebuildTree();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid price format", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void handleAddCategory() {
        Category selectedCategory = categoryPanel.getSelectedCategory();
        if (selectedCategory == null) {
            JOptionPane.showMessageDialog(null, 
                "Please select a parent category first", 
                "No Category Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String categoryName = JOptionPane.showInputDialog(null, 
            "Enter category name:", 
            "Add New Category", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            Category newCategory = new Category(categoryName);
            selectedCategory.add(newCategory);
            
            categoryPanel.rebuildTree();
        }
    }
}

/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import javax.swing.*;
import java.awt.*;

public class POSFrame extends JFrame implements POSView {
    private CategoryPanel categoryPanel;
    private ReceiptPanel receiptPanel;
    private JButton generateInvoiceBtn;
    private JButton clearReceiptBtn;
    private JComboBox<String> invoiceFormatCombo;
    
    public POSFrame() {
        setTitle("Point of Sale System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        categoryPanel = new CategoryPanel();
        receiptPanel = new ReceiptPanel();
        
        generateInvoiceBtn = new JButton("Generate Invoice");
        clearReceiptBtn = new JButton("Clear Receipt");
        
        invoiceFormatCombo = new JComboBox<>(new String[]{"Text", "PDF"});
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Left panel - Categories
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Menu Categories"));
        leftPanel.add(categoryPanel, BorderLayout.CENTER);
        
        // Right panel - Receipt
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel receiptContainer = new JPanel(new BorderLayout());
        receiptContainer.setBorder(BorderFactory.createTitledBorder("Current Receipt"));
        receiptContainer.add(receiptPanel, BorderLayout.CENTER);
        
        // Bottom control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        controlPanel.add(new JLabel("Invoice Format:"));
        controlPanel.add(invoiceFormatCombo);
        controlPanel.add(clearReceiptBtn);
        controlPanel.add(generateInvoiceBtn);
        
        rightPanel.add(receiptContainer, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.4);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void displayCategories(Object menuComponent) {
        // Will be implemented with actual menu data
    }
    
    @Override
    public void updateReceipt(Object receiptModel) {
        // Will be implemented with actual receipt data
    }
    
    @Override
    public void showInvoice(String invoice) {
        InvoiceDialog dialog = new InvoiceDialog(this);
        dialog.displayInvoice(invoice);
    }
    
    @Override
    public Object getSelectedItem() {
        return categoryPanel.getSelectedItem();
    }
    
    public void setController(Object controller) {
        // Will connect controller later
    }
}


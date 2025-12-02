/*
* Author: John Cedeno & John Cornett 
*
*/


package pointOfSale;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class POSFrame extends JFrame implements POSView {
    private CategoryPanel categoryPanel;
    private ReceiptPanel receiptPanel;
    private JButton generateInvoiceBtn;
    private JButton clearReceiptBtn;
    private JComboBox<String> invoiceFormatCombo;
    private POSController controller;

    public POSFrame() {
        setTitle("Point of Sale System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        categoryPanel = new CategoryPanel();
        receiptPanel = new ReceiptPanel();

        generateInvoiceBtn = new JButton("Generate Invoice");
        clearReceiptBtn = new JButton("Clear Receipt");
        invoiceFormatCombo = new JComboBox<>(new String[]{"Text", "PDF"});

        layoutComponents();
    }

    private void layoutComponents() {
        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Menu Categories"));
        leftPanel.add(categoryPanel, BorderLayout.CENTER);

        // Right panel
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

    public void setController(Object controller) {
        if (!(controller instanceof POSController)) return;
        this.controller = (POSController) controller;

        // Add to Receipt
        categoryPanel.addAddToReceiptListener((ActionListener) e -> {
            Object selected = categoryPanel.getSelectedItem();
            if (selected == null) return;

            String s = selected.toString();
            String[] parts = s.split(" - \\$");
            if (parts.length != 2) return;

            String name = parts[0].trim();
            double price;
            try {
                price = Double.parseDouble(parts[1].trim());
            } catch (NumberFormatException ex) { return; }

            Item item = new Item(name, price);
            this.controller.handleItemSelection(item);
        });

        // Remove Selected
        receiptPanel.addRemoveButtonListener((ActionListener) e -> {
            int row = receiptPanel.getSelectedRow();
            if (row >= 0) this.controller.handleRemoveItem(row);
        });

        // Clear Receipt
        clearReceiptBtn.addActionListener(e -> this.controller.handleClearReceipt());

        // Generate Invoice
        generateInvoiceBtn.addActionListener(e -> this.controller.handleGenerateInvoice(e));

        // Invoice format change
        invoiceFormatCombo.addActionListener(e -> {
            String format = (String) invoiceFormatCombo.getSelectedItem();
            this.controller.handleChangeInvoiceFormat(format);
        });
    }

    @Override
    public void displayCategories(Object menuComponent) {}
    @Override
    public void updateReceipt(Object receiptModel) {
        if (receiptModel instanceof ReceiptModel)
            receiptPanel.updateDisplay((ReceiptModel) receiptModel);
    }
    @Override
    public void showInvoice(String invoice) {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	int result = fileChooser.showSaveDialog(this);
    	System.out.println(invoiceFormatCombo.getSelectedItem());
    	if (invoiceFormatCombo.getSelectedItem().equals("Text")) {
    		fileChooser.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt file","txt"));
    	} else if (invoiceFormatCombo.getSelectedItem().equals("PDF")) {
    		fileChooser.setFileFilter(new FileNameExtensionFilter("pdf file","pdf"));
    		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("pdf file","pdf"));
    	}
    	if (result == JFileChooser.APPROVE_OPTION) {
    		File selectedFile = fileChooser.getSelectedFile();
    		controller.saveInvoiceToFile(selectedFile);
    	} 
    }
    @Override
    public Object getSelectedItem() { return categoryPanel.getSelectedItem(); }
}

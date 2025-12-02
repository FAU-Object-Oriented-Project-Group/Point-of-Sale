/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class InvoiceDialog extends JDialog {
	private JTextArea invoiceTextArea;
    private JButton saveButton;
    private JButton closeButton;
    
    public InvoiceDialog(JFrame parent) {
        super(parent, "Invoice", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);
        invoiceTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        saveButton = new JButton("Save Invoice");
        closeButton = new JButton("Close");
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        closeButton.addActionListener(e -> dispose());
    }
    
    public void displayInvoice(String invoice) {
        invoiceTextArea.setText(invoice);
        setVisible(true);
    }
    
    public void addSaveListener(Object listener) {
    	
    }
}

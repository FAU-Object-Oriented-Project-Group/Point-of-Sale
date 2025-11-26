/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ReceiptPanel extends JPanel {
    private JTable itemsTable;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public ReceiptPanel() {
        setLayout(new BorderLayout(5, 5));
        initComponents();
        addSampleData();
    }
    
    private void initComponents() {
        String[] columnNames = {"Item", "Quantity", "Price", "Subtotal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        itemsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        itemsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        itemsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel with total and remove button
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("$0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 100, 0));
        totalPanel.add(totalLabel);
        
        JButton removeButton = new JButton("Remove Selected");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(removeButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(totalPanel, BorderLayout.EAST);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void addSampleData() {
        tableModel.addRow(new Object[]{"Coffee", 2, "$2.50", "$5.00"});
        tableModel.addRow(new Object[]{"Sandwich", 1, "$5.99", "$5.99"});
        totalLabel.setText("$10.99");
    }
    
    public void updateDisplay(Object receiptModel) {
        // Will be implemented with actual ReceiptModel
    }
    
    public void clearReceipt() {
        tableModel.setRowCount(0);
        totalLabel.setText("$0.00");
    }
    
    public void addRemoveButtonListener(Object listener) {
        // Will add ActionListener
    }
}

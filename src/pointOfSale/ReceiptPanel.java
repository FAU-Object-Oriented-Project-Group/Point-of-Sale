/*
* Author: John Cedeno & John Cornett 
*
*/
package pointOfSale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

class ReceiptPanel extends JPanel {
    private JTable itemsTable;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;

    public ReceiptPanel() {
        setLayout(new BorderLayout(5, 5));
        initComponents();
    }

    private void initComponents() {
        String[] columnNames = {"Item", "Quantity", "Price", "Subtotal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("$0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 100, 0));
        totalPanel.add(totalLabel);

        // Remove button
        JButton removeButton = new JButton("Remove Selected");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(removeButton);

        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(totalPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Updates table display from a ReceiptModel
    public void updateDisplay(ReceiptModel receiptModel) {
        tableModel.setRowCount(0); // clear table

        if (receiptModel != null && !receiptModel.getItems().isEmpty()) {
            for (LineItem line : receiptModel.getItems()) {
                tableModel.addRow(new Object[]{
                        line.getItem().getName(),
                        line.getQuantity(),
                        String.format("$%.2f", line.getItem().getPrice()),
                        String.format("$%.2f", line.getSubtotal())
                });
            }
            totalLabel.setText(String.format("$%.2f", receiptModel.getTotal()));
        } else {
            totalLabel.setText("$0.00");
        }
    }

    public int getSelectedRow() {
        return itemsTable.getSelectedRow();
    }

    public void addRemoveButtonListener(Object listener) {
        if (!(listener instanceof ActionListener)) return;
        ActionListener al = (ActionListener) listener;

        if (!(getLayout() instanceof BorderLayout)) return;
        BorderLayout bl = (BorderLayout) getLayout();
        Component southComp = bl.getLayoutComponent(BorderLayout.SOUTH);
        if (!(southComp instanceof JPanel)) return;
        JPanel bottomPanel = (JPanel) southComp;

        Component westComp = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.WEST);
        if (!(westComp instanceof JPanel)) return;
        JPanel buttonPanel = (JPanel) westComp;

        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton &&
                    ((JButton) comp).getText().equals("Remove Selected")) {
                ((JButton) comp).addActionListener(al);
                return;
            }
        }
    }
}



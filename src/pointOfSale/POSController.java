/*
 * Author: Jacob Eurglunes
 * 
 */

package pointOfSale;

import java.awt.event.ActionEvent;

public class POSController {

    private final ReceiptModel receiptModel;
    private final POSView view;
    private final Object menuRoot;         
    private InvoiceStrategy invoiceStrategy;

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
        view.updateReceipt(receiptModel);
    }


    public void handleRemoveItem(int index) {
        if (index < 0 || index >= receiptModel.getItems().size()) {
            return; // out of bounds, ignore
        }
        receiptModel.removeItem(index);
        view.updateReceipt(receiptModel);
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
        view.updateReceipt(receiptModel);
    }

    public void initialize() {
        if (menuRoot != null) {
            view.displayCategories(menuRoot);
        }
        view.updateReceipt(receiptModel);
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
}

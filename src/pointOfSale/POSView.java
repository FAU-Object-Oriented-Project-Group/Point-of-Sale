/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

interface POSView {
    void displayCategories(Object menuComponent);
    void showInvoice(String invoice);
    Object getSelectedItem();
	void setReceiptModel(ReceiptModel receiptModel);
}
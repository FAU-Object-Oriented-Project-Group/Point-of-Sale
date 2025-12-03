/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

interface POSView {
    void showInvoice(String invoice);
	void setReceiptModel(ReceiptModel receiptModel);
}
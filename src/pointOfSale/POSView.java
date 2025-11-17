package pointOfSale;

interface POSView {
    void displayCategories(Object menuComponent);
    void updateReceipt(Object receiptModel);
    void showInvoice(String invoice);
    Object getSelectedItem();
}
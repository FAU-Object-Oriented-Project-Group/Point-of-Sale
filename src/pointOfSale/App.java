/*
* Author: John Cedeno & John Cornett 
*
*/

package pointOfSale;

public class App {
    public static void main(String[] args) {
        ReceiptModel model = new ReceiptModel();
        InvoiceStrategy strategy = new TextFileStrategy();
        POSFrame frame = new POSFrame();

        POSController controller = new POSController(model, strategy, frame, null);
        controller.initialize();

        frame.setVisible(true);
    }
}

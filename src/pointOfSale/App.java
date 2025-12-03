/*
* Author: John Cedeno & John Cornett 
*
*/

package pointOfSale;

import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
    		// Set the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel. Using default Metal L&F.");
        }
        ReceiptModel model = new ReceiptModel();
        InvoiceStrategy strategy = new TextFileStrategy();
        POSFrame frame = new POSFrame();

        POSController controller = new POSController(model, strategy, frame, null);
        controller.initialize();

        frame.setVisible(true);
    }
}

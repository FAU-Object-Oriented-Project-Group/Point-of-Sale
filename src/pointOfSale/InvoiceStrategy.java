/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import java.io.File;
import java.io.IOException;

public interface InvoiceStrategy {
	void generate(ReceiptModel reciept, File filepath);
}

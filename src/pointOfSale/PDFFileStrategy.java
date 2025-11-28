/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import java.io.File;
import java.io.IOException;

public class PDFFileStrategy implements InvoiceStrategy {

	@Override
	public void generate(ReceiptModel reciept, File filepath) {
		if (filepath.isFile()) {
			saveFile(filepath, reciept.toString());
		} else {
			try {
				if (filepath.createNewFile()) {
					saveFile(filepath, reciept.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveFile(File fp, String reciept) {
		
	}

}

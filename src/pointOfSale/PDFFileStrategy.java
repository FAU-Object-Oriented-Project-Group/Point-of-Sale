/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import java.io.File;
import java.io.IOException;

public class PDFFileStrategy implements InvoiceStrategy {

	@Override
	public void generate(String reciept, File filepath) {
		if (filepath.isFile()) {
			saveFile(filepath, reciept);
		} else {
			try {
				if (filepath.createNewFile()) {
					saveFile(filepath, reciept);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveFile(File fp, String reciept) {
		
	}

}

/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import java.io.*;

public class TextFileStrategy implements InvoiceStrategy {

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
	
	private void saveFile(File fp, String str) {
		try {
			PrintWriter writer = new PrintWriter(fp, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException e) {
			// Should not happen.
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Also should not happen.
			e.printStackTrace();
		}
	}

}

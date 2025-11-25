package pointOfSale;

import java.io.File;
import java.io.IOException;

public interface InvoiceStrategy {
	void generate(String reciept, File filepath);
}

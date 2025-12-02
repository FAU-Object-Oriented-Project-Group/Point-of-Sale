/*
 * Author: John Cedeno
 * 
 */

package pointOfSale;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PDFFileStrategy implements InvoiceStrategy {

	@Override
	public void generate(ReceiptModel reciept, File filepath) {
		try {
			if (filepath.isFile()) {
				saveFile(filepath, reciept.toString());
			} else {
				filepath.createNewFile();
				saveFile(filepath, reciept.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveFile(File fp, String reciept) throws IOException {
		try (FileOutputStream fo = new FileOutputStream(fp)) {
			fo.write(pdfTemplate(reciept).getBytes(StandardCharsets.ISO_8859_1));
		}
	}
	
	private String pdfTemplate(String text) {
		// Dependency-less PDF file generator.
		StringBuilder pdf = new StringBuilder();
		
		pdf.append("%PDF-1.4\n%");
		pdf.append("%âãÏÓ\n"); // Magic binary marker
		
		// Catalog
		int obj1Start = pdf.length();
		pdf.append(pdfObject(1, "<< /Type /Catalog /Pages 2 0 R >>"));
		
		// Pages
		int obj2Start = pdf.length();
		pdf.append(pdfObject(2, "<< /Type /Pages /Kids [3 0 R] /Count 1>>"));
		
		// Page
		int obj3Start = pdf.length();
		StringBuilder sb = new StringBuilder();
		sb.append("<< /Type /Page /Parent 2 0 R /Resources 4 0 R ");
        sb.append("/MediaBox [0 0 612 792] /Contents 5 0 R >>\n");
		pdf.append(pdfObject(3, sb.toString()));
		
		// Resources (Font)
        int obj4Start = pdf.length();
        pdf.append("4 0 obj\n");
        pdf.append("<< /Font << /F1 << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> >> >>\n");
        pdf.append("endobj\n");
        
        // Content Stream
        String contentStream = createContentStream(text);
        int obj5Start = pdf.length();
        pdf.append("5 0 obj\n");
        pdf.append("<< /Length ").append(contentStream.length()).append(" >>\n");
        pdf.append("stream\n");
        pdf.append(contentStream);
        pdf.append("\nendstream\n");
        pdf.append("endobj\n");
        
        // Cross-reference table
        int xrefStart = pdf.length();
        pdf.append("xref\n");
        pdf.append("0 6\n");
        pdf.append("0000000000 65535 f \n");
        pdf.append(String.format("%010d 00000 n \n", obj1Start));
        pdf.append(String.format("%010d 00000 n \n", obj2Start));
        pdf.append(String.format("%010d 00000 n \n", obj3Start));
        pdf.append(String.format("%010d 00000 n \n", obj4Start));
        pdf.append(String.format("%010d 00000 n \n", obj5Start));
        
        // Trailer
        pdf.append("trailer\n");
        pdf.append("<< /Size 6 /Root 1 0 R >>\n");
        pdf.append("startxref\n");
        pdf.append(xrefStart).append("\n");
        pdf.append("%%EOF\n");
		
        return pdf.toString();
	}
	
	private static String createContentStream(String text) {
		StringBuilder content = new StringBuilder();
	    content.append("BT\n");  // Begin text
	    content.append("/F1 12 Tf\n");  // Set font and size
	    content.append("14 TL\n");
	    content.append("50 750 Td\n");  // Set position (x, y from bottom-left)
	    
	    String[] lines = text.split("\r?\n");
	    
	    for (int i = 0; i < lines.length; i++) {
	    	String currentLine = escapePDFString(lines[i]);
	    	content.append("(").append(currentLine).append(") Tj\n");
	    	if (i < lines.length - 1) {
	    		content.append("T*\n");
	    	}
	    }
	    
	    content.append("ET");  // End text
	    return content.toString();
	}
	
    private static String escapePDFString(String text) {
        // Escape special characters in PDF strings
        return text.replace("\\", "\\\\")
                   .replace("(", "\\(")
                   .replace(")", "\\)")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
	
	public String pdfObject(int objNum, String str) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(objNum);
		sb.append(" 0 obj\n");
		sb.append(str);
		sb.append("\n");
		sb.append("endobj\n");
		
		return sb.toString();
	}

}

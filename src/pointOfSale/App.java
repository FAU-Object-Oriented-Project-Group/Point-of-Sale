package pointOfSale;

import javax.swing.*;

public class App {
	
	public static void main(String [] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		POSFrame frame = new POSFrame();
		frame.setVisible(true);
	}	
}

package pointOfSale;

import javax.swing.*;

public class App {
	public static void main(String [] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Hello, world!");
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JLabel label = new JLabel("Hello, world!");
			
			frame.getContentPane().add(label);
			
			frame.pack();
			
			frame.setVisible(true);
		});
	}
}

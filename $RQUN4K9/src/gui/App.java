package gui;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {

	public static void main(String[] args) {
		
		 

		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				new MainFrame();

			}

		});
		
		
		/*
		 * lub LAMBDA:
		 * 
		 * SwingUtilities.invokeLater(() -> new MainFrame());
		 * 
		 * 
		 * 
		 * lub Method Reference:
		 * 
		 * SwingUtilities.invokeLater(MainFrame::new);
		 * 
		 */

	}

}

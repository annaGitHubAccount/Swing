import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {

		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

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

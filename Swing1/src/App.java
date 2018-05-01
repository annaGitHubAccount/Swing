import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
	

		// SwingUtilities.invokeLater() jest po to jakbym chciala w mojej App uzywac multithreading 
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				JFrame frame = new JFrame("Hello World");
				
				frame.setSize(600, 500);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // przy zamykaniu okienka, jak klikam na przycisk X to App w Consoli tez sie wylaczy (terminate) - nie musze tego robic recznie
				frame.setVisible(true);
				
			}
			
			
		});
		
		
		
		/*
		 * albo JAVA 8 - lambda:
		 * 
		 * SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Hello World");

            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // przy zamykaniu okienka, jak klikam na przycisk X to App w Consoli tez sie wylaczy (terminate) - nie musze tego robic recznie
            frame.setVisible(true);

        });
		 */
		
		
	}

}

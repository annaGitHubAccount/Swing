import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton helloButton;
	private JButton goodbyeButton;
	
	
	// dodaje 2 przyciski do paska narzedzi:
	public Toolbar() {
	
		helloButton = new JButton("Hello");
		goodbyeButton = new JButton("Goodbye");
		
		// Layout Manager: https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
		// ustawiam przyciski na pasku narzedzi do lewa
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(helloButton);
		add(goodbyeButton);
	}
}

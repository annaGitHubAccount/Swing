import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {

	/**
	 * Toolbar i TextPanel sa ze soba scisle powiazane (metoda actionPerformed()), a to nie jest dobry sposob programowania, gdy App robi sie coraz wieksza.
	 * Zarzadzanie tymi klasami powinno znajdowac sie w Kontrolerze - MainFrame --> Swing7.
	 */
	private static final long serialVersionUID = 1L;
	private JButton helloButton;
	private JButton goodbyeButton;
	private TextPanel textPanel;

	// dodaje 2 przyciski do paska narzedzi:
	public Toolbar() {

		helloButton = new JButton("Hello");
		goodbyeButton = new JButton("Goodbye");

		// na tych przyciskach wywoluje sie metoda actionPerformed(). To jest metoda z ActionListener
		helloButton.addActionListener(this);
		goodbyeButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(helloButton);
		add(goodbyeButton);
	}

	public void setTextPanel(TextPanel textPanel) {
		this.textPanel = textPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// getSource() zwraca obiekt na ktorym wykonuje sie Listener, tu Button
		JButton clicked = (JButton) event.getSource();

		if (clicked == helloButton) {
			textPanel.appendText("Hello\n");
			
		} else if (clicked == goodbyeButton) {
			textPanel.appendText("Goodbye\n");
		}
		
	}
}

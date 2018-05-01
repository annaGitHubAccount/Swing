import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JButton helloButton;
	private JButton goodbyeButton;
	
	private StringListener textListener;
	
	
	
	public Toolbar() {
		
		setBorder(BorderFactory.createEtchedBorder());

		helloButton = new JButton("Hello");
		goodbyeButton = new JButton("Goodbye");

		// na tych przyciskach ustawiam Listener. Wywoluje sie tu metoda actionPerformed(). To jest metoda z ActionListener
		helloButton.addActionListener(this);
		goodbyeButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(helloButton);
		add(goodbyeButton);
	}

	
	
	// ustawiam StringListener
	public void setTextListener(StringListener textListener) {
		this.textListener = textListener;
	}
	
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// ustawiam co sie dzieje jak klikam na Button --> Listener
	// dzieki Interface StringListener Button i Toolbar nie sa ze soba polaczone. Nic o sobie nie wiedza.
	// w taki sposob pracuje wiekszosc Komponentow w Swingu.
	@Override
	public void actionPerformed(ActionEvent event) {

		JButton clicked = (JButton) event.getSource();

		if (clicked == helloButton) {
			if(textListener != null) {
				textListener.textEmitted("Hello\n");
			}
			
		} else if (clicked == goodbyeButton) {
			if(textListener !=null) {
				textListener.textEmitted("Gooodbye\n");
			}
		}
		
		
	}


	
}

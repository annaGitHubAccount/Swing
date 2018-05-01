package gui;
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
	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener toolbarListener;
	
	
	
	public Toolbar() {
		
		setBorder(BorderFactory.createEtchedBorder());

		saveButton = new JButton("Save");
		refreshButton = new JButton("Refresh");

		// na tych przyciskach ustawiam Listener. Wywoluje sie tu metoda actionPerformed(). To jest metoda z ActionListener
		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(saveButton);
		add(refreshButton);
	}

	
	
	// ustawiam StringListener
	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.toolbarListener = toolbarListener;
	}
	
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// ustawiam co sie dzieje jak klikam na Button --> Listener
	// dzieki Interface StringListener Button i Toolbar nie sa ze soba polaczone. Nic o sobie nie wiedza.
	// w taki sposob pracuje wiekszosc Komponentow w Swingu.
	@Override
	public void actionPerformed(ActionEvent event) {

		JButton clicked = (JButton) event.getSource();

		if (clicked == saveButton) {
			if(toolbarListener != null) {
				toolbarListener.saveEventOccured();
			}
			
		} else if (clicked == refreshButton) {
			if(toolbarListener !=null) {
				toolbarListener.refreshEventOccured();
			}
		}
		
		
	}


	
}

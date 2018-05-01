import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	/**
	 * ta klasa dziala jak Kontroler. Decyduje o tym jakie komponenty, gdzie sa
	 * wyswietlane
	 * 
	 * Chce zeby dane wprowadzone pezeze mnie w formularzu byly przekazane do TextPanel i wyswietlone.
	 * Czyli jak klikne na Button OK, to dane wprowadzone do formularzy pokaza sie w TextPanel. Czyli trzeba dodac Listener do Button OK - klasa FormPanel.
	 * Caly mechanizm bedzie sie odbywal poprzez moj Kontroler - klase MainFrame.
	 */
	private static final long serialVersionUID = 1L;

	// Komponenty
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;  

	public MainFrame() {

		super("Hello World");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		
		
		toolbar.setStringListener(new StringListener() {

			@Override
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});

		/*
		 * albo Lambda: 
		 * toolbar.setTextListener(text -> textPanel.appendText(text));
		 */
		
		
		formPanel.setFormListener(new FormListener() {
			
			@Override
			public void formEventOccured(FormEvent event) {
				String name = event.getName();
				String occupation = event.getOccupation();
				
				textPanel.appendText(name + ": " + occupation + "\n");
			}
			
		});
		
		
		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

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
	 * GridBagLauout dodaje w FormPanel.
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
		
		
		toolbar.setTextListener(new StringListener() {

			@Override
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});

		/*
		 * albo Lambda: 
		 * toolbar.setTextListener(text -> textPanel.appendText(text));
		 */
		
		
		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

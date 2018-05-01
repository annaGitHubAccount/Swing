import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	/**
	 * jak klikne w przycisk to cos sie wydarzy
	 */
	private static final long serialVersionUID = 1L;
	
	// 2 Komponenty:
	private JButton button;
	private TextPanel textPanel;

	public MainFrame() {

		super("Hello World");

		setLayout(new BorderLayout());

		button = new JButton("Click Me!");
		textPanel = new TextPanel();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textPanel.appendText("Hello\n"); 
			}
		});

		add(textPanel, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

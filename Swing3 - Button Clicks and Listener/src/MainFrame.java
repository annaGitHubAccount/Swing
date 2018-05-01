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
	private JTextArea textArea;
	private JButton button;

	public MainFrame() {

		super("Hello World");

		setLayout(new BorderLayout());

		textArea = new JTextArea();
		button = new JButton("Click Me!");

		button.addActionListener(new ActionListener() {

			// ta metoda zawsze sie wykona jak klikne na przycisk:
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("Hello\n");  // \n --> next line
			}
		});

		add(textArea, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

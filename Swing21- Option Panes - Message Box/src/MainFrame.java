import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class MainFrame extends JFrame {

	/**
	 * ta klasa dziala jak Kontroler. Decyduje o tym jakie komponenty, gdzie sa
	 * wyswietlane
	 * 
	 * Chce dodac okienko, w ktorym bedzie zapytanie czy na pewno chce zakonczyc
	 * program.
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

		setJMenuBar(createMenuBar());

		toolbar.setStringListener(new StringListener() {

			@Override
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});

		/*
		 * albo Lambda: toolbar.setTextListener(text -> textPanel.appendText(text));
		 */

		formPanel.setFormListener(new FormListener() {

			@Override
			public void formEventOccured(FormEvent event) {
				String name = event.getName();
				String occupation = event.getOccupation();
				int ageCategory = event.getAgeCategory();
				String employmentCategory = event.getEmploymentCategory();

				textPanel.appendText(name + ": " + occupation + ": " + ageCategory + ", " + employmentCategory + "\n");

				// sluzy tylko do sprawdzenia na konsoli czy dziala
				System.out.println(event.getGender());

			}

		});

		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(500, 400)); // ponizej tych wartosci, mojej App nie da sie zmniejszyc  
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		// Menu "File"
		JMenu fileMenu = new JMenu("File");

		JMenuItem exportDataItem = new JMenuItem("Export Data ...");
		JMenuItem importDataItem = new JMenuItem("Import Data ...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.add(exitItem);
		fileMenu.addSeparator();

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		showFormItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) event.getSource();

				formPanel.setVisible(menuItem.isSelected());

			}
		});

		fileMenu.setMnemonic(KeyEvent.VK_F);

		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				/*
				// ustawiam okienko z showInputDialog():
				// oba okienka sa do siebie podobne, roznia sie zwracanym typem i znakiem ikonki w okienku
				String showInputDialog = JOptionPane.showInputDialog(MainFrame.this, "Enter your user name.","Enter User Name", JOptionPane.QUESTION_MESSAGE);
				System.out.println(showInputDialog);
				*/

				// ustawiam okienko z zapytaniem showConfirmDialog():
				int showConfirmDialog = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit the application?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);

				if (showConfirmDialog == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		return menuBar;
	}

}

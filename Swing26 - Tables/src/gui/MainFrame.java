package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {

	/**
	 * ta klasa dziala jak Kontroler. Decyduje o tym jakie komponenty, gdzie sa
	 * wyswietlane
	 * 
	 * Zmieniam TextPanel na JTable --> Klasa TablePanel, PersonTableModel
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Komponenty
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	
	private TablePanel tablePanel;
	

	public MainFrame() {

		super("Hello World");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		
		controller = new Controller();
		
		// tak pobieram dane z bazy danych do gui - poprzez kontroler
		tablePanel.setData(controller.getPeople());
		
	
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		
		
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
				controller.addPerson(event);
				
				// tworze ta metode, zeby moja tablica odswierzala sie za kazdym razem jak dodaje dane
				tablePanel.refresh();
			}

		});

		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	
	
	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

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

		
		
		// ustawiam JFileChooser na importDataItem. W okienku pojawia sie napis "Öffnen":
		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

					// sprawdzam czy dziala. Sciezka wybranego pliku wyswietla mi sie na konsoli
					System.out.println(fileChooser.getSelectedFile());
				}

			}
		});

		
		
		// ustawiam JFileChooser na exportDataItem. W okienku pojawia sie napis "Speichern":
		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

					// sprawdzam czy dziala. Sciezka wybranego pliku wyswietla mi sie na konsoli
					System.out.println(fileChooser.getSelectedFile());
				}

			}
		});

		
		
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

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

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.prefs.Preferences;

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
	 * Upiekszanie aplikacji.
	 * W PreferencesDialog tworze 3 panele i grupuje w nich poszczegolne komponenty --> layoutControlsSet()
	 */
	
	
	private static final long serialVersionUID = 1L;

	// Komponenty
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PreferencesDialog preferencesDialog;
	
	private Preferences preferences;

	
	
	
	public MainFrame() {

		super("Hello World");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		controller = new Controller();
		preferencesDialog = new PreferencesDialog(this);
		
		preferences = Preferences.userRoot().node("db");

		
		tablePanel.setData(controller.getPeople());

		
		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
			}
		});

		
		// Preferences -------------------------------------------------
		
		preferencesDialog.setPreferencesListener(new PreferencesListener() {

			@Override
			public void preferencesSet(String user, String password, int portNumber) {
				
				// wprowadzam dane do formlarza:
				preferences.put("user", user);
				preferences.put("password", password);
				preferences.putInt("port", portNumber);
				// new Integer(portNumber).toString()  - zamiana int na Stringa
								
				//System.out.println(user + ": " + password + portNumber);					
			}	
		});
		
		// odczytuje dane z formularza i ustawiam je jako default,zebym nie musiala ich za kazdym razem wprowadzac
		// tutaj sa to dane dostepu do bazy SQL
		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		int port = preferences.getInt("port", 3306);
		
		preferencesDialog.setDefaults(user, password, port);
		
		// ---------------------------------------------------------------------
		
		
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		setJMenuBar(createMenuBar());

		toolbar.setStringListener(new StringListener() {

			@Override
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});

		

		formPanel.setFormListener(new FormListener() {

			@Override
			public void formEventOccured(FormEvent event) {
				controller.addPerson(event);

				// tworze ta metode, zeby moja tablica odswierzala sie za kazdym razem jak
				// dodaje dane
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
		JMenuItem preferencesItem = new JMenuItem("Preferences...");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(preferencesItem);

		
		
		
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		
		// ustawiam nowe okienko dla menu "Preferences..." 
		preferencesItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				preferencesDialog.setVisible(true);
								
			}
		});
		
		
		

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

		preferencesItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

					// dodaje nowa metode z Database poprzez Controller:
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {

						// czyli jak w wybranym pliku pojawi sie blad to:
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

				}

			}
		});

		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

					// dodaje nowa metode z Database poprzez Controller:
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {

						// czyli jak w wybranym pliku pojawi sie blad to:
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);

					}
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

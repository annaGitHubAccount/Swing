package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
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
	 * 
	 * Intercepting - przechwytywanie
	 * 
	 * to make sure you kann disconnect from your database, even if you havn#t connected.
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
				// new Integer(portNumber).toString() - zamiana int na Stringa

				// System.out.println(user + ": " + password + portNumber);
			}
		});

		// odczytuje dane z formularza i ustawiam je jako default,zebym nie musiala ich
		// za kazdym razem wprowadzac
		// tutaj sa to dane dostepu do bazy SQL
		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		int port = preferences.getInt("port", 3306);

		preferencesDialog.setDefaults(user, password, port);

		// ---------------------------------------------------------------------

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		setJMenuBar(createMenuBar());

		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void saveEventOccured() {

				// System.out.println("Save");

			
				connect();
				
				
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database","Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}

			}

			@Override
			public void refreshEventOccured() {
				
				//System.out.println("Refresh");
				
				connect();
				
				try {
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database","Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
				
				tablePanel.refresh();

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
		
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				
				//System.out.println("Window closing");
				
				controller.disconnect();
				
				
				// jak zamykam okienko, to automatycznie moja App sie wylaczy
				dispose();
			}
			
		});
		

		add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);

		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
					
					//System.exit(0);
					
					WindowListener[] windowListeners = getWindowListeners();
					
					for(WindowListener listener : windowListeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});

		return menuBar;
	}
	
	
	private void connect() {
		
		try {
			controller.connect();
		} catch (Exception e) {
			
			// wyskoczy okienko jest bedzie blad w polaczeniu:
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect a database","Database Connection Problem", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}

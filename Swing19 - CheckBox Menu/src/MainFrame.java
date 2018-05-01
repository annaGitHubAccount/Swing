import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	/**
	 * ta klasa dziala jak Kontroler. Decyduje o tym jakie komponenty, gdzie sa
	 * wyswietlane
	 * 
	 *
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
	
		
		// ustawiam Menu Bar dla MainFrame
		setJMenuBar(createMenuBar());
		
		
		
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
		fileMenu.addSeparator(); // dodaje niewidzialna linie
		
		
		// Menu "Window" ma podmenu "Show"
		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		
		// JMenuItem showFormItem = new JMenuItem("Person Form");  // zamieniam na:
		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);  // z defaultu jest zaznaczone
		
		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		// do JMenu mozna dodac Listener - bedzie on nasluchiwal czy JCheckBoxMenuItem("Person Form") jest klikniety lub nie
		// i wtedy Form Panel albo sie pojawi albo nie
		showFormItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)event.getSource();  // moj source to: JCheckBoxMenuItem, dlatego Object kastuje na JCheckBoxMenuItem
				
				formPanel.setVisible(menuItem.isSelected());
				
			}
		});
		
		return menuBar;
	}

}

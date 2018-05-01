package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener toolbarListener;
	
	
	
	public Toolbar() {
		
		// jak pozbede sie Border to moje przyciski moge przenosic gdzie chce
		setBorder(BorderFactory.createEtchedBorder());
		
		// moge tez ustawic, ze nie chce zeby Toolbar mozna bylo przesuwac
		//setFloatable(false);

		saveButton = new JButton();
		saveButton.setIcon(createIcon("/images/save.png"));
		saveButton.setToolTipText("Save");  // jak najade myszka na ikonke, to pojawi sie chmurka z napisem Save.
		
		refreshButton = new JButton();
		refreshButton.setIcon(createIcon("/images/refresh.gif"));
		refreshButton.setToolTipText("Refresh");

		// na tych przyciskach ustawiam Listener. Wywoluje sie tu metoda actionPerformed(). To jest metoda z ActionListener
		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		//setLayout(new FlowLayout(FlowLayout.LEFT));

		add(saveButton);
		addSeparator();  // dodaje puste miejsce miedzy ikonkami. Ikonki robia sie kwadratowe.
		add(refreshButton);
	}

	
	// tworze metode, za pomoca ktorej dodaje ikonki do moich przyciskow Save i Refresh, ale rowniez dostane informacje, gdy 
	// ikonki sie nie zaladuja na stronie
	private ImageIcon createIcon(String path) {
		
		URL url = getClass().getResource(path);
		
		if(url == null) {
			System.out.println("Unable to load image: " + path);
		}
		
		ImageIcon icon = new ImageIcon(url);
		
		return icon;
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

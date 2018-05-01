package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class PreferencesDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public PreferencesDialog(JFrame parent) {

		super(parent, "Preferences", false);

		setSize(400, 300);
		
	}

}

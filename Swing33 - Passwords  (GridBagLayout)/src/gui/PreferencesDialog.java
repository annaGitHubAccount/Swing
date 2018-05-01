package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class PreferencesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;

	private JTextField userField;
	private JPasswordField passwordField;

	public PreferencesDialog(JFrame parent) {

		super(parent, "Preferences", false);

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		// do obiektu Spinner dodaje SpinnerModel:
		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);

		userField = new JTextField();
		passwordField = new JPasswordField(10);

		userField.setPreferredSize(new Dimension(115, 20));
		
		// ustawiam zeby pokazywaly mi sie gwiazdki zamiast kropek:
		passwordField.setEchoChar('*');
		
		
		
		/// Layout GridBagLayout -------------------------------

		setLayout(new GridBagLayout());

		// constraints to GridBagLayout:
		GridBagConstraints gc = new GridBagConstraints();

		/*
		 * gridx: -------
		 * 
		 * gridy: | | |
		 */

		/// First row

		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		add(new JLabel("User: "), gc);

		gc.gridx++;
		add(userField, gc);

		/// next row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		add(new JLabel("Password: "), gc);

		gc.gridx++;
		add(passwordField, gc); // spinner

		/// next row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		add(new JLabel("Port: "), gc);

		gc.gridx++;
		add(portSpinner, gc); // spinner

		//// next row

		gc.gridy++;

		gc.gridx = 0;
		add(okButton, gc);

		gc.gridx++;
		add(cancelButton, gc);

		// ----------------------------------------------------------

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// wiem, ze moj spinner przechowuje int, wiec kastuje obiekt na Integer, zeby
				// otrzymac jego aktualna wartosc
				Integer value = (Integer) portSpinner.getValue();

				String user = userField.getText();
				char[] password = passwordField.getPassword();
				
				System.out.println(user + ": " + new String(password));

				// sprawiam, ze okienko znika po kliknieciu przycisku OK/Cancel
				setVisible(false);

			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// sprawiam, ze okienko znika po kliknieciu przycisku OK/Cancel
				setVisible(false);
			}
		});

		setSize(400, 300);
		setLocationRelativeTo(parent);

	}

}

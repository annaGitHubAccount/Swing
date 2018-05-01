package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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

	private PreferencesListener preferencesListener;

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

		layoutControlsSet();

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// wiem, ze moj spinner przechowuje int, wiec kastuje obiekt na Integer, zeby
				// otrzymac jego aktualna wartosc
				Integer portNumber = (Integer) portSpinner.getValue();

				String user = userField.getText();
				char[] password = passwordField.getPassword();

				// System.out.println(user + ": " + new String(password));

				if (preferencesListener != null) {
					preferencesListener.preferencesSet(user, new String(password), portNumber);
				}

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

		setSize(340, 250);
		setLocationRelativeTo(parent);

	}

	// ustawiam defaultowe dane w okienku
	public void setDefaults(String user, String password, int portNumber) {
		userField.setText(user);
		passwordField.setText(password);
		portSpinner.setValue(portNumber);
	}

	// ustawiam PreferencesListener, zeby przechwycic wprowadzane dane do okna
	// Preferences... i przekazac je do MainFrame
	public void setPreferencesListener(PreferencesListener preferencesListener) {
		this.preferencesListener = preferencesListener;

	}

	
	
	private void layoutControlsSet() {
		
		
		
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		TitledBorder titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		
		//buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		

		controlsPanel.setLayout(new GridBagLayout());

		// constraints to GridBagLayout:
		GridBagConstraints gc = new GridBagConstraints();

		/*
		 * gridx: -------
		 * 
		 * gridy: 
		 * | 
		 * | 
		 * |
		 */
		
		Insets rightPadding = new Insets(0, 0, 0, 15);  // dodaje odstep miedzy label i field
		Insets noPadding = new Insets(0, 0, 0, 0); 

		/// First row

		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST; // powoduje ze label i field beda blisko siebie
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("User: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(userField, gc); // powoduje ze label i field beda blisko siebie

		/// next row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Password: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(passwordField, gc); // spinner

		/// next row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Port: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(portSpinner, gc); // spinner

		
		//// buttons panel

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		// dzieki temu oba buttony maja ten sam rozmiar
		Dimension buttonSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(buttonSize);
		
		
		
		// add sub panels to dialog
		
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

	}

}

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	// Komponenty
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okButton;
	private FormListener formListener;
	private JList ageList;
	private JComboBox employmentCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup; 
	
	
	
	
	public FormPanel() {

		Dimension dimension = getPreferredSize();
		dimension.width = 250;
		setPreferredSize(dimension);

		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList<>();
		employmentCombo = new JComboBox<>();
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		okButton = new JButton("OK");
		
		
		
		// set up mnemonics for OK Button --> ALT + O:
		okButton.setMnemonic(KeyEvent.VK_O);
		
		// mnemonic dla JTextField nameField. Dla pola tekstowego nie da sie ustawic mnemonic, tylko dla Label przy tym polu tekstowym --> ALT + N:
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
			
		
		
	
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		
		// zeby przechwycic, ktory radio button jest klikniety, a ktory nie - nie uzywa sie tu Action Listener, tylko setActionCommand():
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		
		genderGroup = new ButtonGroup();
		
		maleRadio.setSelected(true);
		
		
		// set up gender radio buttons
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		
		
		
		
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);
		
		
		citizenCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// actionListener wykona sie gdy citizenCheck jest lub nie jest zaznaczony
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);
				
				
			}
		});
		
		
		
		
		
		ageList.setPreferredSize(new Dimension(110, 70));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		 

		
		DefaultListModel ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
		ageList.setModel(ageModel);
		ageList.setSelectedIndex(1);

		
		
		DefaultComboBoxModel employmentModel = new DefaultComboBoxModel();
		employmentModel.addElement("employed");
		employmentModel.addElement("self-employed");
		employmentModel.addElement("unemployed");
		employmentCombo.setModel(employmentModel);
		employmentCombo.setSelectedIndex(0); 
		employmentCombo.setEditable(true);  
				
		
		
		

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				// pobieram dane z pola tekstowego:
				String name = nameField.getText();
				String occupation = occupationField.getText();
				
				// pobieram dane z JList:
				AgeCategory ageCategory = (AgeCategory) ageList.getSelectedValue();
				
				// pobieram dane z combobox - podobnie robilam juz z listBox / JList ageList:
				String employmentCategory = (String)employmentCombo.getSelectedItem();
				
				// pobieram dane z check box
				String taxID = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();
				
				// klikniety radio button mozna przechwycic tak - getActionCommand():
				ButtonModel selection = genderGroup.getSelection();
				String gender = selection.getActionCommand(); // zwroci "male" / "female"
				
				
				// sprawdzenie na konsoli wartosci z combobox
				System.out.println(employmentCategory);

			
				FormEvent formEvent = new FormEvent(this, name, occupation, ageCategory.getId(), employmentCategory, taxID, usCitizen, gender);
				
				if (formListener != null) {
					formListener.formEventOccured(formEvent);

				}

			}
		});

		Border insideBorder = BorderFactory.createTitledBorder("Add Person");
		Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

	
		setLayoutComponents();

	}
	
	
	
	
	public void setLayoutComponents() {
		
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// wiersze sa numerowane od 0 poprzez zmienna gc.gridy
		
		//////// First row ////////////
		
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

		//////// next row ////////////

		gc.gridy++;  // zamiast numerowac kazdy wiersz, uzywam ++
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridy = 1;
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

		//////// next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Age: "), gc); // nie musze tworzyc Label jako zmiennej, moge tylko utworzyc jej obiekt

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(ageList, gc);

		
		////////next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Employment: "), gc);  // nie musze tworzyc Label jako zmiennej, moge tylko utworzyc jej obiekt

		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(employmentCombo, gc);
		
		
		////////next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("US Citizen: "), gc);  // nie musze tworzyc Label jako zmiennej, moge tylko utworzyc jej obiekt

		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(citizenCheck, gc);
		
		
		////////next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(taxLabel, gc); 

		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(taxField, gc);
		
		
		////////next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.05;
		
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Gender: "), gc); 

		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(maleRadio, gc);
		
		
		////////next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(femaleRadio, gc);
		
		//////// next row ////////////

		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 2.0;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(okButton, gc);
		
	}
	
	
	

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;

	}

}





// zamiast wartosci w JList jako String, przyporzadkowuje String do ID
class AgeCategory {

	private int id;
	private String text;

	public AgeCategory(int id, String text) {

		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public int getId() {
		return id;
	}
}

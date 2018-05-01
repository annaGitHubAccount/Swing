import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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

		
		ageList.setPreferredSize(new Dimension(110, 70));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		 

		
		DefaultListModel ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
		ageList.setModel(ageModel);
		ageList.setSelectedIndex(1);

		
		// set up combo box
		DefaultComboBoxModel employmentModel = new DefaultComboBoxModel();
		employmentModel.addElement("employed");
		employmentModel.addElement("self-employed");
		employmentModel.addElement("unemployed");
		employmentCombo.setModel(employmentModel);
		employmentCombo.setSelectedIndex(0); // ustawiam 1 element w bombobox zeby byl wybrany/podswietlony
		employmentCombo.setEditable(true);  // moge wpisac jakakolwiek wartosc do combobox i bedzie ona wyswietlona
				
		
		
		okButton = new JButton("OK");

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
				
				// sprawdzenie na konsoli wartosci z combobox
				System.out.println(employmentCategory);

			
				FormEvent formEvent = new FormEvent(this, name, occupation, ageCategory.getId(), employmentCategory);
				
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

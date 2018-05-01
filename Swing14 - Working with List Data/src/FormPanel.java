import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
	private JList<AgeCategory> ageList;

	public FormPanel() {

		Dimension dimension = getPreferredSize();
		dimension.width = 250;
		setPreferredSize(dimension);

		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList<>();

		ageList.setPreferredSize(new Dimension(110, 70));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		

		// set up list box
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "65 or over"));
		ageList.setSelectedIndex(1); 

		// Sets the model that represents the contents or "value" of the list, notifies
		// property change listeners, and then clears the list's selection.
		ageList.setModel(ageModel);

		
		
		okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				String name = nameField.getText();
				String occupation = occupationField.getText();

				// moja list ageList przechowywala tylko Stringi, co nie jest dobre, bo
				// kolejnosc Stringow moze sie zmienic w przszlosci.
				// Najlepiej uzywac jest ID do ktorego przypisany jest String --> class AgeCategory
				// String ageCategory = (String) ageList.getSelectedValue();

				AgeCategory ageCategory = (AgeCategory) ageList.getSelectedValue();

				// moge sprawdzic czy dziala. Wyniki po kliknieciu Button OK pojawia sie na konsoli:
				System.out.println(ageCategory.getId());

			
				FormEvent formEvent = new FormEvent(this, name, occupation, ageCategory.getId());

				if (formListener != null) {
					formListener.formEventOccured(formEvent);

				}

			}
		});

		Border insideBorder = BorderFactory.createTitledBorder("Add Person");
		Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// wiersze sa numerowane od 0 poprzez zmienna gc.gridy
		//////// First row ////////////

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);

		//////// Second row ////////////

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridy = 1;
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridy = 1;
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);

		//////// Third row ////////////

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(ageList, gc);

		//////// Fourth row ////////////

		gc.weightx = 1;
		gc.weighty = 2.0;

		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(okButton, gc);

	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;

	}

}

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

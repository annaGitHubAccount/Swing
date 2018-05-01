package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Person;



// PersonTableModel to jest wrapper dla moich danych, zeby je dobrze zaprezentowac w mojej tablicy JTable w TablePanel

public class PersonTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Person> db;

	
	private String[] columnNames = {"ID", "Name", "Occupation", "Age Category", "Employment Category", "US Citizen", "Tax ID"};
	
	
	// columnNames[column] oznacza ze dla kazdej kolumny spod indexu od 0-6 bedzie zwracany string - nazwa kolumny
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public int getColumnCount() {

		// 7 - bo tyle mam case w metodzie getValueAt();
		return 7;
	}

	
	// ta metoda pobiera dane dla kazdego wiersza i kolumny
	@Override
	public Object getValueAt(int row, int column) {

		Person person = db.get(row);

		switch (column) {

		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCategory();
		case 4:
			return person.getEmploymentCategory();
		case 5:
			return person.isUsCitizen();
		case 6:
			return person.getTaxID();

		}

		return null;
	}

}

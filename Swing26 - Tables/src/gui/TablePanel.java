package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Person;


public class TablePanel extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private PersonTableModel personTableModel;
	
	
	
	public TablePanel() {
	
		personTableModel = new PersonTableModel();
		table = new JTable(personTableModel);
		
		setLayout(new BorderLayout());
		
		// do TablePanel dodaje JTable
		// dzieki JScrollPane moge zmieniac rozmiar kolumn jak chce
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	
	
	public void setData(List<Person> db) {
		personTableModel.setData(db);
	}
	
	// tworze ta metode, zeby moja tablica odswierzala sie za kazdym razem jak dodaje dane
	public void refresh() {
		personTableModel.fireTableDataChanged();
	}

}

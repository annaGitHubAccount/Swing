package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import model.Person;


public class TablePanel extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private PersonTableModel personTableModel;
	private JPopupMenu jPopupMenu;
	private PersonTableListener personTableListener;
	
	
	
	public TablePanel() {
	
		personTableModel = new PersonTableModel();
		table = new JTable(personTableModel);
		jPopupMenu = new JPopupMenu();
		
		
		// popup menu: 
		
		JMenuItem removeItem = new JMenuItem("Delete row");
		jPopupMenu.add(removeItem);
		
		// MouseAdapter to implementacja MouseListener
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent event) {
				
				// ustalam miejsce/lokalizacje, gdzie znajduje sie moja mysz
				int row = table.rowAtPoint(event.getPoint());
				
				/* na konsoli moge sprawdzic, ktory wiersz znajduje sie pod mysza
				System.out.println(row);
				*/
				
				// wybieram/podswietlam wiersz mysza od-do:
				table.getSelectionModel().setSelectionInterval(row, row);
								
				
				// MouseEvent.BUTTON3 to prawy klawisz myszy
				if(event.getButton() == MouseEvent.BUTTON3) {
					
					// getX i geY to miejsce w ktorym znajduje sie mysz
					jPopupMenu.show(table, event.getX(), event.getY());
				}
			}
			
		});
		
		// teraz usuwam ten wiersz, ktory zostal klikniety
		removeItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				
				if(personTableListener != null) {
					personTableListener.rowDeleted(row);
					personTableModel.fireTableRowsDeleted(row, row);
					
				}
				
				
				/* na konsoli moge sprawdzic, ktory wiersz znajduje sie pod mysza
				System.out.println(row);
				*/
				
			}
			
			
			
		});
		
		// -------------------------------------------------------------
		
		
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	
	
	public void setData(List<Person> db) {
		personTableModel.setData(db);
	}
	
	
	public void refresh() {
		personTableModel.fireTableDataChanged();
	}
	
	
	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
		
	}

}

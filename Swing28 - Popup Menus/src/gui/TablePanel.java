package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
	private JPopupMenu jPopupMenu;
	
	
	
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
				
				// MouseEvent.BUTTON3 to prawy klawisz myszy
				if(event.getButton() == MouseEvent.BUTTON3) {
					
					// getX i geY to miejsce w ktorym znajduje sie mysz
					jPopupMenu.show(table, event.getX(), event.getY());
				}
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

}

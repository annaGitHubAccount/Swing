import java.util.EventObject;

public class FormEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// tworze pola, bo chce zeby ta klasa przechowywala tymczasowo dane i przekazywala je do MainFrame
	private String name;
	private String occupation;

	// konstruktor, ktory bierze za argument zrodlo Eventu, czyli tutaj FormPanel a dokladnie OK Button, w ktorym wykonuje sie ActionEvent.
	public FormEvent(Object source) {
		super(source);
	}
	
	
	// przekazuje wartosci z parametrow do zmiennych obiektu
	public FormEvent(Object source, String name, String occupation) {
		super(source);
		
		this.name = name;
		this.occupation = occupation;
	} 
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}

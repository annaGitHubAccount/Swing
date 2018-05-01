import java.util.EventObject;

public class FormEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// tworze pola, bo chce zeby ta klasa przechowywala tymczasowo dane i przekazywala je do MainFrame
	private String name;
	private String occupation;
	private int ageCategory;
	private String employmentCategory;

	// konstruktor, ktory bierze za argument zrodlo Eventu, czyli tutaj FormPanel a dokladnie OK Button, w ktorym wykonuje sie ActionEvent.
	public FormEvent(Object source) {
		super(source);
	}
	
	
	// konstruktor, ktory bierze za argument zrodlo Eventu, czyli tutaj FormPanel a dokladnie OK Button, w ktorym wykonuje sie ActionEvent + 
	// przekazuje wartosci z parametrow do zmiennych obiektu
	public FormEvent(Object source, String name, String occupation, int ageCat, String employmentCategory) {
		super(source);
		
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCat;
		this.setEmploymentCategory(employmentCategory);
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


	public int getAgeCategory() {
		return ageCategory;
	}


	public void setAgeCategory(int ageCategory) {
		this.ageCategory = ageCategory;
	}


	public String getEmploymentCategory() {
		return employmentCategory;
	}


	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}
	
	

}

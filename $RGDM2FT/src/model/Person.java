package model;

import java.io.Serializable;

public class Person implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// zeby id samo sie inkrementowalo jak tworze Person:
	private static int count = 1;
	
	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory employmentCategory;
	private String taxID;
	private boolean usCitizen;
	private Gender gender;

	

	public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory employmentCategory,
			String taxID, boolean usCitizen, Gender gender) {
		
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.employmentCategory = employmentCategory;
		this.taxID = taxID;
		this.usCitizen = usCitizen;
		this.gender = gender;
		
		// zeby id samo sie inkrementowalo jak tworze Person:
		this.id = count;
		count++;
	}
	
	
	// ten konstruktor wywoluje inny konstruktor z tej samej klasy, zeby uniknac duplikowania sie kodu.
	// i jeszcze dodaje id, ktore ustawia. Nie ma tu zmiennej count.
	public Person(int id, String name, String occupation, AgeCategory ageCategory, EmploymentCategory employmentCategory,
			String taxID, boolean usCitizen, Gender gender) {
		
		this(name, occupation, ageCategory, employmentCategory, taxID, usCitizen, gender);
		
		this.id = id;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory(EmploymentCategory employmentCategory) {
		this.employmentCategory = employmentCategory;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(boolean usCitizen) {
		this.usCitizen = usCitizen;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}


	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	
	

}

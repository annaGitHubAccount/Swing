package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;


// przez kontroler komunikuja sie ze soba gui i model

public class Controller {

	Database db = new Database();

	// ta metoda to wrapper dla mojej bazy danych. I ta metode moge wywolac w gui
	// ta liste osob List<Person> musze teraz dopasowac do PersonTableModel. Ta klasa znajduje sie w TablePanel.
	public List<Person> getPeople(){
		return db.getPeople();
	}
	
	
	
	// this method turns person event object into the right form for data model 
	// dopasowuje event objekt do mojej bazy danych i dodaje objekt Person do bazy danych 
	public void addPerson(FormEvent event) {

				
		String name = event.getName();
		String occupation = event.getOccupation();
		int ageCategoryID = event.getAgeCategory();
		String employmentCategory = event.getEmploymentCategory();
		boolean isUsCitizen = event.isUsCitizen();
		String taxID = event.getTaxID();
		String gender = event.getGender();

		
		// zamieniam rozne typy danych na Enums, zeby moc wlozyc je do konstruktora Person:
		AgeCategory ageCategory = null;

		switch (ageCategoryID) {

		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		}

		
		
		EmploymentCategory empCategory;

		if (employmentCategory.equals("employed")) {
			empCategory = EmploymentCategory.employed;
		} else if (employmentCategory.equals("self-employed")) {
			empCategory = EmploymentCategory.selfEmployed;
		} else if (employmentCategory.equals("unemployed")) {
			empCategory = EmploymentCategory.unemployed;
		} else {
			empCategory = EmploymentCategory.other;
			System.out.println(employmentCategory);
		}

		
		
		Gender genderCategory;

		if (gender.equals("male")) {
			genderCategory = Gender.male;
		} else {
			genderCategory = Gender.female;
		}

		Person person = new Person(name, occupation, ageCategory, empCategory, taxID, isUsCitizen, genderCategory);

		db.addPerson(person);
		
	}
	
	
	// nowe metody, ktore opakowuja mi metody z Database:
	
	public void saveToFile(File file) throws IOException{
		
		db.saveToFile(file);
	}
	
	
	public void loadFromFile(File file) throws IOException{
		
		db.loadFromFile(file);
	}
	

}

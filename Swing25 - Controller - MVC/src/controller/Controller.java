package controller;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {

	
	
	
	public void addPerson(FormEvent event) {

		Database db = new Database();

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

	}

}

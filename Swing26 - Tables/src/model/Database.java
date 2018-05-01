package model;

import java.util.ArrayList;
import java.util.List;

//ta klasa sluzy chwilowo za baze danych. Pozniej podlaczymy prawdziwa baze danych.
public class Database {

	

	// w mojej bazie danych znajduje sie lista Person
	private ArrayList<Person> people;

	
	
	
	public Database() {

		people = new ArrayList<Person>();
	}
	
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public List<Person> getPeople(){
		return people;
	}

}

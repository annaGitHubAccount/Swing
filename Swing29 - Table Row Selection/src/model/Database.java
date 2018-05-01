package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	// nowe metody:
	
	public void saveToFile(File file) throws IOException {
		
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		//zapisuje ArrayList<Person> do ObjectOutputStream.
		// Mozna to zrobic na wiele sposobow, ale przy tym sposobie nie wystepuje: unchecked conversions warning. Poniewaz zapisujac
		// ArrayList do pliku, jej typ <Person> zostalby usuniety. Dlatego najpierw zamieniam ArrayList na Array i zapisuje do pliku.
		Person[] arrayOfPersons = people.toArray(new Person[people.size()]);
		objectOutputStream.writeObject(arrayOfPersons);  // tablica w Javie to objekt
		
		
		objectOutputStream.close();
		
	}
	
	
	
	public void loadFromFile(File file) throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		try {
			
			// Array zamieniam na ListArray
			 Person[] arrayOfPersons = (Person[])objectInputStream.readObject();
			 people.clear();
			 
			 List<Person> arrayAsList = Arrays.asList(arrayOfPersons);
			 people.addAll(arrayAsList);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		objectInputStream.close();
		
	}
	
	
	
	
	
	
	
	
	
	
	

}

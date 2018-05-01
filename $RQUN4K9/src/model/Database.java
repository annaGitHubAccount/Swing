package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//ta klasa sluzy chwilowo za baze danych. Pozniej podlaczymy prawdziwa baze danych.
public class Database {

	// w mojej bazie danych znajduje sie lista Person
	private List<Person> people;
	
	private Connection connection;
	

	public Database() {

		people = new ArrayList<Person>();
	}

	
	// lacze sie mySQL przy pomocy JDBC Driver
	public void connect() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:mysql://localhost:3306/swingtest";
		connection = DriverManager.getConnection(url, "root", "sys123");
		//System.out.println("Connection: " + connection);
	
	}

	public void disconnect() {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}

	
	
	public void save() throws SQLException {
		
		// sprawdzam (PreparedStatement) ile osob / jesli w ogole / znajduje sie w bazie danych, jesli tak -> update, jesli nie -> insert
		
		// ? bo nie mozna uzywac w zapytaniu sql doslownych wartosci. ? oznacza "jakies id"
		// The COUNT() function returns the number of rows that matches a specified criteria.
		String checkSql = "select count(*) as count from people where id=?";  
		PreparedStatement checkStatement = connection.prepareStatement(checkSql);
		
		for(Person person : people) {
			
			int id = person.getId();
			
			ResultSet checkResult = checkStatement.executeQuery();
			checkResult.next();
			int columnIndex = checkResult.getInt(1);
			System.out.println("Count for person with ID " + id + "is " + columnIndex);
			
		}
		
		checkStatement.close();
		
		
	}
	
	
	
	public void addPerson(Person person) {
		people.add(person);
	}

	public List<Person> getPeople() {

		// inne klasy nie beda mogly zmienic zawartosci listy. Maja read-only dostep.
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		// zapisuje ArrayList<Person> do ObjectOutputStream.
		// Mozna to zrobic na wiele sposobow, ale przy tym sposobie nie wystepuje:
		// unchecked conversions warning. Poniewaz zapisujac
		// ArrayList do pliku, jej typ <Person> zostalby usuniety. Dlatego najpierw
		// zamieniam ArrayList na Array i zapisuje do pliku.
		Person[] arrayOfPersons = people.toArray(new Person[people.size()]);
		objectOutputStream.writeObject(arrayOfPersons); // tablica w Javie to objekt

		objectOutputStream.close();

	}

	public void loadFromFile(File file) throws IOException {

		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		try {

			// Array zamieniam na ListArray
			Person[] arrayOfPersons = (Person[]) objectInputStream.readObject();
			people.clear();

			List<Person> arrayAsList = Arrays.asList(arrayOfPersons);
			people.addAll(arrayAsList);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		objectInputStream.close();

	}

	public void removePerson(int rowIndex) {

		people.remove(rowIndex);

	}

}

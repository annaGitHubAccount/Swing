package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		// System.out.println("Connection: " + connection);

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

		// sprawdzam (PreparedStatement) ile osob / jesli w ogole / pod danym id znajduje sie w bazie danych, jesli tak -> update, jesli nie -> insert

		// ? bo nie mozna uzywac w zapytaniu sql doslownych wartosci. ? oznacza "jakies id"
		// The COUNT() function returns the number of rows that matches a specified criteria.
		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStatement = connection.prepareStatement(checkSql);

		// to sa nazwy kolumn wziete z bazy danych mySQL. 
		//Nigdy nie powinno uzywac sie w zapytaniu SQL zmiennych uzywanych w pliku .java
		String insertSql = "insert into people (id, name, age, employment_status, tax_id, us_citizen, gender, occupation) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = connection.prepareStatement(insertSql);

		String updateSql = "update people set id=?, name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatement = connection.prepareStatement(updateSql);
		
		
		
		for (Person person : people) {

			int id = person.getId();
			AgeCategory ageCategory = person.getAgeCategory();
			EmploymentCategory employmentCategory = person.getEmploymentCategory();
			Gender gender = person.getGender();
			String name = person.getName();
			String occupation = person.getOccupation();
			String taxID = person.getTaxID();
			boolean usCitizen = person.isUsCitizen();

			ResultSet checkResult = checkStatement.executeQuery();
			checkResult.next();
			int count = checkResult.getInt(1);

			if (count == 0) {
				System.out.println("Inserting person with ID " + id);
				
				// wprowadzam parametry do values(?, ?, ?, ?, ?, ?, ?, ?) z zapytania sql w tej samej kolejnosci jak wpisalam nazwy kolumn po insert:
				int col = 1;
				insertStatement.setInt(col++,  id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++,  ageCategory.name());
				insertStatement.setString(col++, employmentCategory.name());
				insertStatement.setString(col++, taxID);
				insertStatement.setBoolean(col++,  usCitizen);
				insertStatement.setString(col++,  gender.name());
				insertStatement.setString(col++, occupation);
				
				insertStatement.executeUpdate();
				
			} else {
				System.out.println("Updating person with ID " + id);
				
				// wprowadzam parametry do zapytania sql w tej samej kolejnosci jak wpisalam nazwy kolumn po update (czyli 'id' jest teraz na koncu):
				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setString(col++,  ageCategory.name());
				updateStatement.setString(col++, employmentCategory.name());
				updateStatement.setString(col++, taxID);
				updateStatement.setBoolean(col++,  usCitizen);
				updateStatement.setString(col++,  gender.name());
				updateStatement.setString(col++, occupation);
				updateStatement.setInt(col++,  id);
				
				updateStatement.executeUpdate();
			}

		}

		updateStatement.close();
		insertStatement.close();
		checkStatement.close();

	}
	
	
	public void load() throws SQLException {
		
		people.clear();
		
		String sql = " select id, name, age, employment_status, tax_id, us_citizen, gender, occupation from people order by name";
		Statement selectStatement = connection.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
			
		
		while(results.next()) {
			
			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String employment_status = results.getString("employment_status");
			String tax_id = results.getString("tax_id");
			boolean us_citizen = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occupation = results.getString("occupation");
			
			
			Person person = new Person(id, name, occupation, AgeCategory.valueOf(age), EmploymentCategory.valueOf(employment_status), tax_id, us_citizen, Gender.valueOf(gender));
			people.add(person);
			
			//System.out.println(person);
			
		}
		
		results.close();
		selectStatement.close();
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

import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;


 
//normalnie tu powinny znajdowac sie JUnit tests, ale ten tutorial tego nie obejmuje
public class TestDatabase {

	public static void main(String[] args) {
		
		System.out.println("Running database test");
		
		
		
		Database db = new Database();
		
		try {
			db.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		db.addPerson(new Person("Joe", "builder", AgeCategory.adult, EmploymentCategory.employed, "777", true, Gender.male));
		db.addPerson(new Person("Sue", "builder", AgeCategory.senior, EmploymentCategory.selfEmployed, null, false, Gender.female));
		
		
		
		
		try {
			db.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		db.disconnect();
		
		

	}

}

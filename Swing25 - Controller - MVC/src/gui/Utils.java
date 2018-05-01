package gui;

public class Utils {

	
	public static String getFileExtension(String name) {
		
		int pointIndex = name.lastIndexOf(".");
		
		// metoda lastIndexOf() zwraca -1 jesli nie znajdzie stringa 
		if(pointIndex == -1) {
			return null;
		}
		
		// name.length()-1) czyli plik nie ma zadnego rozszerzenia
		if(pointIndex == name.length()-1) {
			return null;
		}
		
		return name.substring(pointIndex+1, name.length());
		
	}
	
}

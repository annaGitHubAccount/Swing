import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		
		// to jest po to zebym widziala foldery podczas wybierania pliku (gdy wyfiltruje na .per pliki). Inaczej nie bede mogla znalezc pliku ktorego szukam
		if(file.isDirectory()) {
			return true;
		}
		
		
		String name = file.getName();
		
		String fileExtension = Utils.getFileExtension(name);
		
		if(fileExtension == null) {
			return false;
		}
		
		if(fileExtension.equals("per")) {
			return true;
		}
		
		return false;
	}

	
	
	@Override
	public String getDescription() {
		return "Person database files (*.per)";
	}

}

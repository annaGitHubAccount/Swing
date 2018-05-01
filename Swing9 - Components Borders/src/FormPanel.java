import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FormPanel extends JPanel{
	
	public FormPanel() {

		Dimension dimension = getPreferredSize();
		
		// System.out.println(dimension); --> java.awt.Dimension[width=10,height=10]
		
		//zmieniam szerokosc panelu na:
		dimension.width = 250;
		setPreferredSize(dimension);
		
		// tworze Border wraz z tytulem
		Border insideBorder = BorderFactory.createTitledBorder("Add Person");
		
		// tworze pusty border i ustawiam jego wielkosc
		Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		// zeby polaczy oba Borders tworze:
		setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	}

}

import java.awt.Dimension;

import javax.swing.JPanel;

public class FormPanel extends JPanel{
	
	public FormPanel() {

		Dimension dimension = getPreferredSize();
		
		// System.out.println(dimension); --> java.awt.Dimension[width=10,height=10]
		
		//zmieniam szerokosc panelu na:
		dimension.width = 250;
		setPreferredSize(dimension);
		
		
	}

}

package problem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener {
	
	
	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand())) {
			System.out.println("Save");
		} 
		else if ("Restar".equals(e.getActionCommand())) {
			System.out.println("Restar");
		} 
		else {
			throw new UnsupportedOperationException();
		}
	}


}


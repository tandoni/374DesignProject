package problem.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class MenuActionListener implements ActionListener {
	JMenuItem item;

	public MenuActionListener(JMenuItem item) {
		this.item = item;
	}

	public void actionPerformed(ActionEvent e) {
		if ("Save".equals(e.getActionCommand())) {
			System.out.println("Save");
		} else if ("Restar".equals(e.getActionCommand())) {
			System.out.println("Restar");
		} else if (this.item.getText().equals("About")) {
			JFrame frame = new JFrame("About");
			JTextArea text = new JTextArea();
			text.setText("This is software allows you to parse an arbitrary number of Java files and determine "
					+ "what classes are using which design pattern.  Designed by Ruying Chen, Ishank Tandon, and Max Morgan, 2016.");
			text.setFont(new Font("Serif", Font.ITALIC, 16));
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setOpaque(false);
			text.setEditable(false);
			frame.add(text);
			frame.setMinimumSize(new Dimension(300, 200));
			frame.setVisible(true);
		} else if (this.item.getText().equals("Config Help")) {
			JFrame frame = new JFrame("About");
			JTextArea text = new JTextArea();
			text.setText("Below are different inputs and how the corresponding " + "outputs should be formatted.\n\n"
					+ "Input-Folder: " + "A file path " + "separated by /, and not including the C:, but instead "
					+ "beginning with /\n\n" + "Output-Directory: "
					+ "wherever you want the outgoing file to be directed.  Same "
					+ "notation as Input-Folder, and ./ preceding the path " + "Indicates from the current path\n\n"
					+ "Dot-Path: C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe "
					+ "(this is an example which is the one for Max Morgan's directory)."
					+ "  In general, this is the directory where the GraphViz " + "dot.exe file is located\n\n"
					+ "Phases: " + "Class-Loading for " + "Loading in classes to ASM.  " + "DOT-Generation to generate "
					+ "the GraphViz.gv file.  {Pattern}-Detection to detect a given "
					+ "pattern.  For example, Adapter-Detection will detect the adapter "
					+ "pattern.  a comma (,) and space separates each word.\n\n"
					+ "Decorator-MethodDelegation: An integer that indicates "
					+ "the number of methods between two classes that must exhibit "
					+ "the decorator pattern in order for the two classes to be "
					+ "recognized as an example of the decorator pattern.\n\n"
					+ "Adapter-MethodDelegation: An integer that indicates the number of methods in common between two "
					+ "Classes in order for them to be recognized as exhibiting the adapter "
					+ "pattern.\n\nSingleton-RequireGetInstance: " + "boolean to indicate "
					+ "whether a class must have a method named getInstance() to be "
					+ "identified as a Singleton.\n\nInput-Classes: Any number of classes"
					+ " with the path indicated by a period (.) instead of a / or \\"
					+ ", with each class separated by a comma (,) and a space, using either the full path name, if it is "
					+ "a Java class or random class in a directory, or a relative path name if it is in the workspace where "
					+ "the core code is.  Example: Input-Classes: java.awt.Component, java.awt.Container");
			text.setFont(new Font("Arial", Font.BOLD, 14));
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setOpaque(false);
			text.setEditable(false);
			frame.add(text);
			frame.setMinimumSize(new Dimension(650, 600));
			frame.setVisible(true);
		} else {
			throw new UnsupportedOperationException();
		}
	}
}

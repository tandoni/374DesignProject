package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewConfigFrame implements ActionListener {

	private JFrame frame;

	private JTextField inputClassesField;
	private JTextField outputDirField;
	private JTextField dotPathField;
	private JTextField phasesField;

	public void createNewConfig() {
		frame = new JFrame("New Config");
		frame.setMinimumSize(new Dimension(300, 400));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel inputClasses = new JLabel("Input classes wihch separated by ',' ");
		inputClasses.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel outputDir = new JLabel("Output Directory: ");
		JLabel dotPath = new JLabel("Dot Path: ");
		JLabel phases = new JLabel("Phases: ");

		inputClassesField = new JTextField();
		outputDirField = new JTextField();
		dotPathField = new JTextField();
		phasesField = new JTextField();

		JButton createConfig = new JButton("Create Config");
		createConfig.addActionListener(this);
		createConfig.setActionCommand("createNewConfig");

		//copy from moodle mileston7 example
		//TODO: correct into actual working fields and paths
		
		//Input-Folder: c:\\User1\\Documents\\Lab2-1\\src
		inputClassesField.setText("java.io.Reader,java.io.BufferedReader,java.lang.Runtime,org.asm.ClassVisitor");
		outputDirField.setText("input_output\\");
		dotPathField.setText("C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe");
		phasesField.setText("Class-Loading, Decorator-Detection, Singleton-Detection, DOT-Generation");
		//Adapter-MethodDelegation: 2
		//Decorator-MethodDelegation: 1
		//Singleton-RequireGetInstance: true
		
		panel.add(inputClasses);
		panel.add(inputClassesField);
		panel.add(outputDir);
		panel.add(outputDirField);
		panel.add(dotPath);
		panel.add(dotPathField);
		panel.add(phases);
		panel.add(phasesField);
		panel.add(createConfig);

		frame.setVisible(true);
		frame.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("createNewConfig")) {

			try {
				this.writeProperties();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
	}
	
	public void writeProperties() throws IOException {
		Properties p = new Properties();
		
		p.setProperty("Input-Classes", this.inputClassesField.getText());
		p.setProperty("Output-Directory", this.outputDirField.getText());
		p.setProperty("Dot-Path", this.dotPathField.getText());
		p.setProperty("Phases", this.phasesField.getText());
		
		File file = new File("resources/config.properties");
		FileOutputStream output = new FileOutputStream(file);
		p.store(output, "Properties");
		
		output.close();	
	}
}

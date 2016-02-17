package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfigFrame implements ActionListener {

	private JFrame frame;
	private JPanel panel;

	private String inputClasses;
	private String outputDir;
	private String dotPath;
	private String phases;
	private ArrayList<String> phasesList;

	public ConfigFrame() {
		frame = new JFrame("Configs");
		JPanel panel = new JPanel();

		JButton loadExistingButton = new JButton("Load existing");
		loadExistingButton.addActionListener(this);
		loadExistingButton.setActionCommand("loadExistingConfig");
		panel.add(loadExistingButton);

		JButton newButton = new JButton("New");
		newButton.setActionCommand("createNewConfig");
		newButton.addActionListener(this);
		panel.add(newButton);

		frame.add(panel);

		frame.setMinimumSize(new Dimension(300, 200));
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();

		if (cmd.equals("createNewConfig")) {
			NewConfigFrame newConfig = new NewConfigFrame();
			newConfig.createNewConfig();
			frame.dispose();
		}

		if (cmd.equals("loadExistingConfig")) {
			final JFileChooser fc = new JFileChooser();
			int retVal = fc.showDialog(frame, "Choose");

			if (retVal == 0) {

				if (fc.getSelectedFile().getName().endsWith(".properties")) {
					File pFile = fc.getSelectedFile();

					try {
						// copy to myConfig
						this.readProperties(pFile);
						writeProperties();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(frame, "choose successed");
					frame.dispose();

				} else {
					JOptionPane.showMessageDialog(frame, "choose failed");
				}

			} else {
				// no existing configurations
			}

		}
	}

	// copy for MyMainGUI's readProperties()
	private void readProperties(File file) throws IOException {

		FileInputStream input = new FileInputStream(file);
		Properties p = new Properties();

		if (input != null) {
			p.load(input);
			input.close();
		}

		this.dotPath = p.getProperty("Dot-Path");
		this.inputClasses = p.getProperty("Input-Classes");
		this.outputDir = p.getProperty("Output-Directory");
		this.phases = p.getProperty("Phases");

		this.phasesList = new ArrayList<String>();
		String[] splitPhases = phases.split(",");
		for (String phase : splitPhases) {
			phase = phase.trim();
			this.phasesList.add(phase);
		}

		String[] splitClasses = inputClasses.split(",");
		for (String clazz : splitClasses) {
			clazz = clazz.trim();
		}
	}

	public void writeProperties() throws IOException {
		Properties p = new Properties();

		p.setProperty("Input-Classes", this.inputClasses);
		p.setProperty("Output-Directory", this.outputDir);
		p.setProperty("Dot-Path", this.dotPath);
		p.setProperty("Phases", this.phases);

		File file = new File("input_output/config.properties");
		FileOutputStream output = new FileOutputStream(file);
		p.store(output, "Properties");

		output.close();
	}

}

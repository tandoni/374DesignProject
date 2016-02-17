package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import problem.asm.DesignParser;

public class LandingScreen implements ActionListener {

	private JFrame frame;
	private JPanel panel;
	
	private String inputClasses;
	private String outputDir;
	private String dotPath;
	private String phases;
	private ArrayList<String> phasesList;


	public void createScreen() {

		// frame
		this.frame = new JFrame("Design Parse");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(500, 500));

		// panel
		this.panel = new JPanel();

		JButton loadConfigButton = new JButton("Load Config");
		loadConfigButton.setActionCommand("loadConfig");
		loadConfigButton.addActionListener(this);
		this.panel.add(loadConfigButton);

		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setActionCommand("analyze");
		analyzeButton.addActionListener(this);
		this.panel.add(analyzeButton);

		this.panel.setVisible(true);
		this.frame.add(panel);

		// menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem saveItem = new JMenuItem();
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setText("Save");
		saveItem.setActionCommand("save");
		saveItem.addActionListener(this);

		fileMenu.add(saveItem);
		menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("get more information");

		menuBar.add(helpMenu);
		this.frame.setJMenuBar(menuBar);

		// let this Window to be sized to fit the preferred size and layouts of
		// its subcomponents.
		this.frame.pack();
	}

	public void disableFrame() {
		System.out.println("disable");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("analyze")) {
			this.analyzeClicked();
		} 
		
		if (cmd.equals("loadConfig")) {
			this.loadConfigClicked();
		} 
	}

	private void readProperties() throws IOException {
		File file = new File("resources/config.properties");
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

	private void analyzeClicked() {

		try {
			this.readProperties();
		} catch (IOException e1) {
			System.out.println("check is config.properties in ./input_output/");
		}

		// it should not be final(?), but otherwise ResultsGUI(dp) will give a
		// error
		final DesignParser dp = new DesignParser();

		// TODO : finish the connection to DesignParser, e.g. setting parse/
		// setting output directories

		Thread resultGui = new Thread(new Runnable() {
			@Override
			public void run() {

				ResultsScreen results = new ResultsScreen(dp);

				// try {
				// ResultsGUI results = new ResultsGUI(dp);
				// } catch (IOException e) {
				// e.printStackTrace();
				// }

			}
		});
		resultGui.start();
		this.frame.dispose();
	}

	private void loadConfigClicked() {
		ConfigFrame config = new ConfigFrame();
	}

}

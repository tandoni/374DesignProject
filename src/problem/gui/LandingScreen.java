package problem.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import problem.app.MyMainApp;

public class LandingScreen implements ActionListener {
	// This is the file that will be sent to MyMainApp to be loaded up.
	// Yes, it is meant to be a .txt file
	private File configFile = new File("./input_output/input.txt");
	private MyMainApp app = new MyMainApp();

	private JFrame frame;
	private JPanel panel;

	private String inputClasses;
	private String outputDir;
	private String dotPath;
	private String phases;
	private ArrayList<String> phasesList;

	public void createScreen() {

		// frame
		this.frame = new JFrame("Design Parser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(500, 500));

		// panel
		this.panel = new JPanel();
		GridLayout layout = new GridLayout(2, 2);
		JButton loadConfigButton = new JButton("Load Config");
		loadConfigButton.setActionCommand("loadConfig");
		loadConfigButton.addActionListener(this);
		this.panel.add(loadConfigButton);

		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setActionCommand("analyze");
		analyzeButton.addActionListener(this);
		this.panel.add(analyzeButton);

		JTextArea temp = new JTextArea(String.format("Note the default file to analyze is %s", configFile.getPath()));
		temp.setEditable(false);
		this.panel.add(temp);

		this.panel.setVisible(true);
		this.frame.add(panel);

		// menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new MenuActionListener());
		saveItem.setActionCommand("Save");
		
		JMenuItem restartItem = new JMenuItem("Restart");
		restartItem.addActionListener(new MenuActionListener());
		restartItem.setActionCommand("Restart");

		fileMenu.add(saveItem);
		fileMenu.add(restartItem);
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
			try {
				this.analyzeClicked();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (cmd.equals("loadConfig")) {
			this.loadConfigClicked();
		}
	}

	private void readProperties() throws IOException {
		MyMainApp.setFile(configFile);
	}

	private void analyzeClicked() throws IOException {

		try {
			this.readProperties();
		} catch (IOException e1) {
			System.out.println("check is config.properties in ./input_output/");
		}

		// The string array passed in does not matter
		MyMainApp.main(new String[2]);

		Thread resultGui = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					ResultsScreen results = new ResultsScreen(LandingScreen.this.app);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
		ConfigFrame config = new ConfigFrame(this);
	}

	public void setConfigFile(File f) {
		this.configFile = f;
	}

}

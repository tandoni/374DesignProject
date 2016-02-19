package problem.gui;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import problem.app.MyMainApp;

public class LandingScreen implements ActionListener {
	// This is the file that will be sent to MyMainApp to be loaded up.
	// Yes, it is meant to be a .txt file
	private File configFile = new File("./input_output/input.txt");
	private static MyMainApp app = new MyMainApp();

	private JFrame frame;
	private JPanel panel;
	public static JProgressBar loader;
	public static JTextArea loaderTxt;

	// private String inputClasses;
	// private String outputDir;
	// private String dotPath;
	// private String phases;
	// private ArrayList<String> phasesList;

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

		this.loader = new JProgressBar();
		this.loader.setForeground(Color.gray);
		this.loader.setMinimumSize(new Dimension(1000, 30));
		this.loader.setStringPainted(true);
		this.loader.setBorderPainted(true);
		this.loader.setVisible(false);
		this.panel.add(this.loader);

		this.panel.setVisible(true);
		this.frame.add(panel);

		// menu bar
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.addActionListener(this);
		saveItem.setActionCommand("save");

		JMenuItem restartItem = new JMenuItem("Restart");
		restartItem.setMnemonic(KeyEvent.VK_S);
		restartItem.addActionListener(this);
		restartItem.setActionCommand("restart");

		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.setMnemonic(KeyEvent.VK_S);
		quitItem.addActionListener(this);
		quitItem.setActionCommand("quit");

		fileMenu.add(saveItem);
		fileMenu.add(restartItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.setMnemonic(KeyEvent.VK_S);
		helpItem.addActionListener(this);
		helpItem.setActionCommand("help");

		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_S);
		aboutItem.addActionListener(this);
		aboutItem.setActionCommand("about");

		menuBar.add(helpMenu);
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);
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

		if (cmd.equals("save")) {
			this.saveClicked();
		}

		if (cmd.equals("restart")) {
			this.restartClicked();
		}

		if (cmd.equals("quit")) {
			this.quitClicked();
		}

		if (cmd.equals("help")) {
			this.helpClicked();
		}

		if (cmd.equals("about")) {
			this.aboutClicked();
		}
	}

	private void aboutClicked() {
		JOptionPane.showMessageDialog(null, "Program written by Ishank Tandon, Max Morgan, and Ruying Chen.", "About",
				JOptionPane.QUESTION_MESSAGE);
	}

	private void helpClicked() {
		// TODO Auto-generated method stub

	}

	private void quitClicked() {
		frame.dispose();
		System.exit(0);
	}

	private void restartClicked() {
		// TODO Auto-generated method stub

	}

	private void saveClicked() {
		// TODO Auto-generated method stub

	}

	private void readProperties() throws IOException {
		MyMainApp.setFile(configFile);
	}

	private void analyzeClicked() throws IOException {

		try {
			this.readProperties();
		} catch (IOException e1) {
			System.out.println("check is config.properties in ./input_output/");
			JOptionPane.showMessageDialog(null, "Please load or create a config.properties file.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		// if their exist any property
		this.loader.setMaximum(MyMainApp.classes.length);
		this.loader.setMinimum(0);
		this.loader.setVisible(true);
		// be update d in DesignParser's updateProgress()
		
		// The string array passed in does not matter
		MyMainApp.main(new String[2]);

//		Thread resultGui = new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				try {
//					ResultsScreen results = new ResultsScreen(LandingScreen.this.app);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				// try {
//				// ResultsGUI results = new ResultsGUI(dp);
//				// } catch (IOException e) {
//				// e.printStackTrace();
//				// }
//
//			}
//		});
//		resultGui.start();
//		this.frame.dispose();
	}

	private void loadConfigClicked() {
		ConfigFrame config = new ConfigFrame(this);
	}

	public void setConfigFile(File f) {
		this.configFile = f;
	}


	public static void showResult() {
		Thread resultGui = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					ResultsScreen results = new ResultsScreen(app);
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
		//this.frame.dispose();
		
	}

}

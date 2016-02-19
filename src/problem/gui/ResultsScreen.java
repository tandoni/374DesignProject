package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;

@SuppressWarnings("serial")
public class ResultsScreen extends JFrame {
	private String os = System.getProperty("os.name");
	Process p = null;
	public Panel panel;
	private static final int WIDTH = 1750;
	private static final int HEIGHT = 1080;
	Container content;
	MyMainApp app;
	private Response response;

	public ResultsScreen(MyMainApp app) throws IOException {
		this.app = app;
		// DesignParser dp = MyMainApp.getParser();
		// List<IClass> a = (List<IClass>) dp.model.getClasses();
		// String[] all = new String[a.size()];
		// for (int i = 0; i < all.length; i++) {
		// all[i] = a.get(i).getFullName();
		// }
		// dp.main(all);

		// debug exec
		// InputStream is = p.getErrorStream();
		// InputStreamReader isr = new InputStreamReader(is);
		// BufferedReader br = new BufferedReader(isr);
		// String line;
		//
		// System.out.printf("\n\n\n\n");
		//
		// while ((line = br.readLine()) != null) {
		// System.out.println(line);
		// }
		// end here

		super.setTitle("Python4Lyfe");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel = new Panel();
		this.panel.setUpParameters();

		this.response = new Response(this);
		this.setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.content = getContentPane();
		this.content.setLayout(new BorderLayout());
		// this.content.add(new ControlPanel(), BorderLayout.EAST);
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "stuff");
		border.setTitleJustification(TitledBorder.LEFT);
		this.panel.setBorder(border);
		runCMD();
		this.content.add(this.response, BorderLayout.WEST);
		// this.panel.setUpParameters();
		setVisible(true);

	}

	private void runCMD() throws IOException {
		synchronized (this) {
			if (os.toLowerCase().contains("windows")) {
				Runtime.getRuntime().exec(
						"\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot\" -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
			} else {
				p = Runtime.getRuntime().exec(
						"/usr/local/bin/dot -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
				// p = Runtime.getRuntime().exec("ls");

			}
			this.content.add(this.panel, BorderLayout.CENTER);
		}
	}

	/**
	 * This displays the classes (with checkboxes) in their respective patterns.
	 * 
	 * @author morganml
	 */
	class Response extends JPanel {
		// boolean to indicate whether this change in CB was from a pattern CB
		// being clicked. Used to determine if MyMainApp.main() should be ran
		// again or not
		private boolean patternCBClicked = false;
		// Map so that if a user selects a checkbox for the pattern, that
		// all classes that are that pattern achieve the same value as the
		// checkbox has become.
		HashMap<JCheckBox, ArrayList<JCheckBox>> patternCBoxtoChilds;
		// HashMap that matches a given checkbox to the class (in Model) it
		// represents
		HashMap<JCheckBox, IClass> cBoxToClass;
		HashMap<String, Boolean> pMap;
		// This list is to be checked against the hashMap of patterns from
		// Model to see if a given pattern exists in the UML
		Collection<String> patternList;
		// We want to know the classes that we started with before we started
		// checking/unchecking them.
		Collection<IClass> origClasses;
		private ResultsScreen resScreen;

		public Response(ResultsScreen resScreen) {
			this.resScreen = resScreen;
			TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "select");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setFont(new Font("Helvetica", Font.BOLD, 16));
			this.setPreferredSize(new Dimension(250, 5));
			origClasses = MyMainApp.getParser().model.getClasses();
			patternList = MyMainApp.getParser().model.getPatternNames();
			pMap = MyMainApp.getParser().model.getContainsPatternMap();
			patternCBoxtoChilds = new HashMap<JCheckBox, ArrayList<JCheckBox>>();
			cBoxToClass = new HashMap<JCheckBox, IClass>();
			generateResponse();
		}

		private void generateResponse() {
			// Iterate through the list of strings, and check the pattern map in
			// Model to see if that pattern is respresented in Model. If it is,
			// then find all classes with that pattern, and display their
			// checkboxes
			// below a checkbox that is the name of the pattern
			for (String pattern : patternList) {
				// If this is true, then we know this pattern is represented in
				// the that patterns map
				if (pMap.get(pattern)) {
					JPanel patternPanel = new JPanel();
					patternPanel.setLayout(new GridLayout(0, 1));
					JCheckBox patterncheck = new JCheckBox(pattern);
					patterncheck.setSelected(true);
					ItemListener patternItemListener = new MyPatternCBListener(this);
					patterncheck.addItemListener(patternItemListener);
					patternPanel.add(patterncheck);
					for (IClass c : MyMainApp.getParser().model.getClasses()) {
						if (c.getClassTypes2().containsKey(pattern)) {
							JCheckBox check = new JCheckBox(c.getName());
							cBoxToClass.put(check, c);
							ArrayList<JCheckBox> subs;
							if (this.patternCBoxtoChilds.containsKey(patterncheck)) {
								subs = this.patternCBoxtoChilds.get(patterncheck);
							} else {
								subs = new ArrayList<JCheckBox>();
							}
							subs.add(check);
							this.patternCBoxtoChilds.put(patterncheck, subs);

							check.setSelected(true);

							ItemListener itemListener = new MyItemListener(this, this.resScreen.panel);

							check.addItemListener(itemListener);
							check.setBorder(new EmptyBorder(0, 20, 0, 0));
							patternPanel.add(check);
						}
					}
					add(patternPanel);
				}
			}
		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
		}
	}

	/**
	 * ItemListener for the checkboxes corresponding to individual classes
	 * 
	 * @author morganml
	 *
	 */
	class MyItemListener implements ItemListener {
		Response response;
		Panel lPanel;

		MyItemListener(Response response, Panel lPanel) {
			this.response = response;
			this.lPanel = lPanel;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			AbstractButton abstractButton = (AbstractButton) e.getSource();
			boolean selected = abstractButton.getModel().isSelected();
			ArrayList<String> classes = MyMainApp.getClassez();
			if (selected) {
				System.out.println("selected");
				classes.add(this.response.cBoxToClass.get(abstractButton).getFullName().replace("/", "."));
				// MyMainApp.getParser().model.getClasses().add(this.response.cBoxToClass.get(abstractButton));
			} else {
				System.out.println("not selected");
				classes.remove(this.response.cBoxToClass.get(abstractButton).getFullName().replace("/", "."));
				// MyMainApp.getParser().model.getClasses().remove(this.response.cBoxToClass.get(abstractButton));
			}
			if (!this.response.patternCBClicked) {
				// The string array passed in does not matter
				try {
					DesignParser parser = MyMainApp.getParser();
					parser.model.resetModel();
					MyMainApp.callDP(parser);
					// MyMainApp.main(new String[2]);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					runCMD();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				this.response.repaint();
				// this.lPanel = new Panel();
				this.lPanel.revalidate();
				this.lPanel.setUpParameters();
				this.lPanel.revalidate();
				this.lPanel.repaint();
			}
		}
	}

	class MyPatternCBListener implements ItemListener {
		Response response;

		MyPatternCBListener(Response response) {
			this.response = response;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			this.response.patternCBClicked = true;
			AbstractButton abstractButton = (AbstractButton) e.getSource();
			boolean selected = abstractButton.getModel().isSelected();
			if (selected) {
				HashMap<JCheckBox, ArrayList<JCheckBox>> childs1 = this.response.patternCBoxtoChilds;
				ArrayList<JCheckBox> childs = childs1.get(abstractButton);
				for (JCheckBox child : childs) {
					child.setSelected(true);
				}
				System.out.println("selected");
			} else {
				HashMap<JCheckBox, ArrayList<JCheckBox>> childs1 = this.response.patternCBoxtoChilds;
				ArrayList<JCheckBox> childs = childs1.get(abstractButton);
				for (JCheckBox child : childs) {
					child.setSelected(false);
				}
				System.out.println("not selected");
			}
			try {
				DesignParser parser = MyMainApp.getParser();
				parser.model.resetModel();
				MyMainApp.callDP(parser);
				// MyMainApp.main(new String[2]);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				runCMD();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.response.repaint();
			// Set false again to initialize
			this.response.patternCBClicked = false;
		}
	}

	// class Panel extends JPanel {
	// String arg = "./input_output/graph1.png";
	// String arg2 = "./input_output/graph2.png";
	// JScrollPane p;
	// ImageIcon icon;
	// JLabel label;
	//
	// public Panel() {
	// this.setBackground(Color.white);
	//
	// }
	//
	// /**
	// * Call this to set up the parameters for the right pane to render.
	// */
	// protected void setUpParameters() {
	// icon = new ImageIcon(arg);
	// label = new JLabel(icon);
	// label.setIcon(icon);
	// label.revalidate();
	// label.setIcon(icon);
	// p = new JScrollPane(label, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	// ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	// p.setPreferredSize(new Dimension(1000, 722));
	// p.setVisible(true);
	// add(p);
	// }
	//
	// @Override
	// public void paintComponent(Graphics comp) {
	// super.paintComponent(comp);
	// }
	// }
}

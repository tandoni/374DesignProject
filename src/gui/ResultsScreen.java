package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import problem.asm.DesignParser;
import problem.interfaces.IClass;

public class ResultsScreen extends JFrame {
	public Panel panel;
	private static final int WIDTH = 1750;
	private static final int HEIGHT = 1080;
	Container content;
	public Response response;

	public ResultsScreen(DesignParser dp) {
		List<IClass> a = (List<IClass>) dp.model.getClasses();
		System.out.println("\n\n\n\n\n\n\n");
		String[] all = new String[a.size()];
		for (int i = 0; i < all.length; i++) {
			all[i] = a.get(i).getFullName();
		}

		super.setTitle("Python");
		this.panel = new Panel();
		this.response = new Response();

		this.setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.content = getContentPane();
		this.content.setLayout(new BorderLayout());
		// this.content.add(new ControlPanel(), BorderLayout.EAST);
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "stuff");
		border.setTitleJustification(TitledBorder.LEFT);
		this.panel.setBorder(border);
		this.content.add(this.panel, BorderLayout.CENTER);

		this.content.add(this.response, BorderLayout.WEST);

		setVisible(true);

	}

	class Response extends JTextArea {
		public Response() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "select");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setFont(new Font("Helvetica", Font.BOLD, 16));
			this.setPreferredSize(new Dimension(250, 5));
			this.append("selection logic goes here");
		}
	}

	class Panel extends JPanel {

		public Panel() {
		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
		}
	}
}

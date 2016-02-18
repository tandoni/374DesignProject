package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import problem.app.MyMainApp;
import problem.asm.DesignParser;
import problem.interfaces.IClass;

@SuppressWarnings("serial")
public class ResultsScreen extends JFrame {
	public Panel panel;
	private static final int WIDTH = 1750;
	private static final int HEIGHT = 1080;
	Container content;
	MyMainApp app;
	public Response response;

	public ResultsScreen(MyMainApp app) throws IOException {
		this.app = app;
		// DesignParser dp = MyMainApp.getParser();
		// List<IClass> a = (List<IClass>) dp.model.getClasses();
		// String[] all = new String[a.size()];
		// for (int i = 0; i < all.length; i++) {
		// all[i] = a.get(i).getFullName();
		// }
		// dp.main(all);
		Process p = null;
		String os = System.getProperty("os.name");
		if (os.toLowerCase().contains("windows")) {
			Runtime.getRuntime()
					.exec("\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot\" -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
		} else {
			p = Runtime.getRuntime()
					.exec("/usr/local/bin/dot -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
			// p = Runtime.getRuntime().exec("ls");

		}
		// InputStream is = process.getInputStream();
		// InputStreamReader isr = new InputStreamReader(is);
		// BufferedReader br = new BufferedReader(isr);
		// String line;
		//
		// System.out.printf("\n\n\n\n");
		//
		// while ((line = br.readLine()) != null) {
		// System.out.println(line);
		// }

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

	/**
	 * This displays the classes in their respective patterns.
	 * 
	 * @author morganml
	 */
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
		String arg = "input_output/graph1.png";
		// JScrollPane scroll;

		public Panel() {
			this.setBackground(Color.white);
			// scroll = new JScrollPane(this);
			// scroll.setBounds(10, 20, 100, 100);
			// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			// scroll.setViewportView(this);

		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);

			ImageIcon icon = new ImageIcon(arg);
			JLabel label = new JLabel();
			label.setIcon(icon);
			// scroll.setPreferredSize(label.getPreferredSize());
			// JScrollPane p = new JScrollPane();
			// p.setSize(300,300);
			// p.add(label);

			// scroll.add(label);
			// add(scroll);
			this.add(label);
			// Graphics2D g2 = (Graphics2D) comp;
			// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			// RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			// double scale = 0.5;
			// int w = label.getWidth();
			// int h = label.getHeight();
			// label.setSize((int)(w * scale), (int)(h *scale));
			// repaint();
			// int iW = icon.getIconWidth();
			// int iH = icon.getIconHeight();
			// double scale = 0.5;
			// g2.translate(w/2, h/2);
			// g2.scale(scale, scale);
			// g2.translate(-w/2, -h/2);
			// double x = (w - scale*iW)/2;
			// double y = (h - scale*iH)/2;
			// AffineTransform at = AffineTransform.getTranslateInstance(x, y);
			// at.scale(scale, scale);
			// g2.drawRenderedImage((RenderedImage) icon, at);
		}
	}
}

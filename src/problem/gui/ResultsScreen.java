package problem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import problem.app.MyMainApp;

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
			Runtime.getRuntime().exec(
					"\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot\" -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
		} else {
			p = Runtime.getRuntime()
					.exec("/usr/local/bin/dot -Tpng -o ./input_output/graph1.png ./input_output/GraphForGraphViz.gv");
			// p = Runtime.getRuntime().exec("ls");

		}

		// debug exec
		InputStream is = p.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		System.out.printf("\n\n\n\n");

		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		// end here

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

	class ScrollablePicture extends JLabel implements Scrollable, MouseMotionListener {

		private int maxUnitIncrement = 1;
		private boolean missingPicture = false;

		public ScrollablePicture(ImageIcon i, int m) {
			super(i);
			if (i == null) {
				missingPicture = true;
				setText("No picture found.");
				setHorizontalAlignment(CENTER);
				setOpaque(true);
				setBackground(Color.white);
			}
			maxUnitIncrement = m;

			// Let the user scroll by dragging to outside the window.
			setAutoscrolls(true); // enable synthetic drag events
			addMouseMotionListener(this); // handle mouse drags
		}

		// Methods required by the MouseMotionListener interface:
		public void mouseMoved(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			// The user is dragging us, so scroll!
			Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
			scrollRectToVisible(r);
		}

		public Dimension getPreferredSize() {
			if (missingPicture) {
				return new Dimension(320, 480);
			} else {
				return super.getPreferredSize();
			}
		}

		public Dimension getPreferredScrollableViewportSize() {
			return getPreferredSize();
		}

		public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
			// Get the current position.
			int currentPosition = 0;
			if (orientation == SwingConstants.HORIZONTAL) {
				currentPosition = visibleRect.x;
			} else {
				currentPosition = visibleRect.y;
			}

			// Return the number of pixels between currentPosition
			// and the nearest tick mark in the indicated direction.
			if (direction < 0) {
				int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
				return (newPosition == 0) ? maxUnitIncrement : newPosition;
			} else {
				return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
			}
		}

		public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
			if (orientation == SwingConstants.HORIZONTAL) {
				return visibleRect.width - maxUnitIncrement;
			} else {
				return visibleRect.height - maxUnitIncrement;
			}
		}

		public boolean getScrollableTracksViewportWidth() {
			return false;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		public void setMaxUnitIncrement(int pixels) {
			maxUnitIncrement = pixels;
		}
	}

	class Panel extends JPanel {
		String arg = "./input_output/graph1.png";
		JScrollPane p;

		public Panel() {
			this.setBackground(Color.white);

			// scroll.setBounds(10, 20, 100, 100);
			// scroll.setViewportView(this);
			ImageIcon icon = new ImageIcon(arg);
			JLabel label = new JLabel(icon);
//			ScrollablePicture pic = new ScrollablePicture(icon, 1000);
//			add(pic);
			p = new JScrollPane(label,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			p.setPreferredSize(new Dimension(1000, 722));
			p.setVisible(true);
			add(p);
		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);

			// scroll.setPreferredSize(label.getPreferredSize());
			// JScrollPane p = new JScrollPane();
			// p.add(label);

			// this.add(label);
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

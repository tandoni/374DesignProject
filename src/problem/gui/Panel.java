package problem.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Panel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String arg = "./input_output/graph1.png";
	String arg2 = "./input_output/uml.png";
	JScrollPane p;
	ImageIcon icon;
	JLabel label;

	public Panel() {
		this.setBackground(Color.white);
		this.label = new JLabel();
	}

	/**
	 * Call this to set up the parameters for the right pane to render.
	 */
	protected void setUpParameters() {
		this.removeAll();
		icon = new ImageIcon(arg);
		label.setIcon(icon);
		label.revalidate();
		label.setIcon(icon);
		p = new JScrollPane(label, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		p.setPreferredSize(new Dimension(1000, 722));
		p.setVisible(true);
		this.add(p);
		label.repaint();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
	}

}

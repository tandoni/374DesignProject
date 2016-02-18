package problem.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class SplitPanel {
	private JFrame frame;

	public SplitPanel() {
		init();
	}
	
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SplitPanel window = new SplitPanel();
				window.frame.setVisible(true);
			}
		});
	}

	private void init() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane();
		this.frame.getContentPane().add(splitPane, BorderLayout.WEST);
		
		JScrollPane scrollPane_right = new JScrollPane();
		splitPane.setRightComponent(scrollPane_right);
		
		JScrollPane scrollPane_left = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_left);
	}


}

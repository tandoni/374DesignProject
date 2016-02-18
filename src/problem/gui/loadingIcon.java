package problem.gui;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class loadingIcon {
	ImageIcon icon;
	String filePath;
	boolean retrieving = false;

	public loadingIcon(String filePath) {
		this.filePath = filePath;
	}

	public void paintIcon(final Component c, Graphics g, int x, int y) throws Exception, InterruptedException {
		if (icon != null) {
			icon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading...", x + 100, y + 20);
			Thread.sleep(1000);

			if (!retrieving) {
				retrieving = true;

				Thread retrievalThread = new Thread(new Runnable() {
					public void run() {

						icon = new ImageIcon(filePath);

						c.revalidate();
						c.repaint();

					}
				});
				retrievalThread.start();
			}
		}
	}

}

package hh.learnj.swing.guide.usecase;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class QRCodeMenuItem extends JMenuItem {

	private static final long serialVersionUID = -7741212084453396860L;
	
	protected JFrame frame;
	
	public QRCodeMenuItem(JFrame frame) {
		this.frame = frame;
		init();
	}
	
	protected void init() {
		this.setText("QRCode");
		this.setMnemonic(KeyEvent.VK_Q);
		this.setToolTipText("QRCode action.");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				QRCodeInternalFrame internalFrame = new QRCodeInternalFrame(frame);
				Container container = frame.getContentPane();
				JDesktopPane desktop = (JDesktopPane)container;
				if (desktop.getAllFrames().length == 0) {
					desktop.add(internalFrame);
	            } else {
	                desktop.remove(0);
	                desktop.add(internalFrame);
	                revalidate();
	                repaint();
	            }
				internalFrame.setVisible(true);
				// Every JInternalFrame must be added to content pane using JDesktopPane
				try {
					internalFrame.setSelected(true);
					internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});
	}

}

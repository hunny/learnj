package hh.learnj.swing.guide.usecase.searcher;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

public class FinderMenuItem extends JMenuItem {
	
	private static final long serialVersionUID = -6288966894526113606L;
	
	protected JFrame frame;
	
	public FinderMenuItem(JFrame frame) {
		this.frame = frame;
		init();
	}
	
	protected void init() {
		this.setText("Find...");
		this.setMnemonic(KeyEvent.VK_F);
		this.setToolTipText("File searcher.");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				FinderInternalFrame internalFrame = null;
				Container container = frame.getContentPane();
				JDesktopPane desktop = (JDesktopPane)container;
				if (desktop.getAllFrames().length != 0) {
	            	JInternalFrame [] frames = desktop.getAllFrames();
	            	for (JInternalFrame mFrame : frames) {
	            		if (mFrame instanceof FinderInternalFrame) {
	            			internalFrame = (FinderInternalFrame)mFrame;
	            			break;
	            		}
	            	}
//	                desktop.remove(0);
	            }
            	if (null == internalFrame) {
            		internalFrame = new FinderInternalFrame(frame);
	                desktop.add(internalFrame);
	                revalidate();
	                repaint();
            	}
				internalFrame.setVisible(true);
				// Every JInternalFrame must be added to content pane using JDesktopPane
				try {
					internalFrame.setSelected(true);
//					internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

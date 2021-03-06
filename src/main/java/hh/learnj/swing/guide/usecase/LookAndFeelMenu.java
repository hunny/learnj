package hh.learnj.swing.guide.usecase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelMenu extends JMenu {
	
	private static final long serialVersionUID = 6347752793353866230L;
	
	protected JFrame frame = null;
	
	public LookAndFeelMenu(JFrame frame) {
		this.frame = frame;
		init();
	}
	
	protected void init() {
		this.setText("Look&Feel");
		this.setMnemonic(KeyEvent.VK_L);
		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : plafs) {
			String lookAndFeelName = info.getName();
			JMenuItem menuItem = new JMenuItem(lookAndFeelName);
			menuItem.setToolTipText(info.getClassName());
			menuItem.setActionCommand(lookAndFeelName);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					SwingUtils.updateComponentLookAndFeelUI(frame, event.getActionCommand());
				}
			});
			this.add(menuItem);
		}
	}

}

package hh.learnj.swing.guide.usecase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

public class ExitMenuItem extends JMenuItem {

	private static final long serialVersionUID = 4749441998566989211L;
	
	public ExitMenuItem() {
		init();
	}
	
	protected void init() {
		this.setText("Exit");
//		ImageIcon icon = new ImageIcon(
//				this.getClass().getResource("/swing/icons/exit.png"));
		this.setMnemonic(KeyEvent.VK_E);
		this.setToolTipText("Exit application");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
	}

}

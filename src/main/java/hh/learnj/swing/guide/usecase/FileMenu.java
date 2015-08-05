package hh.learnj.swing.guide.usecase;

import hh.learnj.swing.guide.usecase.qrcode.QRCodeMenuItem;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = 7819918746782148538L;

	protected JFrame frame;
	
	public FileMenu(JFrame frame) {
		this.frame = frame;
		this.init();
	}
	
	protected void init() {
		this.setText("File");
		this.setMnemonic(KeyEvent.VK_F);
		this.add(new LookAndFeelMenu(frame));
		this.add(new QRCodeMenuItem(frame));
		this.add(new ExitMenuItem());
	}

}

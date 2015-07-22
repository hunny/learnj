package hh.learnj.swing.guide.usecase;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class SwingUseCase implements Runnable {

	public static String DEFAULE_LOOK_AND_FEEL = "Metal";

	public static void main(String[] args) {
		// schedule this for the event dispatch thread (edt)
		SwingUtilities.invokeLater(new SwingUseCase());
	}

	public void run() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("SwingUseCase");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screen.getHeight() / 2);
		int width = (int) (screen.getWidth() / 2);
		Dimension dimension = new Dimension(width, height);
		frame.setPreferredSize(dimension);
		frame.setSize(dimension);
		frame.setLocation(screen.width / 2 - frame.getSize().width / 2,
				screen.height / 2 - frame.getSize().height / 2);
		frame.pack();
		frame.setVisible(true);
		createMenuBar(frame);
		try {
			UIManager
					.setLookAndFeel(UseCaseSwingUtils.getLookAndFeelClassName(DEFAULE_LOOK_AND_FEEL));
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createMenuBar(final JFrame frame) {
		JMenuBar menubar = new JMenuBar();

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		menuFile.add(new LookAndFeelMenu(frame, "Look&Feel"));
		
		ImageIcon icon = new ImageIcon(
				this.getClass().getResource("/swing/icons/exit.png"));
		
		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menuFile.add(eMenuItem);
		
		menubar.add(menuFile);
		frame.setJMenuBar(menubar);
	}

}

package hh.learnj.swing.guide.usecase;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class SwingUseCase implements Runnable {

	public static String DEFAULE_LOOK_AND_FEEL = "Metal";

	protected JDesktopPane desktopPane;
	
	public static void main(String[] args) {
		// schedule this for the event dispatch thread (edt)
		SwingUtilities.invokeLater(new SwingUseCase());
	}

	public void run() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		//UIManager.getLookAndFeelDefaults().put("OptionPane.sameSizeButtons", true);
		SwingUtils.updateComponentLookAndFeelUI(createFrame(), DEFAULE_LOOK_AND_FEEL);
	}

	protected void createMenuBar(JFrame frame) {
		JMenuBar menubar = new JMenuBar();
		
		menubar.add(new FileMenu(frame));
		
		frame.setJMenuBar(menubar);
	}
	
	protected JFrame createFrame() {
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
		createMenuBar(frame);
		desktopPane = new JDesktopPane();
		frame.setContentPane(desktopPane);
		// Make dragging faster by setting drag mode to Outline
		desktopPane.putClientProperty("JDesktopPane.dragMode", "outline");
		frame.pack();
		frame.setVisible(true);
		return frame;
	}

}

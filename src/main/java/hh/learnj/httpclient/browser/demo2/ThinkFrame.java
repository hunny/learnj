/**
 * 
 */
package hh.learnj.httpclient.browser.demo2;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ThinkFrame {
	@SuppressWarnings("restriction")
	public ThinkFrame() {
		// 设置窗口外观
		try {
			UIManager.setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new MainUI(), "GUI初始化失败！", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
		}

		MainUI ui = new MainUI();
		ui.pack();
		ui.setVisible(true);
	}

	public static void main(String[] args) {
		new ThinkFrame();
	}
}

package hh.learnj.swing.guide.usecase;

import java.awt.Component;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SwingUtils {

	/**
	 * Returns the class name of the installed LookAndFeel with a name
	 * containing the name snippet or null if none found.
	 * 
	 * @param nameSnippet
	 *            a snippet contained in the Laf's name
	 * @return the class name if installed, or null
	 */
	public static String getLookAndFeelClassName(String nameSnippet) {
		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : plafs) {
			if (info.getName().contains(nameSnippet)) {
				return info.getClassName();
			}
		}
		return null;
	}
	
	public static void updateComponentLookAndFeelUI(Component component, String name) {
		try {
			UIManager
					.setLookAndFeel(getLookAndFeelClassName(name));
			SwingUtilities.updateComponentTreeUI(component);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

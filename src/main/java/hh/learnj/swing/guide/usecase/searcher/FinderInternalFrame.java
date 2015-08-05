package hh.learnj.swing.guide.usecase.searcher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

public class FinderInternalFrame extends JInternalFrame implements
		VetoableChangeListener {

	private static final long serialVersionUID = 626514033051912224L;

	protected JFrame frame;
	protected JTextField pathField = null;
	protected JTextField textField = null;
	protected JTextField typeField = null;
	protected JTextArea console = null;
	protected JLabel status = null;

	public FinderInternalFrame(JFrame frame) {
		super("File Finder.", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		this.frame = frame;
		pathField = new JTextField(20);
		textField = new JTextField(20);
		typeField = new JTextField(20);
		console = new JTextArea(15, 15);
		status = new JLabel();
		status.setText("Loaded.");
		init();
		addVetoableChangeListener(this);
		
		typeField.setText(".\\.sql$");
	}

	protected void init() {
		setSize(frame.getWidth(), frame.getHeight());
		setVisible(true);
		// Set the window's location.
		setLocation(0, 0);
		this.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

//		panel.setBorder(BorderFactory.createTitledBorder("Details..."));
		panel.setBorder(BorderFactory.createEtchedBorder());

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);

		int offset = 0;
		setConstraints(c, 0, offset, GridBagConstraints.EAST);
		panel.add(new JLabel("File Type "), c);
		setConstraints(c, 1, offset, GridBagConstraints.WEST);
		panel.add(typeField, c);

		offset++;
		setConstraints(c, 0, offset, GridBagConstraints.EAST);
		panel.add(new JLabel("Find Text "), c);
		setConstraints(c, 1, offset, GridBagConstraints.WEST);
		panel.add(textField, c);

		offset++;
		setConstraints(c, 0, offset, GridBagConstraints.EAST);
		panel.add(new JLabel("File Path "), c);
		setConstraints(c, 1, offset, GridBagConstraints.WEST);
		panel.add(pathField, c);

		offset++;
		setConstraints(c, 0, offset, GridBagConstraints.EAST);
		JButton choose = new JButton("Choose... ");
		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(pathField.getText());
				File dFile = null;
				if (null != file && file.exists()) {
					if (file.isDirectory()) {
						dFile = file;
					} else {
						dFile = file.getParentFile();
					}
				}
				String name = chooseForFile(dFile);
				if (null != name) {
					pathField.setText(name);
				}
			}
		});
		panel.add(choose, c);
		setConstraints(c, 1, offset, GridBagConstraints.WEST);
		JButton confirm = new JButton("Find...");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(pathField.getText());
				if (!file.exists()) {
					JOptionPane.showMessageDialog(frame, "File not existed ");
				} else {
					if (StringUtils.isBlank(textField.getText())) {
						JOptionPane.showMessageDialog(frame, "File Text is blank.");
						return;
					}
					if (StringUtils.isBlank(typeField.getText())) {
						JOptionPane.showMessageDialog(frame, "File Type is blank.");
						return;
					}
					try {
						long start = System.currentTimeMillis();
						status.setText("Finding...");
						List<String> result = check(file, textField.getText(), typeField.getText());
						status.setText("Finished. Result:" + result.size() + ". " + (System.currentTimeMillis() - start) + "ms.");
						console.setText("");
						for (String s : result) {
							System.out.print(s);
							console.append(s);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, e.getMessage());
					}
				}
			}
		});
		panel.add(confirm, c);
		
		offset ++;
		JScrollPane scrollPane = new JScrollPane(console);
		scrollPane.setPreferredSize(new Dimension(frame.getWidth() - 50, 150));
		c.weightx = 0.0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		setConstraints(c, 0, offset, GridBagConstraints.WEST);
		panel.add(scrollPane, c);

		this.add(panel);
		this.add(panel, BorderLayout.CENTER);
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(BorderFactory.createEtchedBorder());
		statusPanel.add(status, BorderLayout.WEST);
		this.add(statusPanel, BorderLayout.SOUTH);
		this.pack();
	}

	@Override
	public void vetoableChange(PropertyChangeEvent pce)
			throws PropertyVetoException {
		if (pce.getPropertyName().equals(IS_CLOSED_PROPERTY)) {
			boolean changed = ((Boolean) pce.getNewValue()).booleanValue();
			if (changed) {
				int option = JOptionPane.showOptionDialog(this, "Close "
						+ getTitle() + "?", "Close Confirmation",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (option != JOptionPane.YES_OPTION) {
					throw new PropertyVetoException("Cancelled", null);
				}
				Container container = frame.getContentPane();
				JDesktopPane desktop = (JDesktopPane)container;
				JInternalFrame [] frames = desktop.getAllFrames();
            	for (JInternalFrame mFrame : frames) {
            		if (mFrame instanceof FinderInternalFrame) {
            			desktop.remove(mFrame);
            		}
            	}
			}
		}
	}

	protected GridBagConstraints setConstraints(GridBagConstraints c,
			int gridx, int gridy, int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.anchor = anchor;
		return c;
	}

	protected String chooseForFile(File dFile) {
		JFileChooser fileChooser = new JFileChooser();
		if (null != dFile && dFile.isDirectory() && dFile.exists()) {
			fileChooser.setCurrentDirectory(dFile);
		} else {
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home")));
		}
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
		// "Image files", ImageIO.getReaderFileSuffixes()));
		// fileChooser.setFileFilter(new FileNameExtensionFilter(
		// "Image files", ImageIO.getReaderFileSuffixes()));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public List<String> check(File file, String key,
			final String matchFile) throws Exception {
		List<String> result = new ArrayList<String>();
		if (!file.isDirectory()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int found = 0;
			int n = 0;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				n++;
				String mLine = line.toLowerCase();
				if (isMatcher(mLine, key)) {// mLine.contains(key)
					found++;
					if (found == 1) {
						buffer.append("[+][File found]\t|");
						buffer.append(file.getAbsolutePath());
						buffer.append("\n");
					}
					buffer.append("[-][Line:");
					buffer.append(n);
					buffer.append("]\t|");
					buffer.append(line);
					buffer.append("\n");
				}
			}
			reader.close();
			if (found > 0) {
				result.add(buffer.toString());
				return result;
			}
			return Collections.emptyList();
		} else {
			File[] files = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					File mFile = new File(dir + "/" + name);
					if (mFile.isDirectory()) {
						return true;
					}
					return isMatcher(name, matchFile);// name.endsWith(".sql");
				}
			});
			for (File mFile : files) {
				List<String> mResult = check(mFile, key, matchFile);
				if (null != mResult && !mResult.isEmpty()) {
					result.addAll(mResult);
				}
			}
		}
		if (null == result || result.isEmpty()) {
			return Collections.emptyList();
		}
		return result;
	}

	public boolean isMatcher(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

}

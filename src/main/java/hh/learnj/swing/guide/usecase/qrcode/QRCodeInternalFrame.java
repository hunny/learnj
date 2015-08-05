package hh.learnj.swing.guide.usecase.qrcode;

import hh.learnj.qrcode.guide.zxing.QRCode;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.FileFilter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class QRCodeInternalFrame extends JInternalFrame implements
		VetoableChangeListener {

	private static final long serialVersionUID = 8261542418971359839L;

	protected JFrame frame;
	protected JTextField textField = null;
	protected JTextArea textArea = null;

	public QRCodeInternalFrame(JFrame frame) {
		super("QR Code", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		this.frame = frame;
		textField = new JTextField(20);
		textArea = new JTextArea(3, 20);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		init();
		addVetoableChangeListener(this);
	}

	protected void init() {
		setSize(frame.getWidth(), frame.getHeight());
		setVisible(true);
		// Set the window's location.
		setLocation(0, 0);
		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		panel.setBorder(BorderFactory.createTitledBorder("QRCode"));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3,3,3,3);
		
		setConstraints(c, 0, 0, GridBagConstraints.EAST);
		panel.add(new JLabel("File Path "), c);
		
		setConstraints(c, 1, 0, GridBagConstraints.WEST);
		panel.add(textField, c);
		
		setConstraints(c, 0, 1, GridBagConstraints.EAST);
		panel.add(new JLabel("File "), c);
		
		setConstraints(c, 1, 1, GridBagConstraints.WEST);
		JButton choose = new JButton("Choose... ");
		choose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(textField.getText());
				File dFile = null;
				if (null != file && file.exists()) {
					if (file.isDirectory()) {
						dFile = file;
					} else {
						dFile = file.getParentFile();
					}
				}
				String name = chooseForFile(dFile);
				System.out.println(name);
				if (null != name) {
					textField.setText(name);
				}
			}
		});
		panel.add(choose, c);
		
		setConstraints(c, 0, 2, GridBagConstraints.EAST);
		panel.add(new JLabel("Read "), c);
		
		setConstraints(c, 1, 2, GridBagConstraints.WEST);
		JButton read = new JButton("Read QR");
		read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = QRCode.readInfo(textField.getText());
					textArea.setText(message);
				} catch (Exception e) {
					e.printStackTrace();
					// JOptionPane.showMessageDialog (frame, "Message", "Title",
					// JOptionPane.INFORMATION_MESSAGE);
					// JOptionPane.showMessageDialog (null, "Message", "Title",
					// JOptionPane.WARNING_MESSAGE);
					JOptionPane.showMessageDialog(frame, e.getMessage(),
							"Title", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(read, c);
		
		setConstraints(c, 0, 3, GridBagConstraints.EAST);
		panel.add(new JLabel("Write "), c);
		
		setConstraints(c, 1, 3, GridBagConstraints.WEST);
		JButton write = new JButton("Generate QR");
		write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					QRCode.writeInfo(textField.getText(), textArea.getText());
					JOptionPane.showMessageDialog(frame, "Success", "Title",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
					// JOptionPane.showMessageDialog (frame, "Message", "Title",
					// JOptionPane.INFORMATION_MESSAGE);
					// JOptionPane.showMessageDialog (null, "Message", "Title",
					// JOptionPane.WARNING_MESSAGE);
					JOptionPane.showMessageDialog(frame, e.getMessage(),
							"Title", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(write, c);

		setConstraints(c, 0, 4, GridBagConstraints.EAST);
		panel.add(new JLabel("Info "), c);
		
		setConstraints(c, 1, 4, GridBagConstraints.WEST);
		panel.add(textArea, c);

		this.add(panel);
		this.add(panel, BorderLayout.NORTH);

		// this.setLayout(new GridLayout(3, 2));
		// this.add(new JButton("Button 1"));
		// this.add(new JButton("Button 2"));
		// this.add(new JButton("Button 3"));
		// this.add(new JButton("Button 4"));
		// this.add(new JButton("Button 5"));
		// this.add(new JButton("Button 6"));
		// this.add(new JButton("Button 7"));
		// this.add(new JButton("Button 8"));
		this.pack();
	}

	protected void setConstraints(GridBagConstraints c, int gridx, int gridy,
			int anchor) {
		c.gridx = gridx;
		c.gridy = gridy;
		c.anchor = anchor;
	}

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
            		if (mFrame instanceof QRCodeInternalFrame) {
            			desktop.remove(mFrame);
            		}
            	}
			}
		}
	}

	protected String chooseForFile(File dFile) {
		JFileChooser fileChooser = new JFileChooser();
		if (null != dFile && dFile.isDirectory() && dFile.exists()) {
			fileChooser.setCurrentDirectory(dFile);
		} else {
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home")));
		}
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
			    "Image files", ImageIO.getReaderFileSuffixes()));
//		fileChooser.setFileFilter(new FileNameExtensionFilter(
//			    "Image files", ImageIO.getReaderFileSuffixes()));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

}

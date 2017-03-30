/**
 * 
 */
package hh.learnj.httpclient.browser.demo2;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JMPWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2953320982119198128L;
	// 面板容器
	JPanel contentpane;
	// 两个单独的面板
	JPanel textpanel = new JPanel();
	JPanel buttonpanel = new JPanel();
	// 声明文本区域用于显示源代码
	JTextArea textarea = new JTextArea();
	JScrollPane scrollpane = new JScrollPane();
	// 保存和关闭按钮
	JButton closebutton = new JButton("关闭");
	JButton savebutton = new JButton("保存");
	// 源文件
	String sourcecode = "";
	// 页面名称
	String pageName = "";

	public JMPWindow() {
		// 设置窗口属性
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.setSize(new Dimension(800, 600));
		this.setTitle("帮助");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 添加面板
		contentpane = (JPanel) getContentPane();
		contentpane.setLayout(new BorderLayout());
		scrollpane.getViewport().add(textarea);
		textpanel.setLayout(new BorderLayout());
		textpanel.add(scrollpane, BorderLayout.CENTER);
		contentpane.add(textpanel, BorderLayout.CENTER);
		// 设置按钮区域
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.add(closebutton);
		closebutton.setFocusable(true);
		contentpane.add(buttonpanel, BorderLayout.SOUTH);
		String linesep = "";
		linesep = System.getProperty("line.separator");
		String readme = "";
		String temp = "";
		BufferedReader bufferedreader = null;
		try {
			// 读取readme文件
			File readmefile = new File("readme.txt");
			FileReader filereader;
			filereader = new FileReader(readmefile);
			bufferedreader = new BufferedReader(filereader);
			while ((temp = bufferedreader.readLine()) != null) {
				readme = readme + temp + linesep;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(JMPWindow.this, "读取帮助文件出错！", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				bufferedreader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// readme不可编辑
		this.textarea.setEditable(false);
		this.textarea.setText(readme);
		closebutton.addActionListener(this);
	}

	public JMPWindow(String title, String urladdress) {
		// 设置窗口属性
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.sourcecode = urladdress;
		this.setSize(new Dimension(800, 600));
		this.setTitle("源代码");
		this.pageName = title;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 添加面板
		contentpane = (JPanel) getContentPane();
		contentpane.setLayout(new BorderLayout());
		scrollpane.getViewport().add(textarea);
		textpanel.setLayout(new BorderLayout());
		textpanel.add(scrollpane, BorderLayout.CENTER);
		contentpane.add(textpanel, BorderLayout.CENTER);
		// 设置按钮区域
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.add(savebutton);
		savebutton.setFocusable(true);
		buttonpanel.add(closebutton);
		closebutton.setFocusable(false);
		contentpane.add(buttonpanel, BorderLayout.SOUTH);
		this.textarea.setEditable(false);
		this.textarea.setText(sourcecode);
		savebutton.addActionListener(this);
		closebutton.addActionListener(this);
	}

	// 响应按钮事件
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closebutton) {
			dispose();
		} else if (e.getSource() == savebutton) {
			// 若单击保存按钮，则弹出保存对话框
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
			filechooser.showSaveDialog(JMPWindow.this);
			File savefile = filechooser.getSelectedFile();
			String savefilename = savefile.getPath();
			savefilename = savefilename + ".html";
			try {
				FileWriter filewriter = new FileWriter(savefilename);
				BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
				bufferedwriter.write(textarea.getText());
				bufferedwriter.close();
				filewriter.close();
			} catch (IOException event) {
				JOptionPane.showMessageDialog(JMPWindow.this, "保存失败!", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

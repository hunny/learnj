/**
 * 
 */
package hh.learnj.httpclient.browser.demo2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainUI extends JFrame {
	private static final long serialVersionUID = 1627994874131624139L;
	// 建立主框架
	JFrame think = new JFrame("think");
	// 建立主菜单栏
	JMenuBar menubar = new JMenuBar();
	// 文件菜单
	JMenu filemenu = new JMenu("文件(F)");
	JMenuItem newwindow = new JMenuItem("新窗口(N)");
	JMenuItem openfile = new JMenuItem("打开(O)");
	JMenuItem saveas = new JMenuItem("另存为(S)");
	JMenuItem quit = new JMenuItem("退出(Q)");
	// 查看菜单
	JMenu watchmenu = new JMenu("查看(V)");
	JMenuItem sourcecode = new JMenuItem("源代码(O)");
	// 书签菜单
	static JMenu bookmarkmenu = new JMenu("书签(B)");
	JMenuItem addbookmark = new JMenuItem("添加书签(B)");
	// 帮助菜单
	JMenu helpmenu = new JMenu("帮助(H)");
	JMenuItem help = new JMenuItem("think 帮助");
	JMenuItem about = new JMenuItem("关于 think");
	// 打开保存对话框
	JFileChooser openfilechooser = new JFileChooser("D://");
	JFileChooser saveasfilechooser = new JFileChooser("D://");
	// 定义三种窗口外观
	public final static String Windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public final static String Metal = "javax.swing.plaf.metal.MetalLookAndFeel";
	public final static String Motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	// 定义按钮的图标
	ImageIcon bookmarkicon = new ImageIcon(getClass().getResource("/icons/back.png"));
	ImageIcon forwardicon = new ImageIcon(getClass().getResource("/icons/forward.png"));
	ImageIcon backicon = new ImageIcon(getClass().getResource("/icons/back.png"));
	ImageIcon refreshicon = new ImageIcon(getClass().getResource("/icons/refresh.png"));
	ImageIcon homeicon = new ImageIcon(getClass().getResource("/icons/home.png"));
	ImageIcon goicon = new ImageIcon(getClass().getResource("/icons/go.png"));
	ImageIcon stopicon = new ImageIcon(getClass().getResource("/icons/stop.png"));
	// 建立工具栏
	JToolBar toolbar = new JToolBar();
	JButton bookmark = new JButton("书签", bookmarkicon);
	JButton back = new JButton("后退", backicon);
	JButton forward = new JButton("前进", forwardicon);
	JButton stop = new JButton("停止", stopicon);
	JButton refresh = new JButton("刷新", refreshicon);
	JButton home = new JButton("主页", homeicon);
	// 地址栏
	JLabel addresslabel = new JLabel("地址:");
	JTextField urlfield = new JTextField(50);
	JButton go = new JButton("转到", goicon);
	Box addressbox = new Box(BoxLayout.LINE_AXIS);
	JToolBar addresstoolbar = new JToolBar();
	// 建立显示网页的页面
	JEditorPane webpagepane = new JEditorPane();
	JScrollPane scrollpane = new JScrollPane(webpagepane);
	JScrollPane treescollpane = new JScrollPane();
	// 默认主页URL地址
	String urladdress = "http://www.baidu.com";
	// 建立分隔栏
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	// 创建树根节点
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("收藏夹");
	// 创建一个文件节点
	DefaultMutableTreeNode homepage = new DefaultMutableTreeNode("主页");
	JTree jtree = new JTree(root);
	// 建立一个链表来保存历史记录
	PageList historylist = new PageList();
	// 建立一个链表来保存书签记录
	PageList bookmarklist = new PageList();

	public MainUI() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		think.setPreferredSize(new Dimension(800, 600));
		int thinkWidth = this.getPreferredSize().width;
		int thinkHeight = this.getPreferredSize().height;
		think.setSize(thinkWidth, thinkHeight);
		think.setLocation((screenSize.width - thinkWidth) / 2, (screenSize.height - thinkHeight) / 2);
		// 设置窗口属性
		// think.setLocationRelativeTo(null);
		think.setLayout(new BorderLayout());
		think.setTitle("Think");
		think.setResizable(true);
		// 添加菜单栏
		think.setJMenuBar(menubar);
		menubar.add(filemenu);
		filemenu.setMnemonic('F');
		menubar.add(watchmenu);
		watchmenu.setMnemonic('V');
		menubar.add(bookmarkmenu);
		bookmarkmenu.setMnemonic('B');
		menubar.add(helpmenu);
		helpmenu.setMnemonic('H');
		// 添加菜单项
		filemenu.add(newwindow);
		// 添加键盘快捷方式
		newwindow.setMnemonic('N');
		newwindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newwindow.addActionListener(new Action());
		filemenu.addSeparator();
		filemenu.add(openfile);
		openfile.setMnemonic('O');
		openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		openfile.addActionListener(new Action());
		filemenu.add(saveas);
		saveas.setMnemonic('S');
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveas.addActionListener(new Action());
		filemenu.addSeparator();
		filemenu.add(quit);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		quit.addActionListener(new Action());
		watchmenu.add(sourcecode);
		sourcecode.setMnemonic('U');
		sourcecode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		sourcecode.addActionListener(new Action());
		bookmarkmenu.add(addbookmark);
		addbookmark.setMnemonic('D');
		addbookmark.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		addbookmark.addActionListener(new Action());
		helpmenu.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.BUTTON1_MASK));
		help.addActionListener(new Action());
		helpmenu.addSeparator();
		helpmenu.add(about);
		about.addActionListener(new Action());
		// 添加工具按钮
		toolbar.add(bookmark);
		bookmark.setFocusable(false);
		bookmark.setEnabled(true);
		bookmark.setHorizontalTextPosition(SwingConstants.RIGHT);
		bookmark.setVerticalTextPosition(SwingConstants.CENTER);
		bookmark.addActionListener(new Action());
		toolbar.add(back);
		back.setFocusable(false);
		back.setEnabled(false);
		back.setHorizontalTextPosition(SwingConstants.RIGHT);
		back.setVerticalTextPosition(SwingConstants.CENTER);
		back.addActionListener(new Action());
		toolbar.add(forward);
		forward.setFocusable(false);
		forward.setEnabled(false);
		forward.setHorizontalTextPosition(SwingConstants.RIGHT);
		forward.setVerticalTextPosition(SwingConstants.CENTER);
		forward.addActionListener(new Action());
		toolbar.add(stop);
		stop.setFocusable(false);
		stop.setHorizontalTextPosition(SwingConstants.RIGHT);
		stop.setVerticalTextPosition(SwingConstants.CENTER);
		stop.addActionListener(new Action());
		toolbar.add(refresh);
		refresh.setFocusable(false);
		refresh.setHorizontalTextPosition(SwingConstants.RIGHT);
		refresh.setVerticalTextPosition(SwingConstants.CENTER);
		refresh.addActionListener(new Action());
		toolbar.add(home);
		home.setFocusable(false);
		home.setHorizontalTextPosition(SwingConstants.RIGHT);
		home.setVerticalTextPosition(SwingConstants.CENTER);
		home.addActionListener(new Action());
		toolbar.addSeparator();
		addressbox.add(addresslabel, BorderLayout.WEST);
		addressbox.add(urlfield, BorderLayout.CENTER);
		urlfield.setFocusable(true);
		urlfield.addActionListener(new Action());
		urlfield.addKeyListener(new KeyAction());
		addressbox.add(go, BorderLayout.EAST);
		go.setFocusable(false);
		go.setHorizontalTextPosition(SwingConstants.RIGHT);
		go.setVerticalTextPosition(SwingConstants.CENTER);
		go.addActionListener(new Action());
		toolbar.add(addressbox);
		scrollpane.setPreferredSize(new Dimension(800, 600));
		root.add(homepage);
		jtree.updateUI();
		jtree.addTreeSelectionListener(new TreeNodeChange());
		treescollpane.setViewportView(jtree);
		splitPane.setDividerLocation(0);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerSize(0);
		splitPane.add(treescollpane);
		splitPane.add(scrollpane);
		// 获得内容面板,将菜单和工具栏添加到内容面板里面
		Container contentpane = getContentPane();
		// 将menubar放在框架的顶端
		setJMenuBar(menubar);
		contentpane.add(toolbar, BorderLayout.NORTH);
		contentpane.add(splitPane);
		// 程序加载时载入书签文件
		try {
			File loadbookmarklist = new File("BookMarkList.txt");
			FileReader filereader = new FileReader(loadbookmarklist);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			// 临时存放网页标题
			String temp = "";
			// 临时存放网页URL
			String content = "";
			while ((temp = bufferedreader.readLine()) != null) {
				// 由于每个标题对应一个网址，所以一次可以读取两行
				content = bufferedreader.readLine();
				// 将书签重新添加到树节点上
				DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(temp);
				root.add(treenode);
				jtree.updateUI();
				bookmarklist.addURL(temp, content);
			}
			bufferedreader.close();
			filereader.close();
		} catch (IOException event) {
			event.printStackTrace();
			JOptionPane.showMessageDialog(MainUI.this, "书签文件打开失败,可能是文件已丢失!", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
		}
		// 设置窗口关闭监听事件
		this.addWindowListener(new WindowState());
		this.addWindowStateListener(new WindowState());
		setNewPage(0, urladdress);
	}

	// 获得url地址的内容
	public String getContent(String urladdress) {
		String linesep;
		String line = "";
		String tempsource = "";
		linesep = System.getProperty("line.separator");
		try {
			// 用输入输出流读取URL对象
			URL source = new URL(urladdress);
			URLConnection urlconnection = source.openConnection();
			String encoding = urlconnection.getContentEncoding();
			System.out.println(encoding);
			InputStream inputstream = urlconnection.getInputStream();
			// 由于有的网页使用的字符集不同，导致有的网页源代码打开的时候出现乱码
			// 此时utf-8可以使用，但是会导致保存之后的网页
			// 在别的浏览器中打开的时候，依然是乱码
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "utf-8");
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			while ((line = bufferedreader.readLine()) != null) {
				tempsource = tempsource + line + linesep;
			}
			// 关闭流
			inputstream.close();
			bufferedreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempsource;
	}

	// 重设页面
	public void setNewPage(int flag, String urladdress) {
		String temp = getContent(urladdress);
		String title = getTitle(temp);
		try {
			urlfield.setText(urladdress);
			webpagepane.setEditable(false);
			webpagepane.setPage(urladdress);
			if (historylist.isPrePageNameExist(title)) {
				back.setEnabled(true);
			}
			if (historylist.isNextPageNameExist(title)) {
				forward.setEnabled(true);
			}
			urlfield.repaint();
			webpagepane.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 添加进历史链表
		if (flag == 0) {
			historylist.addURL(title, urladdress);
		}
		webpagepane.addHyperlinkListener(new HTMLView());
	}

	// 获取网页标题
	public String getTitle(String htmlcontent) {
		String temp = "";
		String regex = "<[Tt][Ii][Tt][Ll][Ee]>([^</[Tt][Ii][Tt][Ll][Ee]>]*)";
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(htmlcontent);
		while (match.find()) {
			int start = match.start();
			int end = match.end();
			temp = htmlcontent.substring(start + 7, end - 1);
		}
		return temp;
	}

	// 退出时处理书签文件的保存
	public void savebookmarklist() {
		File savefile = new File("BookMarkList.txt");
		String linesep = System.getProperty("line.separator");
		try {
			FileWriter filewriter = new FileWriter(savefile);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			// 将bookmarklist链表里面保存的记录一次写入文件
			String temppagename = "";
			String tempurladdress = "";
			PageNode head = bookmarklist.getPageNode().getNext();
			while (head != null) {
				// 读取链表里面的内容
				temppagename = head.getPagename();
				temppagename = temppagename + linesep;
				tempurladdress = head.getUrl();
				tempurladdress = tempurladdress + linesep;
				bufferedwriter.write(temppagename);
				bufferedwriter.write(tempurladdress);
				head = head.getNext();
			}
			bufferedwriter.close();
			filewriter.close();
		} catch (IOException event) {
			JOptionPane.showMessageDialog(MainUI.this, "书签列表保存失败!", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 处理事件的类
	private class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 处理菜单事件
			if (e.getSource() == newwindow) {
				// 生成新窗体
			}
			// 打开文件,由于不会解析HTML文件，只能以源代码的形式读出来了
			if (e.getSource() == openfile) {
				openfilechooser.setDialogTitle("打开");
				openfilechooser.setDialogType(JFileChooser.OPEN_DIALOG);
				int result = openfilechooser.showOpenDialog(MainUI.this);
				File fileopen = openfilechooser.getSelectedFile();
				String openfilename = fileopen.getPath();
				System.out.println(openfilename);
				if (result == JFileChooser.APPROVE_OPTION) {
					// 从硬盘目录下读取文件
					try {
						FileReader filereader = new FileReader(openfilename);
						BufferedReader bufferedreader = new BufferedReader(filereader);
						String temp = "";
						String content = "";
						String linesep = System.getProperty("line.separator");
						while ((temp = bufferedreader.readLine()) != null) {
							content = content + temp + linesep;
						}
						bufferedreader.close();
						filereader.close();
						urlfield.setText(openfilename);
						urlfield.revalidate();
						webpagepane.setText(content);
						webpagepane.revalidate();
					} catch (IOException event) {
						event.printStackTrace();
						JOptionPane.showMessageDialog(MainUI.this, "试图打开源代码文件失败!", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// 页面另存为
			if (e.getSource() == saveas) {
				urladdress = urlfield.getText().toString().trim();
				// 设置弹出对话框类型
				saveasfilechooser.setDialogTitle("另存为...");
				saveasfilechooser.setDialogType(JFileChooser.SAVE_DIALOG);
				int result = saveasfilechooser.showSaveDialog(MainUI.this);
				// 获取文件名
				File filesave = saveasfilechooser.getSelectedFile();
				String savefilename = filesave.getPath();
				savefilename = savefilename + ".html";
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						// 将文件存盘
						String temp = getContent(urladdress);
						FileWriter filewriter = new FileWriter(savefilename);
						BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
						bufferedwriter.write(temp);
						bufferedwriter.close();
						filewriter.close();
					} catch (IOException event) {
						JOptionPane.showMessageDialog(MainUI.this, "Web页面保存失败!", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// 退出浏览器
			if (e.getSource() == quit) {
				if (JOptionPane.showConfirmDialog(MainUI.this, "确定要退出吗？", "Think 提示您:", JOptionPane.OK_CANCEL_OPTION) == 0) {
					// 退出时将书签记录写入文件
					savebookmarklist();
					System.exit(0);
				} else {
					// Do Nothing
				}
			}
			// 查看源代码
			if (e.getSource() == sourcecode) {
				urladdress = urlfield.getText().toString().trim();
				if (urladdress.length() > 0 && !urladdress.startsWith("http://")) {
					urladdress = "http://" + urladdress;
				}
				if (!urladdress.equals("")) {
					String temp = getContent(urladdress);
					String title = getTitle(temp);
					temp = getContent(urladdress);
					// 建立源代码窗体对象
					JMPWindow viewsourcecode = new JMPWindow(title, temp);
					viewsourcecode.setVisible(true);
				} else if (urladdress.length() == 0) {
					JOptionPane.showMessageDialog(MainUI.this, "对不起,你输入了空地址,这是非法操作！", "Think 提示您:", JOptionPane.WARNING_MESSAGE);
				}
			}
			// 添加页面到书签
			if (e.getSource() == addbookmark) {
				urladdress = urlfield.getText().toString().trim();
				String temp = getContent(urladdress);
				String title = getTitle(temp);
				if (title != "" && urladdress != "") {
					if (bookmarklist.isPageNameExist(title) == false) {
						bookmarklist.addURL(title, urladdress);
						System.out.println("书签");
						System.out.println(title);
						System.out.println(urladdress);
						// 生成收藏夹窗口对象
						AddBookMark addbookmarkdone = new AddBookMark(title, urladdress, false);
						addbookmarkdone.setVisible(true);
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(title);
						root.add(node);
						jtree.updateUI();
					} else if (bookmarklist.isPageNameExist(title) == true) {
						AddBookMark addbookmarkdone = new AddBookMark(title, urladdress, true);
						addbookmarkdone.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(MainUI.this, "添加书签标题或者网址不能为空！", "Think 提示您:", JOptionPane.WARNING_MESSAGE);
				}
			}
			// 打开帮助文档
			if (e.getSource() == help) {
				// 待写一个chm格式的文档
				JMPWindow viewhelp = new JMPWindow();
				viewhelp.setVisible(true);
			}
			// 打开关于窗口
			if (e.getSource() == about) {
				JOptionPane.showMessageDialog(MainUI.this, "Think version 0.1" + "/n" + "谢谢您的使用!", "关于 Think",
						JOptionPane.INFORMATION_MESSAGE);
			}
			// 单击书签按钮事件：显示/隐藏
			if (e.getSource() == bookmark) {
				if (splitPane.getDividerLocation() == 250) {
					splitPane.setDividerLocation(0);
				} else {
					splitPane.setDividerLocation(250);
				}
			}
			// 后退事件
			if (e.getSource() == back) {
				urladdress = urlfield.getText().toString().trim();
				try {
					// 获得历史链表中的前一个URL地址
					String pagename = "";
					if (historylist.isPrePageNameExist(urladdress)) {
						pagename = historylist.getPrePageName(pagename);
						urladdress = historylist.getURL(pagename);
						MainUI.this.setNewPage(1, urladdress);
					}
				} catch (Exception event) {
					event.printStackTrace();
					JOptionPane.showMessageDialog(MainUI.this, "网络连接失败！", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
				}
			}
			// 前进事件
			if (e.getSource() == forward) {
				urladdress = urlfield.getText().toString().trim();
				try {
					String pagename = "";
					if (historylist.isNextPageNameExist(urladdress)) {
						// 获得历史链表中的下一个URL地址
						pagename = historylist.getNextPageName(pagename);
						System.out.println(pagename);
						urladdress = historylist.getURL(pagename);
						MainUI.this.setNewPage(1, urladdress);
					}
				} catch (Exception event) {
					event.printStackTrace();
					JOptionPane.showMessageDialog(MainUI.this, "网络连接失败！", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
				}
			}
			// 停止事件
			if (e.getSource() == stop) {
				// 将页面置空
				urladdress = "";
				MainUI.this.setNewPage(1, urladdress);
			}
			// 刷新事件
			if (e.getSource() == refresh) {
				urladdress = urlfield.getText().toString().trim();
				MainUI.this.setNewPage(1, urladdress);
			}
			// 回到主页
			if (e.getSource() == home) {
				urladdress = "http://www.baidu.com";
				MainUI.this.setNewPage(0, urladdress);
			}
			// 单击go或者回车事件
			if ((e.getSource() == go) || (e.getSource() == urlfield)) {
				urladdress = urlfield.getText().toString().trim();
				if (urladdress.length() > 0 && urladdress.startsWith("http://")) {
					MainUI.this.setNewPage(0, urladdress);
				} else if (urladdress.length() > 0 && !urladdress.startsWith("http://")) {
					urladdress = "http://" + urladdress;
					MainUI.this.setNewPage(0, urladdress);
				} else if (urladdress.length() == 0) {
					JOptionPane.showMessageDialog(MainUI.this, "对不起,你输入了空地址,这是非法操作！", "Think 提示您:", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	// 实现HyperlinkListener方法,实现页面内的超链接
	private class HTMLView implements HyperlinkListener {
		@Override
		public void hyperlinkUpdate(HyperlinkEvent event) {
			if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				JEditorPane pane = (JEditorPane) event.getSource();
				if (event instanceof HTMLFrameHyperlinkEvent) {
					// 处理超链接
					HTMLFrameHyperlinkEvent htmlframehyperlinkevent = (HTMLFrameHyperlinkEvent) event;
					HTMLDocument htmldocument = (HTMLDocument) pane.getDocument();
					htmldocument.processHTMLFrameHyperlinkEvent(htmlframehyperlinkevent);
				} else {
					try {
						String linkurl = event.getURL().toString();
						String temp = getContent(linkurl);
						String title = getTitle(temp);
						pane.setPage(linkurl);
						pane.setEditable(false);
						urlfield.setText(linkurl);
						urlfield.revalidate();
						pane.invalidate();
						webpagepane.addHyperlinkListener(new HTMLView());
						historylist.addURL(title, linkurl);
					} catch (IOException t) {
						t.printStackTrace();
						JOptionPane.showMessageDialog(MainUI.this, "网络连接失败！", "Think 提示您:", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	// 键盘监听事件类
	private class KeyAction extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			char key = e.getKeyChar();
			// 按下回车键
			if (key == '\n') {
				urladdress = urlfield.getText().toString().trim();
				if (urladdress.length() > 0 && urladdress.startsWith("http://")) {
					MainUI.this.setNewPage(0, urladdress);
				} else if (urladdress.length() > 0 && !urladdress.startsWith("http://")) {
					urladdress = "http://" + urladdress;
					MainUI.this.setNewPage(0, urladdress);
				} else if (urladdress.length() == 0) {
					JOptionPane.showMessageDialog(MainUI.this, "对不起,你输入了空地址,这是非法操作！", "Think 提示您:", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	// 监听树节点改变的事件
	private class TreeNodeChange implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			String nodename = selectednode.toString();
			if (selectednode.isLeaf()) {
				if (bookmarklist.isPageNameExist(nodename)) {
					// 获取节点名字
					String tempurl = bookmarklist.getURL(nodename);
					setNewPage(0, tempurl);
				} else if (nodename.equalsIgnoreCase("主页")) {
					String tempurl = "http://www.baidu.com";
					setNewPage(0, tempurl);
				}
			}
		}
	}

	// 鉴定窗口大小改变的类
	private class WindowState extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			if (JOptionPane.showConfirmDialog(MainUI.this, "确定要退出吗？", "Think 提示您:", JOptionPane.OK_CANCEL_OPTION) == 0) {
				// 退出时将书签记录写入文件
				savebookmarklist();
				System.exit(0);
			} else {
				// Do Nothing
			}
		}

		public void windowStateChanged(WindowEvent e) {
			splitPane.setDividerSize(0);
		}
	}
}

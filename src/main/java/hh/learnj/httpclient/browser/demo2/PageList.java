/**
 * 
 */
package hh.learnj.httpclient.browser.demo2;

public class PageList {
	// 头节点
	private PageNode head;
	// 自由节点
	private PageNode link;
	// 记录当前的节点所在位置
	private PageNode linkpointer;

	public PageList() {
		head = new PageNode();
		link = head;
		linkpointer = head;
	}

	// 添加新的页面到链表
	public void addURL(String pagename, String urladdress) {
		PageNode node = new PageNode();
		link = head;
		while (link.getNext() != null) {
			link = link.getNext();
		}
		node.setPagename(pagename);
		node.setUrl(urladdress);
		link.setNext(node);
		node.setPre(link);
		node.setNext(null);
		linkpointer = node;
	}

	// 获得链表头
	public PageNode getPageNode() {
		return this.head;
	}

	// 查找某个pagename对应的URL
	public String getURL(String pagename) {
		link = head;
		String url = "";
		while (!((link.getNext()).getPagename()).equalsIgnoreCase(pagename)) {
			link = link.getNext();
		}
		url = (link.getNext()).getUrl();
		linkpointer = link;
		return url;
	}

	// 获取某个URL对应的pagename
	public String getPageName(String urladdress) {
		link = head;
		String pagename = "";
		while (!(link.getNext().getUrl()).equalsIgnoreCase(urladdress)) {
			link = link.getNext();
		}
		pagename = link.getNext().getPagename();
		linkpointer = link;
		return pagename;
	}

	// 获取当前pagename的前一个pagename
	public String getPrePageName(String pagename) {
		link = linkpointer.getPre();
		linkpointer = link;
		return link.getPagename();
	}

	// 获取当前pagename的下一个pagename
	public String getNextPageName(String pagename) {
		link = linkpointer.getNext();
		linkpointer = link;
		return link.getPagename();
	}

	// 查找某个pagename地址是否存在
	public boolean isPageNameExist(String pagename) {
		link = this.head.getNext();
		// 标记是否找到
		boolean isfind = false;
		String pageName = "";
		while (link != null) {
			pageName = link.getPagename();
			if (pageName.equalsIgnoreCase(pagename)) {
				isfind = true;
				break;
			}
			link = link.getNext();
		}
		System.out.println("查找本页结果:" + isfind);
		return isfind;
	}

	// 判断某个pagename的前一个pagename是否存在
	public boolean isPrePageNameExist(String pagename) {
		boolean isfind = false;
		link = head;
		link = linkpointer.getPre();
		if (link != null && link != head) {
			isfind = true;
		}
		System.out.println("查找前页结果:" + isfind);
		return isfind;
	}

	// 判断某个pagename的后一个pagename是否存在
	public boolean isNextPageNameExist(String pagename) {
		boolean isfind = false;
		link = head;
		link = linkpointer.getNext();
		if (link != null) {
			isfind = true;
		}
		System.out.println("查找后页结果:" + isfind);
		return isfind;
	}
}

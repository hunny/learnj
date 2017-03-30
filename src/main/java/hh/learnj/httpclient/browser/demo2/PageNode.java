/**
 * 
 */
package hh.learnj.httpclient.browser.demo2;

public class PageNode {
	// 页面的名称
	private String pagename = "";
	// url为网址实例
	private String url = "";
	// next为后一个节点
	private PageNode next = null;
	// pre为前一个节点
	private PageNode pre = null;

	// get、set方法
	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageNode getNext() {
		return next;
	}

	public void setNext(PageNode next) {
		this.next = next;
	}

	public PageNode getPre() {
		return pre;
	}

	public void setPre(PageNode pre) {
		this.pre = pre;
	}

	// 重写默认构造函数
	public PageNode() {
		this.pagename = "";
		this.url = "";
		this.next = null;
		this.pre = null;
	}

	// 自定义构造函数
	public PageNode(String pagename, String urladdress) {
		this.pagename = pagename;
		this.url = urladdress;
		this.next = null;
		this.pre = null;
	}
}

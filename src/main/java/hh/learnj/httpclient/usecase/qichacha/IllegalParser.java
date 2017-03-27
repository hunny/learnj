/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author huzexiong
 *
 */
public class IllegalParser implements Parser {

	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
//		checkLogin(doc);
		checkVerify(doc);
	}
	
	protected void checkLogin(Document doc) {
		Element element = doc.select("a.V3_index_loginbt").first();
		if (null != element) {
			throw new UnsupportedOperationException("需要登录。");
		}
	}
	
	protected void checkVerify(Document doc) {
		Element element = doc.select("script").first();
		if (null != element) {
			String text = element.childNode(0).toString();
			if (null != text && text.startsWith("window.location.href")) {
				throw new UnsupportedOperationException("需要手工验证。");
			}
		}
	}

}

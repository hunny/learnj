/**
 * 
 */
package hh.learnj.httpclient.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 *
 */
public class DetailResultHandler implements ResultHandler {

	private final Logger logger = LoggerFactory.getLogger(DetailResultHandler.class);
	
	private PhantomjsSpider spider;
	
	/**
	 * @return the spider
	 */
	public PhantomjsSpider getSpider() {
		return spider;
	}

	/**
	 * @param spider the spider to set
	 */
	public void setSpider(PhantomjsSpider spider) {
		this.spider = spider;
	}
	
	@Override
	public void handle(String html) {
		Document doc = Jsoup.parse(html, "UTF-8");
		Element element = doc.select("div.company_info_text").first();
		if (null == element) {
			logger.debug("无公司节点信息。");
			return;
		}
		Element company = element.select("div.company_info_text div.ng-binding").first();
		Elements spans = element.select("div.company_info_text span.ng-binding");
		//电话: 18
		//地址: 青浦区
		
	}

}

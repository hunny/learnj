/**
 * 
 */
package hh.learnj.httpclient.spider;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 */
public class ListResultHandler implements ResultHandler {

	private final Logger logger = LoggerFactory.getLogger(ListResultHandler.class);

	private PhantomjsSpider spider;
	private DetailResultHandler detailHandler;

	public ListResultHandler(DetailResultHandler detailHandler) {
		this.detailHandler = detailHandler;
	}

	public PhantomjsSpider getSpider() {
		return spider;
	}

	public void setSpider(PhantomjsSpider spider) {
		this.spider = spider;
	}

	@Override
	public void handle(String html) {
		Document doc = Jsoup.parse(html, "UTF-8");
		Element element = doc.select("a.query_name").first();
		if (null == element) {
			if (null != getSpider()) {
				logger.debug("查询地址无结果[{}]", getSpider().getUrl());
			} else {
				logger.debug("查询无结果[{}]。", html);
			}
			return;
		}
		String url = element.attr("href");
		if (StringUtils.isBlank(url)) {
			logger.debug("没有找到公司详情地址[{}]。", element.toString());
			return;
		}
		detailHandler.setSpider(spider);
		this.spider.justDoIt(url, detailHandler);
	}

}

/**
 * 
 */
package hh.learnj.httpclient.spider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB;

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
	 * @param spider
	 *            the spider to set
	 */
	public void setSpider(PhantomjsSpider spider) {
		this.spider = spider;
	}

	@Override
	public void handle(String html) {
		Document doc = Jsoup.parse(html, "UTF-8");
		Element element = doc.select("div.company_info_text").first();
		if (null == element) {
			logger.debug("无公司节点信息[{}]。", doc.toString());
			return;
		}
		Map<String, String> data = new HashMap<String, String>();
		Element company = element.select("div.company_info_text div.ng-binding").first();
		data.put("name", StringUtils.trim(company.text()));
		data.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Elements spans = element.select("div.company_info_text span.ng-binding");
		// 电话: 18
		// 地址: 青浦区
		for (Element span : spans) {
			String text = StringUtils.trim(span.text());
			if (StringUtils.isBlank(text)) {
				continue;
			}
			if (text.startsWith("电话:")) {
				text = text.replaceAll("^电话:", "");
				data.put("mobile", StringUtils.trim(text));
			} else if (text.startsWith("地址:")) {
				text = text.replaceAll("^地址:", "");
				data.put("address", StringUtils.trim(text));
			} else if (text.startsWith("网址:")) {
				text = text.replaceAll("^网址:", "");
				if (StringUtils.isNotBlank(text) && !"暂无".equals(text)) {
					data.put("url", StringUtils.trim(text));
				}
			}
		}
		Elements baseinfos = doc.select("div.baseinfo-module-content");
		for (Element elem : baseinfos) {
			String text = StringUtils.trim(elem.text());
			if (text.startsWith("法定代表人")) {
				Elements hrefs = elem.select("a");
				if (null == hrefs) {
					logger.debug("没有找到法定代表人。");
					continue;
				}
				logger.debug("[{}]", hrefs.toString());
				Element href = hrefs.first();
				String businesser = StringUtils.trim(href.text());
				if (StringUtils.isNotBlank(businesser)) {
					data.put("businesser", businesser);
				}
			} else if (text.startsWith("注册资金")) {
				Elements hrefs = elem.select("a");
				if (null == hrefs) {
					logger.debug("没有找到注册资金。");
					continue;
				}
				logger.debug("[{}]", hrefs.toString());
				Element href = hrefs.first();
				String registerAmount = StringUtils.trim(href.text());
				if (StringUtils.isNotBlank(registerAmount)) {
					data.put("registerAmount", registerAmount);
				}
			}
		}
		DB.handle(new UpdateHandler(data));
	}

}

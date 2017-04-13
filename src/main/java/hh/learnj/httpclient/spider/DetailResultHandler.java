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
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		Element element = doc.select("div.company_header_width").first();
		if (null == element) {
			logger.debug("无公司节点信息。");
			return;
		}
		logger.debug("[{}]", element.toString());
		Map<String, String> data = new HashMap<String, String>();
		data.put("name", this.getName());
		data.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Elements spans = element.select("div.in-block");
		// 电话: 18
		// 地址: 青浦区
		for (Element span : spans) {
			String text = StringUtils.trim(span.text());
			if (StringUtils.isBlank(text)) {
				continue;
			}
			if (text.startsWith("电话：")) {
				text = text.replaceAll("^电话：", "");
				data.put("mobile", StringUtils.trim(text));
			} else if (text.startsWith("地址：")) {
				text = text.replaceAll("^地址：", "");
				data.put("address", StringUtils.trim(text));
			} else if (text.startsWith("网址：")) {
				text = text.replaceAll("^网址：", "");
				if (StringUtils.isNotBlank(text) && !"暂无".equals(text)) {
					data.put("url", StringUtils.trim(text));
				}
			}
		}
		Elements baseinfos = doc.select("table.companyInfo-table tbody td");
		if (baseinfos.size() > 1) {
			Elements hrefs = baseinfos.get(0).select("a.in-block");
			if (null != hrefs) {
				logger.debug("[{}]", hrefs.toString());
				Element href = hrefs.first();
				String businesser = StringUtils.trim(href.text());
				if (StringUtils.isNotBlank(businesser)) {
					data.put("businesser", businesser);
				}
			}
			Elements divs = baseinfos.get(1).select("div.ng-binding");
			if (null != divs) {
				logger.debug("[{}]", divs.toString());
				Element href = divs.first();
				String registerAmount = StringUtils.trim(href.text());
				if (StringUtils.isNotBlank(registerAmount)) {
					data.put("registerAmount", registerAmount);
				}
			}
		}
		DB.handle(new UpdateHandler(data));
	}

}

/**
 * 
 */
package hh.learnj.httpclient.usecase.tianyancha;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.Parser;

/**
 * @author huzexiong
 *
 */
public class TianYanChaListParser implements Parser {

	private static final Logger logger = LoggerFactory.getLogger(TianYanChaListParser.class);

	private Map<String, String> header;
	
	public TianYanChaListParser(Map<String, String> header) {
		this.header = header;
	}
	
	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".search_result_single .search-2017 .pb15 .ng-scope");
		if (null == elements || elements.isEmpty()) {
			logger.error(doc.html());
			return;
		}
		Element element = elements.select(".col-xs-10 .search_repadding2 .f18").first();
		if (null == element) {
			logger.debug("无查询结果。");
			return;
		}
		Element detailElement = element.select("a").first();
		if (null == detailElement) {
			logger.debug("无公司详情链接。");
			return;
		}
		String detail = detailElement.attr("href");
		final Map<String, String> map = new HashMap<String, String>();
		map.put("name", StringUtils.trim(element.text()));
		map.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Elements elementTitles = elements.select("div.search_row_new div.title");
		for (Element val : elementTitles) {
			String str = StringUtils.trim(val.text());
			if (str.startsWith("法定代表人：")) {
				str = str.replaceAll("^法定代表人：\\s*", "");
				map.put("businesser", str);
				break;
			}
		}
		try {
			getDetails(detail, map);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (map.size() >= 2 && StringUtils.isNotBlank(map.get("mobile"))) {
			logger.debug("更新信息[{}]", map);
//			DB.handle(new UpdateHandler(Collections.singletonList(map)));
		}
	}

	/**
	 * @param detail
	 * @param map
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private void getDetails(String detail, final Map<String, String> map) throws ClientProtocolException, IOException {
		new TianYanChaHttp().get(detail, "UTF-8", new Parser() {
			@Override
			public void parse(String html) {
				Document doc = Jsoup.parse(html);
				Element element = doc.select(".company_info_text").first();
				Elements spans = element.select("span");
				for (Element val : spans) {
					String str = StringUtils.trim(val.text());
					if (str.startsWith("电话:")) {
						str = str.replaceAll("^电话:\\s*", "");
						map.put("mobile", str);
						break;
					} else if (str.startsWith("地址:")) {
						str = str.replaceAll("^地址:\\s*", "");
						map.put("address", str);
						break;
					} 
				}
			}
		}, this.header);
	}

	public String html2text(String html) {
		return Jsoup.parse(html).text();
	}

}

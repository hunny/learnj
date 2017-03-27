/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.text.SimpleDateFormat;
import java.util.Collections;
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

/**
 * @author huzexiong
 *
 */
public class QiChaChaListParser implements Parser {

	private static final Logger logger = LoggerFactory.getLogger(QiChaChaListParser.class);
	
	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(".m_srchList tbody td");
		if (null == elements || elements.isEmpty()) {
			System.err.println(doc.html());
			return;
		}
		Element element = elements.get(1);
		String[] arr = element.html().split("<br>");
		if (null == arr || arr.length == 0) {
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		String name = html2text(arr[0]);
		map.put("name", name);
		map.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		for (String val : arr) {
			String str = StringUtils.trim(html2text(val));
			if (str.startsWith("企业法人：")) {
				str = str.replaceAll("^企业法人：", "");
				map.put("businesser", str);
			} else if (str.startsWith("联系方式：")) {
				str = str.replaceAll("^联系方式：", "");
				map.put("mobile", str);
			} else if (str.startsWith("地址：")) {
				str = str.replaceAll("^地址：", "");
				map.put("address", str);
			}
		}
		if (map.size() >= 2) {
			logger.debug("更新信息[{}]", map);
			DB.handle(new UpdateHandler(Collections.singletonList(map)));
		}
	}

	public String html2text(String html) {
		return Jsoup.parse(html).text();
	}

}

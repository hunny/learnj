package hh.learnj.httpclient.usecase.topease;

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

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.Parser;
import hh.learnj.httpclient.usecase.qichacha.UpdateHandler;

public class OAHttpParser implements Parser {

	private static final Logger logger = LoggerFactory.getLogger(OAHttpParser.class);

	private static void debug(String format, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}

	private String name = null;

	public OAHttpParser(String name) {
		this.name = StringUtils.trim(name);
	}

	@Override
	public void parse(String html) {
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tbody tr");
		for (Element tr : trs) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			String state = "-";
			Elements tds = tr.select("td");
			if (null != tds && !tds.isEmpty()) {
				Element tdName = tds.get(1);// 公司名称
				if (null == tdName) {
					continue;
				}
				Element stateName = tds.get(4);// 客户状态
				state = StringUtils.trim(stateName.text());
				Element saleMan = tds.get(2);// 销售代表
				Element serviceMan = tds.get(3);// 服务代表
				map.put("saleman", StringUtils.trim(saleMan.text()));
				map.put("serviceman", StringUtils.trim(serviceMan.text()));
			}
			map.put("state", state);
			debug("更新信息[{}]", map);
			DB.handle(new UpdateHandler(Collections.singletonList(map)));
		}
	}

}

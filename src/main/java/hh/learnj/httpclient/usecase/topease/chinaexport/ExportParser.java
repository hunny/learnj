package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.Parser;

public class ExportParser implements Parser {

	private final Logger logger = LoggerFactory.getLogger(ExportParser.class);
	
	private String next;
	private String url;
	private CompanyData companyData;
	
	public ExportParser(String url, CompanyData companyData) {
		this.url = url;
		this.companyData = companyData;
	}
	
	@Override
	public void parse(String html) {
		if (html.startsWith("{")) {
			throw new UnsupportedOperationException(html);
		}
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tbody tr");
		if (trs.isEmpty()) {
			debug("查询无结果。");
			this.setNext(null);
			return;
		}
		for (Element element : trs) {
			Elements tds = element.select("td");
			String name = StringUtils.trim(tds.get(1).text());
			if (name.contains("物流")) {
				continue;
			}
			Map<String, String> map = new HashMap<String, String>();
			String amount = StringUtils.trim(tds.get(2).text()).replaceAll(",", "");
			map.put("name", name);
			map.put("outAmount", amount);
			map.put("code", this.companyData.getHscode());
			map.put("category", this.companyData.getCategory());
			debug("{}", map);
			DB.handle(new ExportDBHandler(map));
		}
		Integer page = Integer.parseInt(middleWords("pagenum=", "&recordCount")) + 1;
		this.setNext(this.url.replaceFirst("pagenum=\\d+", "pagenum=" + page));
		debug(this.getNext());
	}
	
	protected String middleWords(String strbegin, String strend) {
		if (StringUtils.isBlank(strbegin) || StringUtils.isBlank(strend)) {
			return null;
		}
		int start = this.url.indexOf(strbegin) + strbegin.length();
		int end = this.url.indexOf(strend);
		return this.url.substring(start, end);
	}
	
	private void debug(String format, Object...arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}
	
	public String read(String fileName) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

}

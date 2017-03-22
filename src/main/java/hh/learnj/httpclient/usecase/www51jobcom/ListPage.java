/**
 * 
 */
package hh.learnj.httpclient.usecase.www51jobcom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
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
public class ListPage {

	public static final Logger logger = LoggerFactory.getLogger(ListPage.class);
	public static final String CHARSET = "GBK";

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws Exception {
		String url = listUrl(1);
		req51Job(url);
		
	}

	/**
	 * @param url
	 */
	private static void req51Job(String url) {
		logger.info(url);
		Req.get(url, CHARSET, new Req.Parser() {
			@Override
			public void parse(String html) {
				try {
					parseList(html);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String listUrl(Integer page) throws UnsupportedEncodingException {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://search.51job.com/jobsearch/search_result.php?");
		urlBuilder.append(
				"fromJs=1&jobarea=020000%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99");
		urlBuilder.append("&keyword=");
		urlBuilder.append(URLEncoder.encode("外贸", "UTF-8"));
		urlBuilder.append("&keywordtype=2");
		urlBuilder.append("&curr_page=");
		urlBuilder.append(page);
		urlBuilder.append(
				"&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9");
		return urlBuilder.toString();
	}
	
	/**
	 * @param html
	 * @return 返回下一页
	 * @throws InterruptedException 
	 */
	public static Integer parseList(String html) throws InterruptedException {
		
		Document document = Jsoup.parse(html);
		Elements nexts = document.select("div.p_in li.bk a");
		Elements elements = document.select(".t2");
		if (null == elements) {
			return null;
		}
		final List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
    final CountDownLatch countDownLatch = new CountDownLatch(elements.size());
		for (Element element : elements) {
			final Element elem = element.select("a").first();
			if (null == elem) {
				countDownLatch.countDown();
				continue;
			}
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Map<String, String> value = handleSingleCompany(elem);
						values.add(value);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		countDownLatch.await();
    executorService.shutdown();
		if (null == values || values.isEmpty()) {
			return null;
		}
		DB.handle(values, new DB.Hander() {
			@Override
			public void handle(List<Map<String, String>> values, Statement stmt) {
				if (null != values && !values.isEmpty()) {
					for (Map<String, String> value : values) {
						DB.insert(getTableName(), value, stmt);
					}
				}
			}
			@Override
			public String getTableName() {
				return "company";
			}
		});
		if (null == nexts) {
			return null;
		}
		Element next = null;
		if (nexts.size() <= 1) {
			next = nexts.first();
		} else {
			next = nexts.get(1);
		}
		if (null != next) {
			String url = next.attr("href");
			if (StringUtils.isNotBlank(url)) {
				req51Job(url);
			}
		}
		return null;
	}
	
	public static Map<String, String> handleSingleCompany(final Element elem) {
		final Map<String, String> value = new HashMap<String, String>();
		value.put("name", elem.attr("title"));
		String url51 = elem.attr("href");
		value.put("url51", url51);
		value.put("dateCreated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if (StringUtils.isBlank(url51)) {
			return Collections.emptyMap();
		}
		Req.get(url51, CHARSET, new Req.Parser() {
			@Override
			public void parse(String html) {
				parseDetails(html, value);
			}
		});
		return value;
	}
	
	public static void parseDetails(String html, Map<String, String> value) {
		Document document = Jsoup.parse(html);
		Element element = document.select("div.tCompany_full").first();
		if (null == element) {
			return;
		}
		Element details = element.select(".tBorderTop_box.bt").first();
		if (null != details) {
			value.put("details", StringUtils.trim(details.text()));
		}
		Elements elements = element.select(".tBorderTop_box");
		for (Element elem : elements) {
			String text = StringUtils.trim(elem.text());
			if (text.startsWith("公司地址")) {
				text = text.replaceAll("^公司地址\\s*：\\s*", "");
				text = text.replaceAll("发送到手机\\s*地图$", "");
				value.put("address", text);
			} else if (text.startsWith("公司官网")) {
				text = text.replaceAll("^公司官网\\s*：\\s*", "");
				value.put("url", text);
			}
		}
	}

}

/**
 * 
 */
package hh.learnj.httpclient.usecase.proxy.weighment;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hh.learnj.httpclient.usecase.proxy.Parser;
import hh.learnj.httpclient.usecase.proxy.Req;
import hh.learnj.httpclient.usecase.qichacha.DB;
import hh.learnj.httpclient.usecase.qichacha.DB.Hander;

/**
 * @author huzexiong
 *
 */
public class Weighment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 1; i < 4; i++) {
			String url = String.format("http://www.weighment.com/exhi2017/e_list.asp?page_no=%d&sort_order=booth_no&keyw=%s",
					i, "%C9%CF%BA%A3");
			Req.get(url, "gb2312", new Parser() {
				@Override
				public void parse(String html) {
					Document doc = Jsoup.parse(html);
					Elements elements = doc.select("td");
					for (Element element : elements) {
						if (element.text().startsWith("展位号")) {
							String[] tmpHtmls = element.html().split("<br>");
							if (null == tmpHtmls || tmpHtmls.length < 3) {
								continue;
							}
							final Map<String, String> values = new HashMap<String, String>();
							Document company = Jsoup.parse(tmpHtmls[2]);
							System.out.println();
							values.put("name", StringUtils.trim(company.text()));
							try {
								DB.handle(new Hander() {
									@Override
									public String getTableName() {
										return "company";
									}
									@Override
									public void handle(Statement stmt) throws SQLException {
										DB.insert(getTableName(), values, stmt);
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
	}

}

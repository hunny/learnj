/**
 * 
 */
package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import hh.learnj.httpclient.usecase.qichacha.Parser;

/**
 * 国家分析
 * 
 * @author huzexiong
 *
 */
public class CountryParser implements Parser {

	private final Logger logger = LoggerFactory.getLogger(CountryParser.class);
	private TokenHander tokenHander;
	private CompanyData companyData = new CompanyData();
	
	public CountryParser(TokenHander tokenHandler, CompanyData companyData) {
		this.tokenHander = tokenHandler;
		BeanUtils.copyProperties(companyData, this.companyData);;
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
			return;
		}
		for (Element element : trs) {
			Elements tds = element.select("td");
			Element country = tds.get(1);
			String name = StringUtils.trim(country.text());
			Element link = country.select("a").first();
			String href = link.attr("href");
			String token = href.split("token=")[1];
			debug("name[{}]token[{}]href[{}]", name, token, href);
			if (null != tokenHander) {
				tokenHander.handler(name, token, href);
			}
		}
	}
	
	private void debug(String format, Object...arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}
	
	public String getUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append("http://cntrade.topease.net/Main/Country?companyIds=&ctrlie=&hscode=");
		builder.append(this.companyData.getHscode());
		builder.append("&bdate=");
		builder.append(this.companyData.getBegin());
		builder.append("&edate=");
		builder.append(this.companyData.getEnd());
		builder.append("&comp.id=&comp.name=&ctry.id=&ctry.name=&city.id=&city.name=&ie=0&_=");
		builder.append(new Date().getTime());
		return builder.toString();
	}
	
	public interface TokenHander {
		void handler(String name, String token, String url);
	}

}

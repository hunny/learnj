/**
 * 
 */
package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author huzexiong
 *
 */
public class CompanyDataService {

	private CompanyData companyData;
	private TopEaseHttp http = null;
	
	public CompanyDataService(CompanyData companyData) {
		this.companyData = companyData;
		this.http = new TopEaseHttp(this.companyData.getSessionId());
	}
	
	public void justDoIt() {
		String url = getUrl(companyData.getBegin(), companyData.getEnd(), "1");
		do {
			url = get(url);
		} while (null != url);
	}
	
	public String get(String url) {
		ExportParser parser = new ExportParser(url, this.companyData);
		try {
			http.get(url, "UTF-8", parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parser.getNext();
	}
	
	protected String getUrl(String bdate, String edate, String pagenum) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://cntrade.topease.net/Main/CompanyData?token="); 
		builder.append(this.companyData.getToken());
		builder.append("&pagenum=");
		builder.append(pagenum);
		builder.append("&recordCount=");
		builder.append(this.companyData.getRecordCount());
		builder.append("&bdate=");//2015-03
		builder.append(bdate);
		builder.append("&edate=");//2016-02
		builder.append(edate);
		builder.append("&companyFilter=");
		builder.append(encode(this.companyData.getCompanyFilter()));
		builder.append("&_=");
		builder.append(new Date().getTime());
		return builder.toString();
	}
	
	protected String encode(String name) {
		try {
			return URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}
	
}

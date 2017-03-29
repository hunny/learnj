package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import hh.learnj.httpclient.usecase.topease.TopeaseHttp;

public class App {
	
	private TopeaseHttp http = new TopeaseHttp();

	public static void main(String[] args) {
		App app = new App();
		String url = app.getUrl("2016-03", "2017-03", "1");
		do {
			url = app.get(url);
		} while (null != url);
	}
	
	public String get(String url) {
		ExportParser parser = new ExportParser(url, "中国出口");
		try {
			http.get(url, "UTF-8", parser);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
//		parser.parse(parser.read("/Users/hunnyhu/Desktop/印度-上海.html"));
		return parser.getNext();
	}
	
	protected String getUrl(String bdate, String edate, String pagenum) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://cntrade.topease.net/Main/CompanyData?token="); 
		builder.append("90A16018F2E2B4800BF65F266B3C15A004746A4B7A8DAEFF74C851B1A067BB1B81E75495A478163858D28419EFF1B1AADCB52392E177EE9848FF5E3C51E2610D6D7E13682835CF56");
		builder.append("&pagenum=");
		builder.append(pagenum);
		builder.append("&recordCount=122");
		builder.append("&bdate=");//2015-03
		builder.append(bdate);
		builder.append("&edate=");//2016-02
		builder.append(edate);
		builder.append("&companyFilter=%E4%B8%8A%E6%B5%B7");
		builder.append("&_=");
		builder.append(new Date().getTime());
		return builder.toString();
	}

}

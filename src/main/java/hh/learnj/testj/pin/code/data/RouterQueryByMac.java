package hh.learnj.testj.pin.code.data;

import hh.learnj.httpclient.usecase.tumblr.TumblrDownloadImages;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RouterQueryByMac {
	
	private static Logger logger = Logger.getLogger(TumblrDownloadImages.class);

	public static void main(String[] args) throws Exception {
		
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>(); 
//		params.add(new BasicNameValuePair("mac", "C8:3A:35:04:C1:A0")); 
//		params.add(new BasicNameValuePair("mac", "0C:72:2C:DC:AB:90")); 
		params.add(new BasicNameValuePair("mac", "40:CB:A8:7C:CC:F8")); 
		
		//对参数编码 
		String param = URLEncodedUtils.format(params, "UTF-8"); 
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.wenzk.com/mac-query?" + param);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity);
		Document document = Jsoup.parse(html);
		Elements divs = document.select("div.entry-content");
		for (Element elem : divs) {
			logger.info(elem.toString());
		}
	}

}

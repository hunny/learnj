package hh.learnj.testj.pin.code.data;

import hh.learnj.httpclient.usecase.tumblr.TumblrDownloadImages;
import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class RouterQueryByMac {
	
	private static Logger logger = Logger.getLogger(RouterQueryByMac.class);

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private JdbcSqliteUtil db = new JdbcSqliteUtil();

	public static void main(String[] args) throws Exception {
		RouterQueryByMac net = new RouterQueryByMac();
		logger.info("[+][-] REAVER_CRACKED COLTD Fixing.");
		net.fixReaverCracked();
		logger.info("[+][-] REAVER_CRACKED COLTD Fixed.");
	}
	
	public void fixReaverCracked() throws Exception {
		List<Map<String, Object>> list = db.query("select * from " + JdbcSqliteUtil.REAVER_CRACKED + " where COLTD is null");
		if (null != list && !list.isEmpty()) {
			for (Map<String, Object> m : list) {
				String BSSID = (String)m.get("BSSID");
				if (null == BSSID) {
					continue;
				}
				String coltd = getRooterCompany(BSSID);
				if (null != coltd) {
					Map<String, Object> values = new HashMap<String, Object>();
					values.put("COLTD", coltd);
					Map<String, Object> wheres = new HashMap<String, Object>();
					wheres.put("ID", m.get("ID"));
					db.updateToReaverCracked(values, wheres);
					logger.info("[+][-] " + BSSID + " [-]" + coltd + "[-] " + m.get("ID") + " Fixed.");
				}
			}
		}
	}
	
	public String getRooterCompany(String mac) throws Exception {
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>(); 
		params.add(new BasicNameValuePair("mac", mac)); 
		//对参数编码 
		String param = URLEncodedUtils.format(params, "UTF-8"); 
		HttpGet httpget = new HttpGet("http://www.wenzk.com/mac-query?" + param);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity);
		Document document = Jsoup.parse(html);
		Elements divs = document.select("div.entry-content");
		if (null == divs || divs.size() == 0) {
			return null;
		}
		Element elem = divs.get(0);
		List<TextNode> nodes = elem.textNodes();
		String coltd = null;
		for (TextNode node : nodes) {
			String tmp = node.text();
			if (tmp.contains("属于:")) {
				String [] arr = tmp.split("属于:");
				if (arr.length >= 2) {
					coltd = arr[1];
					coltd = coltd.trim();
				}
				logger.info(node.text());
			}
		}
		return coltd;
	}

}

package hh.learnj.testj.pin.code.data.collect;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class CollectHttpClientNetworkPin {
	
	private static Logger logger = Logger.getLogger(CollectHttpClientNetworkPin.class);

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private JdbcSqliteUtil db = new JdbcSqliteUtil();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new CollectHttpClientNetworkPin().fetchFromURL(null);
	}
	
	public void loop() throws Exception {
		fetchFromURL(null);
	}
	
	public void fetchFromURL(String id) throws Exception {
		if (null == id) {
			id = "5312";
		}
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>(); 
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("rnd", "14882733090780675"));
		//对参数编码 
		String param = URLEncodedUtils.format(params, "UTF-8");
		String url = "http://mac-pin.456vv.net/_JsonList.asp?" + param;
		HttpGet httpget = new HttpGet(url);
		logger.info(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity);
		JSONObject object = new JSONObject(html);
		JSONArray arr = object.getJSONArray("data");
		if (null == arr || arr.length() <= 0) {
			return;
		}
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
//			System.out.print("obj = " + obj);
			JSONArray text = obj.getJSONArray("text");
//			System.out.print(", text = " + obj);
//			System.out.println(", " + text.getString(0));
			String BSSID = text.getString(2).toUpperCase();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("COLTD", text.getString(0));
			map.put("ESSID", text.getString(1));
			map.put("BSSID", BSSID);
			map.put("PIN", text.getString(3));
			List<Map<String, Object>> qList = db.query("select * from " + JdbcSqliteUtil.REAVER_WASH + " where BSSID = '" + BSSID + "'");
			if (null == qList || qList.isEmpty()) {
				logger.info("[+][-]" + BSSID + "[-]Will be inserted.[-]" + map + "[-]");
				db.saveToWashReaver(map);
			} else {
				logger.info("[+][-]" + BSSID + "[-]Has existed.[-]" + map + "[-]");
			}
		}
		fetchFromURL(object.getString("endid"));
	}
	
	

}

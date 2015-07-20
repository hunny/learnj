package hh.learnj.httpclient.usecase.googleips;

import hh.learnj.httpclient.usecase.googletranslate.DynamicProxyRoutePlanner;
import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpClientGoogleIps {
	
	public Logger logger = Logger.getLogger(HttpClientGoogleIps.class);
	public GoogleIpsJdbcSqliteUtil util = null;
	public HttpClientGoogleIps() {
		util = new GoogleIpsJdbcSqliteUtil();
	}

	public static void main(String[] args) {
		HttpClientGoogleIps ips = new HttpClientGoogleIps();
		//ips.logger.info(ips.util.createTabalGoogleIps());
		ips.save(ips.googleIps());
	}
	
	public void save(Map<String, List<String>> result) {
		if (null == result || result.isEmpty()) {
			return;
		}
		for (Map.Entry<String, List<String>> v : result.entrySet()) {
			String area = v.getKey();
			List<String> ips = result.get(area);
			for (String ip : ips) {
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("AREA", area);
				values.put("IP", ip);
				List<Map<String, Object>> list = util.query("select * from " + GoogleIpsJdbcSqliteUtil.GOOGLE_IPS + " where IP = '" + ip + "'");
				if (null == list || list.isEmpty()) {
					logger.info("[+][-] " + area + " [-] " + ip + " [-] saving....");
					util.saveToGoogleIps(values);
					logger.info("[+][-] insert info  [-]" + values);
				} else {
					for (Map<String, Object> m : list) {
						Map<String, Object> wheres = new HashMap<String, Object>();
						wheres.put("ID", m.get("ID"));
						logger.info("[+][-] " + area + " [-] " + ip + " [-] has existed. Will be updated by ID = " + m.get("ID"));
						logger.info("[+][-] update info  [-]" + m);
						logger.info("[+][-] update value [-]" + values);
						util.updateToGoogleIps(values, wheres);
					}
				}
			}
		}
	}
	
	public Map<String, List<String>> googleIps() {
		CloseableHttpClient httpclient = null;
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		try {
			SSLContext sslContext = SSLContexts.custom()
			.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(final X509Certificate[] chain,
						final String authType)
						throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext);
			HttpHost proxy = new HttpHost("127.0.0.1", 8087);
			DynamicProxyRoutePlanner routePlanner = new DynamicProxyRoutePlanner(proxy);
			httpclient = HttpClients.custom()
			.setSSLSocketFactory(connectionFactory)
			.setRoutePlanner(routePlanner)
			.setSSLHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname,
						SSLSession session) {
					return true;
				}
			}).build();
			// 创建httpget.
			HttpGet httpget = new HttpGet("http://www.legendsec.org/google.html");
			logger.info("============> executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					logger.info("============> Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					String html = EntityUtils.toString(entity);
					Document document = Jsoup.parse(html);
					Elements trs = document.getElementsByTag("tr");
					String key = "";
					for (Element elem : trs) {
						Elements tds = elem.select("td");
						if (tds.size() != 0) {
							for (Element td : tds) {
								String ip = td.text();
								if (ip.matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
									List<String> ips = result.get(key);
									if (null == ips) {
										ips = new ArrayList<String>();
									}
									ips.add(ip);
									result.put(key, ips);
									logger.info("key:" + key + ", ip:" + ip);
								}
							}
						} else {
							Element th = elem.select("th").first();
							key = th.text();
							logger.info("=======>key:" + key);
						}
					}
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}

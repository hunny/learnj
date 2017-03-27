/**
 * 
 */
package hh.learnj.httpclient.usecase.tianyancha;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.Parser;

/**
 * @author huzexiong
 *
 */
public class TianYanChaHttp {

	private static final Logger logger = LoggerFactory.getLogger(TianYanChaQueryHandler.class);

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HOST = "www.tianyancha.com";
	private static final String REFERER = "http://www.tianyancha.com/";
	private static HttpClient client = null;
	private String cookies = null;

	static {
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		BasicCookieStore cookieStore = new BasicCookieStore();
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	protected void debug(String format, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}

	public String get(String url, String charset, Parser parser, Map<String, String> map) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");
		if (null != map) {
			for (Map.Entry<String, String> m : map.entrySet()) {
				request.setHeader(m.getKey(), m.getValue());
			}
		}
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		debug("\nSending 'GET' request to URL : {}", url);
		debug("Response Code : {}", responseCode);

		// set cookies
		setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : response.getFirstHeader("Set-Cookie").toString());
		String html = EntityUtils.toString(response.getEntity(), charset);
		if (null != parser) {
			parser.parse(html);
		}
		return html;

	}

	public String post(String url, String charset, List<NameValuePair> postParams, Parser parser) throws Exception {

		HttpPost post = new HttpPost(url);
		// add header
		post.setHeader("Host", HOST);
		post.setHeader("Origin", "http://" + HOST);
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Cookie", getCookies());
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Referer", REFERER);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		HttpResponse response = client.execute(post);

		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		String html = EntityUtils.toString(response.getEntity(), charset);
		if (null != parser) {
			parser.parse(html);
		}
		return html;

	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

}

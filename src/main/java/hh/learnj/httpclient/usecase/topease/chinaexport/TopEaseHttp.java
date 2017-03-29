/**
 * 
 */
package hh.learnj.httpclient.usecase.topease.chinaexport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.httpclient.usecase.qichacha.Parser;

/**
 * @author huzexiong
 */
public class TopEaseHttp {

	private static final Logger logger = LoggerFactory.getLogger(TopEaseHttp.class);

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HOST = "oa.topease.cn";
	private static final String REFERER = "http://oa.topease.cn/";
	private HttpClient client = null;
	private String cookies = null;
	private HttpURLConnection conn;
	private String sessionId;

	public TopEaseHttp(String sessionId) {
		this.sessionId = sessionId;
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(makeCookie("ASP.NET_SessionId", this.sessionId, "oa.topease.cn", "/"));
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	public BasicClientCookie makeCookie(String key, String value, String domain, String path) {
		BasicClientCookie cookie = new BasicClientCookie(key, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		try {
			cookie.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-31"));
		} catch (ParseException e) {
		}
		return cookie;
	}

	protected void debug(String format, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, arguments);
		}
	}

	public String get(String url, String charset, Parser parser)
			throws Exception {
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");
		request.setHeader("Cookie", "ASP.NET_SessionId=" + this.sessionId);
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		debug("Sending 'GET' request to URL : {}", url);
		debug("Response Code : {}", responseCode);

		// set cookies
//		setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : response.getFirstHeader("Set-Cookie").toString());
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

		debug("\nSending 'POST' request to URL : " + url);
		debug("Post parameters : " + postParams);
		debug("Response Code : " + responseCode);

		String html = EntityUtils.toString(response.getEntity(), charset);
		if (null != parser) {
			parser.parse(html);
		}
		return html;

	}

	public void getPage(String url, String chartset, Parser parser) throws Exception {
		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();
		// default is GET
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);
		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		conn.addRequestProperty("Cookie", "ASP.NET_SessionId=" + this.sessionId);
		int responseCode = conn.getResponseCode();
		debug("Sending 'GET' request to URL : " + url);
		debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), chartset));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		if (null != parser) {
			parser.parse(response.toString());
		}

	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

}

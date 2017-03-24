/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

/**
 * @author huzexiong
 *
 */
public class QiChachaHttp {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HOST = "www.qichacha.com";
	private static final String REFERER = "http://www.qichacha.com/";
	private static HttpClient client = null;
	private String cookies = null;

	static {
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(
				makeCookie("gr_session_id_9c1eb7420511f8b2", "72560a76-0e65-4669-9b14-61391240e29d", ".qichacha.com", "/"));
		cookieStore
				.addCookie(makeCookie("CNZZDATA1254842228", "1686878994-1490174686-%7C1490316308", "www.qichacha.com", "/"));
		cookieStore.addCookie(makeCookie("PHPSESSID", "k78jotf34rmgrit5kd63bjmgh6", "www.qichacha.com", "/"));
		cookieStore.addCookie(makeCookie("_umdata",
				"2FB0BDB3C12E491DDD1DCE7FBAEE34EB41808F31E4939C1574E0D662BD86A749650FE2C147034866CD43AD3E795C914CA59A679125B5316A39A8A44C72519E10",
				"www.qichacha.com", "/"));
		cookieStore.addCookie(makeCookie("_uab_collina", "149017829148510557057333", "www.qichacha.com", "/"));
		cookieStore.addCookie(makeCookie("gr_user_id", "65bf1dda-1c3b-4a7e-acad-91d5835b79bb", ".qichacha.com", "/"));
		cookieStore.addCookie(makeCookie("UM_distinctid", "15af58bc3c01a0-0f2ebcf2d24dd9-58133b15-1fa400-15af58bc3c498a",
				".qichacha.com", "/"));
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	public static BasicClientCookie makeCookie(String key, String value, String domain, String path) {
		BasicClientCookie cookie = new BasicClientCookie(key, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		try {
			cookie.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-31"));
		} catch (ParseException e) {
		}
		return cookie;
	}

	public static void main(String[] args) throws Exception {

		String url = "http://localhost:8080/dfm-web/s/app/888888/auth/version";

		QiChachaHttp http = new QiChachaHttp();

		String result = http.get(url, "UTF-8", null);
		System.out.println(result);
		System.out.println("Done");
	}

	public String get(String url, String charset, Parser parser) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		// set cookies
		setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : response.getFirstHeader("Set-Cookie").toString());
		String html = EntityUtils.toString(response.getEntity(), charset);
		try {
			new IllegalParser().parse(html);
		} catch (UnsupportedOperationException e) {
			throw new RuntimeException(e);
		}
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
		try {
			new IllegalParser().parse(html);
		} catch (UnsupportedOperationException e) {
			throw new RuntimeException(e);
		}
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

/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
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

/**
 * @author huzexiong
 *
 */
public class CookieHttp {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String DOMAIN = "localhost";
	private static final String HOST = DOMAIN + ":8080";
	private static final String REFERER = "https://" + HOST + "/dfm-web/";
	private String cookies = null;
	private static HttpClient client = null;

	static {
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(makeCookie("dfmJwt",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c3JOYSI6Iua1t-m8juWFrOWPuCIsImV4cCI6MTQ5MDQwNzAxNSwidXNyTW8iOiIxNTU1NTU1NTU1NSIsInVzcklkIjoiOCIsImNobCI6ImFwcCIsImVwIjoiODg4ODg4IiwiaWF0IjoxNDkwMzIwNjE1LCJ1c3JMdCI6MTQ5MDMyMDYxNTA3OH0.D4WEXCOO70vgsTeX9Dbwkp9lC_pB0TQawD6h9PFsCqQ"));
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	public static BasicClientCookie makeCookie(String key, String value) {
		BasicClientCookie cookie = new BasicClientCookie(key, value);
		cookie.setDomain(DOMAIN);
		cookie.setPath("/");
		try {
			cookie.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-31"));
		} catch (ParseException e) {
		}
		return cookie;
	}

	public static void main(String[] args) throws Exception {

		String url = "http://localhost:8080/dfm-web/s/app/888888/auth/version";

		CookieHttp http = new CookieHttp();

		String result = http.get(url);
		System.out.println(result);

		System.out.println("Done");
	}

	public String get(String url) throws Exception {

		HttpGet request = new HttpGet(url);

		request.setHeader("User-Agent", USER_AGENT);
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "en-US,en;q=0.5");

		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		// set cookies
		setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : response.getFirstHeader("Set-Cookie").toString());

		return result.toString();

	}

	public String post(String url, List<NameValuePair> postParams) throws Exception {

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

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();

	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

}

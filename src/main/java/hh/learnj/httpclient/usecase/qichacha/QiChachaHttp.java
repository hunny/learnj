/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import hh.learnj.httpclient.usecase.googletranslate.DynamicProxyRoutePlanner;

/**
 * @author huzexiong
 *
 */
public class QiChachaHttp {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HOST = "www.qichacha.com";
	private static final String REFERER = "http://www.qichacha.com/";
	private static HttpClient client = null;
	private static DynamicProxyRoutePlanner routePlanner = null;
	private String cookies = null;

	static {
		// make sure cookies is turn on
		// CookieHandler.setDefault(new CookieManager());
		// BasicCookieStore cookieStore = new BasicCookieStore();
		// cookieStore.addCookie(
		// makeCookie("gr_session_id_9c1eb7420511f8b2",
		// "72560a76-0e65-4669-9b14-61391240e29d", ".qichacha.com", "/"));
		// cookieStore
		// .addCookie(makeCookie("CNZZDATA1254842228",
		// "1686878994-1490174686-%7C1490316308", "www.qichacha.com", "/"));
		// cookieStore.addCookie(makeCookie("PHPSESSID",
		// "k78jotf34rmgrit5kd63bjmgh6", "www.qichacha.com", "/"));
		// cookieStore.addCookie(makeCookie("_umdata",
		// "2FB0BDB3C12E491DDD1DCE7FBAEE34EB41808F31E4939C1574E0D662BD86A749650FE2C147034866CD43AD3E795C914CA59A679125B5316A39A8A44C72519E10",
		// "www.qichacha.com", "/"));
		// cookieStore.addCookie(makeCookie("_uab_collina",
		// "149017829148510557057333", "www.qichacha.com", "/"));
		// cookieStore.addCookie(makeCookie("gr_user_id",
		// "65bf1dda-1c3b-4a7e-acad-91d5835b79bb", ".qichacha.com", "/"));
		// cookieStore.addCookie(makeCookie("UM_distinctid",
		// "15af58bc3c01a0-0f2ebcf2d24dd9-58133b15-1fa400-15af58bc3c498a",
		// ".qichacha.com", "/"));
		// client =
		// HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

		// HttpHost proxy = new HttpHost("211.144.217.66", 80, "http");
		// routePlanner = new DynamicProxyRoutePlanner(proxy);
		// client =
		// HttpClientBuilder.create().setRoutePlanner(routePlanner).build();

		// setPropertyProxy("211.144.217.66", 80);
		// client = HttpClientBuilder.create().useSystemProperties().build();
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", new MyHTTPConnectionSocketFactory())
				.register("https", new MyHTTPSConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
//		setPropertyProxy("211.144.217.66", 80);
		client = HttpClientBuilder.create().useSystemProperties().setConnectionManager(cm).build();
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

	public static RequestConfig proxy(String host, int port) {
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(host, port, "http");
		return RequestConfig.custom().setProxy(proxy).build();
	}

	public static CloseableHttpClient proxyAuth() {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("", 8080), // 可以访问的范围
				new UsernamePasswordCredentials("username", "password"));// 用户名和密码
		CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		return client;
	}

	public static void main(String[] args) throws Exception {

		String url = "http://localhost:8080/dfm-web/s/app/888888/auth/version";

		QiChachaHttp http = new QiChachaHttp();

		String result = http.get(url, "UTF-8", null);
		System.out.println(result);
		System.out.println("Done");
	}

	public static void setPropertyProxy(String ip, int port) {
		System.getProperty("proxySet", "true");
		System.setProperty("java.net.useSystemProxies", "true");
		System.getProperties().setProperty("http.proxyHost", ip);
		System.getProperties().setProperty("http.proxyPort", String.valueOf(port));
	}

	public String get(String url, String charset, Parser parser) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", USER_AGENT);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
		// routePlanner.setProxy(new HttpHost("211.144.217.66", 80, "http"));
		setPropertyProxy("211.144.217.66", 80);
		HttpResponse response = client.execute(httpGet);
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
		// post.setHeader("Cookie", getCookies());
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

	static class MyHTTPConnectionSocketFactory extends PlainConnectionSocketFactory {
		@Override
		public Socket createSocket(final HttpContext context) throws IOException {
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("211.144.217.66", 80));
			return new Socket(proxy);
		}
	}

	static class MyHTTPSConnectionSocketFactory extends SSLConnectionSocketFactory {
		public MyHTTPSConnectionSocketFactory(final SSLContext sslContext) {
			super(sslContext);
		}

		@Override
		public Socket createSocket(final HttpContext context) throws IOException {
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("211.144.217.66", 80));
			return new Socket(proxy);
		}
	}

}

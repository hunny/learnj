/**
 * 
 */
package hh.learnj.httpclient.usecase.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author huzexiong
 *
 */
public class CheckProxy {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

	public void checkByHttpClient() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		httpGet.setHeader("User-Agent", USER_AGENT);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
		httpGet.setConfig(proxy("111.73.241.191", 9000));
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == 200) {
			System.out.println("OK");
		}
	}

	public static boolean check(String ip, int port) {
		try {
			URL url = new URL("http://www.baidu.com");
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			URLConnection conn = url.openConnection(proxy);
			InputStream in = conn.getInputStream();
			String s = IOUtils.toString(in, "UTF-8");
			// System.out.println(s);
			if (s.indexOf("百度") > 0) {
				return true;
			}
		} catch (MalformedURLException e) {
//			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (IOException e) {
//			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public static String html(String surl, String ip, int port) {
		try {
			URL url = new URL(surl);
			// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			URLConnection conn = url.openConnection(proxy);
			InputStream in = conn.getInputStream();
			String s = IOUtils.toString(in, "UTF-8");
			return s;
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static RequestConfig proxy(String host, int port) {
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(host, port, "http");
		return RequestConfig.custom().setProxy(proxy).build();
	}
	
	public static void main(String[] args) {
		String html = checkYoudaili("http://www.youdaili.net/Daili/http/36307.html");
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div.pagebreak li");
		for (Element element : elements) {
			Element href = element.select("a").first();
			if (null != href) {
				String url = href.attr("href");
				if (StringUtils.isNotBlank(url) && url.endsWith("html")) {
					url = "http://www.youdaili.net/Daili/http/" + url;
					System.out.println("Ready for " + url);
					CheckProxy.checkYoudaili(url);
				}
			}
		}
	}
	
	public static String checkYoudaili(String url) {
		if (null == url) {
			url = "http://www.youdaili.net/Daili/http/36307.html";
		}
		return Req.get(url, "UTF-8", new ProxyIpParser());
	}

}

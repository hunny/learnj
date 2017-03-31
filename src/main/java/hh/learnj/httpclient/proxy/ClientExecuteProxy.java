/**
 * 
 */
package hh.learnj.httpclient.proxy;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author huzexiong
 *
 */
public class ClientExecuteProxy {

	public static void main(String[] args) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpHost target = new HttpHost("www.whoishostingthis.com", 80, "http");
			HttpHost proxy = new HttpHost("117.21.234.96", 8080, "http");

			RequestConfig config = RequestConfig.custom() //
					.setProxy(proxy) //
					.build(); //
			HttpGet request = new HttpGet("/tools/user-agent/");
			request.setHeader("User-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			request.setConfig(config);//

			System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

			CloseableHttpResponse response = httpclient.execute(target, request);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				String html = EntityUtils.toString(response.getEntity());
				System.out.println();
				Document doc = Jsoup.parse(html);
				Element element = doc.select("div#user-agent").first();
				if (null != element) {
					System.out.println(element.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

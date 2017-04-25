/**
 * 
 */
package hh.learnj.httpclient.gogotester;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.ssl.SSLContexts;

/**
 * @author huzexiong
 *
 */
public class HttpConnect {

	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(//
			RegistryBuilder //
					.<ConnectionSocketFactory> create() //
					.register("http", PlainConnectionSocketFactory.getSocketFactory()) //
					.register("https",
							new SSLConnectionSocketFactory(SSLContexts.createDefault(), //
									NoopHostnameVerifier.INSTANCE)) //
					.build()); //

	private static RequestConfig rc = RequestConfig //
			.custom() //
			.setConnectTimeout(Integer.parseInt(System.getProperty("http.connectionTimeout"))) //
			.setCookieSpec("ignoreCookies") //
			.setSocketTimeout(Integer.parseInt(System.getProperty("http.socketTimeout"))) //
			.build(); //

	private static CloseableHttpClient httpClient = HttpClients //
			.custom() //
			.setConnectionManager(cm) //
			.setDefaultRequestConfig(rc) //
			.disableAutomaticRetries() //
			.build(); //

	private static BasicHttpContext bhc = new BasicHttpContext();

	public static HttpResponse execute(HttpRequestBase req) throws IOException {
		return httpClient.execute(req, bhc);
	}

	static {
		cm.setMaxTotal(Integer.parseInt(System.getProperty("http.maxTotal")));
		cm.setDefaultMaxPerRoute(Integer.parseInt(System.getProperty("http.maxPerRoute")));
	}
}

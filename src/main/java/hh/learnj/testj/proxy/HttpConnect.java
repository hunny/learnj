package hh.learnj.testj.proxy;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;

public class HttpConnect {
	
	@SuppressWarnings("deprecation")
	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(RegistryBuilder
			.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.getSocketFactory())
			.register("https", new SSLConnectionSocketFactory(
					SSLContexts.createDefault(),
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
			.build());

	private static RequestConfig rc = RequestConfig.custom() //
			.setConnectTimeout(5000) //
			.setCookieSpec("ignoreCookies") //
			.setSocketTimeout(5000) //
			.build(); //

	private static CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
			.setDefaultRequestConfig(rc).disableAutomaticRetries().build();

	private static BasicHttpContext bhc = new BasicHttpContext();

	public static HttpResponse execute(HttpRequestBase req)
			throws IOException {
		return httpClient.execute(req, bhc);
	}

	static {
		cm.setMaxTotal(10);
		cm.setDefaultMaxPerRoute(5);
	}
}
package hh.learnj.httpclient.usecase.googletranslate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

public class HttpAgentClient {

	public static CloseableHttpClient httpAgentClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
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
			DynamicProxyRoutePlanner routePlanner = new DynamicProxyRoutePlanner(
					proxy);
			return HttpClients.custom()
				.setSSLSocketFactory(connectionFactory)
				.setRoutePlanner(routePlanner)
				.setSSLHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname,
							SSLSession session) {
						return true;
					}
				}).build();
	}

}

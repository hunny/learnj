package hh.learnj.httpclient.usecase.googletranslate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientGoogleTranslate {

	private static final Logger logger = Logger
			.getLogger(HttpClientGoogleTranslate.class);

	// https://translate.google.com/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=en&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=2&rom=1&ssel=0&tsel=3&kc=1&tk=520257|854073&q=what%20the%20fuck%20are%20you%20talking%20about

	public static void main(String[] args) {
		new HttpClientGoogleTranslate().googleTranslate("what");
	}

	public String googleTranslate(String msg) {
		CloseableHttpClient httpclient = null;
		try {
			SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, new TrustStrategy() {
					@Override
					public boolean isTrusted(final X509Certificate[] chain,
							final String authType)
							throws CertificateException {
						return true;
					}
				}).build();
//			SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
//					sslContext, new AllowAllHostnameVerifier());
			SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext);

			HttpHost proxy = new HttpHost("127.0.0.1", 8087);
			DynamicProxyRoutePlanner routePlanner = new DynamicProxyRoutePlanner(
					proxy);

			httpclient = HttpClients.custom()
				.setSSLSocketFactory(connectionFactory)
				.setRoutePlanner(routePlanner)
				.setSSLHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname,
							SSLSession session) {
						return true;
					}
				}).build();
			// 创建httpget.
			HttpGet httpget = new HttpGet(urlBuilder(msg));
			logger.info("============> executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					logger.info("============> Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					logger.info("============> Response content: "
							+ EntityUtils.toString(entity));
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public URI urlBuilder(String msg) throws URISyntaxException {
		URIBuilder builder = new URIBuilder(
				"https://translate.google.com/translate_a/single?");
		builder.addParameter("client", "t");
		builder.addParameter("sl", "auto");
		builder.addParameter("tl", "zh-CN");
		builder.addParameter("hl", "en");
		builder.addParameter("dt", "bd");
		builder.addParameter("dt", "ex");
		builder.addParameter("dt", "ld");
		builder.addParameter("dt", "md");
		builder.addParameter("dt", "qca");
		builder.addParameter("dt", "rw");
		builder.addParameter("dt", "rm");
		builder.addParameter("dt", "ss");
		builder.addParameter("dt", "t");
		builder.addParameter("dt", "at");
		builder.addParameter("ie", "UTF-8");
		builder.addParameter("oe", "UTF-8");
		builder.addParameter("otf", "2");
		builder.addParameter("rom", "1");
		builder.addParameter("ssel", "0");
		builder.addParameter("tsel", "3");
		builder.addParameter("kc", "1");
		builder.addParameter("tk", "520257|854073");
		builder.addParameter("q", msg);
		return builder.build();
	}

}

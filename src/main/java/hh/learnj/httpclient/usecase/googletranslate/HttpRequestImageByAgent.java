package hh.learnj.httpclient.usecase.googletranslate;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hh.learnj.httpclient.usecase.download.DownloadFile;

public class HttpRequestImageByAgent {
	
	private static final Logger logger = Logger
			.getLogger(HttpRequestImageByAgent.class);

	public static void main(String[] args) {
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpAgentClient.httpAgentClient();
			try {
				// 获取响应实体
				String baseUrl = "http://img.abc.com/";
					for (int i = 1000; i < 1500; i++) {
						String src = baseUrl + i + ".jpg";
						logger.info(src);
						DownloadFile.download(httpclient, src, "D:/mytemp/", null);
					}
				System.out.println("------------------------------------");
			} finally {
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

	}

}

package hh.learnj.httpclient.usecase.googletranslate;

import hh.learnj.httpclient.usecase.download.DownloadFile;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpByAgent {
	
	private static final Logger logger = Logger
			.getLogger(HttpByAgent.class);

	public static void main(String[] args) {
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpAgentClient.httpAgentClient();
			// 创建httpget.
			HttpGet httpget = new HttpGet("https://plus.google.com");
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
					//logger.info("============> Response content: "
					//		+ EntityUtils.toString(entity));
					Document doc = Jsoup.parse(EntityUtils.toString(entity));
					Elements elemets = doc.select("input[src]");
					if (elemets.isEmpty()) {
						elemets = doc.select("img[src][onclick]");
					}
					for (Element elem : elemets) {
						String src = elem.attr("src");
						logger.info(src);
						DownloadFile.download(httpclient, src, "D:/mytemp/", null);
					}
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

	}

}

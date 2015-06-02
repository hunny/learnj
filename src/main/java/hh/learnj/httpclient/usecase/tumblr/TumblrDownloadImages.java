package hh.learnj.httpclient.usecase.tumblr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TumblrDownloadImages {

	private static Logger logger = Logger.getLogger(TumblrDownloadImages.class);

	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://wanimal1983.tumblr.com/");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity);
		Document document = Jsoup.parse(html);
		Elements images = document.getElementsByTag("img");
		for (Element elem : images) {
			String src = elem.attr("src");
			String [] srcs = src.split("/");
			logger.info(elem.toString());
			logger.info("file name:" + srcs[srcs.length - 1]);
			try {
				downloadFile(httpclient, src, "D:/images/" + srcs[srcs.length - 1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void downloadFile(CloseableHttpClient httpclient, String url,
			String filePath) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			int inByte;
			while ((inByte = is.read()) != -1)
				fos.write(inByte);
			is.close();
			fos.close();
		}
	}

}

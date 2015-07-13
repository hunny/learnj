package hh.learnj.httpclient.usecase.download;

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
import org.apache.log4j.Logger;

public class DownloadFile {

	private static Logger logger = Logger.getLogger(DownloadFile.class);

	public static void main(String[] args) {
		download("http://download.ted.com/talks/Rives_4AM_2007-480p.mp4",
				"D:/mp4s/");
//		download("http://img.my.csdn.net/uploads/201211/29/1354159363_7245.PNG",
//				"D:/mp4s/");
	}

	public static void download(String src, String local) {
		String[] srcs = src.split("/");
		logger.info("file name:" + srcs[srcs.length - 1]);
		try {
			download(HttpClients.createDefault(), src, local
					+ srcs[srcs.length - 1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void download(CloseableHttpClient httpclient, String url,
			String filePath) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			long length = entity.getContentLength();
			logger.info(length);
			InputStream is = entity.getContent();
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			int inByte;
			long count = 0;
			while ((inByte = is.read()) != -1) {
				fos.write(inByte);
				count += 1;
				if (count % 1024 == 0) {
					System.out.println(count + ", " + (count * 100.0 / length) + "%");
					fos.flush();
				}
			}
			System.out.println(count + ", " + (count * 100.0 / length) + "%");
			is.close();
			fos.close();
		}
	}

}

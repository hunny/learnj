package hh.learnj.httpclient.usecase.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.time.DurationFormatUtils;
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
//		download("http://download.ted.com/talks/Rives_4AM_2007-480p.mp4",
//				"D:/mp4s/");
//		download("http://img.my.csdn.net/uploads/201211/29/1354159363_7245.PNG",
//				"D:/mp4s/");
		download("http://180.97.83.161:443/down/5b376a6cb488e132e8ec3ec49de8460e-30356480/%E7%AC%AC%E4%B8%80%E9%9B%86%E6%97%A0%E7%BA%BFWIFI%E5%AF%86%E7%A0%81PJ%E6%80%BB%E8%BF%B0.avi?cts=dx-f-101A230A3A22777623&ctp=101A230A3A2&ctt=1437008702&limit=2&spd=1200000&ctk=b4b0ae0e655501aa2598974426ab4f70&chk=5b376a6cb488e132e8ec3ec49de8460e-30356480&mtd=1",
				"D:/mp4s/", "第一集无线WIFI密码PJ总述.avi");
	}
	
	public static void download(String src, String local) {
		download(src, local, null);
	}

	public static void download(String src, String local, String localName) {
		String[] srcs = src.split("/");
		logger.info("download :" + src);
		logger.info("file name:" + srcs[srcs.length - 1]);
		try {
			if (null == localName) {
				localName = srcs[srcs.length - 1];
			}
			download(HttpClients.createDefault(), src, local
					+ localName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void download(CloseableHttpClient httpclient, String url,
			String filePath) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		long start = System.currentTimeMillis();
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			long length = entity.getContentLength();
			InputStream is = entity.getContent();
			File file = new File(filePath);
			String fileName = file.getName();
			FileOutputStream fos = new FileOutputStream(file);
			int inByte;
			long count = 0;
			while ((inByte = is.read()) != -1) {
				fos.write(inByte);
				count += 1;
				if (count % 1024 == 0) {
					logger.info(fileName + ", total:" + length + ", precent:" + String.format("%05.2f", count * 100.0 / length) + "%, time: " + longToTime(System.currentTimeMillis() - start) + ", finished:" + count);
					fos.flush();
				}
			}
			logger.info(fileName + ", total:" + length + ", precent:" + (count * 100.0 / length) + "%, time: " + longToTime(System.currentTimeMillis() - start) + ", finished:" + count);
			is.close();
			fos.close();
		}
	}
	
	public static String longToTime(long time) {
//		Date date = new Date(time);
//		DateFormat formatter = new SimpleDateFormat("HH:mm:ss SSS");
//		return formatter.format(date);
		
//		long second = (time / 1000) % 60;
//		long minute = (time / (1000 * 60)) % 60;
//		long hour = (time / (1000 * 60 * 60)) % 24;
//		return String.format("%02d:%02d:%02d:%d", hour, minute, second, time);
		
		return DurationFormatUtils.formatDuration(time, "HH:mm:ss,SSS");
	}

}

package hh.learnj.httpclient.usecase.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import hh.learnj.httpclient.usecase.googletranslate.HttpAgentClient;

public class JavaYoutubeDownloader {

	public static String newline = System.getProperty("line.separator");
	private static final Logger log = Logger.getLogger(JavaYoutubeDownloader.class.getCanonicalName());
	private static final String scheme = "http";
	private static final String host = "www.youtube.com";
	private static final Pattern commaPattern = Pattern.compile(",");
	private static final Pattern pipePattern = Pattern.compile("\\|");
	private static final char[] ILLEGAL_FILENAME_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\',
			'<', '>', '|', '\"', ':' };
	private static final String ENCODER = "UTF-8";

	private static void usage(String error) {
		if (error != null) {
			System.err.println("Error: " + error);
		}
		System.err.println("usage: JavaYoutubeDownload VIDEO_ID DESTINATION_DIRECTORY");
		System.exit(-1);
	}

	public static void main(String[] args) {
//
//		String test = "quality=medium&fallback_host=tc.v9.cache3.googlevideo.com&itag=43&type=video/webm; codecs=\"vp8.0\"";
//		System.out.println(test.replaceAll("fallback_host=(.*)", ""));
		// if (args == null || args.length == 0) {
		// usage("Missing video id. Extract from
		// http://www.youtube.com/watch?v=VIDEO_ID");
		// }
		try {
			log.info("Starting");
//			String videoId = "wBDsVcYnJBc";//https://www.youtube.com/watch?v=wBDsVcYnJBc
			String videoId = "gyeGHirIhYc";//https://www.youtube.com/watch?v=gyeGHirIhYc
			String outdir = "D:/BaiduYunDownload";
			// TODO Ghetto command line parsing
			// if (args.length == 1) {
			// videoId = args[0];
			// } else if (args.length == 2) {
			// videoId = args[0];
			// outdir = args[1];
			// }
			int format = 18; // http://en.wikipedia.org/wiki/YouTube#Quality_and_codecs
			String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
			File outputDir = new File(outdir);
			String extension = getExtension(format);

			play(videoId, format, ENCODER, userAgent, outputDir, extension);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		log.info("Finished");
	}

	private static String getExtension(int format) {
		return "mp4";
	}

	private static void play(String videoId, int format, String encoding, String userAgent, File outputdir,
			String extension) throws Throwable {
		log.info("Retrieving " + videoId);
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("video_id", videoId));
		// qparams.add(new BasicNameValuePair("fmt", "" + format));
		URI uri = getUri("get_video_info", qparams);

		CookieStore cookieStore = new BasicCookieStore();
		HttpContext localContext = new BasicHttpContext();
//		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

		HttpClient httpclient = HttpAgentClient.httpAgentClient();// new
																	// DefaultHttpClient();
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("User-Agent", userAgent);

		log.info("Executing " + uri);
		HttpResponse response = httpclient.execute(httpget, localContext);
		HttpEntity entity = response.getEntity();
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (entity != null && response.getStatusLine().getStatusCode() == 200) {
			InputStream instream = entity.getContent();
			String videoInfo = getStringFromInputStream(encoding, instream);
			log.debug("[+][-][=][=]" + videoInfo);
			if (videoInfo != null && videoInfo.length() > 0) {
				List<NameValuePair> infoMap = new ArrayList<NameValuePair>();
				infoMap = URLEncodedUtils.parse(videoInfo, Charset.forName("UTF-8"));
				String downloadUrl = null;
				String filename = videoId;
				for (NameValuePair pair : infoMap) {
					String key = pair.getName();
					String val = pair.getValue();
					log.debug(key + "=" + val);
					if (key.equals("title")) {
						filename = val;
					} else if (key.equals("fmt_list")) {
						log.info("[+]" + val);
					} else if (key.equals("url_encoded_fmt_stream_map")) {
						log.info("[+][=][=][=][=][=][=][=][=][=][=][=][=]");
						String [] itags = val.split(",");
						for (String s : itags) {
							int iKey = checkItag(s);
							log.info("[+][=][SPOT]" + s);
							String [] a = s.split("url=");
							if (a.length <= 1) {
								continue;
							}
							log.info("[+][=][URL]" + a[1]);
							String decode = URLDecoder.decode(a[1], "UTF-8");
							List<NameValuePair> tmpList = URLEncodedUtils.parse(decode, Charset.forName("UTF-8"));
							for (NameValuePair p : tmpList) {
								log.info("[+][=][" + p.getName() + "]=" + p.getValue());
								if ("type".equals(p.getName()) 
										|| "itag".equals(p.getName()) 
										|| "codecs".equals(p.getName()) 
										|| "; codecs".equals(p.getName())) {
									decode = decode.replaceAll(p.getName() + "=" + p.getValue(), "");
								}
							}
							decode = decode + "&itag=" +  iKey;
							decode = decode.replaceAll("&+", "&");
							map.put(iKey, decode);
						}
						log.info("[+][=][=][=][=][=][=][=][=][=][=][=][=]");
					}
				}
				if (map.isEmpty()) {
					System.out.println("No file downloaded");
					return;
				}
				Integer maxKey = Collections.max(map.keySet());
				downloadUrl = map.get(maxKey);
				filename = cleanFilename(filename);
				if (filename.length() == 0) {
					filename = videoId;
				} else {
					filename += "_" + videoId;
				}
				filename += "." + extension;
				File outputfile = new File(outputdir, filename);
				if (downloadUrl != null) {
					log.info("[+][+][MAXKEY]" + maxKey);
					log.info("[+][+][FILENAME]" + filename);
					log.info("[+][+][DOWNLOAD]" + downloadUrl);
					downloadWithHttpClient(userAgent, downloadUrl, outputfile);
				}
			}
		}
	}
	
	private static int checkItag(String v) {
		if (StringUtils.isBlank(v)) {
			return -1;
		}
		if (v.indexOf("itag=") <= -1) {
			return -1;
		}
		Pattern u = Pattern.compile(".*itag=(\\d*)");
        Matcher um = u.matcher(v);
        if (um.find()) {
			log.info("[+][-][KEY]" + um.group(1));
			return Integer.parseInt(um.group(1));
        }
        return -1;
	}

	private static void downloadWithHttpClient(String userAgent, String downloadUrl, File outputfile) throws Throwable {
		HttpGet httpget2 = new HttpGet(downloadUrl);
		httpget2.setHeader("User-Agent", userAgent);

		log.debug("Executing " + httpget2.getURI());
		HttpClient httpclient2 = HttpAgentClient.httpAgentClient();// new
																	// DefaultHttpClient();
		HttpResponse response2 = httpclient2.execute(httpget2);
		HttpEntity entity2 = response2.getEntity();
		if (entity2 != null && response2.getStatusLine().getStatusCode() == 200) {
			long length = entity2.getContentLength();
			InputStream instream2 = entity2.getContent();
			log.debug("Writing " + length + " bytes to " + outputfile);
			if (outputfile.exists()) {
				outputfile.delete();
			}
			FileOutputStream outstream = new FileOutputStream(outputfile);
			try {
				byte[] buffer = new byte[2048];
				int count = -1;
				while ((count = instream2.read(buffer)) != -1) {
					outstream.write(buffer, 0, count);
				}
				outstream.flush();
			} finally {
				outstream.close();
			}
		} else {
			log.debug("[+][Response Code] " + response2.getStatusLine().getStatusCode());
		}
	}

	private static String cleanFilename(String filename) {
		for (char c : ILLEGAL_FILENAME_CHARACTERS) {
			filename = filename.replace(c, '_');
		}
		return filename;
	}

	private static URI getUri(String path, List<NameValuePair> qparams) throws URISyntaxException {
		URI uri = URIUtils.createURI(scheme, host, -1, "/" + path, URLEncodedUtils.format(qparams, "UTF-8"), null);
//		URI uri = URIBuilder.createURI(scheme, host, -1, "/" + path, URLEncodedUtils.format(qparams, "UTF-8"), null);
		return uri;
	}

	private static String getStringFromInputStream(String encoding, InputStream instream)
			throws UnsupportedEncodingException, IOException {
		Writer writer = new StringWriter();

		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(instream, encoding));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			instream.close();
		}
		String result = writer.toString();
		return result;
	}
}

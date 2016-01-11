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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;

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

	private static void usage(String error) {
		if (error != null) {
			System.err.println("Error: " + error);
		}
		System.err.println("usage: JavaYoutubeDownload VIDEO_ID DESTINATION_DIRECTORY");
		System.exit(-1);
	}

	public static void main(String[] args) {
		// if (args == null || args.length == 0) {
		// usage("Missing video id. Extract from
		// http://www.youtube.com/watch?v=VIDEO_ID");
		// }
		try {
			log.info("Starting");
			String videoId = "wBDsVcYnJBc";//https://www.youtube.com/watch?v=wBDsVcYnJBc
			String outdir = "D:/BaiduYunDownload";
			// TODO Ghetto command line parsing
			// if (args.length == 1) {
			// videoId = args[0];
			// } else if (args.length == 2) {
			// videoId = args[0];
			// outdir = args[1];
			// }
			int format = 18; // http://en.wikipedia.org/wiki/YouTube#Quality_and_codecs
			String encoding = "UTF-8";
			String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
			File outputDir = new File(outdir);
			String extension = getExtension(format);

			play(videoId, format, encoding, userAgent, outputDir, extension);

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
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

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
				URLEncodedUtils.parse(infoMap, new Scanner(videoInfo), encoding);
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
						String [] itags = val.split("url=");
						String fixed = null;
						String prefix = null;
						for (String s : itags) {
							String decode = URLDecoder.decode(s, "UTF-8");
							if (!StringUtil.isBlank(decode) && !decode.startsWith("http") && null == fixed) {
								fixed = itags[0];
								prefix = fixed.split("=")[0];
								log.info("[+][=][FIXED]" + fixed);
								log.info("[+][=][PREFIX]" + prefix);
								continue;
							}
							if (null != prefix) {
								String [] decodes = decode.split(prefix);
								decode = decodes[0];
								if (!decode.endsWith("&")) {
									decode += "&";
								}
								decode += fixed;
								if (decodes.length >= 2) {
									fixed = decodes[1];
								}
							}
							Pattern u = Pattern.compile(".*itag=(\\d*)");
				            Matcher um = u.matcher(decode.toString());
				            if (um.find()) {
								log.info("[+][-][KEY]" + um.group(1));
								if (null != prefix) {
									decode = decode.split(prefix)[0];
								}
								if (decode.indexOf(",") > 0) {
									decode = decode.split(",")[0];
								}
								map.put(Integer.parseInt(um.group(1)), decode);
				            }
							log.info("[+][-]" + decode);
						}
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

	private static Map<Integer, String> splitToMap(String value) throws Exception {
		if (null == value || value.length() == 0) {
			return Collections.emptyMap();
		}
		int begin = value.indexOf("itag=");
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (begin != -1) {
			int end = value.indexOf("&", begin + 5);
			if (end == -1) {
				end = value.length();
			}
			String result = null;
			int fmt = Integer.parseInt(value.substring(begin + 5, end));
			begin = value.indexOf("url=");
			if (begin != -1) {
				end = value.indexOf("&", begin + 4);
				if (end == -1) {
					end = value.length();
				}
				result = URLDecoder.decode(value.substring(begin + 4, end), "UTF-8");
			}
			map.put(fmt, result);
		}
		return map;
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

/**
 * <pre>
 * Exploded results from get_video_info:
 * 
 * fexp=90...
 * allow_embed=1
 * fmt_stream_map=35|http://v9.lscache8...
 * fmt_url_map=35|http://v9.lscache8...
 * allow_ratings=1
 * keywords=Stefan Molyneux,Luke Bessey,anarchy,stateless society,giant stone cow,the story of our unenslavement,market anarchy,voluntaryism,anarcho capitalism
 * track_embed=0
 * fmt_list=35/854x480/9/0/115,34/640x360/9/0/115,18/640x360/9/0/115,5/320x240/7/0/0
 * author=lukebessey
 * muted=0
 * length_seconds=390
 * plid=AA...
 * ftoken=null
 * status=ok
 * watermark=http://s.ytimg.com/yt/swf/logo-vfl_bP6ud.swf,http://s.ytimg.com/yt/swf/hdlogo-vfloR6wva.swf
 * timestamp=12...
 * has_cc=False
 * fmt_map=35/854x480/9/0/115,34/640x360/9/0/115,18/640x360/9/0/115,5/320x240/7/0/0
 * leanback_module=http://s.ytimg.com/yt/swfbin/leanback_module-vflJYyeZN.swf
 * hl=en_US
 * endscreen_module=http://s.ytimg.com/yt/swfbin/endscreen-vflk19iTq.swf
 * vq=auto
 * avg_rating=5.0
 * video_id=S6IZP3yRJ9I
 * token=vPpcFNh...
 * thumbnail_url=http://i4.ytimg.com/vi/S6IZP3yRJ9I/default.jpg
 * title=The Story of Our Unenslavement - Animated
 * </pre>
 */

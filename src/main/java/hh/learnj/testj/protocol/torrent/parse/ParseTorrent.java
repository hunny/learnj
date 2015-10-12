package hh.learnj.testj.protocol.torrent.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTorrent {

	public static void main(String[] args) throws Exception {
		String fileName = "C:/Users/Hunny.hu/Downloads/0uLY5dJ53fmEFR.torrent";
		// mFileReader(fileName);
		mBufferReader(fileName);
		// matchString("d4:path3:C:/8:filename8:test.txte");
	}

	public static void mBufferReader(String fileName) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				new File(fileName)));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			matchString(line);
		}
		bufferedReader.close();
	}

	public static void mFileReader(String fileName) throws Exception {
		List<String> list = new ArrayList<String>();
		FileReader fileReader = new FileReader(new File(fileName));
		int c = -1;
		StringBuilder builder = new StringBuilder();
		while ((c = fileReader.read()) != -1) {
			char m = (char) c;
			if (m == ':') {
				list.add(builder.toString());
				builder = new StringBuilder();
			} else {
				builder.append(m);
			}
		}
		fileReader.close();
		System.out.println("===================================");
		for (String s : list) {
			System.out.println(s);
		}
	}

	public static String matchString(String s) {
		if (null == s) {
			return null;
		}
		System.out.println("[>]" + s);
		Pattern p = Pattern.compile("i\\d+e");
		//匹配整数
		Matcher m = p.matcher(s);
		while (m.find()) {
			String group = m.group();
			System.out.println("[-]" + "s:" + m.start() + ",e:" + m.end());
			System.out.println("[I]" + group.replaceFirst("^i", "").replaceFirst("e$", ""));
			String tmp = s.replaceFirst(group, "");
			System.out.println("[=]" + tmp.length());
			m = p.matcher(tmp);
		}
		//匹配字符串
		m = p.matcher(s);
		while (m.find()) {
			String group = m.group();
			System.out.println("[-]" + "s:" + m.start() + ",e:" + m.end() + "="
					+ group);
			int len = Integer.parseInt(group.replaceFirst(":$", ""));
			System.out.println("[S]" + s.substring(m.end(), m.end() + len));
		}
		return null;
	}

}

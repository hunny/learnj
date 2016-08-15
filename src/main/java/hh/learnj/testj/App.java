package hh.learnj.testj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
//		String input = "12.0,89,98,-10,-12.0,0.9";
//		System.out.println("Hello World=" + input.matches("^(((-?\\d+)|((-?\\d+)(\\.\\d+))),{0,1})+$"));
//		
//		String src = "http://test.cdp-life.com/mobile/wx_authorize.php";
//		try {
//			System.out.println(java.net.URLEncoder.encode(src, "utf-8"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println();
//		test("abccdefcfk");fdsf
		test("qwertyuiopasdfgfdsfdsfsdfhjklzxcvbnmERTYDFGHJASDFGHJKLzxcvbnmwertdfghcvbnRfgfdgdfgTYFGH");
	}
	
	public static List<String> divideString(String str) {
		if (null == str) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<String>();
		if (str.length() == 1) {
			result.add(str);
			return result;
		}
		for (int i = 1; i < str.length(); i++) {
			String substr = str.substring(0, i);
			char c = str.charAt(i);
			int offset = substr.indexOf(c);
			if (offset >= 0) {
				System.out.println("offset" + offset + "=>i" + i);
				result.add(substr);
				String endstr = str.substring(i, str.length());
				result.addAll(divideString(endstr));
//				result.addAll(divideString(str.substring(0, offset + 1)));
				result.addAll(divideString(str.substring(offset + 1, str.length())));
				break;
			}
			if (str.length() == (i + 1)) {
				result.add(str);
			}
		}
		return result;
	}
	
	public static void test(String str) {
		List<String> list = divideString(str);
		String max = null;
		List<String> maxList = new ArrayList<String>();
		for (String s : list) {
			if (null == max) {
				max = s;
			}
			System.out.println("[+] " + s);
			if (s.length() >= max.length()) {
				if (s.length() > max.length()) {
					maxList.clear();
				}
				max = s;
				maxList.add(max);
			}
		}
		System.out.println("max strings:");
		for (String s : maxList) {
			System.out.println("[+][MAX]" + s);
		}
	}
}

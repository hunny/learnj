/**
 * 
 */
package hh.learnj.testj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author huzexiong
 *
 */
public class Test {
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("3", "OK1");
		map.put("1", "OK2");
		map.put("6", "OK3");
		map.put("2", "OK4");
		map.put("4", "OK5");
		map.put("9", "OK6");
		for (Map.Entry<String, Object> m : map.entrySet()) {
			System.out.println(m.getKey() + "=" + m.getValue());
		}
		for (String s : map.keySet()) {
			System.out.println(s);
		}
		
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Set<Date> dates = new LinkedHashSet<Date>();
		dates.add(format.parse("2016-05-19"));
		dates.add(format.parse("2016-06-19"));
		dates.add(format.parse("2016-08-19"));
		dates.add(format.parse("2016-08-20"));
		dates.add(format.parse("2016-07-20"));
		System.out.println(dates.contains(format.parse("2016-06-19")));
		System.out.println(dates.contains(format.parse("2016-06-20")));
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("9");
		System.out.println(list.subList(6, 9));
	}

}

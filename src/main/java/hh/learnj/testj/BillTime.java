/**
 * 
 */
package hh.learnj.testj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huzexiong
 *
 */
public class BillTime {
	
	protected final Pattern TIME_PATTERN = Pattern.compile("\\[(\\d+)\\]ms");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Long> result = new BillTime().readOf("C:/Users/huzexiong/Desktop/profit.log");
		BigDecimal total = BigDecimal.ZERO;
		for (long l : result) {
			total = total.add(BigDecimal.valueOf(l));
		}
		System.out.println("total:" + total + ", size:" + result.size());
		BigDecimal m = BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(3600)).multiply(BigDecimal.valueOf(result.size()));
		BigDecimal r = total.multiply(BigDecimal.valueOf(99000)).divide(m, 5, RoundingMode.HALF_UP);
		System.out.println("total:" + r);
	}
	
	public List<Long> readOf(String fileName) {
		List<Long> result = new LinkedList<Long>();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String time = checkTime(line);
				if (null != time) {
					System.out.println(time);
					result.add(Long.parseLong(time));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public String checkTime(String str) {
		if (null == str) {
			return null;
		}
		Matcher matcher = TIME_PATTERN.matcher(str);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}

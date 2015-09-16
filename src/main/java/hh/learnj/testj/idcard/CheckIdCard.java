package hh.learnj.testj.idcard;

/**
 * 身份证检测及计算
 * 
 * ①1-2 升级行政区代码
 * ②3-4 地级行政区划分代码
 * ③5-6 县区行政区分代码
 * ④7-10 11-12 13-14 出生年、月、日
 * ⑤15-17 顺序码，同一地区同年、同月、同日出生人的编号，奇数是男性，偶数是女性
 * ⑥18 校验码，如果是0-9则用0-9表示，如果是10则用X（罗马数字10）表示
 * @author Hunny.Hu
 */
public class CheckIdCard {
	
	/**
	 * 检查18位证件号码, 得到的结果对比找到尾数即可。
	 * @param str
	 * @return valid result
	 */
	protected static boolean check18Card(String str) {
		if (null == str) {
			return false;
		}
		if (str.length() != 18) {
			return false;
		}
		return str.substring(17, 18).equalsIgnoreCase(check18Bit(str));
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	protected static String check18Bit(String str) {
		int sum = 0;
		for (int i = 0; i <= 16; i++) {
			int n = Integer.parseInt(String.valueOf(str.charAt(i)));
			sum += n * Math.pow(2, 17 - i);
		}
		String [] checkArr = new String [] {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
		return checkArr[sum % 11];
	}
	
	/**
	 * 检查15位证件号码
	 * @param str
	 * @return valid result
	 */
	protected static String convert15to18(String str) {
		if (null == str || str.length() != 15) {
			return null;
		}
		String nstr = str.substring(0, 6) + "19" + str.substring(6, 15);
		nstr += check18Bit(nstr);
		return nstr;
	}
	
	protected static String cardBirthday(String str) {
		if (null == str || str.length() != 18) {
			return null;
		}
		return str.substring(6, 14);
	}
	
	protected static String cardGender(String str) {
		if (null == str || str.length() != 18) {
			return null;
		}
		String sequence = str.substring(14, 17);
		Integer i = Integer.parseInt(sequence);
		//⑤15-17 顺序码，同一地区同年、同月、同日出生人的编号，奇数是男性，偶数是女性
		if (i % 2 == 0) {
			return "1";//女性
		}
		return "2";//男性
	}

	public static void main(String[] args) {
		System.out.println(check18Card("34052419800101001x"));
		System.out.println(convert15to18("110105491231002"));
		System.out.println(cardBirthday("34052419800101001x"));
		System.out.println(cardGender("11010519491231002X"));
	}

}

package hh.learnj.testj.pin.code.data.collect;

import hh.learnj.testj.pin.code.WpsPin;
import hh.learnj.testj.pin.code.tenda.WpsPinTenda;

import java.util.HashMap;
import java.util.Map;

public class CrackTendaPinCode {

	public static void main(String[] args) {
		try {
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("00:B0:0C:05:5A:D8", "Tenda_XXXX");
//			map.put("C8:3A:35:13:E0:00", "Tenda_XXXX");
//			map.put("C8:3A:35:19:26:A0", "Tenda_XXXX");
			for (Map.Entry<String, String> m : map.entrySet()) {
				WpsPin wpsPin = new WpsPinTenda();
				wpsPin.setBssid(m.getKey());
				wpsPin.setSsid(m.getValue());
				wpsPin.getPinCode();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calc8th() {
		// http://bbs.51cto.com/thread-1025567-1.html
		// 方法对于穷举法PJ路由MM用处不是太大，但是这是我PIN了几个路由器得出来的规律，这个方法对所有品种的路由器有效，希望能给大家一点启发。
		// 第8位的算法：
		// 如果pin码前7位分别是：ABCDEFG
		// 第八位= 30 - 3 * (A+C+E+G) - (B+D+F)
		// 如果是正数，取结果的个位数，如果是负数，取结果的（10 - 个位数）
		// 例如，结果27，取第八位为7，如果是结果是-27，取第八位为（10-7），即3
		//
		// 就拿我破的一个腾达的路由MAC为C8：3A:35:19:26:A0
		// 其PIN码前七位可以用网上公布的方法计算得出：1648288，第八位PIN码数字为：30-3*（1+4+2+8）-（6+8+8）=-37
		// ，10-7=3，而我用reaver1.4穷举出来的PIN码为：16482883，最后一位是3，可以和上述方法算出的数字对上！
		// 经过本人实测，计算第八位PIN码的方法适用于所有路由器。我们用网卡驱动输入PIN码时，如果最后一位数字不是按上述得出的，
		// 随便输入会提示PIN码错误。
	}

}

/**
 * 
 */
package hh.learnj.httpclient.spider;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huzexiong
 *
 */
public class TianYanCha {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String exe = args[0];//C:/work/phantomjs-2.1.1-windows/bin/phantomjs.exe
		String code = args[1];//C:/work/phantomjs-2.1.1-windows/bin/code.js
		if (StringUtils.isBlank(exe) || StringUtils.isBlank(code)) {
			System.out.println("参数不正确。");
			return;
		}
	}

}

/**
 * 
 */
package hh.learnj.httpclient.spider;

import org.apache.commons.lang3.StringUtils;

import hh.learnj.httpclient.usecase.qichacha.DB;

/**
 * @author huzexiong
 *
 */
public class TianYanCha {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (null == args || args.length == 0) {
			return;
		}
		String exe = args[0];
		String code = args[1];
		if (StringUtils.isBlank(exe) || StringUtils.isBlank(code)) {
			System.out.println("参数不正确。");
			return;
		}
		DetailResultHandler detailHandler = new DetailResultHandler();
		ListResultHandler listResultHandler = new ListResultHandler(detailHandler);
		PhantomjsSpider spider = new PhantomjsSpider(exe, code);
		DB.handle(new QueryHandler(spider, listResultHandler));
	}

}

/**
 * 
 */
package hh.learnj.httpclient.usecase.tianyancha;

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
		DB.handle(new TianYanChaQueryHandler());
	}

}

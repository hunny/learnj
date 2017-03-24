/**
 * 
 */
package hh.learnj.testj;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;

import org.springframework.util.DigestUtils;

/**
 * @author huzexiong
 *
 */
public class CheckFileSum {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (null == args || args.length == 0) {
			System.out.println("请输入文件名");
			return;
		}
		System.out.println(MessageFormat.format("检查文件[{0}]的md5值", args[0]));
		FileInputStream fis = new FileInputStream(new File(args[0]));
		System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex(fis));
		System.out.println(org.apache.commons.codec.digest.DigestUtils.sha256Hex(fis));
		fis.close();
	}

}

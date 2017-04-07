/**
 * 
 */
package hh.learnj.testj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.MessageFormat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

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

		String fileName = args[0];
		System.out.println(MessageFormat.format("检查文件[{0}]的md5值", fileName));
		System.out.println("apache :");
		apacheMd5(fileName);
		System.out.println("java :");
		javaMd5(fileName);

	}

	public static void apacheMd5(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			System.out.println(DigestUtils.md5Hex(fis));
			System.out.println(DigestUtils.sha256Hex(fis));
			IOUtils.closeQuietly(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String javaMd5(String fileName) throws FileNotFoundException {
		String value = null;
		File file = new File(fileName); 
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(value);
		return value;
	}

}

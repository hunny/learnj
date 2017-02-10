/**
 * 
 */
package hh.learnj.test.license.test.alps;

import java.security.NoSuchAlgorithmException;

/**
 * @author huzexiong
 *
 */
public interface Key {

	static final String CODE = "UTF-8";
	
	/**
	 * 生成加密钥匙，产生public、private和sing
	 * @throws NoSuchAlgorithmException 
	 */
	void make() throws NoSuchAlgorithmException;
	
	/**
	 * 获取公钥
	 * @return 公钥
	 */
	String getPublic();
	
	/**
	 * 获取密钥
	 * @return 密钥
	 */
	String getPrivate();
	
}

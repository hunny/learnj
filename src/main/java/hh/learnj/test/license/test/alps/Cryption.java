/**
 * 
 */
package hh.learnj.test.license.test.alps;

import java.security.NoSuchAlgorithmException;

/**
 * @author huzexiong
 *
 */
public interface Cryption {

	/**
	 * 公钥加密
	 * 
	 * @param key
	 * @param target
	 * @return 加密密文
	 */
	String encryptByPublic(String key, String target);
	
	/**
	 * 公钥解密
	 * 
	 * @param key
	 * @param target
	 * @return 解密密文
	 */
	String decryptByPublic(String key, String target);
	
	/**
	 * 私钥加密
	 * 
	 * @param key
	 * @param target
	 * @return 加密密文
	 */
	String encryptByPrivate(String key, String target);
	
	/**
	 * 私钥解密
	 * 
	 * @param key
	 * @param target
	 * @return 解密密文
	 */
	String decryptByPrivate(String key, String target);
	
	/**
	 * 获取私钥签名
	 * 
	 * @param key
	 * @param target
	 * @return 签名
	 * @throws NoSuchAlgorithmException 
	 */
	String sign(String key, String target);
	
	/**
	 * 验证签名
	 * 
	 * @param key
	 * @param target
	 * @return
	 */
	boolean verify(String key, String target, String sign);
}

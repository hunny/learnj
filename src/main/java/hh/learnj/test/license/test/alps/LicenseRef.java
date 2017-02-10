/**
 * 
 */
package hh.learnj.test.license.test.alps;

/**
 * @author huzexiong
 *
 */
public interface LicenseRef {

	/**
	 * 生成license
	 * 
	 * <p>
	 * 使用私钥，生成license，生成的license分为两行，第一行为签名，第二行为license加密值
	 * </p>
	 * 
	 * @param key
	 * 		私钥
	 * @param info
	 * 		收集的硬件信息，包括签名和加密信息
	 * @param license
	 * 		license信息
	 * @return
	 * 		license
	 */
	String make(String key, String info, License license);
	
	/**
	 * 收集生成license所需的参数值并签名加密
	 * <p>
	 * 使用公钥收集信息，收集的信息中，第一行为签名值，第二行为参数的加密值
	 * </p>
	 * 
	 * @param key
	 * 		公钥
	 * @return
	 */
	String collect(String key);
	
	/**
	 * 验证license
	 * 
	 * @return
	 * 		返回null意味着验证失败
	 */
	License verify(String key, String fileName);
	
}

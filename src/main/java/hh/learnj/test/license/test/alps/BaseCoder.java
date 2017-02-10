/**
 * 
 */
package hh.learnj.test.license.test.alps;

/**
 * @author huzexiong
 */
public interface BaseCoder {

	String encode(byte[] src);
	
	byte[] decode(String src);
}

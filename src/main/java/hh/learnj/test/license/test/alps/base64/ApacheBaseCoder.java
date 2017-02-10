/**
 * 
 */
package hh.learnj.test.license.test.alps.base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import hh.learnj.test.license.test.alps.BaseCoder;
import hh.learnj.test.license.test.alps.rsa.RSAKey;

/**
 * 使用org.apache.commons.codec.binary.Base64来实现编码与解码
 * 
 * @author huzexiong
 */
public class ApacheBaseCoder implements BaseCoder {
	
	private static final String CODE = RSAKey.CODE;

	@Override
	public String encode(byte[] src) {
		try {
			return new String(Base64.encodeBase64(src), CODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

	@Override
	public byte[] decode(String src) {
		try {
			return Base64.decodeBase64(src.getBytes(CODE));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

}

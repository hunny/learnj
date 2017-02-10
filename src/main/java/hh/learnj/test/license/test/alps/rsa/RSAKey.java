/**
 * 
 */
package hh.learnj.test.license.test.alps.rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import hh.learnj.test.license.test.alps.BaseCoder;
import hh.learnj.test.license.test.alps.Key;

/**
 * RSA Key
 * 
 * @author huzexiong
 */
public class RSAKey implements Key {

	public static final String ALGORITHM_RSA = "RSA";
	
	private String publicKey = null;
	private String privateKey = null;
	private BaseCoder base64 = null;

	@Override
	public void make() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		byte[] keyBs = rsaPublicKey.getEncoded();
		publicKey = getBase64().encode(keyBs);
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		keyBs = rsaPrivateKey.getEncoded();
		privateKey = getBase64().encode(keyBs);
	}

	@Override
	public String getPublic() {
		if (null == publicKey) {
			throw new UnsupportedOperationException("Please invoke method of make first.");
		}
		return publicKey;
	}

	@Override
	public String getPrivate() {
		if (null == privateKey) {
			throw new UnsupportedOperationException("Please invoke method of make first.");
		}
		return privateKey;
	}

	public BaseCoder getBase64() {
		if (null == base64) {
			throw new UnsupportedOperationException("Base64 is null");
		}
		return base64;
	}

	public void setBase64(BaseCoder base64) {
		this.base64 = base64;
	}

}

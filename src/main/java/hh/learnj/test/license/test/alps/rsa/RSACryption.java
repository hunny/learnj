/**
 * 
 */
package hh.learnj.test.license.test.alps.rsa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import hh.learnj.test.license.test.alps.BaseCoder;
import hh.learnj.test.license.test.alps.Cryption;
import hh.learnj.test.license.test.alps.Key;

/**
 * 加密、解密与签名
 * 
 * @author huzexiong
 */
public class RSACryption implements Cryption {

	private static final String ALGORITHM_SIGN = "MD5withRSA";
	private BaseCoder baseCoder = null;
	
	private PublicKey getPublicKey(String key) {
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(getBaseCoder().decode(key));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance(RSAKey.ALGORITHM_RSA);
			return keyFactory.generatePublic(publicKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}
	
	private PrivateKey getPrivateKey(String key) {
		try {
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(getBaseCoder().decode(key));
			KeyFactory keyFactory = KeyFactory.getInstance(RSAKey.ALGORITHM_RSA);
			return keyFactory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}
	
	@Override
	public String sign(String key, String target) {
		try {
			PrivateKey rsaPrivateKey = getPrivateKey(key);
			Signature signature = Signature.getInstance(ALGORITHM_SIGN);
			signature.initSign(rsaPrivateKey);
			signature.update(target.getBytes(Key.CODE));
			return getBaseCoder().encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean verify(String key, String target, String sign) {
		PublicKey publicKey = null;
		try {
			publicKey = getPublicKey(key);
			Signature signature = Signature.getInstance(ALGORITHM_SIGN);
			signature.initVerify(publicKey);
			signature.update(target.getBytes(Key.CODE));
			if (signature.verify(getBaseCoder().decode(sign))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
		return false;
	}

	public BaseCoder getBaseCoder() {
		if (null == baseCoder) {
			throw new UnsupportedOperationException("Base64 is null");
		}
		return baseCoder;
	}

	public void setBaseCoder(BaseCoder baseCoder) {
		this.baseCoder = baseCoder;
	}

	@Override
	public String encryptByPublic(String key, String target) {
		PublicKey publicKey = getPublicKey(key);
		try {
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipher.update(target.getBytes(Key.CODE));
			return getBaseCoder().encode(cipher.doFinal());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public String decryptByPublic(String key, String target) {
		PublicKey publicKey = getPublicKey(key);
		try {
			Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			cipher.update(getBaseCoder().decode(target));
			return new String(cipher.doFinal(), Key.CODE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public String encryptByPrivate(String key, String target) {
		PrivateKey privateKey = getPrivateKey(key);
		try {
			Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			cipher.update(target.getBytes(Key.CODE));
			return getBaseCoder().encode(cipher.doFinal());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public String decryptByPrivate(String key, String target) {
		PrivateKey privateKey = getPrivateKey(key);
		try {
			Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			cipher.update(getBaseCoder().decode(target));
			return new String(cipher.doFinal(), Key.CODE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e);
		}
	}

}

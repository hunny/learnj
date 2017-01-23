/**
 * 
 */
package hh.learnj.test.license.test.lincense3j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import com.verhas.licensor.License;

/**
 * @author huzexiong
 *
 */
public class LicenseDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL baseDir = LicenseDemo.class.getResource(".");
		String basePath = baseDir.getPath();
		try {
			File licenseFile = new File(basePath + "demo.license");

			if (!licenseFile.exists()) {
				// license 文件生成
				OutputStream os = new FileOutputStream(basePath + "demo.license");
				License lincense = new License();
				// license 的原文
				lincense.setLicense(new File(basePath + "license-plain.txt"));
				// 私钥与之前生成密钥时产生的USER-ID
				lincense.loadKey(basePath + "privateKeys.pgp", "hunny");
				os.write(lincense.encodeLicense("111111").getBytes("utf-8"));
				os.close();
			} else {
				// licence 文件验证
				License license = new License();
				if (license.loadKeyRing(basePath + "hunny-pub.pgp", null) //
						.setLicenseEncodedFromFile(basePath + "hunny-secu.txt")
						.isVerified()) {
					System.out.println(license.getFeature("edition"));
					System.out.println(license.getFeature("valid-until"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

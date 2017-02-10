/**
 * 
 */
package hh.learnj.test.license.test.alps.hd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import hh.learnj.test.license.test.alps.BaseCoder;
import hh.learnj.test.license.test.alps.Cryption;
import hh.learnj.test.license.test.alps.Hardware;
import hh.learnj.test.license.test.alps.License;
import hh.learnj.test.license.test.alps.LicenseRef;
import hh.learnj.test.license.test.alps.base64.ApacheBaseCoder;
import hh.learnj.test.license.test.alps.oshi.OSHIHardware;
import hh.learnj.test.license.test.alps.rsa.RSACryption;
import hh.learnj.test.license.test.alps.rsa.RSAKey;

/**
 * @author huzexiong
 *
 */
public class LicenseAware implements LicenseRef {
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String SPLITOR = "\n";
	
	private Hardware hardware = null;
	private Cryption cryption = null;

	@Override
	public String make(String key, String target, License license) {
		if (null == key || null == target || null == license 
				|| null == license.getBegin()
				|| null == license.getEnd()
				|| null == license.getNumber()) {
			throw new IllegalArgumentException();
		}
		String hardware = getCryption().decryptByPrivate(key, target);
		String [] hardwares = hardware.split(SPLITOR);
		if (4 != hardwares.length) {
			throw new UnsupportedOperationException("无法识别的信息文件。");
		}
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		StringBuilder builder = new StringBuilder();
		builder.append(hardware);
		builder.append(SPLITOR);
		builder.append(dateFormat.format(license.getBegin()));
		builder.append(SPLITOR);
		builder.append(dateFormat.format(license.getEnd()));
		builder.append(SPLITOR);
		builder.append(license.getNumber());
		target = getCryption().encryptByPrivate(key, builder.toString());
		String sign = getCryption().sign(key, target);
		System.out.println("sign:" + sign);
		System.out.println("target:" + target);
		return sign + SPLITOR + target;
	}

	@Override
	public String collect(String key) {
		//1. 收集硬件信息
		String hardwardInfo = hardwareInfo();
		//2. 加密收集的信息
		return getCryption().encryptByPublic(key, hardwardInfo);
	}
	
	@Override
	public License verify(String key, String fileName) {
		List<String> list = read(fileName);
		if (null == list || list.isEmpty() || list.size() < 2) {
			throw new UnsupportedOperationException("数据信息不正确。");
		}
		String sign = list.get(0);
		String target = list.get(1);
		if (!getCryption().verify(key, target, sign)) {
			throw new IllegalArgumentException("数据信息不正确。");
		}
		String info = getCryption().decryptByPublic(key, target);
		if (StringUtils.isBlank(info)) {
			throw new IllegalArgumentException("数据信息不正确。");
		}
		String [] infos = info.split(SPLITOR);
		if (null == infos || infos.length < 7) {
			throw new IllegalArgumentException("数据信息不正确。");
		}
		String serialNumber = infos[0];
		String systemSerialNumber = infos[1];
		String identifier = infos[2];
		String baseboardSerialNumber = infos[3];
		if (getHardware().getSerialNumber().equals(serialNumber)
				&& getHardware().getSystemSerialNumber().equals(systemSerialNumber)
				&& getHardware().getIdentifier().equals(identifier)
				&& getHardware().getBaseboardSerialNumber().equals(baseboardSerialNumber)) {
			String begin = infos[4];
			String end = infos[5];
			String number = infos[6];
			DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
			try {
				License license = new License();
				license.setBegin(dateFormat.parse(begin));
				license.setEnd(dateFormat.parse(end));
				license.setNumber(Integer.parseInt(number));
				return license;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @return the hardware
	 */
	public Hardware getHardware() {
		return hardware;
	}

	/**
	 * @param hardware the hardware to set
	 */
	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	/**
	 * @return the cryption
	 */
	public Cryption getCryption() {
		return cryption;
	}

	/**
	 * @param cryption the cryption to set
	 */
	public void setCryption(Cryption cryption) {
		this.cryption = cryption;
	}
	
	public List<String> read(String fileName) {
		List<String> result = new ArrayList<String>();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (null != line) {
					result.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 硬件信息
	 * 
	 * @return
	 */
	private String hardwareInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append(getHardware().getSerialNumber());
		builder.append(SPLITOR);
		builder.append(getHardware().getSystemSerialNumber());
		builder.append(SPLITOR);
		builder.append(getHardware().getIdentifier());
		builder.append(SPLITOR);
		builder.append(getHardware().getBaseboardSerialNumber());
		return builder.toString();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		generate();
		verify();
	}
	
	public static void verify() {
		RSACryption cryption = new RSACryption();
		Hardware hardware = new OSHIHardware();
		BaseCoder base64 = new ApacheBaseCoder();
		
		cryption.setBaseCoder(base64);
		LicenseAware licenseAware = new LicenseAware();
		licenseAware.setCryption(cryption);
		licenseAware.setHardware(hardware);
		String path = LicenseAware.class.getResource("/").getPath();
		
		List<String> publicKeys = licenseAware.read(path + "/public.license");
		
		License tmp = licenseAware.verify(publicKeys.get(0), path + "/hd.license");
		System.out.println("license:");
		System.out.println(tmp.getBegin());
		System.out.println(tmp.getEnd());
		System.out.println(tmp.getNumber());
	}
	
	public static void generate() throws NoSuchAlgorithmException {
		RSACryption cryption = new RSACryption();
		Hardware hardware = new OSHIHardware();
		BaseCoder base64 = new ApacheBaseCoder();
		
		cryption.setBaseCoder(base64);
		
		RSAKey key = new RSAKey();
		key.setBase64(base64);
		key.make();
		System.out.println("公钥：");
		System.out.println(key.getPublic());
		System.out.println("私钥：");
		System.out.println(key.getPrivate());
		
		LicenseAware licenseAware = new LicenseAware();
		licenseAware.setCryption(cryption);
		licenseAware.setHardware(hardware);
		
		//1. 设置license
		License license = new License();
		license.setBegin(DateUtils.addDays(new Date(), -9));
		license.setEnd(DateUtils.addDays(new Date(), 13));
		license.setNumber(50);
		
		//2. 收集服务器硬件信息
		String target = licenseAware.collect(key.getPublic());
		System.out.println(target);
		
		//3. 根据私钥产生license
		String make = licenseAware.make(key.getPrivate(), target, license);
		System.out.println(make);
	}

}

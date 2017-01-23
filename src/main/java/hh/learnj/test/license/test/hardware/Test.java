/**
 * 
 */
package hh.learnj.test.license.test.hardware;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author huzexiong
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
		while (networks.hasMoreElements()) {
			NetworkInterface network = networks.nextElement();
			if (!network.isVirtual()) {
				byte[] mac = network.getHardwareAddress();
				if (null == mac) {
					continue;
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				System.out.println(sb.toString());
			}
		}
	}

}

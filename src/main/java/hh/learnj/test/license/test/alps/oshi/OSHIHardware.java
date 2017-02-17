/**
 * 
 */
package hh.learnj.test.license.test.alps.oshi;

import hh.learnj.test.license.test.alps.Hardware;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * 获取硬件部分信息
 * 
 * @author huzexiong
 */
public class OSHIHardware implements Hardware {
	
	private HardwareAbstractionLayer hareware = null;
	
	public OSHIHardware() {
		SystemInfo systemInfo = new SystemInfo();
		hareware = systemInfo.getHardware();
	}
	
	@Override
	public String getSerialNumber() {
		return null;
//		return hareware.getComputerSystem().getSerialNumber();
	}

	@Override
	public String getBaseboardSerialNumber() {
		return null;
//		return hareware.getComputerSystem().getBaseboard().getSerialNumber();
	}

	@Override
	public String getIdentifier() {
		return hareware.getProcessor().getIdentifier();
	}

	@Override
	public String getSystemSerialNumber() {
		return hareware.getProcessor().getSystemSerialNumber();
	}

}

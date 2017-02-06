/**
 * 
 */
package hh.learnj.test.license.test;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class ComputerIdentifier {
	public static String generateLicenseKey() throws Exception {
		SystemInfo systemInfo = new SystemInfo();
		OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
		HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
		CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();

		String vendor = operatingSystem.getManufacturer();
		String processorSerialNumber = centralProcessor.getSystemSerialNumber();
		String processorIdentifier = centralProcessor.getIdentifier();
		int processors = centralProcessor.getLogicalProcessorCount();

		String delimiter = "#";

		return vendor + delimiter + processorSerialNumber + delimiter + processorIdentifier + delimiter + processors;
	}

	public static void main(String[] arguments) throws Exception {
		String identifier = generateLicenseKey();
		System.out.println(identifier);
	}
}

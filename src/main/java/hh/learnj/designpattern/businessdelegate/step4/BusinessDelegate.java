package hh.learnj.designpattern.businessdelegate.step4;

import hh.learnj.designpattern.businessdelegate.step1.BusinessService;
import hh.learnj.designpattern.businessdelegate.step3.BusinessLookUp;

public class BusinessDelegate {

	private BusinessLookUp lookupService = new BusinessLookUp();
	private BusinessService businessService;
	private String serviceType;

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void doTask() {
		businessService = lookupService.getBusinessService(serviceType);
		businessService.doProcessing();
	}

}

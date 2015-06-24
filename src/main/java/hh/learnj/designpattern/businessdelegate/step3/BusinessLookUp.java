package hh.learnj.designpattern.businessdelegate.step3;

import hh.learnj.designpattern.businessdelegate.step1.BusinessService;
import hh.learnj.designpattern.businessdelegate.step2.EJBService;
import hh.learnj.designpattern.businessdelegate.step2.JMSService;

public class BusinessLookUp {

	public BusinessService getBusinessService(String serviceType) {
		
		if (serviceType.equalsIgnoreCase("EJB")) {
			return new EJBService();
		} else {
			return new JMSService();
		}
	}

}

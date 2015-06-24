package hh.learnj.designpattern.businessdelegate.step2;

import hh.learnj.designpattern.businessdelegate.step1.BusinessService;

public class JMSService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("Processing task by invoking JMS Service");
	}

}

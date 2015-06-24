package hh.learnj.designpattern.businessdelegate.step5;

import hh.learnj.designpattern.businessdelegate.step4.BusinessDelegate;

public class Client {
	
	private BusinessDelegate businessService;

	public Client(BusinessDelegate businessService) {
		this.businessService = businessService;
	}

	public void doTask() {
		businessService.doTask();
	}
}

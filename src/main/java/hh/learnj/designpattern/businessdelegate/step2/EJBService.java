package hh.learnj.designpattern.businessdelegate.step2;

import hh.learnj.designpattern.businessdelegate.step1.BusinessService;

public class EJBService implements BusinessService {

	@Override
	public void doProcessing() {
		System.out.println("Processing task by invoking EJB Service");
	}

}

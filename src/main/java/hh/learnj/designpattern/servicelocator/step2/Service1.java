package hh.learnj.designpattern.servicelocator.step2;

import hh.learnj.designpattern.servicelocator.step1.Service;

public class Service1 implements Service {
	
	public void execute() {
		System.out.println("Executing Service1");
	}

	@Override
	public String getName() {
		return "Service1";
	}

}

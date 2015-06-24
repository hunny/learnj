package hh.learnj.designpattern.servicelocator.step2;

import hh.learnj.designpattern.servicelocator.step1.Service;

public class Service2 implements Service {
	
	public void execute() {
		System.out.println("Executing Service2");
	}

	@Override
	public String getName() {
		return "Service2";
	}

}

package hh.learnj.designpattern.nullobject.step2;

import hh.learnj.designpattern.nullobject.step1.AbstractCustomer;

public class NullCustomer extends AbstractCustomer {

	@Override
	public String getName() {
		return "Not Available in Customer Database";
	}

	@Override
	public boolean isNil() {
		return true;
	}
}

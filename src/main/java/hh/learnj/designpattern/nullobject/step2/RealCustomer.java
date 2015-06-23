package hh.learnj.designpattern.nullobject.step2;

import hh.learnj.designpattern.nullobject.step1.AbstractCustomer;

/**
 * Create concrete classes extending the above class.
 * 
 * @author hunnyhu
 *
 */
public class RealCustomer extends AbstractCustomer {

	public RealCustomer(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isNil() {
		return false;
	}
}

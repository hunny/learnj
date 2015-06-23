package hh.learnj.designpattern.nullobject.step3;

import hh.learnj.designpattern.nullobject.step1.AbstractCustomer;
import hh.learnj.designpattern.nullobject.step2.NullCustomer;
import hh.learnj.designpattern.nullobject.step2.RealCustomer;

public class CustomerFactory {

	public static final String[] names = { "Rob", "Joe", "Julie" };

	public static AbstractCustomer getCustomer(String name) {

		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(name)) {
				return new RealCustomer(name);
			}
		}
		return new NullCustomer();
	}
}

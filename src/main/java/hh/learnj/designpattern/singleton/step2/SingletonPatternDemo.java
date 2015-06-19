package hh.learnj.designpattern.singleton.step2;

import hh.learnj.designpattern.singleton.step1.SingleObject;

/**
 * Get the only object from the singleton class.
 * Details:http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * @author hunnyhu
 */
public class SingletonPatternDemo {
	
	public static void main(String[] args) {

		// illegal construct
		// Compile Time Error: The constructor SingleObject() is not visible
		// SingleObject object = new SingleObject();

		// Get the only object available
		SingleObject object = SingleObject.getInstance();

		// show the message
		object.showMessage();
	}
}

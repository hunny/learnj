package hh.learnj.designpattern.singleton.step1;

/**
 * Create a Singleton Class.
 * 
 * @author hunnyhu
 */
public class SingleObject {

	// 1. create an object of SingleObject
	private static SingleObject instance = new SingleObject();

	// 2. make the constructor private so that this class cannot be
	// instantiated
	private SingleObject() {
	}

	// 3. Get the only object available
	public static SingleObject getInstance() {
		return instance;
	}

	public void showMessage() {
		System.out.println("Hello World!");
	}

}

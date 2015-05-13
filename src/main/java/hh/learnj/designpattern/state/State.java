package hh.learnj.designpattern.state;

/**
 * 状态类的核心类
 * @author hunnyhu
 */
public class State {
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void method1() {
		System.out.println("Execute the first opt.");
	}
	
	public void method2() {
		System.out.println("Execute the second opt.");
	}

}

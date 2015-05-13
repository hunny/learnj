package hh.learnj.designpattern.chainofresponsibility;

public class MyHandler extends AbstractHandler implements Handler {
	
	private String name;
	
	public MyHandler(String name) {
		this.name = name;
	}

	@Override
	public void operator() {
		System.out.println(this.name + " ready to operate.");
		if (null != this.getHandler()) {
			System.out.println("Handler " + this.name + " to execute.");
			this.getHandler().operator();
		}
		
	}

}

package hh.learnj.designpattern.observer.step3;

import hh.learnj.designpattern.observer.step1.Subject;
import hh.learnj.designpattern.observer.step2.Observer;

/**
 * Create concrete observer classes
 * 
 * @author hunnyhu
 *
 */
public class BinaryObserver extends Observer {

	public BinaryObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Binary String: "
				+ Integer.toBinaryString(subject.getState()));
	}
}

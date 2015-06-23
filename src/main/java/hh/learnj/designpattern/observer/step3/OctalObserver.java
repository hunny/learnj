package hh.learnj.designpattern.observer.step3;

import hh.learnj.designpattern.observer.step1.Subject;
import hh.learnj.designpattern.observer.step2.Observer;

public class OctalObserver extends Observer {

	public OctalObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Octal String: "
				+ Integer.toOctalString(subject.getState()));
	}
}

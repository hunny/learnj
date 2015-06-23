package hh.learnj.designpattern.observer.step3;

import hh.learnj.designpattern.observer.step1.Subject;
import hh.learnj.designpattern.observer.step2.Observer;

public class HexaObserver extends Observer {

	public HexaObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Hex String: "
				+ Integer.toHexString(subject.getState()).toUpperCase());
	}
}

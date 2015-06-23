package hh.learnj.designpattern.observer.step2;

import hh.learnj.designpattern.observer.step1.Subject;

public abstract class Observer {
	
	protected Subject subject;

	public abstract void update();
}

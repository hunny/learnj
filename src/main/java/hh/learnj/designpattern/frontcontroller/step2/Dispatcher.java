package hh.learnj.designpattern.frontcontroller.step2;

import hh.learnj.designpattern.frontcontroller.step1.HomeView;
import hh.learnj.designpattern.frontcontroller.step1.StudentView;

public class Dispatcher {

	private StudentView studentView;
	private HomeView homeView;

	public Dispatcher() {
		studentView = new StudentView();
		homeView = new HomeView();
	}

	public void dispatch(String request) {
		if ("STUDENT".equalsIgnoreCase(request)) {
			studentView.show();
		} else {
			homeView.show();
		}
	}
}

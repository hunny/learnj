package hh.learnj.designpattern.interceptingfilter.step2;

import hh.learnj.designpattern.interceptingfilter.step1.Filter;

public class AuthenticationFilter implements Filter {
	public void execute(String request) {
		System.out.println("Authenticating request: " + request);
	}
}

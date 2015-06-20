package hh.learnj.designpattern.responsibility.step2;

import hh.learnj.designpattern.responsibility.step1.AbstractLogger;

public class ErrorLogger extends AbstractLogger {

	public ErrorLogger(int level) {
		this.level = level;
	}

	@Override
	protected void write(String message) {
		System.out.println("Error Console::Logger: " + message);
	}
}

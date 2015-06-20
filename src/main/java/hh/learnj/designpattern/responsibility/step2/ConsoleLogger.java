package hh.learnj.designpattern.responsibility.step2;

import hh.learnj.designpattern.responsibility.step1.AbstractLogger;

public class ConsoleLogger extends AbstractLogger {

	public ConsoleLogger(int level) {
		this.level = level;
	}

	@Override
	protected void write(String message) {
		System.out.println("Standard Console::Logger: " + message);
	}
}

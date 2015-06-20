package hh.learnj.designpattern.responsibility.step2;

import hh.learnj.designpattern.responsibility.step1.AbstractLogger;

public class FileLogger extends AbstractLogger {

	public FileLogger(int level) {
		this.level = level;
	}

	@Override
	protected void write(String message) {
		System.out.println("File::Logger: " + message);
	}
}

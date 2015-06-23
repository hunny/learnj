package hh.learnj.designpattern.strategy.step3;

import hh.learnj.designpattern.strategy.step1.Strategy;

public class Context {

	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public int executeStrategy(int num1, int num2) {
		return strategy.doOperation(num1, num2);
	}
	
}

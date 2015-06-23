package hh.learnj.designpattern.strategy.step2;

import hh.learnj.designpattern.strategy.step1.Strategy;

public class OperationSubstract implements Strategy {
	
	@Override
	public int doOperation(int num1, int num2) {
		return num1 - num2;
	}
	
}

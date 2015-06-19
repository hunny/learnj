package hh.learnj.designpattern.builder.step4;

import hh.learnj.designpattern.builder.step3.ColdDrink;

public class Coke extends ColdDrink {

	@Override
	public float price() {
		return 30.0f;
	}

	@Override
	public String name() {
		return "Coke";
	}
}

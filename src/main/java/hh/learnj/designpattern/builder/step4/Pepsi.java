package hh.learnj.designpattern.builder.step4;

import hh.learnj.designpattern.builder.step3.ColdDrink;

public class Pepsi extends ColdDrink {

	@Override
	public float price() {
		return 35.0f;
	}

	@Override
	public String name() {
		return "Pepsi";
	}
}

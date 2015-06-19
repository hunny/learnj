package hh.learnj.designpattern.builder.step2;

import hh.learnj.designpattern.builder.step1.Packing;

public class Bottle implements Packing {

	@Override
	public String pack() {
		return "Bottle";
	}

}

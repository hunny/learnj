package hh.learnj.designpattern.builder.step3;

import hh.learnj.designpattern.builder.step1.Item;
import hh.learnj.designpattern.builder.step1.Packing;
import hh.learnj.designpattern.builder.step2.Bottle;

public abstract class ColdDrink implements Item {

	@Override
	public Packing packing() {
		return new Bottle();
	}

	@Override
	public abstract float price();
}

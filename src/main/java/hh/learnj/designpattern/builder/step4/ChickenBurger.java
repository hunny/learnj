package hh.learnj.designpattern.builder.step4;

import hh.learnj.designpattern.builder.step3.Burger;

public class ChickenBurger extends Burger {

	@Override
	public float price() {
		return 50.5f;
	}

	@Override
	public String name() {
		return "Chicken Burger";
	}
}

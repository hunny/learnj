package hh.learnj.designpattern.builder.step4;

import hh.learnj.designpattern.builder.step3.Burger;

/**
 * Create concrete classes extending Burger classes
 * 
 * @author hunnyhu
 *
 */
public class VegBurger extends Burger {

	@Override
	public String name() {
		return "Veg Burger";
	}

	@Override
	public float price() {
		return 25.0f;
	}

}

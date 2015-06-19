package hh.learnj.designpattern.builder.step3;

import hh.learnj.designpattern.builder.step1.Item;
import hh.learnj.designpattern.builder.step1.Packing;
import hh.learnj.designpattern.builder.step2.Wrapper;

/**
 * Create abstract classes implementing the item interface providing default
 * functionalities.
 * 
 * @author hunnyhu
 *
 */
public abstract class Burger implements Item {

	@Override
	public Packing packing() {
		return new Wrapper();
	}

	@Override
	public abstract float price();

}

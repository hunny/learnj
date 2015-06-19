package hh.learnj.designpattern.builder.step2;

import hh.learnj.designpattern.builder.step1.Packing;

/**
 * Create concrete classes implementing the Packing interface.
 * 
 * @author hunnyhu
 *
 */
public class Wrapper implements Packing {

	@Override
	public String pack() {
		return "Wrapper";
	}

}

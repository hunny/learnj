package hh.learnj.designpattern.builder.step1;

/**
 * Create an interface Item representing food item and packing.
 * 
 * @author hunnyhu
 *
 */
public interface Item {

	public String name();

	public Packing packing();

	public float price();
}

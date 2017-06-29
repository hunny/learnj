/**
 * 
 */
package hh.learnj.testj.event;

import java.util.EventObject;

/**
 * @author huzexiong
 *
 */
public class ValueChangeEvent extends EventObject {

	private static final long serialVersionUID = -338682598629731319L;

	/**
	 * @param source
	 */
	public ValueChangeEvent(Object source) {
		super(source);
	}

	private int value;

	public ValueChangeEvent(Object source, int newValue) {
		super(source);
		value = newValue;
	}

	public int getValue() {
		return value;
	}

}

/**
 * 
 */
package hh.learnj.testj.event;

/**
 * @author huzexiong
 *
 */
public class EventConsumer implements ValueChangeListener {

	@Override
	public void performed(ValueChangeEvent e) {
		System.out.println("Thread " + Thread.currentThread().getName());
		System.out.println("value changed, new value = " + e.getValue());
	}

}

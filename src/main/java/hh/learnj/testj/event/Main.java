/**
 * 
 */
package hh.learnj.testj.event;

/**
 * @author huzexiong
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventProducer producer = new EventProducer();
		producer.addListener(new EventConsumer());
		producer.setValue(2);
	}

}

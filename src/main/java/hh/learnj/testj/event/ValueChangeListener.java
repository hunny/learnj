/**
 * 
 */
package hh.learnj.testj.event;

import java.util.EventListener;

/**
 * @author huzexiong
 *
 */
public interface ValueChangeListener extends EventListener {
	
	void performed(ValueChangeEvent e);
	
}

/**
 * 
 */
package hh.learnj.testj.event;

import java.util.Vector;

/**
 * @author huzexiong
 *
 */
public class ListenerRegister {
	
	private Vector<ValueChangeListener> listeners = new Vector<ValueChangeListener>();

	public synchronized void addListener(ValueChangeListener a) {
		listeners.addElement(a);
	}

	public synchronized void removeListener(ValueChangeListener a) {
		listeners.removeElement(a);
	}

	@SuppressWarnings("unchecked")
	public void fireAEvent(ValueChangeEvent evt) {
		Vector<ValueChangeListener> currentListeners = null;
		synchronized (this) {
			currentListeners = (Vector<ValueChangeListener>) listeners.clone();
		}
		for (int i = 0; i < currentListeners.size(); i++) {
			ValueChangeListener listener = (ValueChangeListener) currentListeners.elementAt(i);
			listener.performed(evt);
		}
	}
}

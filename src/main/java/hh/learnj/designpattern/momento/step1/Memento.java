package hh.learnj.designpattern.momento.step1;

/**
 * Create Memento class.
 * 
 * @author hunnyhu
 *
 */
public class Memento {

	private String state;

	public Memento(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}

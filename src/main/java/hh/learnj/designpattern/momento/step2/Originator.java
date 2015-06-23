package hh.learnj.designpattern.momento.step2;

import hh.learnj.designpattern.momento.step1.Memento;

public class Originator {
	
	private String state;

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public Memento saveStateToMemento() {
		return new Memento(state);
	}

	public void getStateFromMemento(Memento memento) {
		state = memento.getState();
	}
}

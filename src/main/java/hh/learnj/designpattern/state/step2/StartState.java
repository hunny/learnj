package hh.learnj.designpattern.state.step2;

import hh.learnj.designpattern.state.step1.State;
import hh.learnj.designpattern.state.step3.Context;

public class StartState implements State {

	public void doAction(Context context) {
		System.out.println("Player is in start state");
		context.setState(this);
	}

	public String toString() {
		return "Start State";
	}
}

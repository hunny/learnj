package hh.learnj.designpattern.state.step2;

import hh.learnj.designpattern.state.step1.State;
import hh.learnj.designpattern.state.step3.Context;

public class StopState implements State {

	public void doAction(Context context) {
		System.out.println("Player is in stop state");
		context.setState(this);
	}

	public String toString() {
		return "Stop State";
	}
}

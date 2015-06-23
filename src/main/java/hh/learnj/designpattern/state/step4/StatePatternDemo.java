package hh.learnj.designpattern.state.step4;

import hh.learnj.designpattern.state.step2.StartState;
import hh.learnj.designpattern.state.step2.StopState;
import hh.learnj.designpattern.state.step3.Context;

/**
 * In State pattern a class behavior changes based on its state. This type of
 * design pattern comes under behavior pattern.
 * 
 * In State pattern, we create objects which represent various states and a
 * context object whose behavior varies as its state object changes.
 * 
 * Implementation We are going to create a State interface defining an action
 * and concrete state classes implementing the State interface. Context is a
 * class which carries a State.
 * 
 * StatePatternDemo, our demo class, will use Context and state objects to
 * demonstrate change in Context behavior based on type of state it is in.
 * 
 * http://www.tutorialspoint.com/design_pattern/state_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class StatePatternDemo {

	public static void main(String[] args) {

		Context context = new Context();

		StartState startState = new StartState();
		startState.doAction(context);

		System.out.println(context.getState().toString());

		StopState stopState = new StopState();
		stopState.doAction(context);

		System.out.println(context.getState().toString());
	}

}

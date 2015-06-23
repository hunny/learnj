package hh.learnj.designpattern.template.step2;

import hh.learnj.designpattern.template.step1.Game;

/**
 * Create concrete classes extending the above class.
 * 
 * @author hunnyhu
 *
 */
public class Cricket extends Game {

	@Override
	public void endPlay() {
		System.out.println("Cricket Game Finished!");
	}

	@Override
	public void initialize() {
		System.out.println("Cricket Game Initialized! Start playing.");
	}

	@Override
	public void startPlay() {
		System.out.println("Cricket Game Started. Enjoy the game!");
	}
}

package hh.learnj.designpattern.adapter.step2;

import hh.learnj.designpattern.adapter.step1.AdvancedMediaPlayer;

/**
 * Create concrete classes implementing the AdvancedMediaPlayer interface.
 * 
 * @author hunnyhu
 *
 */
public class VlcPlayer implements AdvancedMediaPlayer {

	@Override
	public void playVlc(String fileName) {
		System.out.println("Playing vlc file. Name: "+ fileName);
	}

	@Override
	public void playMp4(String fileName) {
		//do nothing
	}

}

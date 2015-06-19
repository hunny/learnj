package hh.learnj.designpattern.adapter.step3;

import hh.learnj.designpattern.adapter.step1.AdvancedMediaPlayer;
import hh.learnj.designpattern.adapter.step1.MediaPlayer;
import hh.learnj.designpattern.adapter.step2.Mp4Player;
import hh.learnj.designpattern.adapter.step2.VlcPlayer;

/**
 * Create adapter class implementing the MediaPlayer interface.
 * 
 * @author hunnyhu
 *
 */

public class MediaAdapter implements MediaPlayer {

	AdvancedMediaPlayer advancedMusicPlayer;

	public MediaAdapter(String audioType) {

		if ("vlc".equalsIgnoreCase(audioType)) {
			advancedMusicPlayer = new VlcPlayer();
		} else if ("mp4".equalsIgnoreCase(audioType)) {
			advancedMusicPlayer = new Mp4Player();
		}
	}

	@Override
	public void play(String audioType, String fileName) {

		if ("vlc".equalsIgnoreCase(audioType)) {
			advancedMusicPlayer.playVlc(fileName);
		} else if ("mp4".equalsIgnoreCase(audioType)) {
			advancedMusicPlayer.playMp4(fileName);
		}
	}
}

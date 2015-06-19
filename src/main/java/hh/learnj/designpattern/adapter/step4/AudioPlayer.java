package hh.learnj.designpattern.adapter.step4;

import hh.learnj.designpattern.adapter.step1.MediaPlayer;
import hh.learnj.designpattern.adapter.step3.MediaAdapter;

/**
 * Create concrete class implementing the MediaPlayer interface.
 * 
 * @author hunnyhu
 *
 */

public class AudioPlayer implements MediaPlayer {
	MediaAdapter mediaAdapter;

	@Override
	public void play(String audioType, String fileName) {

		// inbuilt support to play mp3 music files
		if ("mp3".equalsIgnoreCase(audioType)) {
			System.out.println("Playing mp3 file. Name: " + fileName);
		}

		// mediaAdapter is providing support to play other file formats
		else if ("vlc".equalsIgnoreCase(audioType)
				|| "mp4".equalsIgnoreCase(audioType)) {
			mediaAdapter = new MediaAdapter(audioType);
			mediaAdapter.play(audioType, fileName);
		}

		else {
			System.out.println("Invalid media. " + audioType
					+ " format not supported");
		}
	}
}

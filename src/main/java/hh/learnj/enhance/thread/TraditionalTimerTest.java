package hh.learnj.enhance.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TraditionalTimerTest {

	public static void main(String[] args) {
		
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("Bomb! At:" + new Date());
			}
			
		}, 1000);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Period Bomb! At:" + new Date());
			}
			
		}, 1000, 3000);

	}

}

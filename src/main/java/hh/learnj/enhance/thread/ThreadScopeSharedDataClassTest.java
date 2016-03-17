package hh.learnj.enhance.thread;

import java.util.Random;

import com.itextpdf.text.log.SysoCounter;

public class ThreadScopeSharedDataClassTest {

	public static void main(String[] args) {
		final SharedData sharedData = new SharedData();
		int increment = new Random().nextInt(20);
		int decrement = new Random().nextInt(30);
		for (int i = 0; i < increment; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					sharedData.increment();
				}
			}).start();
		}
		for (int i = 0; i< decrement; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					sharedData.decrement();
				}
			}).start();
		}
		System.out.println("increment=" + increment + ",decrement=" + decrement);
	}

}

class SharedData {
	private Integer count = 100;
	
	public synchronized void increment() {
		count ++;
		System.out.println("Increment:" + count);
	}
	
	public synchronized void decrement() {
		count --;
		System.out.println("Decrement:" + count);
	}
}

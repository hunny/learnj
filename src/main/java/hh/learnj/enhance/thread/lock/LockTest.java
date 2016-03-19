package hh.learnj.enhance.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

	public static void main(String[] args) {

		final Console console = new Console();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
					console.print("Hunny.Hu@test.cn");
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
					console.print("huxiong888@163.com");
				}
			}
		}).start();

	}

}

class Console {

	private Lock lock = new ReentrantLock();

	public void print(String str) {
		lock.lock();
		try {
			for (int i = 0; i < str.length(); i++) {
				System.out.print(str.charAt(i));
			}
			System.out.println();
		} finally {
			lock.unlock();
		}
	}

}

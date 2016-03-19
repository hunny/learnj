package hh.learnj.enhance.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {

	public static void main(String[] args) {
		// testWithStaticClassLevel();
		tstWithClassLevel();
	}

	public static void tstWithClassLevel() {

		final Business business = new Business();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					business.sub(i);
				}
			}

		}).start();

		for (int i = 0; i < 10; i++) {
			business.main(i);
		}
	}

	public static void testWithStaticClassLevel() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (LockConditionTest.class) {
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 20; j++) {
							System.out
									.println("static level sub thread sequence of "
											+ j + ", loop at " + i);
						}
					}
				}
			}

		}).start();

		synchronized (LockConditionTest.class) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 30; j++) {
					System.out.println("static level main thread sequence of "
							+ j + ", loop at " + i);
				}
			}
		}
	}

}

class Business {

	private boolean shouldSub = true;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	public void sub(int i) {
		lock.lock();
		try {
			while (!shouldSub) {// 如果不是子线程，需要启动等待模式
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 0; j < 20; j++) {
				System.out.println("class level sub thread sequence of " + j
						+ ", loop at " + i);
			}
			shouldSub = false;
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public void main(int i) {
		lock.lock();
		try {
			while (shouldSub) {
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j = 0; j < 30; j++) {
				System.out.println("class level main thread sequence of " + j
						+ ", loop at " + i);
			}
			shouldSub = true;
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
}

package hh.learnj.enhance.thread;

public class TraditionalThreadCommunicateTest {

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
				synchronized (TraditionalThreadCommunicateTest.class) {
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

		synchronized (TraditionalThreadCommunicateTest.class) {
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

	public synchronized void sub(int i) {
		while (!shouldSub) {// 如果不是子线程，需要启动等待模式
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 20; j++) {
			System.out.println("class level sub thread sequence of " + j
					+ ", loop at " + i);
		}
		shouldSub = false;
		this.notify();
	}

	public synchronized void main(int i) {
		while (shouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 30; j++) {
			System.out.println("class level main thread sequence of " + j
					+ ", loop at " + i);
		}
		shouldSub = true;
		this.notify();
	}
}

package hh.learnj.testj.traditional.thread.test01;

public class TraditionalThread {

	public static void main(String[] args) {
		final ThreadAlter threadAlter = new ThreadAlter();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int n = 1; n <= 5; n++) {
					threadAlter.subThread(n);
				}
			}

		}).start();
		for (int n = 1; n <= 10; n++) {
			threadAlter.mainThread(n);
		}

	}

}

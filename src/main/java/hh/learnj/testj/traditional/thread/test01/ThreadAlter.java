package hh.learnj.testj.traditional.thread.test01;

public class ThreadAlter {
	
	private boolean isSub = true;
	
	public synchronized void subThread(int n) {
		while (!isSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 3; i++) {
			System.out.println("Sub thread at " + i + " of " + n);
		}
		isSub = false;
		this.notify();
	}
	
	public synchronized void mainThread(int n) {
		while (isSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 3; i++) {
			System.out.println("Main thread at " + i + " of " + n);
		}
		isSub = true;
		this.notify();
	}

}

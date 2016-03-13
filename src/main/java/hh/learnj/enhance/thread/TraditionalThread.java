package hh.learnj.enhance.thread;

public class TraditionalThread {

	public static void main(String[] args) {
		Thread thread1 = new Thread();
		thread1.start();
		System.out.println("Thread1:" + thread1.getName());
		
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out
						.println("Thread2-1:" + Thread.currentThread().getName());
				System.out.println("Thread2-2:" + this.getName());
			}
		};
		thread2.start();
		
		Thread thread3 = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out
					.println("Thread3:" + Thread.currentThread().getName());
				}
			}
		};
		thread3.start();
		
		Thread thread4 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out
					.println("Thread4:" + Thread.currentThread().getName());
				}
			}
		});
		thread4.start();
		
		
	}

}

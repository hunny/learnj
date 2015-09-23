package hh.learnj.testj.traditional.thread.test03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
//		ExecutorService threadPool = Executors.newCachedThreadPool();
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();//线程死掉后重新启动
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int n = 1; n <= 5; n++) {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()
								+ " is looping of " + n + " for task" + task);
					}
				}
			});
		}
		System.out.println("All tasks have committed!");
		threadPool.shutdown();
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " bomb!");
			}
		}, 6, 2, TimeUnit.SECONDS);
	}

}

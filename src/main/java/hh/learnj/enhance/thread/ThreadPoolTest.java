package hh.learnj.enhance.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		
//		ExecutorService executorService = Executors.newFixedThreadPool(1);
//		ExecutorService executorService = Executors.newCachedThreadPool();
		//实现线程死掉后重新启动
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			final int task = i;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					for (int k = 0; k < 10; k++) {
						try {
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread() + " is loop of " + k + " for task " + task);
					}
				}
			});
		}
		//All thread execute over and it will shutdown.
		executorService.shutdown();
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("Schedule executor program.");
			}
		}, 5, TimeUnit.SECONDS);
		scheduledExecutorService.shutdown();
		
		Executors.newScheduledThreadPool(2).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("scheduleAtFixedRate executor program.");
			}
		}, 4, 2, TimeUnit.SECONDS);
	}

}

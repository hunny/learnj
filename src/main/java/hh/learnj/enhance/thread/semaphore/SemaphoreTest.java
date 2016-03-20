package hh.learnj.enhance.thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i < 10; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("线程：" + Thread.currentThread().getName() + "进入，当前已有" + (3 - semaphore.availablePermits()) + "个迸发");
					try {
						long time = (long)(Math.random() * 10000);
						Thread.sleep(time);
						System.out.println("线程：" + Thread.currentThread().getName() + "执行时长：" + time + "ms");
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("线程：" + Thread.currentThread().getName() + "即将离开。");
					semaphore.release();
					System.out.println("线程：" + Thread.currentThread().getName() + "已经离开，当前已有" + (3 - semaphore.availablePermits()) + "个迸发");
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
		
	}

}

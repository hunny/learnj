package hh.learnj.enhance.thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量可以用来限制对某个共享资源进行访问的线程的数量。
 * 在对资源进行访问之前，线程必须从得到信号量的许可（调用Semaphore对象的acquire()方法）；
 * 在完成对资源的访问后，线程必须向信号量归还许可（调用Semaphore对象的release()方法）。
 * 
 * @author hunnyhu
 *
 */
public class SemaphoreDemo {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能3个线程同时访问
		final Semaphore semph = new Semaphore(3);
		// 模拟个客户端访问
		for (int index = 1; index < 10; index++) {
			final int NO = index;
			Runnable ru = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semph.acquire();
						Integer num = new Integer(NO);
						System.out.println("accessing: " + num);

						Thread.sleep((long) (Math.random() * 1000));
						// 访问完后，释放
						System.out.println("release: " + num);
						semph.release();
					} catch (InterruptedException e) {
					}
				}
			};
			exec.execute(ru);
		}
		// 退出线程池
		exec.shutdown();
	}

}

package hh.learnj.enhance.thread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier (周期障碍)类可以帮助同步，它允许一组线程等待整个线程组到达公共屏障点。CyclicBarrier
 * 是使用整型变量构造的，其确定组中的线程数。当一个线程到达屏障时（通过调用
 * CyclicBarrier.await()），它会被阻塞，直到所有线程都到达屏障，
 * 然后在该点允许所有线程继续执行。与CountDownLatch不同的是，CyclicBarrier
 * 所有公共线程都到达后，可以继续执行下一个目标点，而CountDownLatch第一次到达指定点后，也就是记数器减制零，就无法再次执行下一目标工作。
 * 
 * @author hunnyhu
 *
 */
public class TestCyclicBarrier {

	private static final int THREAD_NUM = 5;

	public static class WorkerThread implements Runnable {

		CyclicBarrier barrier;

		public WorkerThread(CyclicBarrier b) {
			this.barrier = b;
		}

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName()
						+ " Worker's waiting");
				// 线程在这里等待，直到所有线程都到达barrier。
				barrier.await();
				System.out.println("ID:" + Thread.currentThread().getName()
						+ " Working");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, new Runnable() {
			// 当所有线程到达barrier时执行
			@Override
			public void run() {
				System.out.println("Inside Barrier when all thread arrival.");

			}
		});

		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(new WorkerThread(cb)).start();
		}
	}

}

package hh.learnj.enhance.thread.collects.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
	public static void main(String[] args) {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
		for (int i = 0; i < 5; i++) {
			new Thread() {
				public void run() {
					while (true) {
						try {
							long time = (long) (Math.random() * 1000);
							Thread.sleep(time);
							System.out.println(Thread.currentThread().getName()
									+ "准备放数据!");
							queue.put(Thread.currentThread().getName() + "的数据" + time);
							System.out.println(Thread.currentThread().getName()
									+ "已经放了数据，" + "队列目前有" + queue.size() + "个数据");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	
					}
				}
	
			}.start();
		}

		new Thread() {
			public void run() {
				while (true) {
					try {
						// 将此处的睡眠时间分别改为100和1000，观察运行结果
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName()
								+ "准备取数据!");
						String val = queue.take();
						System.out.println(Thread.currentThread().getName()
								+ "已经取走数据:" + val  + ", 队列目前有" + queue.size() + "个数据");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}
}

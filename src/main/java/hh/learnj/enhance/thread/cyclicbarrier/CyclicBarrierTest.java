package hh.learnj.enhance.thread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				System.out.println("完成一批。");
			}
		});
		for (int i = 0; i < 3 * 12; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						long time = (long)(Math.random() * 1000);
						Thread.sleep(time);
						System.out.println("线程：" + Thread.currentThread().getName() 
								+ "即将到达，当前已有" + (barrier.getNumberWaiting() + 1) + "个已经到达，正在等待。时长：" 
								+ time);
						barrier.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
	}

}

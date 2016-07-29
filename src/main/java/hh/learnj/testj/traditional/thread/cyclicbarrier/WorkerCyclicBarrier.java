package hh.learnj.testj.traditional.thread.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class WorkerCyclicBarrier implements Runnable {
	
	private CyclicBarrier cyclicBarrier;
	
	public WorkerCyclicBarrier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			int sec = new Random().nextInt(1000);
			Thread.sleep(sec);
			System.out.println(Thread.currentThread().getName() + "花费" + sec + "ms后到达汇合点。");
			//到达后等待
			this.cyclicBarrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		//设置汇集数量以及汇集后完成的任务
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
			@Override
			public void run() {
				System.out.println("隧道已经打通");
			}
		});
		new Thread(new WorkerCyclicBarrier(cyclicBarrier), "工人A").start();
		new Thread(new WorkerCyclicBarrier(cyclicBarrier), "工人B").start();
		new Thread(new WorkerCyclicBarrier(cyclicBarrier), "工人C").start();
		new Thread(new WorkerCyclicBarrier(cyclicBarrier), "工人D").start();
		new Thread(new WorkerCyclicBarrier(cyclicBarrier), "工人E").start();
	}

}

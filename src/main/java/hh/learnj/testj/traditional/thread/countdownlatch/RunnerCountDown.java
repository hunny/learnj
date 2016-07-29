package hh.learnj.testj.traditional.thread.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RunnerCountDown implements Callable<Integer> {
	/**
	 * 开始信号
	 */
	private CountDownLatch begin;
	/**
	 * 结束信号
	 */
	private CountDownLatch end;
	

	public RunnerCountDown(CountDownLatch begin, CountDownLatch end) {
		super();
		this.begin = begin;
		this.end = end;
	}


	@Override
	public Integer call() throws Exception {
		/**
		 * 跑步的成绩
		 */
		int score = new Random().nextInt(100);
		System.out.println(Thread.currentThread().getName() + "就位。" + System.nanoTime());
		begin.await();
		System.out.println("枪响" + System.nanoTime() + ", " + Thread.currentThread().getName() + "开始跑步");
		/**
		 * 跑步中...
		 */
		TimeUnit.MICROSECONDS.sleep(score);
		/**
		 * 跑步者已经跑完全程
		 */
		end.countDown();
		System.out.println("时间" + System.nanoTime() + ", " + Thread.currentThread().getName() + "跑完，耗时：" + score);
		return score;
	}
	
	public static void main(String [] args) throws Exception {
		/**
		 * 参加赛跑的人数
		 */
		int num = 10;
		/**
		 * 发令枪只响一次
		 */
		CountDownLatch begin = new CountDownLatch(1);
		/**
		 * 参与跑步的人有多个
		 */
		CountDownLatch end = new CountDownLatch(num);
		/**
		 * 每个跑步者分配一个跑道
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(num);
		/**
		 * 记录比赛成绩
		 */
		List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
		/**
		 * 跑步者就位，所有线程处于等待状态
		 */
		for (int i = 0; i < num; i++) {
			futures.add(executorService.submit(new RunnerCountDown(begin, end)));
		}
		/**
		 * 准备工作
		 */
		TimeUnit.MICROSECONDS.sleep(500000);
		/**
		 * 发令枪响，跑步者开始跑步
		 */
		begin.countDown();
		/**
		 * 等待所有跑步者跑完全程
		 */
		end.await();
		int total = 0;
		/**
		 * 统计总分
		 */
		for (Future<Integer> future : futures) {
			total += future.get();
		}
		System.out.println("平均分数为：" + (total / num));
		executorService.shutdown();
	}

}

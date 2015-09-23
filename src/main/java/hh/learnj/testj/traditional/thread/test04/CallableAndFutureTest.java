package hh.learnj.testj.traditional.thread.test04;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1500);
				return Thread.currentThread().getName();
			}
		});
		System.out.println("等待结果");
		try {
			System.out.println("结果返回：" + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		
		
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool);
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			completionService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					int millis = new Random().nextInt(1000);
					Thread.sleep(millis);
					return Thread.currentThread().getName() + " task:" + task + " cost: " + millis;
				}
			});
		}
		//取任务
		for (int i = 1; i <= 10; i++) {
			try {
				Future<String> mFuture = completionService.take();
				System.out.println(mFuture.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		threadPool.shutdown();
	}

}

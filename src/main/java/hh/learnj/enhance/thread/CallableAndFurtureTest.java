package hh.learnj.enhance.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFurtureTest {

	public static void main(String[] args) throws Exception {
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				return "Hello, Callable Future.";
			}
		});
		System.out.println(future.get());
		executorService.shutdown();
		
		ExecutorService executors = Executors.newFixedThreadPool(10);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executors);
		for (int i = 0; i < 10; i++) {
			final int task = i;
			completionService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					int random = new Random().nextInt(1000);
					Thread.sleep(random);
					return Thread.currentThread().getName() + " at task " + task + " cost " + random + " ms";
				}
			});
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(completionService.take().get());
		}
		executors.shutdown();
	}

}

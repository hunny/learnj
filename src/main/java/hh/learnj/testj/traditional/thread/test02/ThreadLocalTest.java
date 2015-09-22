package hh.learnj.testj.traditional.thread.test02;

import java.util.Random;

/**
 * 线程范围内共享
 * @author hunnyhu
 */
public class ThreadLocalTest {

	private static ThreadLocal<Integer> threadData = new ThreadLocal<Integer>();
	private static ThreadLocal<ThreadObjectInstance> instanceData = new ThreadLocal<ThreadObjectInstance>();

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					threadData.set(new Random().nextInt());
					System.out.println(Thread.currentThread().getName() + "=" + threadData.get());
					ThreadObjectInstance.getThreadInstance().setName("[+]Name" + threadData.get());
					instanceData.set(ThreadObjectInstance.getThreadInstance());
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			System.out.println("A==>" + Thread.currentThread().getName() + ":"
					+ threadData.get());
			System.out.println("A instance ==>" + Thread.currentThread().getName() + ":"
					+ instanceData.get().getName());
		}
	}

	static class B {
		public void get() {
			System.out.println("B==>" + Thread.currentThread().getName() + ":"
					+ threadData.get());
			System.out.println("B instance ==>" + Thread.currentThread().getName() + ":"
					+ instanceData.get().getName());
		}
	}

}

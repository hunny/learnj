package hh.learnj.enhance.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ThreadScopeSharedDataTest {
	
	
	protected static Integer plainData = 0;
	protected static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();
	protected static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

	public static void main(String[] args) {

		for (int i = 0; i < 4; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					plainData = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " has putted data:" + plainData);
					Integer mRandom = new Random().nextInt();
					threadData.put(Thread.currentThread(), mRandom);
					threadLocal.set(mRandom);
					new A().get(index);
					new B().get(index);
				}
				
			}).start();
		}
	}
	
	static class A {
		public void get(int index) {
			System.out.println("Class A" + index + " for " + Thread.currentThread().getName() + " get data:" + plainData);
			Integer mData = threadData.get(Thread.currentThread());
			System.out.println("SafeData A" + index + " for " + Thread.currentThread().getName() + " get data:" + mData);
			System.out.println("LocalData A" + index + " for " + Thread.currentThread().getName() + " get data:" + threadLocal.get());
		}
	}
	
	static class B {
		public void get(int index) {
			System.out.println("Class B" + index + " for " + Thread.currentThread().getName() + " get data:" + plainData);
			Integer mData = threadData.get(Thread.currentThread());
			System.out.println("SafeData B" + index + " for " + Thread.currentThread().getName() + " get data:" + mData);
			System.out.println("LocalData B" + index + " for " + Thread.currentThread().getName() + " get data:" + threadLocal.get());
		}
	}

}

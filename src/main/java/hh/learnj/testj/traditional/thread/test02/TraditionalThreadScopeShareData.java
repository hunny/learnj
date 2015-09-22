package hh.learnj.testj.traditional.thread.test02;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 传统原始程序线程变量共享
 * @author hunnyhu
 */
public class TraditionalThreadScopeShareData {

	private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					threadData.put(Thread.currentThread(),
							new Random().nextInt());
					System.out.println(Thread.currentThread().getName() + "=" + threadData.get(Thread.currentThread()));
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			System.out.println("A==>" + Thread.currentThread().getName() + ":"
					+ threadData.get(Thread.currentThread()));
		}
	}

	static class B {
		public void get() {
			System.out.println("B==>" + Thread.currentThread().getName() + ":"
					+ threadData.get(Thread.currentThread()));
		}
	}

}

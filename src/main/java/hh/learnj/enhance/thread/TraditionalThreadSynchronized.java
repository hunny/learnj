package hh.learnj.enhance.thread;

import java.lang.reflect.Method;

public class TraditionalThreadSynchronized {

	public static void main(String[] args) throws Exception {
		TraditionalThreadSynchronized test = new TraditionalThreadSynchronized();
//		test.threadOutputUnsafe();
//		test.threadOutputSafeFragment();
//		test.threadInvokeTest("unsafePrint");
		test.threadInvokeTest("safePrintMethod");
	}
	
	public void threadInvokeTest(String methodName) throws Exception {
		Outer outer = new Outer();
		this.threadTest(outer, methodName, "Hello.World@163.com");
		this.threadTest(outer, methodName, "Hunny.Hu@abc.cn");
	}
	
	protected void threadTest(final Outer outer, final String methodName, final String msg) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						Method method = Outer.class.getMethod(methodName, String.class);
						method.invoke(outer, msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//outer.safePrint("Hello.World@163.com");
				}
			}
			
		}).start();
	}
	
	protected void threadOutputSafeFragment() {
		final Outer outer = new Outer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outer.safePrint("Hello.World@163.com");
				}
			}
			
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outer.safePrint("Hunny.Hu@abc.cn");
				}
				
			}
			
		}).start();
	}
	
	protected void threadOutputUnsafe() {
		final Outer outer = new Outer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outer.unsafePrint("Hello.World@163.com");
				}
				
			}
			
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outer.unsafePrint("Hunny.Hu@abc.cn");
				}
				
			}
			
		}).start();
	}
	
	static class Outer {
		
		/**
		 * thread unsafe print
		 * @param str
		 */
		public void unsafePrint(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));;
			}
			System.out.println();
		}
		
		/**
		 * synchronized with code block
		 * @param str
		 */
		public void safePrint(String str) {
			int length = str.length();
			synchronized(this) {
				for (int i = 0; i < length; i++) {
					System.out.print(str.charAt(i));;
				}
				System.out.println();
			}
		}
		
		/**
		 * synchronized with method
		 * @param str
		 */
		public synchronized void safePrintMethod(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));;
			}
			System.out.println();
		}
		
		/**
		 * static level synchronized thread safe.
		 * @param str
		 */
		public static synchronized void safePrintMethodClassLevel(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));;
			}
			System.out.println();
		}
		
		/**
		 * equals static synchronized thread safe with code block.
		 * @param str
		 */
		public void safePrintBlockEqualsStatic(String str) {
			int length = str.length();
			synchronized(Outer.class) {
				for (int i = 0; i < length; i++) {
					System.out.print(str.charAt(i));;
				}
				System.out.println();
			}
		}
		
	}

}

package hh.learnj.enhance.thread;

public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		TraditionalThreadSynchronized test = new TraditionalThreadSynchronized();
//		test.threadOutputUnsafe();
		test.threadOutputSafeFragment();
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
					outer.safePrint("Hunny.Hu@abc.com");
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
					outer.unsafePrint("Hunny.Hu@abc.com");
				}
				
			}
			
		}).start();
	}
	
	class Outer {
		
		public void unsafePrint(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));;
			}
			System.out.println();
		}
		
		public void safePrint(String str) {
			int length = str.length();
			synchronized(this) {
				for (int i = 0; i < length; i++) {
					System.out.print(str.charAt(i));;
				}
				System.out.println();
			}
		}
		
	}

}

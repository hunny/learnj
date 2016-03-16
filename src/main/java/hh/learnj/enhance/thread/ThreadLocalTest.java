package hh.learnj.enhance.thread;

import java.util.Random;

public class ThreadLocalTest {

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Integer random = new Random().nextInt();
					ThreadScopeData.getThreadInstance().setIndex(random);
					ThreadScopeData.getThreadInstance().setName("[+]Name[+]" + random);
					new TestA().get();
					new TestB().get();
				}
			}).start();
		}
	}

}

class TestA {
	public void get() {
		System.out.println("TestA " + Thread.currentThread().getName() + " " + ThreadScopeData.getThreadInstance());
	}
}
class TestB {
	public void get() {
		System.out.println("TestB " + Thread.currentThread().getName() + " " + ThreadScopeData.getThreadInstance());
	}
}

class ThreadScopeData {
	
	private ThreadScopeData() {}
	
	private static ThreadLocal<ThreadScopeData> data = new ThreadLocal<ThreadScopeData>();
	
	public static ThreadScopeData getThreadInstance() {
		ThreadScopeData instance = data.get();
		if (null == instance) {
			instance = new ThreadScopeData();
			data.set(instance);
		}
		return instance;
	}
	
	private String name = null;
	private Integer index = 0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		return "Name:" + this.name + ", index:" + this.index;
	}
	
}

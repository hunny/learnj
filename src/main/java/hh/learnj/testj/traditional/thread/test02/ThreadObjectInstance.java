package hh.learnj.testj.traditional.thread.test02;

/**
 * 线程范围内共享实例
 * @author hunnyhu
 */

public class ThreadObjectInstance {
	
	private ThreadObjectInstance() {}
	
	private static ThreadLocal<ThreadObjectInstance> map = new ThreadLocal<ThreadObjectInstance>();
	
	public static /* synchronized */ ThreadObjectInstance getThreadInstance() {
		ThreadObjectInstance instance = map.get();
		if (null == instance) {
			instance = new ThreadObjectInstance();
			map.set(instance);
		}
		return instance;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}

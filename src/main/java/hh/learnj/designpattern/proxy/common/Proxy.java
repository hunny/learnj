package hh.learnj.designpattern.proxy.common;

/**
 * 代理主题角色
 * 
 * @author hunnyhu
 */
public class Proxy implements Subject {

	/**
	 * 要代理哪个实现类
	 */
	private Subject subject = null;

	public Proxy() {
		subject = new RealSubject();
	}

	@Override
	public void request() {
		System.out.println("预处理");
		subject.request();
		System.out.println("善后处理");
	}

}

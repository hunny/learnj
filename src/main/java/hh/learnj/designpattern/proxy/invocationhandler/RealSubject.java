package hh.learnj.designpattern.proxy.invocationhandler;

/**
 * 具体主题角色
 * @author hunnyhu
 *
 */
public class RealSubject implements Subject {

	/**
	 * 具体主题角色实现方法
	 */
	@Override
	public void request() {
		System.out.println("Subject的具体主题角色实现方法RealSubject");
	}

}

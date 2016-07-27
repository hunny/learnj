package hh.learnj.designpattern.proxy.common;

/**
 * 具体主题角色
 * 
 * @author hunnyhu
 *
 */
public class RealSubject implements Subject {

	@Override
	public void request() {
		System.out.println("具体主题角色的实现");
	}

}

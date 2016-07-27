package hh.learnj.designpattern.decorator.reflect;

/**
 * 飞行能力
 * @author hunnyhu
 *
 */
public class FlyFeature implements Feature {

	@Override
	public void load() {
		System.out.println("增加一只翅膀");
	}

}

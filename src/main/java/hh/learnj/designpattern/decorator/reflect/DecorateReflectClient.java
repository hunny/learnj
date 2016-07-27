package hh.learnj.designpattern.decorator.reflect;

public class DecorateReflectClient {

	public static void main(String[] args) {
		//定义家喻户晓的小老鼠Jerry
		Animal jerry = new Rat();
		//增加飞行能力
		jerry = new DecorateAnimal(jerry, FlyFeature.class);
		//增加钻地能力
		jerry = new DecorateAnimal(jerry, DigFeature.class);
		//Jerry开始耍猫
		jerry.doStuff();
		
	}
}

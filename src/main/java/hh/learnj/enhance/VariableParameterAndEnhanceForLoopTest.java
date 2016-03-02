package hh.learnj.enhance;

/**
 * A. 可变参数测试
 * 1. 只能出现在参数列表的最后；
 * 2. [...]可变参数标识位于变量类型和变量名之间；前后有无空格都可以；
 * 3. 调用可变参数的方法时，编译器为该可变参数隐含创建一个数组，在方法体中以数组的形式访问可变参数；
 * B. 增强的for循环
 * for (type 变量名 : 集合变量名) { ...}
 * 集合变更名可以是数组或者实现了Iterable接口的集合类；
 * @author hunnyhu
 *
 */
public class VariableParameterAndEnhanceForLoopTest {

	public static void main(String[] args) {
		System.out.println(add(3));
		System.out.println(add(3, 2));
		System.out.println(add(3, 2, 1, 9));
		System.out.println(add(3, 2, 1, 4, 8));
		System.out.println(add(3, 2, 1, 4, 5, 6));
	}
	
	public static int add(int a, int ... others) {
		int sum = 0;
		if (others.length == 0) {
			return a;
		}
		sum += a;
		for (int i : others) {
			sum += i;
		}
		return sum;
	}

}

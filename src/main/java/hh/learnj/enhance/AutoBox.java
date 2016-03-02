package hh.learnj.enhance;

public class AutoBox {

	public static void main(String[] args) {
		
		Integer tmp = 3; //auto enbox
		tmp = tmp + 5;//auto debox;
		System.out.println(tmp);
		
		//编译器会缓存-128到127之间的数字对象，所以在这个范围之内的数字直接比较是相同的
		//该设计模式为享元模式：flyweight，很多很小的对象，有很多相同的特征，则设计为一个类
		System.out.print("在-128至127之间:");
		Integer tmp1 = 13;
		Integer tmp2 = 13;
		System.out.println(tmp1 == tmp2);
		System.out.print("在-128至127之间使用equals:");
		System.out.println(tmp1.equals(tmp2));
		System.out.print("在-128至127之外:");
		Integer tmp3 = 137;
		Integer tmp4 = 137;
		System.out.println(tmp3 == tmp4);
		System.out.print("在-128至127之外使用equals:");
		System.out.println(tmp3.equals(tmp4));
		System.out.print("在-128至127之间:");
		Integer tmp5 = Integer.valueOf(13);
		Integer tmp6 = Integer.valueOf(13);
		System.out.println(tmp5 == tmp6);
		System.out.print("在-128至127之间使用equals:");
		System.out.println(tmp5.equals(tmp6));
		System.out.print("在-128至127之外:");
		Integer tmp7 = Integer.valueOf(137);
		Integer tmp8 = Integer.valueOf(137);
		System.out.println(tmp7 == tmp8);
		System.out.print("在-128至127之外使用equals:");
		System.out.println(tmp7.equals(tmp8));
	}

}

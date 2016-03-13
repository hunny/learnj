package hh.learnj.enhance.classloader;

public class ClassLoaderTest {

	public static void main(String[] args) {
		
		System.out.println(ClassLoaderTest.class.getClassLoader().getClass()
				.getName());
		
		System.out.println(System.class.getClassLoader());
		
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while (loader != null) {
			System.out.println("[+][-]" + loader.getClass().getName());
			loader = loader.getParent();
		}
		System.out.println("[+][-]" + loader);

	}

}

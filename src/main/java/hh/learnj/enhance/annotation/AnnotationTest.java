package hh.learnj.enhance.annotation;

@DefinedAnnotation
public class AnnotationTest {

	public static void main(String[] args) {
		if (AnnotationTest.class
				.isAnnotationPresent(DefinedAnnotation.class)) {
			DefinedAnnotation annotation = AnnotationTest.class.getAnnotation(DefinedAnnotation.class);
			System.out.println(annotation);
		} else {
			System.out.println("Oops!!!");
		}
	}

}

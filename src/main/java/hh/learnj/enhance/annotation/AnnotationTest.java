package hh.learnj.enhance.annotation;

import java.util.Arrays;

@DefinedAnnotation(annotationAttr=@MetaAnnotation("AZC"), color="RED",value="OK", arrAttr= {1, 2, 3})
public class AnnotationTest {

	@DefinedAnnotation("OK")
	public static void main(String[] args) {
		if (AnnotationTest.class
				.isAnnotationPresent(DefinedAnnotation.class)) {
			DefinedAnnotation annotation = AnnotationTest.class.getAnnotation(DefinedAnnotation.class);
			System.out.println(annotation);
			System.out.println(annotation.color());
			System.out.println(annotation.value());
			System.out.println(Arrays.asList(annotation.arrAttr()));
			System.out.println(annotation.annotationAttr().value());
		} else {
			System.out.println("Oops!!!");
		}
	}

}

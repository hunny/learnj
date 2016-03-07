package hh.learnj.enhance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//元注解, meta annotation 
//RetentionPolicy.SOURCE，RetentionPolicy.CLASS, RetentionPolicy.RUNTIME
//分别对应Java源文件，class文件，内存中的字节码
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DefinedAnnotation {
	String color() default "BLUE";
	String value();
	int [] arrAttr() default {3, 4, 5};
	MetaAnnotation annotationAttr() default @MetaAnnotation("abc");
}

/**
 * 
 */
package hh.learnj.testj.annotation.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 * @author HuZeXiong
 * 
 */
@Target(ElementType.METHOD)//这个注解是应用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivilegeInfo {
	
    /**
     * 权限的名称
     * @return
     */
    String value() default "";
    
    /**
     * 权限类型
     * @return
     */
    String type() default "";
}

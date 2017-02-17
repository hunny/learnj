/**
 * 
 */
package hh.learnj.testj.annotation.privilege;

import java.lang.reflect.Method;

/**
 * 权限注解解析器 这个解析器的主要功能，是解析目标方法上如果有PrivilegeInfo注解，那么解析出这个注解中的value值（权限的值）
 * 
 * @author HuZeXiong
 *
 */
public class PrivilegeAnnotationParse {

	/**
	 * 解析注解
	 * 
	 * @param targetClass
	 *            目标类的class形式
	 * @param methodName
	 *            在客户端调用哪个方法,methodName就代表哪个方法
	 * @return
	 * @throws Exception
	 */
	public static String parse(Class targetClass, String methodName) throws Exception {
		String methodAccess = "";
		/*
		 * 为简单起见，这里考虑该方法没有参数
		 */
		Method method = targetClass.getMethod(methodName);
		// 判断方法上是否有Privilege注解
		if (method.isAnnotationPresent(PrivilegeInfo.class)) {
			// 得到方法上的注解
			PrivilegeInfo privilegeInfo = method.getAnnotation(PrivilegeInfo.class);
			methodAccess = privilegeInfo.value();
		}
		return methodAccess;
	}
}

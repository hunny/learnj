/**
 * 
 */
package hh.learnj.testj.annotation.privilege;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 权限检查切面 根据用户原有的权限，与目标方法的权限配置进行匹配， 如果目标方法需要的权限在用户原有的权限以内，则调用目标方法 如果不匹配，则不调用目标方法
 * 
 * @author HuZeXiong
 *
 */
public class PrivilegeAspect {
	
	/**
	 * 用户本身的权限
	 */
	private List<UserPrivilege> privileges;

	public List<UserPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<UserPrivilege> privileges) {
		this.privileges = privileges;
	}

	/**
	 * aop中的环绕通知 在这个方法中检查用户的权限和目标方法的需要的权限是否匹配 如果匹配则调用目标方法，不匹配则不调用
	 * 
	 * @param joinPoint
	 *            连接点
	 * @throws Throwable
	 */
	public void isAccessMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		/**
		 * 1.获取访问目标方法应该具备的权限 为解析目标方法的PrivilegeInfo注解，根据我们定义的解析器，需要得到：目标类的class形式
		 * 方法的名称
		 */
		Class targetClass = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		// 得到该方法的访问权限
		String methodAccess = PrivilegeAnnotationParse.parse(targetClass, methodName);
		/*
		 * 2.遍历用户的权限，看是否拥有目标方法对应的权限
		 */
		boolean isAccessed = false;
		for (UserPrivilege firmPrivilege : privileges) {
			/*
			 * 如果目标方法没有使用PrivilegeInfo注解，则解析出来的权限字符串就为空字符串 则默认用户拥有这个权限
			 */
			if ("".equals(methodAccess)) {
				isAccessed = true;
				break;
			}
			/*
			 * 用户原有权限列表中有的权限与目标方法上PrivilegeInfo注解配置的权限进行匹配
			 */
			if (firmPrivilege.getValue() != null && firmPrivilege.getValue().equalsIgnoreCase(methodAccess)) {
				isAccessed = true;
				break;
			}
		}
		/*
		 * 3.如果用户拥有权限，则调用目标方法 ，如果没有，则不调用目标方法，只给出提示
		 */
		if (isAccessed) {
			joinPoint.proceed();// 调用目标方法
		} else {
			System.out.println("你没有权限");
		}
	}
}

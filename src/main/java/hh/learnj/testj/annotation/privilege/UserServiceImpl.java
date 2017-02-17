/**
 * 
 */
package hh.learnj.testj.annotation.privilege;

/**
 * 用户业务实现
 * 
 * @author huzexiong
 *
 */
public class UserServiceImpl implements UserService {

	/**
     * 在需要权限的目标方法上，使用PrivilegeInfo注解，配置权限
     */
    @Override
    @PrivilegeInfo("save")
    public void save() {
        System.out.println("UserServiceImpl.save()");

    }

	/**
     * 在需要权限的目标方法上，使用PrivilegeInfo注解，配置权限
     */
    @Override
    @PrivilegeInfo("update")
    public void update() {
        System.out.println("UserServiceImpl.update()");

    }

	/**
     * 不需要权限的目标方法上，则不添加PrivilegeInfo注解
     * 在切面中，默认用户拥有权限
     */
    @Override
    public void get() {
        System.out.println("UserServiceImpl.get()");

    }
}

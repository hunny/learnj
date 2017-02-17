/**
 * 
 */
package hh.learnj.testj.annotation.privilege;

/**
 * 封装用户权限 为简单，只封装了权限的名称
 * 
 * @author huzexiong
 *
 */
public class UserPrivilege {
	/**
	 * 用户权限的名称
	 */
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UserPrivilege(String value) {
		this.value = value;
	}

	public UserPrivilege() {
	}
}

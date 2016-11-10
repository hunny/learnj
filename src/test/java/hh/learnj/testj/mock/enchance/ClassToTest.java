/**
 * 
 */
package hh.learnj.testj.mock.enchance;

/**
 * @author huzexiong
 *
 */
public class ClassToTest {
	
	private MyDatabase myDatabase;
	
	public ClassToTest(MyDatabase myDatabase) {
		this.myDatabase = myDatabase;
	}

	public boolean query(String str) {
		myDatabase.query(str);
		return true;
	}

	public Object getUniqueId() {
		return null;
	}
	
}

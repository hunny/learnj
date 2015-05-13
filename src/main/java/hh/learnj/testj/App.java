package hh.learnj.testj;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String input = "12.0,89,98,-10,-12.0,0.9";
		System.out.println("Hello World=" + input.matches("^(((-?\\d+)|((-?\\d+)(\\.\\d+))),{0,1})+$"));
	}
}

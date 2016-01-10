package hh.learnj.testj.math;

/**
 * X + X - 9 = 4
 * +   -   -
 * X - X * X = 4
 * /   *   -
 * X + X - X = 4
 * =   =   =
 * 4   4   4
 * =============
 * i + j - 9 = 4
 * +   -   -
 * k - m * n = 4
 * /   *   -
 * o + p - q = 4
 * =   =   =
 * 4   4   4
 *
 */
public class TestMath {
	
	private static final int C = 20;
	private static final int V = 2;

	public static void main(String[] args) {
		for (int i = 0; i < C; i++) {
			for (int j = 0; j < C; j++) {
				for (int k = 0; k < C; k++) {
					for (int m = 0; m < C; m++) {
						for (int n = 0; n < C; n++) {
							for (int o = 1; o <= C; o++) {
								for (int p = 0; p < C; p++) {
									for (int q = 0; q < C; q++) {
										if ((i + j - 9) == V 
												&& (k - m * n) == V 
												&& (o + p -q) == V
												&& (i + k / o) == V && (k % o) == 0
												&& (j - m * p) == V
												&& (9 - n - q) == V) {
											System.out.println(" " + i + " + " + j + "- 9 = " + V);
											System.out.println(" +   -   -");
											System.out.println(" " + k + " - " + m + " * " + n + " = " + V);
											System.out.println(" /   *   -");
											System.out.println(" " + o + " + " + p + " - " + q + " = " + V);
											System.out.println(" =   =   =");
											System.out.println(" " + V + "   " + V + "   " + V + "");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("over");
	}

}

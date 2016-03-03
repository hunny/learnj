package hh.learnj.enhance.enumtest;

public class Workday {
	
	/**
	 * 不能随便创建对象
	 */
	private Workday() {};
	
	public final static Workday SUN = new Workday();
	public final static Workday SAT = new Workday();
	public final static Workday FRI = new Workday();
	public final static Workday THU = new Workday();
	public final static Workday WED = new Workday();
	public final static Workday TUE = new Workday();
	public final static Workday MON = new Workday();
	
	public Workday nextDay() {
		if (this == SUN) {
			return MON;
		} else if (this == MON) {
			return TUE;
		} else if (this == TUE) {
			return WED;
		} else if (this == WED) {
			return THU;
		} else if (this == THU) {
			return FRI;
		} else if (this == FRI) {
			return SAT;
		} else {
			return SUN;
		}
	}
	
	public String toString() {
		if (this == SUN) {
			return "Sunday";
		} else if (this == MON) {
			return "Monday";
		} else if (this == TUE) {
			return "Tuesday";
		} else if (this == WED) {
			return "Wednesday";
		} else if (this == THU) {
			return "Thurday";
		} else if (this == FRI) {
			return "Friday";
		} else {
			return "Saturday";
		}
	}

}

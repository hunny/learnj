package hh.learnj.enhance.enumtest;

public class EnumTest {

	public static void main(String[] args) {

		Workday day = Workday.FRI;
		System.out.println(day);
		System.out.println(day.nextDay());

		// Original ENUM
		Weekday weekday = Weekday.SAT;
		System.out.println(weekday);
		System.out.println(weekday.name());
		System.out.println(weekday.ordinal());
		System.out.println(Weekday.valueOf("MON"));
		System.out.println(Weekday.values().length);
		System.out.println(Weekday.valueOf(Weekday.class, "WED"));
		
		System.out.println(TrafficLamp.RED);
		System.out.println(TrafficLamp.RED.nextLamp());

	}

	public enum Weekday {
		MON, TUE, WED, THU, FRI, SAT(3), SUN;
		private Weekday() {
			System.out.println("first constructor");
		}

		private Weekday(int day) {
			System.out.println("second constructor");
		}
	}
	
	public enum TrafficLamp {
		RED (30) {
			@Override
			public TrafficLamp nextLamp() {
				return GREEN;
			}
		}, 
		GREEN (45) {
			@Override
			public TrafficLamp nextLamp() {
				return YELLOW;
			}
		}, 
		YELLOW(3) {
			@Override
			public TrafficLamp nextLamp() {
				return RED;
			}
		};
		
		public abstract TrafficLamp nextLamp();
		
		private int time;
		private TrafficLamp(int time) {
			this.time = time;
			System.out.println(this.time);
		}
	}

}

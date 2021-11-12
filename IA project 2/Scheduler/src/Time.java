
public class Time {
	private int day;    //any number 0 or greater
	private int hour;   //any number 0 - 23
	private int minute; //any number 0 - 59
	
	//Constructors
	public Time(int d, int h, int m) {
		day = d;
		hour = h;
		minute = m;
	}
	
	public Time(Time t) {  //creates an exact copy of another Time object
		day = t.getDay();
		hour = t.getHour();
		minute = t.getMinute();
	}
	
	public Time() {
		day = 0;
		hour = 0;
		minute = 0;
	}

	//Methods
	public int toMinutes() {
		return day * 1440 + hour * 60 + minute;
	}
	
	public static int getOrder(Time t1, Time t2) {  //returns 1 if t2 is after t1, -1 if t2 is before t1, and 0 if they are the same
		if (t1.toMinutes() < t2.toMinutes()) {
			return 1;
		} else if (t1.toMinutes() > t2.toMinutes()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	//Getters and setters
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
}

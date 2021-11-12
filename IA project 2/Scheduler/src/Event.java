
public class Event {
	private String name;
	private Time startTime;
	private Time endTime;
	
	//Constructor
	public Event(String n, Time st, Time et) {
		name = n;
		startTime = new Time(st);
		endTime = new Time(et);
	}
	
	public Event(String n) {
		name = n;
		startTime = new Time();
		endTime = new Time();
	}

	//Methods
	public int getLengthMinutes() {
		return endTime.toMinutes() - startTime.toMinutes();
	}
	
	public static boolean isSimultaneous(Event e1, Event e2) {
		if (
			(Time.getOrder(e1.getEndTime(), e2.getStartTime()) == 1 || Time.getOrder(e1.getEndTime(), e2.getStartTime()) == 0)
			|| (Time.getOrder(e2.getEndTime(), e1.getStartTime()) == 1 || Time.getOrder(e2.getEndTime(), e1.getStartTime()) == 0)
		) {
			return false;
		} else {
			return true;
		}
	}

	//Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
}

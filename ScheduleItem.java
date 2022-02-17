import java.util.ArrayList;

public class ScheduleItem {
	private String name;
	private ArrayList<Event> eventList;
	
	//Constructors
	public ScheduleItem(String n) {
		name = n;
		eventList = new ArrayList<Event>();
	}
	
	public ScheduleItem(String n, ArrayList<Event> el) {
		name = n;
		eventList = el;
	}

	//Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Event> getEventList() {
		return eventList;
	}

	public void setEventList(ArrayList<Event> eventList) {
		this.eventList = eventList;
	}
}

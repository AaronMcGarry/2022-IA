import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PageEndPanel extends JPanel implements ActionListener {
	private JButton createScheduleButton = new JButton("Create a Schedule");
	private LineStartPanel lsPanel;
	private ArrayList<ScheduleItem> itemList = new ArrayList<ScheduleItem>();
	
	public PageEndPanel(LineStartPanel p) {
		lsPanel = p;
		
		createScheduleButton.addActionListener(this);
		createScheduleButton.setFocusable(false);
		this.add(createScheduleButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(createScheduleButton)) {
			itemList = lsPanel.getItemList();
			
			//Check that all end times are after start times and that there is at least one event
			boolean hasWrongTime = false;
			boolean hasNoEvents = true;
			for (ScheduleItem i : itemList) {
				if (hasWrongTime) {
					break;
				}
				
				for (Event event : i.getEventList()) {
					hasNoEvents = false;
					if (event.getLengthMinutes() < 0) {
						hasWrongTime = true;
					}
				}
			}
			
			if (hasNoEvents) {
				JOptionPane.showMessageDialog(null, "Can't create a schedule; you don't have any events inputted.", "", JOptionPane.ERROR_MESSAGE);
			} else if (hasWrongTime) {
				JOptionPane.showMessageDialog(null, "Can't create a schedule; some of your events have the end time before the start time.", "", JOptionPane.ERROR_MESSAGE);
			} else {
				//I have to use an iterator here because you can't remove items in a for each loop.
				Iterator<ScheduleItem> itemIterator = itemList.iterator();
				while (itemIterator.hasNext()) {
					ScheduleItem i = itemIterator.next();
					if (i.getEventList().size() == 0) {
						itemIterator.remove();
					}
				}
				
				ArrayList<Integer> schedule = new ArrayList<Integer>();
				schedule.add(0);
				schedule = generateSchedule(schedule);
				
				if (schedule.size() == 1 && itemList.size() != 1) {
					JOptionPane.showMessageDialog(null, "There was no schedule found that satisfies all of your schedule items.", "", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Schedule found! Press OK to view your schedule.", "", JOptionPane.INFORMATION_MESSAGE);
					
					String scheduleString = "";
					for (int i = 0; i < itemList.size(); i++) {
						scheduleString += itemList.get(i).getName() + ": " + itemList.get(i).getEventList().get(schedule.get(i)).getName() + "\n";
					}
					JOptionPane.showMessageDialog(null, scheduleString, "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	private ArrayList<Integer> generateSchedule(ArrayList<Integer> selectedEvents) {
		//if the last event in selectedEvents is out of range of its corresponding schedule item,
		if (selectedEvents.get(selectedEvents.size() - 1) == itemList.get(selectedEvents.size() - 1).getEventList().size()) {
			//either it means there is no possible schedule,
			if (selectedEvents.size() == 1) {
				return selectedEvents;
			//or it needs to check the next event one step up.
			} else {
				selectedEvents.remove(selectedEvents.size() - 1);
				selectedEvents.set(selectedEvents.size() - 1, selectedEvents.get(selectedEvents.size() - 1) + 1);
				selectedEvents = advanceSchedule(selectedEvents);
				return generateSchedule(selectedEvents);
			}
		//if the two lists are the same size, that means it's found a schedule that works.
		} else if (selectedEvents.size() == itemList.size()) {
			return selectedEvents;
		} else {
			selectedEvents.add(0);
			selectedEvents = advanceSchedule(selectedEvents);
			return generateSchedule(selectedEvents);
		}
	}
	
	//Starting with a given schedule, this function checks that the last event doesn't overlap any of the previous ones. If it
	//doesn't, it returns the schedule, and if it does, it progresses to the next event of the last item and checks again until
	//it finds one with no overlaps or it determines that there are none with no overlaps.
	private ArrayList<Integer> advanceSchedule(ArrayList<Integer> selectedEvents) {
		//case of no event found with no overlaps. Then the function above will remove the last element and check the schedule
		//item one step up.
		if (selectedEvents.get(selectedEvents.size() - 1) == itemList.get(selectedEvents.size() - 1).getEventList().size()) {
			return selectedEvents;
		}
		
		boolean noOverlaps = true;
		for (int i = 0; i < selectedEvents.size() - 1; i++) {
			if (Event.isSimultaneous(
				itemList.get(i).getEventList().get((int)selectedEvents.get(i)),
				itemList.get(selectedEvents.size() - 1).getEventList().get((int)selectedEvents.get(selectedEvents.size() - 1))
			)) {
				noOverlaps = false;
			}
		}
		
		if (noOverlaps) {
			return selectedEvents;
		} else {
			//increment final element of array
			selectedEvents.set(selectedEvents.size() - 1, selectedEvents.get(selectedEvents.size() - 1) + 1);
			return advanceSchedule(selectedEvents);
		}
	}
}                                                                                                                                                                                   

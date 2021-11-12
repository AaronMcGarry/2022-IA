import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainLineEndPanel extends JPanel implements FocusListener{
	private Event e;
	
	JLabel title = new JLabel("");
	JTextField stDayField = new JTextField(4);
	JTextField stHourField = new JTextField(4);
	JTextField stMinuteField = new JTextField(4);
	JTextField etDayField = new JTextField(4);
	JTextField etHourField = new JTextField(4);
	JTextField etMinuteField = new JTextField(4);
	
	public MainLineEndPanel() {
		this.setPreferredSize(new Dimension(200, 350));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		stDayField.addFocusListener(this);
		stHourField.addFocusListener(this);
		stMinuteField.addFocusListener(this);
		etDayField.addFocusListener(this);
		etHourField.addFocusListener(this);
		etMinuteField.addFocusListener(this);
	}
	
	public void renderPanel(Event e) {
		this.e = e; //Allows event to be passed to the focus methods
		
		this.removeAll();
		
		JPanel stdPanel = new JPanel();
		JPanel sthPanel = new JPanel();
		JPanel stmPanel = new JPanel();
		JPanel etdPanel = new JPanel();
		JPanel ethPanel = new JPanel();
		JPanel etmPanel = new JPanel();
		
		stdPanel.add(new JLabel("Day"));
		stdPanel.add(stDayField);
		sthPanel.add(new JLabel("Hour"));
		sthPanel.add(stHourField);
		stmPanel.add(new JLabel("Minute"));
		stmPanel.add(stDayField);
		
		etdPanel.add(new JLabel("Day"));
		etdPanel.add(etDayField);
		ethPanel.add(new JLabel("Hour"));
		ethPanel.add(etHourField);
		etmPanel.add(new JLabel("Minute"));
		etmPanel.add(etDayField);
		
		title.setText("<html>Start and end times of: " + e.getName() + "</html>");
		this.add(title);
		this.add(new JLabel(" ")); //space between the title and the text fields
		this.add(new JLabel("Start"));
		this.add(stdPanel);
		this.add(sthPanel);
		this.add(stmPanel);
		this.add(new JLabel("End"));
		this.add(etdPanel);
		this.add(ethPanel);
		this.add(etmPanel);
		
		stDayField.setText(String.valueOf(e.getStartTime().getDay()));
		stHourField.setText(String.valueOf(e.getStartTime().getHour()));
		stMinuteField.setText(String.valueOf(e.getStartTime().getMinute()));
		etDayField.setText(String.valueOf(e.getEndTime().getDay()));
		etHourField.setText(String.valueOf(e.getEndTime().getHour()));
		etMinuteField.setText(String.valueOf(e.getEndTime().getMinute()));
		
		this.repaint();
		this.revalidate();
	}
	
	public void renderPanel() {
		this.removeAll();
		
		this.repaint();
		this.revalidate();
	}

	public void focusGained(FocusEvent fe) {}
	
	public void focusLost(FocusEvent fe) {
		JTextField flField = (JTextField)fe.getSource();
		
		int flInt = 0;
		try {
			flInt = Integer.parseInt(flField.getText()); //attempt to convert string to int
		} catch (NumberFormatException nfe) {
			flField.setText("0");
		}
		
		if (flInt < 0) {
			flField.setText("0");
		}
		
		//check if hours are greater than 23 or minutes are greater than 59
		if ((flField.equals(stHourField) || flField.equals(etHourField)) && flInt > 23) {
			flField.setText("23");
		}
		if ((flField.equals(stMinuteField) || flField.equals(etMinuteField)) && flInt > 59) {
			flField.setText("59");
		}
		
		//update the time of the event
		if (flField.equals(stDayField)) {
			e.getStartTime().setDay(Integer.parseInt(flField.getText()));
		}
		if (flField.equals(stHourField)) {
			e.getStartTime().setHour(Integer.parseInt(flField.getText()));
		}
		if (flField.equals(stMinuteField)) {
			e.getStartTime().setMinute(Integer.parseInt(flField.getText()));
		}
		if (flField.equals(etDayField)) {
			e.getEndTime().setDay(Integer.parseInt(flField.getText()));
		}
		if (flField.equals(etHourField)) {
			e.getEndTime().setHour(Integer.parseInt(flField.getText()));
		}
		if (flField.equals(etMinuteField)) {
			e.getEndTime().setMinute(Integer.parseInt(flField.getText()));
		}
    }
}

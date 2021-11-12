import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPageEndPanel extends JPanel {
	JButton createScheduleButton = new JButton("Create a Schedule");
	
	public MainPageEndPanel () {
		createScheduleButton.setFocusable(false);
		this.add(createScheduleButton);
	}
}

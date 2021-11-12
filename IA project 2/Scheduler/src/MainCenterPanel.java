import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class MainCenterPanel extends JPanel implements ActionListener{
	private MainLineEndPanel lePanel;
	private ScheduleItem item;
	
	JLabel title = new JLabel("");
	JButton addButton = new JButton("Add Event");
	JButton removeButton = new JButton("Remove Event");
	JButton renameButton = new JButton("Rename Event");
	
	ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
	ButtonGroup rbGroup = new ButtonGroup(); //ButtonGroup makes it so that only one radio button can be selected at a time
	
	int buttonIndex = 0; //keeps track of which radioButton is selected
	
	public MainCenterPanel(MainLineEndPanel p) {
		lePanel = p;
		
		this.setPreferredSize(new Dimension(200, 350));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addButton.setFocusable(false);
		addButton.addActionListener(this);
		removeButton.setFocusable(false);
		removeButton.addActionListener(this);
		renameButton.setFocusable(false);
		renameButton.addActionListener(this);
	}
	
	public void renderPanel(ScheduleItem item) {
		this.item = item; //Allows item to be passed to the actionPerformed method
		
		this.removeAll();
		
		rbList.clear();
		
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.setMaximumSize(new Dimension(120, 75));
		
		if (item.getEventList().size() == 0) {
			removeButton.setEnabled(false);
			renameButton.setEnabled(false);
		} else {
			removeButton.setEnabled(true);
			renameButton.setEnabled(true);
		}
		
		title.setText("<html>Possible event times for: " + item.getName() + "</html>");
		this.add(title);
		for (Event e : item.getEventList()) {
			JRadioButton rb = new JRadioButton(e.getName());
			rb.setAlignmentX(Component.CENTER_ALIGNMENT);
			rb.setFocusable(false);
			rb.addActionListener(this);
			rbGroup.add(rb);
			rbList.add(rb);
			scrollPanel.add(rb);
		}
		scrollPanel.add(new JLabel(" ")); //space between the radio buttons and the buttons
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(renameButton);
		
		scrollPanel.add(buttonPanel);
		
		this.add(scrollPane);
		
		//These two methods display the updates to the components
		this.repaint();
		this.revalidate();
	}
	
	public void renderPanel() {
		lePanel.renderPanel();
		
		this.removeAll();
		
		this.repaint();
		this.revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		//Executed when addButton is pressed
		if (e.getSource() == addButton) {
			String name = (String)JOptionPane.showInputDialog(null, "Name of the new event:", "", JOptionPane.QUESTION_MESSAGE);
			if (name != null) {
				boolean isDuplicate = false;
				for (Event event : item.getEventList()) {
					if (event.getName().equals(name)) {
						isDuplicate = true;
					}
				}

				//Two error messages to handle unwanted input
				if (name.trim().length() < 1) { //Checks if the input is empty or all whitespace
					JOptionPane.showMessageDialog(null, "You must give the event a name.", "", JOptionPane.ERROR_MESSAGE);
				} else if (isDuplicate) {
					JOptionPane.showMessageDialog(null, "Events can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					item.getEventList().add(new Event(name));
					renderPanel(item);
				}
			}
		
		//Executed when removeButton is pressed
		} else if (e.getSource() == removeButton) {
			String[] itemNameArray = new String[item.getEventList().size()];
			for (int i = 0; i < item.getEventList().size(); i++) {
				itemNameArray[i] = item.getEventList().get(i).getName();
			}
			
			String name = (String)JOptionPane.showInputDialog(
					null, "Event to remove:", "", JOptionPane.QUESTION_MESSAGE, null, itemNameArray, itemNameArray[0]
			);
			if (name != null) {
				int nameIndex = 0;
				for (int i = 0; i < item.getEventList().size(); i++) {
					if (item.getEventList().get(i).getName().equals(name)) {
						nameIndex = i;
					}
				}

				//If the deleted button is the selected one, clear the right panel
				if (nameIndex == buttonIndex) {
					lePanel.renderPanel();
				}

				item.getEventList().remove(nameIndex);
				rbList.remove(nameIndex);
				renderPanel(item);
			}
		
		//Executed when renameButton is pressed
		} else if (e.getSource() == renameButton) {
			String[] eventNameArray = new String[item.getEventList().size()];
			for (int i = 0; i < item.getEventList().size(); i++) {
				eventNameArray[i] = item.getEventList().get(i).getName();
			}
			
			String oldName = (String)JOptionPane.showInputDialog(
					null, "Event to rename", "", JOptionPane.QUESTION_MESSAGE, null, eventNameArray, eventNameArray[0]
			);
			if (oldName != null) {
				String newName = (String)JOptionPane.showInputDialog(null, "New name of this event:", "", JOptionPane.QUESTION_MESSAGE);
				if (newName != null) {
					boolean isDuplicate = false;
					for (Event i : item.getEventList()) {
						if (i.getName().equals(newName)) {
							isDuplicate = true;
						}
					}
					int oldNameIndex = 0;
					for (int i = 0; i < item.getEventList().size(); i++) {
						if (item.getEventList().get(i).getName().equals(oldName)) {
							oldNameIndex = i;
						}
					}

					//Three error messages to handle unwanted input
					if (newName == null || newName.trim().length() < 1) { //Checks if the input is empty or all whitespace
						JOptionPane.showMessageDialog(null, "You must give the event a name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (oldName.equals(newName)) {
						JOptionPane.showMessageDialog(null, "You must give the event a new name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (isDuplicate) {
						JOptionPane.showMessageDialog(null, "Events can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
					} else {
						item.getEventList().get(oldNameIndex).setName(newName);;
						rbList.remove(oldNameIndex);
						rbList.add(oldNameIndex, new JRadioButton(newName));
						renderPanel(item);
						lePanel.renderPanel(item.getEventList().get(buttonIndex));
					}
				}
			}
		
		//Executed when a radio button is selected
		} else {
			//Find the index of the radio button
			for (int i = 0; i < rbList.size(); i++) {
				if (rbList.get(i).equals(e.getSource())) {
					buttonIndex = i;
				}
			}
			
			//Pass the event object to the next panel
			lePanel.renderPanel(item.getEventList().get(buttonIndex));
		}
	}
}

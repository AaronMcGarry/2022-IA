import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class LineStartPanel extends JPanel implements ActionListener {
	private CenterPanel cPanel;
	private LineEndPanel lePanel;
	
	//The components must be declared up here because they are used in renderPanel and the constructor.
	private JButton addButton = new JButton("Add Item");
	private JButton removeButton = new JButton("Remove Item");
	private JButton renameButton = new JButton("Rename Item");
	
	private ArrayList<ScheduleItem> itemList = new ArrayList<ScheduleItem>();
	private ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
	private ButtonGroup rbGroup = new ButtonGroup(); //ButtonGroup makes it so that only one radio button can be selected at a time
	
	private int buttonIndex = 0; //keeps track of which radioButton is selected
	
	public LineStartPanel(CenterPanel p1, LineEndPanel p2) {
		cPanel = p1;
		lePanel = p2;
		
		this.setPreferredSize(new Dimension(200, 350));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		addButton.setFocusable(false);
		addButton.addActionListener(this);
		removeButton.setFocusable(false);
		removeButton.addActionListener(this);
		renameButton.setFocusable(false);
		renameButton.addActionListener(this);
		
		renderPanel();
	}
	
	private void renderPanel() {
		this.removeAll();
		
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(scrollPanel);
		scrollPane.setBorder(new EmptyBorder(0, 20, 0, 0));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.setMaximumSize(new Dimension(120, 75));
		
		if (itemList.size() == 0) {
			removeButton.setEnabled(false);
			renameButton.setEnabled(false);
		} else {
			removeButton.setEnabled(true);
			renameButton.setEnabled(true);
		}
		
		for (JRadioButton rb : rbList) {
			rb.setFocusable(false);
			rb.addActionListener(this);
			rbGroup.add(rb);
			scrollPanel.add(rb);
		}
		
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(renameButton);
		
		this.add(new JLabel("<html>Items on your schedule</html>")); //html tags wrap the text
		this.add(scrollPane);
		this.add(buttonPanel);
		
		//These two methods display the updates to the components
		this.repaint();
		this.revalidate();
	}
	
	//read the file and set itemList to its contents
	public void windowOpened() {
		Scanner dataReader = null;
		try {
			dataReader = new Scanner(new File("Data.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (dataReader.hasNext()) {
			String name = dataReader.nextLine();
			int numOfEvents = dataReader.nextInt();
			dataReader.nextLine();
			ArrayList<Event> eventList = new ArrayList<Event>();
			
			for (int i = 0; i < numOfEvents; i++) {
				Event e = new Event(dataReader.nextLine());
				e.getStartTime().setDay(dataReader.nextInt());
				dataReader.nextLine();
				e.getStartTime().setHour(dataReader.nextInt());
				dataReader.nextLine();
				e.getStartTime().setMinute(dataReader.nextInt());
				dataReader.nextLine();
				e.getEndTime().setDay(dataReader.nextInt());
				dataReader.nextLine();
				e.getEndTime().setHour(dataReader.nextInt());
				dataReader.nextLine();
				e.getEndTime().setMinute(dataReader.nextInt());
				dataReader.nextLine();
				
				eventList.add(e);
			}
			
			itemList.add(new ScheduleItem(name, eventList));
			rbList.add(new JRadioButton("<html>" + name + "</html>"));
		}
		
		renderPanel();
		
		dataReader.close();
	}
	
	//set the file to the contents of itemList
	public void windowClosing() {
		PrintWriter dataWriter = null;
		try {
			dataWriter = new PrintWriter(new File("Data.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (ScheduleItem item : itemList) {
			dataWriter.println(item.getName());
			dataWriter.println(item.getEventList().size());
			
			for (Event e : item.getEventList()) {
				dataWriter.println(e.getName());
				
				dataWriter.println(e.getStartTime().getDay());
				dataWriter.println(e.getStartTime().getHour());
				dataWriter.println(e.getStartTime().getMinute());	
				dataWriter.println(e.getEndTime().getDay());
				dataWriter.println(e.getEndTime().getHour());
				dataWriter.println(e.getEndTime().getMinute());
			}
		}
		
		dataWriter.close();
	}
	
	public ArrayList<ScheduleItem> getItemList() {
		return itemList;
	}
	
	public void actionPerformed(ActionEvent e) {
		//Executed when addButton is pressed
		if (e.getSource() == addButton) {
			String name = (String)JOptionPane.showInputDialog(null, "Name of the new item:", "", JOptionPane.QUESTION_MESSAGE);
			//abort if cancel is selected
			if (name != null) {
				boolean isDuplicate = false;
				for (ScheduleItem i : itemList) {
					if (i.getName().equals(name)) {
						isDuplicate = true;
					}
				}

				//Checks if the input is empty or all whitespace
				if (name.trim().length() < 1) {
					JOptionPane.showMessageDialog(null, "You must give the item a name.", "", JOptionPane.ERROR_MESSAGE);
				} else if (isDuplicate) {
					JOptionPane.showMessageDialog(null, "Items can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					itemList.add(new ScheduleItem(name));
					rbList.add(new JRadioButton("<html>" + name + "</html>"));
					renderPanel();
				}
			}
		
		//Executed when removeButton is pressed
		} else if (e.getSource() == removeButton) {
			String[] itemNameArray = new String[itemList.size()];
			for (int i = 0; i < itemList.size(); i++) {
				itemNameArray[i] = itemList.get(i).getName();
			}
			
			String name = (String)JOptionPane.showInputDialog(
				null, "Item to remove:", "", JOptionPane.QUESTION_MESSAGE, null, itemNameArray, itemNameArray[0]
			);
			//abort if cancel is selected
			if (name != null) {
				int nameIndex = 0;
				for (int i = 0; i < itemList.size(); i++) {
					if (itemList.get(i).getName().equals(name)) {
						nameIndex = i;
					}
				}

				//If the deleted button is the selected one, clear the center panel
				if (nameIndex == buttonIndex) {
					cPanel.renderPanel();
				}

				itemList.remove(nameIndex);
				rbList.remove(nameIndex);
				renderPanel();
			}
		
		//Executed when renameButton is pressed
		} else if (e.getSource() == renameButton) {
			String[] itemNameArray = new String[itemList.size()];
			for (int i = 0; i < itemList.size(); i++) {
				itemNameArray[i] = itemList.get(i).getName();
			}
			
			String oldName = (String)JOptionPane.showInputDialog(
					null, "Item to rename", "", JOptionPane.QUESTION_MESSAGE, null, itemNameArray, itemNameArray[0]
			);
			//abort if cancel is selected
			if (oldName != null) {
				String newName = (String)JOptionPane.showInputDialog(null, "New name of this item:", "", JOptionPane.QUESTION_MESSAGE);
				//abort if cancel is selected
				if (newName != null) {
					boolean isDuplicate = false;
					int oldNameIndex = 0;
					for (int i = 0; i < itemList.size(); i++) {
						if (itemList.get(i).getName().equals(newName)) {
							isDuplicate = true;
						}
						if (itemList.get(i).getName().equals(oldName)) {
							oldNameIndex = i;
						}
					}

					//Checks if the input is empty or all whitespace
					if (newName.trim().length() < 1) {
						JOptionPane.showMessageDialog(null, "You must give the item a name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (oldName.equals(newName)) {
						JOptionPane.showMessageDialog(null, "You must give the item a new name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (isDuplicate) {
						JOptionPane.showMessageDialog(null, "Items can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
					} else {
						itemList.get(oldNameIndex).setName(newName);
						rbList.get(oldNameIndex).setText(newName);
						renderPanel();
						cPanel.renderPanel(itemList.get(buttonIndex));
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
			
			//Pass the item object to the next panel
			cPanel.renderPanel(itemList.get(buttonIndex));
			lePanel.renderPanel();
		}
	}
}

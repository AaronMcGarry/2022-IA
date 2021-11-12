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

public class MainLineStartPanel extends JPanel implements ActionListener{
	private MainCenterPanel cPanel;
	private MainLineEndPanel lePanel;
	
	//The components must be declared up here because they are used in renderPanel and the constructor.
	JButton addButton = new JButton("Add Item");
	JButton removeButton = new JButton("Remove Item");
	JButton renameButton = new JButton("Rename Item");
	
	ArrayList<ScheduleItem> itemList = new ArrayList<ScheduleItem>();
	ArrayList<JRadioButton> rbList = new ArrayList<JRadioButton>();
	ButtonGroup rbGroup = new ButtonGroup(); //ButtonGroup makes it so that only one radio button can be selected at a time
	
	int buttonIndex = 0; //keeps track of which radioButton is selected
	
	public MainLineStartPanel(MainCenterPanel p1, MainLineEndPanel p2) {
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
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
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
		
		this.add(new JLabel("<html>Items on your schedule</html>")); //html tags wrap the text
		for (JRadioButton rb : rbList) {
			rb.setAlignmentX(Component.CENTER_ALIGNMENT);
			rb.setFocusable(false);
			rb.addActionListener(this);
			rbGroup.add(rb);
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
	
	public void actionPerformed(ActionEvent e) {
		//Executed when addButton is pressed
		if (e.getSource() == addButton) {
			String name = (String)JOptionPane.showInputDialog(null, "Name of the new item:", "", JOptionPane.QUESTION_MESSAGE);
			if (name != null) { //abort if cancel is selected
				boolean isDuplicate = false;
				for (ScheduleItem i : itemList) {
					if (i.getName().equals(name)) {
						isDuplicate = true;
					}
				}

				//Two error messages to handle unwanted input
				if (name.trim().length() < 1) { //Checks if the input is empty or all whitespace
					JOptionPane.showMessageDialog(null, "You must give the item a name.", "", JOptionPane.ERROR_MESSAGE);
				} else if (isDuplicate) {
					JOptionPane.showMessageDialog(null, "Items can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					itemList.add(new ScheduleItem(name));
					rbList.add(new JRadioButton(name));
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
			if (name != null) { //abort if cancel is selected
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
			if (oldName != null) {
				String newName = (String)JOptionPane.showInputDialog(null, "New name of this item:", "", JOptionPane.QUESTION_MESSAGE);
				if (newName != null) {
					boolean isDuplicate = false;
					for (ScheduleItem i : itemList) {
						if (i.getName().equals(newName)) {
							isDuplicate = true;
						}
					}
					int oldNameIndex = 0;
					for (int i = 0; i < itemList.size(); i++) {
						if (itemList.get(i).getName().equals(oldName)) {
							oldNameIndex = i;
						}
					}

					//Three error messages to handle unwanted input
					if (newName.trim().length() < 1) { //Checks if the input is empty or all whitespace
						JOptionPane.showMessageDialog(null, "You must give the item a name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (oldName.equals(newName)) {
						JOptionPane.showMessageDialog(null, "You must give the item a new name.", "", JOptionPane.ERROR_MESSAGE);
					} else if (isDuplicate) {
						JOptionPane.showMessageDialog(null, "Items can't have the same name.", "", JOptionPane.ERROR_MESSAGE);
					} else {
						itemList.get(oldNameIndex).setName(newName);
						rbList.remove(oldNameIndex);
						rbList.add(oldNameIndex, new JRadioButton(newName));
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

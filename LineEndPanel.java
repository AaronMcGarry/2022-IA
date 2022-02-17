import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LineEndPanel extends JPanel implements FocusListener {
	private Event e;
	
	private JLabel title = new JLabel();
	private JTextField stdField = new JTextField(4);
	private JTextField sthField = new JTextField(4);
	private JTextField stmField = new JTextField(4);
	private JTextField etdField = new JTextField(4);
	private JTextField ethField = new JTextField(4);
	private JTextField etmField = new JTextField(4);
	
	public LineEndPanel() {
		this.setPreferredSize(new Dimension(200, 350));
		this.setLayout(new GridBagLayout());
		
		stdField.addFocusListener(this);
		sthField.addFocusListener(this);
		stmField.addFocusListener(this);
		etdField.addFocusListener(this);
		ethField.addFocusListener(this);
		etmField.addFocusListener(this);
	}
	
	public void renderPanel(Event event) {
		e = event; //Allows event to be passed to the focus methods
		
		this.removeAll();
		
		title.setText("<html>Start and end times of: " + e.getName() + "</html>");
		
		stdField.setText(String.valueOf(e.getStartTime().getDay()));
		sthField.setText(String.valueOf(e.getStartTime().getHour()));
		stmField.setText(String.valueOf(e.getStartTime().getMinute()));
		etdField.setText(String.valueOf(e.getEndTime().getDay()));
		ethField.setText(String.valueOf(e.getEndTime().getHour()));
		etmField.setText(String.valueOf(e.getEndTime().getMinute()));
		
		JLabel startLabel = new JLabel("Start");
		JLabel endLabel = new JLabel("End");
		JLabel stdLabel = new JLabel("Day");
		JLabel sthLabel = new JLabel("Hour");
		JLabel stmLabel = new JLabel("Minute");
		JLabel etdLabel = new JLabel("Day");
		JLabel ethLabel = new JLabel("Hour");
		JLabel etmLabel = new JLabel("Minute");
		
		//add components
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridx = 1;
		titleConstraints.gridy = 0;
		titleConstraints.insets = new Insets(0, 0, 10, 0);
		this.add(title, titleConstraints);
		
		GridBagConstraints startLabelConstraints = new GridBagConstraints();
		startLabelConstraints.gridx = 1;
		startLabelConstraints.gridy = 1;
		this.add(startLabel, startLabelConstraints);
		
		GridBagConstraints stdLabelConstraints = new GridBagConstraints();
		stdLabelConstraints.gridx = 0;
		stdLabelConstraints.gridy = 2;
		stdLabelConstraints.anchor = GridBagConstraints.LINE_START; //labels are aligned to the left
		this.add(stdLabel, stdLabelConstraints);
		
		GridBagConstraints stdFieldConstraints = new GridBagConstraints();
		stdFieldConstraints.gridx = 1;
		stdFieldConstraints.gridy = 2;
		this.add(stdField, stdFieldConstraints);
		
		GridBagConstraints sthLabelConstraints = new GridBagConstraints();
		sthLabelConstraints.gridx = 0;
		sthLabelConstraints.gridy = 3;
		sthLabelConstraints.anchor = GridBagConstraints.LINE_START;
		this.add(sthLabel, sthLabelConstraints);
		
		GridBagConstraints sthFieldConstraints = new GridBagConstraints();
		sthFieldConstraints.gridx = 1;
		sthFieldConstraints.gridy = 3;
		this.add(sthField, sthFieldConstraints);
		
		GridBagConstraints stmLabelConstraints = new GridBagConstraints();
		stmLabelConstraints.gridx = 0;
		stmLabelConstraints.gridy = 4;
		stmLabelConstraints.anchor = GridBagConstraints.LINE_START;
		this.add(stmLabel, stmLabelConstraints);
		
		GridBagConstraints stmFieldConstraints = new GridBagConstraints();
		stmFieldConstraints.gridx = 1;
		stmFieldConstraints.gridy = 4;
		stmFieldConstraints.insets = new Insets(0, 0, 5, 0);
		this.add(stmField, stmFieldConstraints);
		
		GridBagConstraints endLabelConstraints = new GridBagConstraints();
		endLabelConstraints.gridx = 1;
		endLabelConstraints.gridy = 5;
		this.add(endLabel, endLabelConstraints);
		
		GridBagConstraints etdLabelConstraints = new GridBagConstraints();
		etdLabelConstraints.gridx = 0;
		etdLabelConstraints.gridy = 6;
		etdLabelConstraints.anchor = GridBagConstraints.LINE_START;
		this.add(etdLabel, etdLabelConstraints);
		
		GridBagConstraints etdFieldConstraints = new GridBagConstraints();
		etdFieldConstraints.gridx = 1;
		etdFieldConstraints.gridy = 6;
		this.add(etdField, etdFieldConstraints);
		
		GridBagConstraints ethLabelConstraints = new GridBagConstraints();
		ethLabelConstraints.gridx = 0;
		ethLabelConstraints.gridy = 7;
		ethLabelConstraints.anchor = GridBagConstraints.LINE_START;
		this.add(ethLabel, ethLabelConstraints);
		
		GridBagConstraints ethFieldConstraints = new GridBagConstraints();
		ethFieldConstraints.gridx = 1;
		ethFieldConstraints.gridy = 7;
		this.add(ethField, ethFieldConstraints);
		
		GridBagConstraints etmLabelConstraints = new GridBagConstraints();
		etmLabelConstraints.gridx = 0;
		etmLabelConstraints.gridy = 8;
		etmLabelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(etmLabel, etmLabelConstraints);
		
		GridBagConstraints etmFieldConstraints = new GridBagConstraints();
		etmFieldConstraints.gridx = 1;
		etmFieldConstraints.gridy = 8;
		etmFieldConstraints.anchor = GridBagConstraints.PAGE_START;
		etmFieldConstraints.weighty = 1.0;
		this.add(etmField, etmFieldConstraints);
		
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
		//attempt to convert string to int
		try {
			flInt = Integer.parseInt(flField.getText());
		} catch (NumberFormatException nfe) {
			flField.setText("0");
		}
		
		//check if any text field is outside range
		if ((flField.equals(stdField) || flField.equals(etdField)) && flInt < 0) {
			flField.setText("0");
			JOptionPane.showMessageDialog(null, "Days must be greater than 0.", "", JOptionPane.ERROR_MESSAGE);
		}
		if ((flField.equals(sthField) || flField.equals(ethField)) && (flInt > 23 || flInt < 0)) {
			flField.setText("0");
			JOptionPane.showMessageDialog(null, "Hours must be within the range of 0-23.", "", JOptionPane.ERROR_MESSAGE);
		}
		if ((flField.equals(stmField) || flField.equals(etmField)) && (flInt > 59 || flInt < 0)) {
			flField.setText("0");
			JOptionPane.showMessageDialog(null, "Minutes must be within the range of 0-59.", "", JOptionPane.ERROR_MESSAGE);
		}
		
		//update the time of the event
		e.getStartTime().setDay(Integer.parseInt(stdField.getText()));
		e.getStartTime().setHour(Integer.parseInt(sthField.getText()));
		e.getStartTime().setMinute(Integer.parseInt(stmField.getText()));
		e.getEndTime().setDay(Integer.parseInt(etdField.getText()));
		e.getEndTime().setHour(Integer.parseInt(ethField.getText()));
		e.getEndTime().setMinute(Integer.parseInt(etmField.getText()));
    }
}

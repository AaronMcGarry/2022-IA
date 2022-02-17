import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Frame extends JFrame implements WindowListener {
	private LineEndPanel lePanel = new LineEndPanel();
	private CenterPanel cPanel = new CenterPanel(lePanel);
	private LineStartPanel lsPanel = new LineStartPanel(cPanel, lePanel); 
	private PageEndPanel pePanel = new PageEndPanel(lsPanel);
	
	public Frame() {
		//"this" refers to the main JFrame of the project
		this.add(lsPanel, BorderLayout.LINE_START);
		this.add(cPanel, BorderLayout.CENTER);
		this.add(lePanel, BorderLayout.LINE_END);
		this.add(pePanel, BorderLayout.PAGE_END);
		this.pack();
		
		addWindowListener(this);
		this.setVisible(true);
		this.setTitle("Scheduler");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);  //Centers window on screen
	}

	@Override
	public void windowOpened(WindowEvent e) {
		lsPanel.windowOpened();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		lsPanel.windowClosing();
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
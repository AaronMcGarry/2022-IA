import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		MainLineEndPanel lePanel = new MainLineEndPanel();
		MainCenterPanel cPanel = new MainCenterPanel(lePanel);
		MainLineStartPanel lsPanel = new MainLineStartPanel(cPanel, lePanel); 
		MainPageEndPanel pePanel = new MainPageEndPanel();
		
		//"this" refers to the main JFrame of the project
		this.add(lsPanel, BorderLayout.LINE_START);
		this.add(cPanel, BorderLayout.CENTER);
		this.add(lePanel, BorderLayout.LINE_END);
		this.add(pePanel, BorderLayout.PAGE_END);
		this.pack();
		
		this.setVisible(true);
		this.setTitle("Scheduler");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);  //Centers window on screen
	}
}
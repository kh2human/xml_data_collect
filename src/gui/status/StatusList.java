package gui.status;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusList  {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel runningStatus = new JLabel("NONE");
	private final JLabel dbConnection = new JLabel("LOADING...");
	private final JLabel xmlConnection = new JLabel("0");
	
	public StatusList() {
		JPanel left = makeLeftPanel();
		JPanel right = makeRightPanel();
		panel.add(left, BorderLayout.WEST);
		panel.add(right, BorderLayout.CENTER);
	}

	private JPanel makeRightPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(3, 1));
		panel.add(runningStatus);
		panel.add(dbConnection);
		panel.add(xmlConnection);
		return panel;
	}
	
	private JPanel makeLeftPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(3, 1));
		final JLabel running = new JLabel("* DATA: ");
		final JLabel dbConnect = new JLabel("* XML LOAD1: ");
		final JLabel xmlConnect = new JLabel("* XML doc: ");
		panel.add(running);
		panel.add(dbConnect);
		panel.add(xmlConnect);
		return panel;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JLabel getRunningStatus() {
		return runningStatus;
	}

	public JLabel getDbConnection() {
		return dbConnection;
	}

	public JLabel getXmlConnection() {
		return xmlConnection;
	}
}

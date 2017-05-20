package gui.db;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class DbNamePanel {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel delimiter = new JLabel("/");
	private final JTextField dbNameField = new JTextField();
	private int dbNameFieldOldLength = 1;
	
	public DbNamePanel() {
		panel.add(delimiter, BorderLayout.WEST);
		panel.add(dbNameField, BorderLayout.CENTER);
	}
	
	public void setDbNameFieldOldLength(int dbNameFieldOldLength) {
		this.dbNameFieldOldLength = dbNameFieldOldLength;
	}
	
	public int getDbNameFieldOldLength() {
		return dbNameFieldOldLength;
	}
	
	public JLabel getDelimiter() {
		return delimiter;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JTextField getDbNameField() {
		return dbNameField;
	}
}

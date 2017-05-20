package gui.db;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JPanel;

public class PortPanel {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JTextField portField = new JTextField(3);
	
	public PortPanel() {
		panel.add(new javax.swing.JLabel(":"), BorderLayout.WEST);
		panel.add(portField, BorderLayout.CENTER);
	}
	
	public JTextField getPortField() {
		return portField;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}

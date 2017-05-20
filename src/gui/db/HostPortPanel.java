package gui.db;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JPanel;

public class HostPortPanel {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JTextField hostField = new JTextField();
	private final PortPanel portPanel = new PortPanel();
	private int hostFieldOldLength = 1;
	
	public HostPortPanel() {
		panel.add(hostField, BorderLayout.CENTER);
		panel.add(portPanel.getPanel(), BorderLayout.EAST);
		/*hostField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				hostField.setColumns(hostField.getText().length());
				hostField.revalidate();
				hostField.repaint();
			}
		});*/
	}
	
	public void setHostFieldOldLength(int hostFieldOldLength) {
		this.hostFieldOldLength = hostFieldOldLength;
	}
	
	public int getHostFieldOldLength() {
		return hostFieldOldLength;
	}
	
	public PortPanel getPortPanel() {
		return portPanel;
	}
	
	public JTextField getHostField() {
		return hostField;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}

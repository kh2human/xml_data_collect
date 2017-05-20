package gui.db;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
//import javax.swing.JTextField;

public class UrlPanel {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel urlCommonLabel = new JLabel("jdbc:");
	private final HostPortPanel hostPortPanel = new HostPortPanel();
	private final DbNamePanel dbNamePanel = new DbNamePanel();
//	private final JTextField urlText = new JTextField();
	
	public UrlPanel() {
//		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(urlCommonLabel, BorderLayout.WEST);
		panel.add(hostPortPanel.getPanel(), BorderLayout.CENTER);
		panel.add(dbNamePanel.getPanel(), BorderLayout.EAST);
//		panel.add(urlText, BorderLayout.CENTER);
	}
	
	public DbNamePanel getDbNamePanel() {
		return dbNamePanel;
	}
	
	public HostPortPanel getHostPortPanel() {
		return hostPortPanel;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JLabel getUrlCommonLabel() {
		return urlCommonLabel;
	}
	
	/*public JTextField getUrlText() {
		return urlText;
	}*/
}

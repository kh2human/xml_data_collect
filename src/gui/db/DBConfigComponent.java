package gui.db;

import config.DBConfig;







//import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JPanel;

import tester.ConnectResultDialog;
import tester.DBConnectTester;
import db.DBDriver;

public class DBConfigComponent  {

	private final JComponent component = new JPanel(new BorderLayout());
	private final JComboBox<DBDriver> dbList = new JComboBox<DBDriver>(DBDriver.values());
	private final UrlPanel urlPanel = new UrlPanel();
//	private final JTextField urlText = new JTextField();
	private final JTextField idField = new JTextField();
	private final JPasswordField pwField = new JPasswordField();
	private final JButton urlTestButton = new JButton("Test");
	
	public DBConfigComponent() {
		component.add(makeDisplayPanel(), BorderLayout.WEST);
		component.add(makeInputPanel(), BorderLayout.CENTER);
		component.add(makeButtonPanel(), BorderLayout.SOUTH);
		dbList.addActionListener(new java.awt.event.ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				Object fired = e.getSource();
				if (fired instanceof JComboBox) {
					JComboBox<?> cb = (JComboBox<Object>)fired;
					Object content = cb.getItemAt(cb.getSelectedIndex());
					if (content instanceof DBDriver) {
						String dbType = content.toString();
						urlPanel.getUrlCommonLabel().setText(DBDriver.getUrlCommon(dbType));
						String format = DBDriver.getUrlFormat(dbType);
						String delimiter = format.substring(format.indexOf("DBNAME") - 1).replaceFirst("DBNAME", "");
						urlPanel.getDbNamePanel().getDelimiter().setText(delimiter);
					}
				} 
			}
		});
		urlTestButton.setActionCommand("DBTest");
		urlTestButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean connected = false;
				if (e.getActionCommand().equals("DBTest")) {
					DBDriver dbType = dbList.getItemAt(dbList.getSelectedIndex());
					DBConfig dbConfig = new DBConfig();
					dbConfig.setDbType(dbType.toString());
					dbConfig.setHostName(urlPanel.getHostPortPanel().getHostField().getText());
					dbConfig.setPort(Integer.parseInt(urlPanel.getHostPortPanel().getPortPanel().getPortField().getText()));
					dbConfig.setDbName(urlPanel.getDbNamePanel().getDbNameField().getText());
					dbConfig.setId(idField.getText());
					dbConfig.setPw(String.valueOf(pwField.getPassword()));
					connected = DBConnectTester.test(dbConfig);
//					connected = DBConnectTester.test(dbType.toString(), urlPanel.getUrlText().getText(), idText.getText(), pwText.getText());
				}
				new ConnectResultDialog(connected);
			}
		});
	}
	
	private JPanel makeButtonPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(urlTestButton, BorderLayout.EAST);
		return panel;
	}
	
	private JPanel makeInputPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(dbList);
		panel.add(urlPanel.getPanel());
		panel.add(idField);
		panel.add(pwField);
		return panel;
	}
	
	private JPanel makeDisplayPanel() {
		final JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(new JLabel("* DB Type: "));
		panel.add(new JLabel("* URL: "));
		panel.add(new JLabel("* ID: "));
		panel.add(new JLabel("* PW: "));
		return panel;
	}
	
	public JComponent getComponent() {
		return component;
	}
	
	public JComboBox<DBDriver> getDbList() {
		return dbList;
	}

	public UrlPanel getUrlPanel() {
		return urlPanel;
	}
	/*public JTextField getUrlText() {
		return urlText;
	}*/

	public JTextField getIdField() {
		return idField;
	}

	public JTextField getPwField() {
		return pwField;
	}

	public JButton getUrlTestButton() {
		return urlTestButton;
	}
}

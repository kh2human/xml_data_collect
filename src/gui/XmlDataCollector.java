package gui;

import java.util.Map;
import config.DBConfig;
import config.XMLConfig;
import core.LoopTransfer;
import gui.db.DBConfigComponent;
import gui.status.ControlPanel;
import gui.status.StatusComponent;
import gui.status.StatusList;
import gui.xml.XMLConfigComponent;
//import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.SystemTray;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import db.DBDriver;

public class XmlDataCollector implements java.awt.event.ActionListener {

	private final JFrame frame = new JFrame("rwisKEPCO for TEST(java)");
	private final StatusComponent statusComponent = new StatusComponent();
	private final DBConfigComponent dbConfigComponent = new DBConfigComponent();
	private final XMLConfigComponent xmlConfigComponent = new XMLConfigComponent();
	private final CheckPanel checkPanel = new CheckPanel();
	private core.LoopTransfer loopWork = null;
	private core.XMLCollector xmlLoop1 = null;
	private core.XMLCollector xmlLoop2 = null;
	private xml.DocumentBus docBus = null;
	
	public XmlDataCollector() {
		docBus = new xml.DocumentBus();
		
		xmlLoop1 = new core.XMLCollector(0);
		xmlLoop1.execute();
		xmlLoop2 = new core.XMLCollector(-1);
		xmlLoop2.execute();
		
		final JPanel panel = new JPanel(new BorderLayout());
		final JTabbedPane tabbedPane = new JTabbedPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		statusComponent.getControlPanel().getStart().addActionListener(this);
		statusComponent.getControlPanel().getStop().addActionListener(this);
		JTextField dbNameField = dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameField();
		dbNameField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int oldLength = dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameFieldOldLength();
				int newLength = dbNameField.getText().length();
				int oldColumns = dbNameField.getColumns();
				dbNameField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
				dbConfigComponent.getUrlPanel().getDbNamePanel().setDbNameFieldOldLength(newLength);
				redraw();
			}
		});
		JTextField hostField = dbConfigComponent.getUrlPanel().getHostPortPanel().getHostField();
		hostField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int oldLength = dbConfigComponent.getUrlPanel().getHostPortPanel().getHostFieldOldLength();
				int newLength = hostField.getText().length();
				int oldColumns = hostField.getColumns();
				hostField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
				dbConfigComponent.getUrlPanel().getHostPortPanel().setHostFieldOldLength(newLength);
				redraw();
			}
		});
		JTextField urlField = xmlConfigComponent.getUrlField(); 
		urlField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent e) {
				int oldLength = xmlConfigComponent.getUrlFieldOldLength();
				int newLength = urlField.getText().length();
				int oldColumns = urlField.getColumns();
				urlField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
				xmlConfigComponent.setUrlFieldOldLength(newLength);
				redraw();
			}
		});
		tabbedPane.addTab("Status", statusComponent.getComponent());
		tabbedPane.addTab("DB Config", dbConfigComponent.getComponent());
		tabbedPane.addTab("XML Config", xmlConfigComponent.getComponent());
		panel.add(tabbedPane, BorderLayout.CENTER);
		final JButton ok = checkPanel.getOk();
		final JButton cancel = checkPanel.getCancel();
		final JButton apply = checkPanel.getApply();
		ok.addActionListener(this);
		ok.setActionCommand("OK");
		cancel.addActionListener(this);
		cancel.setActionCommand("Cancel");
		apply.addActionListener(this);
		apply.setActionCommand("Apply");
		panel.add(checkPanel.getPanel(), BorderLayout.SOUTH);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		Map<String, String> settings = file.FileLoader.parseFile("./resources/XMLCollector.ini");
		String[] keys = settings.keySet().toArray(new String[0]);
		for (int j = 0; j < settings.size(); j++) {
			String value = settings.get(keys[j]);
			//System.out.println(keys[i] + ", " + settings.get(keys[i]));
			switch (keys[j]) {
			case "DBType":
				JComboBox<DBDriver> dbList = dbConfigComponent.getDbList();
				for (int i = 0; i < DBDriver.values().length; i++) {
					if (value.equals(dbList.getItemAt(i).toString())) {
						dbList.setSelectedIndex(i);
						break;
					}
				}
				break;
			case "HostName":
				hostField.setText(value);
//				oldLength = dbConfigComponent.getUrlPanel().getHostPortPanel().getHostFieldOldLength();
//				newLength = hostField.getText().length();
//				oldColumns = hostField.getColumns();
//				hostField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//				dbConfigComponent.getUrlPanel().getHostPortPanel().setHostFieldOldLength(newLength);
				redraw();
				break;
			case "Port":
				dbConfigComponent.getUrlPanel().getHostPortPanel().getPortPanel().getPortField().setText(value);
				break;
			case "DBName":
				dbNameField.setText(value);
//				oldLength = dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameFieldOldLength();
//				newLength = dbNameField.getText().length();
//				oldColumns = dbNameField.getColumns();
//				dbNameField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//				dbConfigComponent.getUrlPanel().getDbNamePanel().setDbNameFieldOldLength(newLength);
				redraw();
				break;	
			case "ID":
				dbConfigComponent.getIdField().setText(value);
				break;
			case "PW":
				dbConfigComponent.getPwField().setText(value);
				break;
			case "XMLURL":
				JTextField xmlField = xmlConfigComponent.getUrlField(); 
				xmlField.setText(value);
//				oldLength = xmlConfigComponent.getUrlFieldOldLength();
//				newLength = xmlField.getText().length();
//				oldColumns = xmlField.getColumns();
//				xmlField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//				xmlConfigComponent.setUrlFieldOldLength(newLength);
				redraw();
				break;
			case "Interval":
				String[] time_unit = value.split(" ");
				String unit = time_unit[1];
				JComboBox<Integer> rangeBox = xmlConfigComponent.getRange();
				for (int i = 0; i < rangeBox.getItemCount(); i++) {
					if (unit.equals(rangeBox.getItemAt(i))) {
						rangeBox.setSelectedIndex(i);
						break;
					}
				}
				int range = Integer.valueOf(time_unit[0]);
				switch (unit) {
				case "sec":
				case "min":
					for (int i = 1; i <= 59; i++) {
						rangeBox.addItem(i);
					}
					break;
				case "hr":
					for (int i = 1; i <= 24; i++) {
						rangeBox.addItem(i);
					}
					default:
				}
				rangeBox.setSelectedIndex(range - 1);
				default:
			}
		}
//		Charset charset = Charset.forName("UTF-8");
//		java.nio.file.Path setting = java.nio.file.Paths.get("./resources", "XMLCollector.ini");
//		if (Files.notExists(setting))
//			return;
//		try {
//			java.io.BufferedReader reader = Files.newBufferedReader(setting, charset);
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				line = line.trim();
//				int colonIndex = line.indexOf(":");
//				String key = line.substring(0, colonIndex).trim();
//				String value = line.substring(colonIndex + 1).trim();
//				int oldLength = 0;
//				int newLength = 0;
//				int oldColumns = 0;
//				switch (key) {
//				case "DBType":
//					JComboBox<DBDriver> dbList = dbConfigComponent.getDbList();
//					for (int i = 0; i < DBDriver.values().length; i++) {
//						if (value.equals(dbList.getItemAt(i).toString())) {
//							dbList.setSelectedIndex(i);
//							break;
//						}
//					}
//					break;
//				case "HostName":
//					hostField.setText(value);
//					oldLength = dbConfigComponent.getUrlPanel().getHostPortPanel().getHostFieldOldLength();
//					newLength = hostField.getText().length();
//					oldColumns = hostField.getColumns();
//					hostField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//					dbConfigComponent.getUrlPanel().getHostPortPanel().setHostFieldOldLength(newLength);
//					redraw();
//					break;
//				case "Port":
//					dbConfigComponent.getUrlPanel().getHostPortPanel().getPortPanel().getPortField().setText(value);
//					break;
//				case "DBName":
//					dbNameField.setText(value);
//					oldLength = dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameFieldOldLength();
//					newLength = dbNameField.getText().length();
//					oldColumns = dbNameField.getColumns();
//					dbNameField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//					dbConfigComponent.getUrlPanel().getDbNamePanel().setDbNameFieldOldLength(newLength);
//					redraw();
//					break;	
//				case "ID":
//					dbConfigComponent.getIdField().setText(value);
//					break;
//				case "PW":
//					dbConfigComponent.getPwField().setText(value);
//					break;
//				case "XMLURL":
//					JTextField xmlField = xmlConfigComponent.getUrlField(); 
//					xmlField.setText(value);
//					oldLength = xmlConfigComponent.getUrlFieldOldLength();
//					newLength = xmlField.getText().length();
//					oldColumns = xmlField.getColumns();
//					xmlField.setColumns(new Double(Math.ceil((double)newLength/oldLength * oldColumns)).intValue());
//					xmlConfigComponent.setUrlFieldOldLength(newLength);
//					redraw();
//					break;
//				case "Interval":
//					String[] time_unit = value.split(" ");
//					String unit = time_unit[1];
//					JComboBox<Integer> rangeBox = xmlConfigComponent.getRange();
//					for (int i = 0; i < rangeBox.getItemCount(); i++) {
//						if (unit.equals(rangeBox.getItemAt(i))) {
//							rangeBox.setSelectedIndex(i);
//							break;
//						}
//					}
//					int range = Integer.valueOf(time_unit[0]);
//					switch (unit) {
//					case "sec":
//					case "min":
//						for (int i = 1; i <= 59; i++) {
//							rangeBox.addItem(i);
//						}
//						break;
//					case "hr":
//						for (int i = 1; i <= 24; i++) {
//							rangeBox.addItem(i);
//						}
//						default:
//					}
//					rangeBox.setSelectedIndex(range - 1);
//					default:
//				}
//			}
//		} catch (IOException ex) {
//			System.out.println(ex.getMessage());
//		}
	}
	
	private void redraw() {
		frame.pack();
		frame.revalidate();
		frame.repaint();
	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) {
		Object src = e.getSource();
		if (src instanceof javax.swing.JButton) {
			JButton b = (JButton)src;
			String cmd = b.getActionCommand();
			JComboBox<DBDriver> dbList = dbConfigComponent.getDbList();
			StatusList statusList = statusComponent.getStatusList();
			JComboBox<Integer> range = xmlConfigComponent.getRange();
			JComboBox<String> unit = xmlConfigComponent.getUnit();
			ControlPanel controlPanel = statusComponent.getControlPanel();
			switch (cmd) {
			case "LoopStart":
				DBConfig dbConfig = new DBConfig();
				dbConfig.setDbType(dbList.getItemAt(dbList.getSelectedIndex()).toString());
				dbConfig.setHostName(dbConfigComponent.getUrlPanel().getHostPortPanel().getHostField().getText());
				dbConfig.setPort(Integer.parseInt(dbConfigComponent.getUrlPanel().getHostPortPanel().getPortPanel().getPortField().getText()));
				dbConfig.setDbName(dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameField().getText());
				dbConfig.setId(dbConfigComponent.getIdField().getText());
				dbConfig.setPw(dbConfigComponent.getPwField().getText());
				XMLConfig xmlConfig = new XMLConfig();
				xmlConfig.setUrl(xmlConfigComponent.getUrlField().getText());
				JComboBox<Integer> timer = xmlConfigComponent.getRange();
				xmlConfig.setTimer(timer.getItemAt(timer.getSelectedIndex()));
				xmlConfig.setUnit(unit.getItemAt(unit.getSelectedIndex()));
				
				loopWork = new LoopTransfer(dbConfig, xmlConfig, statusList, controlPanel);
				loopWork.setDocBus(docBus);
				loopWork.execute();
				
				xmlLoop1.setStatus(statusList.getXmlConnection());
				xmlLoop1.setFileName("./parsed.xml");
				xmlLoop1.setDocBus(docBus);
				
				xmlLoop2.setStatus(statusList.getXmlConnection());
				xmlLoop2.setFileName("./parsed1.xml");
				xmlLoop2.setDocBus(docBus);
				break;
			case "LoopStop":
				if (loopWork != null) {
					loopWork.cancel(true);
					loopWork.setLoop(false);
				}
				break;
			case "Cancel":
				frame.setVisible(false);
				break;
			case "OK":
			case "Apply":
				Charset charset = Charset.forName("UTF-8");
				java.nio.file.Path setting = java.nio.file.Paths.get("./resources", "XMLCollector.ini");
				String s = "";
				try {
					if (Files.notExists(setting.getParent()))
						Files.createDirectories(setting.getParent());
					if (Files.notExists(setting))
						Files.createFile(setting);
					java.io.BufferedWriter writer = Files.newBufferedWriter(setting, charset);
					s = "DBType: " + dbList.getItemAt(dbList.getSelectedIndex());
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "HostName: " + dbConfigComponent.getUrlPanel().getHostPortPanel().getHostField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "Port: " + dbConfigComponent.getUrlPanel().getHostPortPanel().getPortPanel().getPortField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "DBName: " + dbConfigComponent.getUrlPanel().getDbNamePanel().getDbNameField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "ID: " + dbConfigComponent.getIdField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "PW: " + dbConfigComponent.getPwField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "XMLURL: " + xmlConfigComponent.getUrlField().getText();
					writer.write(s, 0, s.length());
					writer.newLine();
					s = "Interval: " + range.getItemAt(range.getSelectedIndex()) + " " + unit.getItemAt(unit.getSelectedIndex());
					writer.write(s, 0, s.length());
					writer.newLine();
					writer.close();
				} catch (java.io.IOException ex) {
					System.out.println(ex.getMessage());
				}
				JDialog savedDialog = new javax.swing.JDialog();
				savedDialog.setModal(true);
				savedDialog.setTitle("Save");
				JLabel saveOK = new JLabel("Setting file saved");
				savedDialog.setLayout(new BorderLayout());
				savedDialog.add(saveOK, BorderLayout.CENTER);
				final JPanel south = new JPanel(new BorderLayout());
				savedDialog.add(south, BorderLayout.SOUTH);
				final JButton close = new JButton("Close");
				south.add(close, BorderLayout.EAST);
				close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						savedDialog.setVisible(false);
					}
				});
				savedDialog.pack();
				savedDialog.setResizable(false);
				if (cmd.equals("Apply"))
					savedDialog.setVisible(true);
				if (cmd.equals("OK"))
					frame.setVisible(false);
				break;
				default:
			}
		}
	}
	
	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}
	
	private static java.awt.Image createImage(String path, String description) {
		java.nio.file.Path image = java.nio.file.Paths.get(path);
		java.net.URL imageURL = null;
		try {
			imageURL = image.toUri().toURL();
		} catch (java.net.MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		if (imageURL == null) {
			System.out.println("Resource not found: " + path);
			return null;
		} else {
			return new javax.swing.ImageIcon(imageURL, description).getImage();
		}
	}
	
	private static void createAndShowGUI() {
		final TrayIcon trayIcon = new TrayIcon(createImage("./resources/bulb.gif", "tray icon"));
		final SystemTray tray = SystemTray.getSystemTray();
		final PopupMenu popup = new PopupMenu();
		final MenuItem open = new MenuItem("Open");
		final MenuItem exit = new MenuItem("Exit");
		XmlDataCollector launcher = new XmlDataCollector();
		popup.add(open);
		popup.addSeparator();
		popup.add(exit);
		trayIcon.setPopupMenu(popup);
		try {
			tray.add(trayIcon);
		} catch (java.awt.AWTException e) {
			System.out.println(e.getMessage());
		}
		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launcher.getFrame().setVisible(true);
			}
		});
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launcher.getFrame().setVisible(true);
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			
		} catch (IllegalAccessException e) {
			
		} catch (InstantiationException e) {
			
		} catch (ClassNotFoundException e) {
			
		}
		
		if (!SystemTray.isSupported()) {
			System.out.println("Tray not supported");
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	public JFrame getFrame() {
		return frame;
	}

}

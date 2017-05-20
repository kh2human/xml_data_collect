package gui.xml;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tester.ConnectResultDialog;
import tester.XMLConnectTester;

public class XMLConfigComponent implements java.awt.event.ActionListener {

	private final JComponent component = new JPanel(new BorderLayout());
	private final JComboBox<Integer> range = new JComboBox<Integer>();
	private final JComboBox<String> unit = new JComboBox<String>(new String[]{"sec", "min", "hr"});
	private final JTextField urlField = new JTextField();
	private final JButton xmlTestButton = new JButton("Test");
	private int urlFieldOldLength = 1;

	public XMLConfigComponent() {
		component.setLayout(new BorderLayout());
		JPanel west = new JPanel(false);
		west.setLayout(new GridLayout(2, 1));
		final JLabel url = new JLabel("* URL: ");
		final JLabel interval = new JLabel("* Interval: ");
		west.add(url);
		west.add(interval);
		JPanel center = new JPanel(false);
		center.setLayout(new GridLayout(2, 1));
		center.add(urlField);
		JPanel inner = new JPanel(false);
		inner.setLayout(new GridLayout(1, 2));
		inner.add(range);
		inner.add(unit);
		unit.addActionListener(this);
		center.add(inner);
		JPanel south = new JPanel(false);
		south.setLayout(new BorderLayout());
		south.add(xmlTestButton, BorderLayout.EAST);
		xmlTestButton.addActionListener(this);
		xmlTestButton.setActionCommand("XMLTest");
		component.add(west, BorderLayout.WEST);
		component.add(center, BorderLayout.CENTER);
		component.add(south, BorderLayout.SOUTH);
//		urlField.addActionListener(new java.awt.event.ActionListener() {
		/*urlField.addKeyListener(new java.awt.event.KeyAdapter() {
//			public void actionPerformed(java.awt.event.ActionEvent e) {
			public void keyTyped(java.awt.event.KeyEvent e) {
				urlField.setColumns(urlField.getText().length() + 1);
				center.revalidate();
				center.repaint();
//				new tester.ConnectResultDialog(true);
			}
		});*/
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(java.awt.event.ActionEvent e) {
		Object fired = e.getSource();
		if (fired instanceof JComboBox) {
			JComboBox<?> cb = (JComboBox<Object>)fired;
			Object content = cb.getItemAt(cb.getSelectedIndex());
			if (content instanceof String) {
				String unit = String.valueOf(content);
				range.removeAllItems();
				switch (unit) {
				case "sec":
				case "min":
					for (int i = 1; i <= 59; i++) {
						range.addItem(new Integer(i));
					}
					break;
				case "hr":
					for (int i = 1; i <= 24; i++) {
						range.addItem(new Integer(i));
					}
					break;
					default:
				}
			}
		} else if (fired instanceof JButton) {
			if (e.getActionCommand().equals("XMLTest")) {
//				boolean connected = XMLConnectTester.test(xmlUrl.getText());
//				boolean connected = new XMLConnectTester(xmlUrl.getText()).test();
				new ConnectResultDialog(XMLConnectTester.test(urlField.getText()));
			}
		}
	}
	
	public void setUrlFieldOldLength(int urlFieldOldLength) {
		this.urlFieldOldLength = urlFieldOldLength;
	}
	
	public int getUrlFieldOldLength() {
		return urlFieldOldLength;
	}
	
	public JComboBox<String> getUnit() {
		return unit;
	}
	
	public JComboBox<Integer> getRange() {
		return range;
	}
	
	public JTextField getUrlField() {
		return urlField;
	}
	
	public JComponent getComponent() {
		return component;
	}
}

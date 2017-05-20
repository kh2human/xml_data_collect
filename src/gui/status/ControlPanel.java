package gui.status;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel implements java.awt.event.ActionListener {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JButton start = new JButton("Start");
	private final JButton stop = new JButton("Stop");
	
	public ControlPanel() {
		final JPanel buttons = new JPanel(false);
		buttons.setLayout(new java.awt.GridLayout(1, 2));
		buttons.add(start);
		buttons.add(stop);
		stop.setEnabled(false);
		start.addActionListener(this);
		stop.addActionListener(this);
		start.setActionCommand("LoopStart");
		stop.setActionCommand("LoopStop");
		panel.add(buttons, BorderLayout.EAST);
	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "LoopStart":
			stop.setEnabled(true);
			start.setEnabled(false);
			break;
		case "LoopStop":
			stop.setEnabled(false);
			start.setEnabled(true);
			break;
			default:
		}
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JButton getStart() {
		return start;
	}
	
	public JButton getStop() {
		return stop;
	}
}

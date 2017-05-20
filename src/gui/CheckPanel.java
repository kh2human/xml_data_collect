package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CheckPanel {

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JButton ok = new JButton("OK");
	private final JButton cancel = new JButton("Cancel");
	private final JButton apply = new JButton("Apply");
	private final JButton exit = new JButton("Exit");
	
	public CheckPanel() {
		final JPanel grid = new JPanel(new java.awt.GridLayout(1, 4));
		grid.add(exit);
		grid.add(ok);
		grid.add(cancel);
		grid.add(apply);
		panel.add(grid, BorderLayout.EAST);
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JButton getOk() {
		return ok;
	}
	public JButton getCancel() {
		return cancel;
	}
	public JButton getApply() {
		return apply;
	}
}

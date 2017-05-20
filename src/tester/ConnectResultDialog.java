package tester;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;

public class ConnectResultDialog {

	public ConnectResultDialog(boolean connected) {
		final JDialog dialog = new JDialog();
		final JLabel result = new JLabel("Connection " + (connected? "OK": "failed"));
		dialog.setModal(true);
		dialog.setTitle("Result");
		dialog.setLayout(new java.awt.BorderLayout());
		dialog.add(result, BorderLayout.CENTER);
		final JPanel south = new JPanel(new BorderLayout());
		dialog.add(south, BorderLayout.SOUTH);
		final JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		south.add(close, BorderLayout.EAST);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setVisible(true);
	}
}

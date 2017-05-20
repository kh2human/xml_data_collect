package gui.status;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class StatusComponent {

	private final JComponent component = new JPanel(new BorderLayout());
	private final StatusList statusList = new StatusList();
	private final ControlPanel controlPanel = new ControlPanel();
	
	public StatusComponent() {
		JPanel status = new JPanel(new BorderLayout());
		status.add(statusList.getPanel(), BorderLayout.CENTER);
		status.add(controlPanel.getPanel(), BorderLayout.SOUTH);
		component.add(status, BorderLayout.CENTER);
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	
	public StatusList getStatusList() {
		return statusList;
	}
	
	public JComponent getComponent() {
		return component;
	}
}

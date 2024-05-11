package cpintar;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;

public class remoteIdSyncPanel extends JPanel {
	public remoteIdSyncPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		JButton btnUpdate = new JButton("Update");
		panel.add(btnUpdate);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

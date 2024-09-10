package cpintar;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class FingerPrintPanel extends JPanel {
	public FingerPrintPanel() {
		setLayout(null);

		JLabel lblImage = new JLabel("Image Here");
		lblImage.setBounds(192, 11, 218, 155);
		add(lblImage);
	}

	public static void main(String args[]) {
		System.out.println("cpintar: Testing finger print java");
	}
}

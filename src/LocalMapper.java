import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;

import javax.swing.JButton;

import com.borland.dbswing.JdbTable;

public class LocalMapper extends JPanel {

	/**
	 * Create the panel.
	 */
	public LocalMapper() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane tableSrollPane = new JScrollPane();
		add(tableSrollPane);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Close");
		
		panel.add(btnNewButton);

		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);

		JButton btnNewButton_1 = new JButton("ConnectMySQL");
		northPanel.add(btnNewButton_1);

	}

	public static void main(String args[]) {
		JFrame f = new JFrame("test");
		f.setSize(800, 600);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setContentPane(new LocalMapper());
		//f.pack(); // centering
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

package cpintar;

import javax.swing.JDialog;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

public class UserMapping extends JDialog {
	public UserMapping() {
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}

}

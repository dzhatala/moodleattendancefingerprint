package cpintar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import moodle.FingerDatePair;

//to render date time error 
//public class UserSessionTableRenderer extends TableFastStringRenderer {

public class UserSessionTableRenderer extends DefaultTableCellRenderer {

	SessionUserTableModel tableModel = null;

	public UserSessionTableRenderer(SessionUserTableModel tableModel) {
		// TODO Auto-generated constructor stub
		this.tableModel = tableModel;

	}

	// StatusRenderer stRdr=new StatusRenderer();
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, col);

		// JTextArea c=new JTextArea();
		// if(value!=null)
		// c.setText(value.toString());
		// c.setLineWrap(true);
		// c.setWrapStyleWord(true);
		// c.setFont(cold.getFont());
		//

		String status = tableModel.getValueAt(row,
				SessionUserTableModel.COL_STATUS) + "";
		// util.Logger.log(this,"status description: "+status);
		if (status.trim().equalsIgnoreCase("Absent")) {

			c.setForeground(Color.RED);
			return c;
		}

		if (tableModel.getValueAt(row, SessionUserTableModel.COL_STATUS) == null) {
			c.setForeground(Color.RED);
			if (col == SessionUserTableModel.COL_STATUS) {
				if (c instanceof JLabel) {
					((JLabel) c).setText(i18n.LL.TR("NOT SET"));
				}
			}
			return c;

		}
		if (status.trim().equalsIgnoreCase("Present")) {

			c.setForeground(Color.BLACK);
			return c;
		}

		if (this.tableModel.getStatusInfos() == null)
			c.setForeground(Color.RED);
		if (this.tableModel.getFingerPair(row) == null) {
			c.setForeground(Color.RED);
		} else {

			FingerDatePair fdp = tableModel.getFingerPair(row);
			String rfName = tableModel
					.getValueAt(row, SessionUserTableModel.COL_FIRSTNAME)
					.toString().trim();
			String locfName = fdp.getFirstName().trim();

			String r_LName = tableModel
					.getValueAt(row, tableModel.COL_LASTNAME).toString().trim();
			String loca_LNAME = fdp.lastName.trim();
			if (!(locfName.equalsIgnoreCase(rfName) & r_LName
					.equalsIgnoreCase(loca_LNAME))) {
				c.setForeground(Color.BLUE);
			} else {
				c.setForeground(Color.BLACK);
			}
		}

		return c;
	}

	public String toString() {
		return this.getClass().toString();
	}

}

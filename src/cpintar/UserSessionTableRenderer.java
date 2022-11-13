package cpintar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import moodle_login_01.FingerDatePair;

import com.borland.dbswing.TableFastStringRenderer;

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
		Color col_orig = c.getForeground();

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
				c.setForeground(Color.YELLOW);
			} else {
				c.setForeground(Color.BLACK);
			}
		}

		
		return c;
	}

}

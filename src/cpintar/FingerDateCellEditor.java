package cpintar;

import java.awt.Component;
import java.sql.Timestamp;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.borland.dbswing.JdbTable;

import moodle_login_01.FingerDatePair;
import json.AttendanceStatusInfo;

/**
 * jcombo is different for each row
 * 
 * @author yoga520
 * 
 */
public class FingerDateCellEditor extends DefaultCellEditor {

	static JComboBox jComboBox = new JComboBox();

	public FingerDateCellEditor() {
		super(jComboBox);
		// JComboBox cb=()getComponent();
		// cb.addItem(new String ("Absent"));
		// TODO Auto-generated constructor stub

	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Component c = super.getTableCellEditorComponent(table, value,
				isSelected, row, column);

		// if(true==true)return null; //debug

		// System.out.println("FDCE removing all item in combo");
		if (value != null)
			System.out.println("FDPCEDTR current value is " + value
					+ ", current class is " + value.getClass());

		// jComboBox=null;
		if (column == SessionUserTableModel.COL_FINGERDATE) {// statuses column
			// System.out.println("FDCE col value type is " + value.getClass());
			jComboBox.removeAllItems();// removeAll
			if (value != null) {
				if (value instanceof FingerDatePair) {
					jComboBox.addItem(value); // must able to choose CURRENT
					FingerDatePair fdp = (FingerDatePair) value;

					Long[] tss = fdp.getTimeStamps();
					System.out.println("FDCE  extra TS for " + fdp.lastName
							+ " extra =" + tss.length);
					if (tss != null)
						for (int i = 0; i < tss.length; i++) {
							// MUST ADD FDP class type ?

							// copy old FDP and switch timestamp ....
							try {
								FingerDatePair item = (FingerDatePair) fdp
										.clone();
								// TODO switch here ...
								// change ts here ...
								item.setPRINT_TS(tss[i]);
								item.removeTimeStamp(tss[i]);
								item.addTimestamp(fdp.getPRINT_TS());

								jComboBox.addItem(item);
								// ??? all set to last 00:38 ?

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

				}

				/*if (value == null) {// NOT TAKEN
					return null;
				}
				if (value instanceof String) {
					if (SessionUserTableModel.FINGER_NOT_TAKEN
							.equalsIgnoreCase(value.toString()))
						return null;
				}*/

				// jComboBox.addItem(SessionUserTableModel.FINGER_NOT_TAKEN);
				// TODO ERROR removing all options

			}
			jComboBox.addItem(SessionUserTableModel.FINGER_NOT_TAKEN);
			return jComboBox;
		}

		return null; // all disabled

	}

	public Object getCellEditorValue() {

		/*
		 * if (column == 4) {
		 * 
		 * return combo.getSelectedItem(); }
		 */

		return jComboBox.getSelectedItem();

	}

}

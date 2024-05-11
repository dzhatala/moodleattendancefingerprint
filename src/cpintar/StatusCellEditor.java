package cpintar;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import json.AttendanceStatusInfo;

public class StatusCellEditor extends DefaultCellEditor {

	static JComboBox jComboBox = new JComboBox();

	public StatusCellEditor(AttendanceStatusInfo[] stInfo) {
		super(jComboBox);
		jComboBox.removeAllItems();
		for (int i = 0; i < stInfo.length; i++) {
			jComboBox.addItem(stInfo[i]);
		}
		// combo=new JComboBox(stInfo);
		// TODO Auto-generated constructor stub

	}

	public StatusCellEditor() {
		super(new JComboBox());
		// JComboBox cb=()getComponent();
		// cb.addItem(new String ("Absent"));
		// TODO Auto-generated constructor stub

	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Component c = super.getTableCellEditorComponent(table, value,
				isSelected, row, column);

		// return c;
		if (column == SessionUserTableModel.COL_STATUS) {// statuses column

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

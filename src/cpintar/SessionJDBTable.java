package cpintar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;

import com.borland.dbswing.JdbTable;

import config.SessionTableConfig;

public class SessionJDBTable extends JdbTable {

	private boolean isColumnWidthChanged;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6876081278490067302L;

	private class TableHeaderMouseListener implements MouseListener {
		SessionJDBTable tableObj;

		private TableHeaderMouseListener(SessionJDBTable jt) {
			this.tableObj = jt;

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			// util.Logger.log("mouse released SJDBTable");

			MouseListener[] ms = tableObj.getMouseListeners();

			for (MouseListener m : ms) {
				util.Logger.log(m + " " + m.getClass() + "");
				if (m.getClass() == Launcher.class) {
					m.mouseReleased(e);
				}
			}

			/*
			 * sjdbt width: 76 sjdbt width: 120 sjdbt width: 154 sjdbt width:
			 * 175 sjdbt width: 162 sjdbt width: 272
			 */

			Enumeration<TableColumn> tce = tableObj.getColumnModel()
					.getColumns();
			int i = 0;
			if (tableObj.getColumnCount() > 2)
				while (tce.hasMoreElements()) {
					TableColumn tc = tce.nextElement();
					// System.out.println("sjdbt "+tc.getHeaderValue()+" width: "
					// + tc.getWidth());
					SessionTableConfig.setWidth(tc.getHeaderValue().toString(),
							tc.getWidth());
				} /* On mouse release, check if column width has changed */
			if (tableObj.getColumnWidthChanged()) {
				// // Do whatever you need to do here
				//
				// // Reset the flag on the table.
				tableObj.setColumnWidthChanged(false);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			// util.Logger.log("mouse mouseClicked SJDBTable");

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			// util.Logger.log("mouse mousePressed SJDBTable");

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// util.Logger.log("mouse mouseEntered SJDBTable");
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public SessionJDBTable() {
		super();
		this.getTableHeader().addMouseListener(
				new TableHeaderMouseListener(this));

		// this.getColumnModel().addColumnModelListener(
		// new TableColumnWidthListener(this));
		//

	}

	private class TableColumnWidthListener implements TableColumnModelListener {

		SessionJDBTable tableObj;

		private TableColumnWidthListener(SessionJDBTable jt) {
			this.tableObj = jt;

		}

		@Override
		public void columnMarginChanged(ChangeEvent e) {
			// util.Logger.log("column margin changed");
			/*
			 * columnMarginChanged is called continuously as the column width is
			 * changed by dragging. Therefore, execute code below ONLY if we are
			 * not already aware of the column width having changed
			 */
			// tableObj.has
			// if(!tableObj.hasColumnWidthChanged())
			// {
			// /* the condition below will NOT be true if
			// the column width is being changed by code. */
			// tableObj.getTableHeader().is
			if (tableObj.getTableHeader().getResizingColumn() != null) {
				// User must have dragged column and changed width
				tableObj.setColumnWidthChanged(true);
			}
			// }
		}

		@Override
		public void columnMoved(TableColumnModelEvent e) {
		}

		@Override
		public void columnAdded(TableColumnModelEvent e) {
			// util.Logger.log("column added");
		}

		@Override
		public void columnRemoved(TableColumnModelEvent e) {
			// util.Logger.log("column removed");
		}

		@Override
		public void columnSelectionChanged(ListSelectionEvent e) {
			// util.Logger.log("column sel changed");

		}
	}

	public boolean getColumnWidthChanged() {
		return isColumnWidthChanged;
	}

	public void setColumnWidthChanged(boolean widthChanged) {
		isColumnWidthChanged = widthChanged;
	}

}

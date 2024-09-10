package cpintar;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class WrapCellRenderer extends JTextArea implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		TableCellRenderer c=table.getColumnModel().getColumn(column).getCellRenderer();
//		setFont(table.getFont());
//		if(value!=null)setText(value+"");
//		else setText("");
//		setWrapStyleWord(true);
//		setLineWrap(true);
		
		return this;
//		return c;
		
	}

}

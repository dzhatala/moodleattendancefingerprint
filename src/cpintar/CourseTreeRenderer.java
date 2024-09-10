package cpintar;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CourseTreeRenderer extends DefaultTreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7630027235374990027L;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");


	public CourseTreeRenderer(){
		super();
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
 
        if( value instanceof DefaultMutableTreeNode){
 
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userValue = node.getUserObject();
 
            if( userValue instanceof Date ){
//            	Date cd=(CachedDate)userValue;
              setText(dateFormat.format(userValue) );
//              setText(dateFormat.format(userValue) +" "+cd.getTime());
            }
         
        }
        return this;
    }
}

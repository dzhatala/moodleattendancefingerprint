package cpintar;

import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import json.AttendanceInstance;
import json.Course;

public class CourseTreeNode extends DefaultMutableTreeNode {

	Hashtable table = new Hashtable();
	Hashtable dates = new Hashtable();

	CourseTreeNode() {
		super("Courses");

	}

	/**
	 * if not exist add, if exist return // TODO sort here
	 * 
	 * @param d
	 * @return
	 */
	@SuppressWarnings("unchecked")
	TreeNode addDateRoot(Date d) {
		DefaultMutableTreeNode ret = null;
		if (d != null) {
			ret = getDate(d);
			if (ret == null) {
				ret = new DefaultMutableTreeNode(d);

				// sorting by dates
				Enumeration<DefaultMutableTreeNode> enums = dates.elements();
				int index = 0;

				while (enums.hasMoreElements()) {
					Date de = (Date) enums.nextElement().getUserObject();
					if (d.compareTo(de) < 0) {
						index++;
					}
				}
				insert(ret, index);
				dates.put(d, ret);
			}
		}

		return ret;

	}

	/**
	 * 
	 * @param c
	 *            .shortname should be uniq in moodle
	 * @return
	 */
	public DefaultMutableTreeNode addCourse(Course c, Date d) {

		DefaultMutableTreeNode dn = (DefaultMutableTreeNode) addDateRoot(d);

		DefaultMutableTreeNode nc = new DefaultMutableTreeNode(c);
		table.put(c.shortname, nc);
		System.out.println("adding course:" + nc);
		dn.add(nc);

		return nc;

	}

	public void removeCourse(Course c) {
		DefaultMutableTreeNode node = getNodeFromCcourse(c);
		if (node != null)
			remove(node);

	}

	public DefaultMutableTreeNode getNodeFromCcourse(Course c) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) table
				.get(c.shortname);
		return node;
	}

	public DefaultMutableTreeNode getNodeFromAttendance(AttendanceInstance att) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) table.get(att);
		return node;
	}

	public DefaultMutableTreeNode addAttendance(Course course,
			AttendanceInstance attInstance) {
		// TODO Auto-generated method stub

		DefaultMutableTreeNode courseNode = getNodeFromCcourse(course);

		DefaultMutableTreeNode attNode = new DefaultMutableTreeNode(attInstance);
		courseNode.add(attNode);
		table.put(attInstance, attNode);
		return attNode;
	}

	public DefaultMutableTreeNode getDate(Date d) {
		// TODO Auto-generated method stub
		return (DefaultMutableTreeNode) dates.get(d);
	}

}

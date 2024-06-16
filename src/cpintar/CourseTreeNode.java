package cpintar;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import json.AttendanceInstance;
import json.Course;

/**
 * TOP MOST node "list all courses"
 * @author User
 *
 */
public class CourseTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817666110570561517L;
	Hashtable<Object, DefaultMutableTreeNode> courses = new Hashtable<Object, DefaultMutableTreeNode>();
	Hashtable<Date, DefaultMutableTreeNode> dates = new Hashtable<Date, DefaultMutableTreeNode>();

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
	private TreeNode addDateRoot(Date d) {
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
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(1);

		DefaultMutableTreeNode dn = (DefaultMutableTreeNode) addDateRoot(d);

		DefaultMutableTreeNode nc = new DefaultMutableTreeNode(c);
		courses.put(c.shortname, nc);
		// System.out.println("adding course:" + nc);
		dn.add(nc);

		return nc;

	}

	/*
	 * public void removeCourse(Course c) { DefaultMutableTreeNode node =
	 * getNodeFromCcourse(c); if (node != null) remove(node);
	 * 
	 * }
	 */

	public DefaultMutableTreeNode getNodeFromCcourse(Course c) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) courses
				.get(c.shortname);
		return node;
	}

	public DefaultMutableTreeNode getNodeFromAttendance(AttendanceInstance att) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) courses.get(att);
		return node;
	}

	public DefaultMutableTreeNode addAttendance(Course course,
			AttendanceInstance attInstance) {
		// TODO Auto-generated method stub

		DefaultMutableTreeNode courseNode = getNodeFromCcourse(course);

		DefaultMutableTreeNode attNode = new DefaultMutableTreeNode(attInstance);
		courseNode.add(attNode);
		courses.put(attInstance, attNode);
		return attNode;
	}

	public DefaultMutableTreeNode getDate(Date d) {
		// TODO Auto-generated method stub
		return (DefaultMutableTreeNode) dates.get(d);
	}

	public void deleteCachedDate(DefaultMutableTreeNode node) {
		// TODO Auto-generated method stub
		Enumeration<DefaultMutableTreeNode> children=node.children();
		while(children.hasMoreElements()){
			DefaultMutableTreeNode nd=children.nextElement();
			Object obj=nd.getUserObject() ;
			if(obj instanceof Course){
				courses.remove(obj);
			}
		}
		remove (node);
	}

}

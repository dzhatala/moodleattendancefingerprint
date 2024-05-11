package test;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.TableDataSet;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dbswing.JdbTable;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.borland.dbswing.JdbNavToolBar;

import com.borland.dbswing.TableFastStringRenderer;

import javax.swing.JScrollPane;
import java.awt.Font;

class MyRenderer extends TableFastStringRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JdbTable dbTable = (JdbTable) table;

		if (column == 1)
			value = dbTable.getValueAt(row, column);// somehow value is missing
													// and need to be refilled
		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);

	}
}

class MyDbTable extends JdbTable {
	protected TableCellRenderer getDefaultCellRenderer(Column dataSetColumn) {

		return new MyRenderer();

	}
}

public class mysql_Jbuilder001 extends JFrame {

	// static String fp_q =
	// "select IDENTIFIED1N.*,fpinfo.person_id,mdl_user.username,mdl_user.username,"
	static String fp_q = "select IDENTIFIED1N.IDENTIFIED1N_ID, fpinfo.person_id,mdl_user.username,"
			+ "mdl_user.firstname,mdl_user.lastname from IDENTIFIED1N, fpinfo,mdl_user where "
			+ "((IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX) OR "
			+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_THUMB)OR "
			+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_INDEX)OR"
			+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_MIDDLE)OR   "
			+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_RING)OR"
			+ " (IDENTIFIED1N.fpid=fpinfo.RIGHT_PINKY)OR"
			+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_THUMB)OR   "
			+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_INDEX)OR   "
			+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_MIDDLE)OR   "
			+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_RING)OR   "
			+ " (IDENTIFIED1N.fpid=fpinfo.LEFT_PINKY)OR   "
			+ " 0) AND person_id=mdl_user.id  order by trialdate desc;";

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=93,339
	 */
	private final Database db1 = new Database();
	private JdbTable jdbTable1;
	private JdbNavToolBar jdbNav1;
	private JScrollPane scrollPane;
	private QueryDataSet _qds = new QueryDataSet();

	static void initLocal(mysql_Jbuilder001 frame)
			throws ClassNotFoundException {
		System.out.println("JbuilderDB01");
		Class.forName("com.mysql.jdbc.Driver");
		Database db1 = new Database();
		/*
		 * db1.setConnection(new ConnectionDescriptor(
		 * "jdbc:mysql://localhost:3306/absensi", "root", "wrong_pwd", false,
		 * "com.mysql.jdbc.Driver"));
		 */

		db1.setConnection(new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi", "root", "", false,
				"com.mysql.jdbc.Driver"));

		QueryDataSet qds = frame._qds;

		// fp_q =
		// "select IDENTIFIED1N.* from IDENTIFIED1N order by IDENTIFIED1N_ID DESC";

		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(db1, fp_q,
				null, true, Load.ALL));
		qds.setReadOnly(true);

		System.out.println("login succesfull");

		frame.setJDBTable1(new MyDbTable()); // if reset table
		frame.scrollPane.setViewportView(frame.jdbTable1);// must re add in
															// scroll pane

		JdbTable tb1 = frame.getJdbTable1();
		db1.openConnection();
		// tb1.set
		// TableDataSet tds = new TableDataSet();
		// //tds.setd
		tb1.setDataSet(qds);

		JdbNavToolBar nav = frame.getJdbNav1();
		nav.setDataSet(qds);

		qds.open();

		System.out.println("tb1 col count:"
				+ tb1.getColumnModel().getColumnCount());

	}

	private static void pr(Object o) {
		// TODO Auto-generated method stub
		System.out.println(o);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mysql_Jbuilder001 frame = new mysql_Jbuilder001();

					initLocal(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mysql_Jbuilder001() {
		setTitle("Test Jbuilder 01");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JButton btnCloseConn = new JButton("Close Conn");
		btnCloseConn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("close con db");
				/*
				 * System.out.println(getJdbTable1().getCellRenderer(1, 0)
				 * .getClass());
				 * System.out.println(getJdbTable1().getCellRenderer(1, 2)
				 * .getClass()); System.out.println("after close tb1 col count:"
				 * + getJdbTable1().getColumnModel().getColumnCount());
				 */

				_qds.close();
				db1.closeConnection();
			}
		});
		contentPane.add(btnCloseConn, BorderLayout.NORTH);

		jdbNav1 = new JdbNavToolBar();
		contentPane.add(jdbNav1, BorderLayout.SOUTH);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		jdbTable1 = new MyDbTable();
		jdbTable1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		scrollPane.setViewportView(jdbTable1);
	}

	public JdbTable getJdbTable1() {
		return jdbTable1;
	}

	public void setJDBTable1(JdbTable tbl) {
		jdbTable1 = tbl;
	}

	public JdbNavToolBar getJdbNav1() {
		return jdbNav1;
	}

	public void test() {
		com.borland.dbswing.DBTableModel x;

	}
}

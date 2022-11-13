package test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

import javax.swing.JScrollPane;

public class JdbTableRenderer extends JFrame {

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=93,339
	 */
	private final Database db1 = new Database();
	private JdbTable jdbTable1;
	private JdbNavToolBar jdbNav1;
	private JScrollPane scrollPane;
	private QueryDataSet _qds = new QueryDataSet();

	static void initLocal(JdbTableRenderer frame) throws ClassNotFoundException {
		System.out.println("JdbTableRenderer");
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

		qds.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(db1,
				"SELECT  * FROM `tbl_manual` ORDER BY `tm_id` DESC ", null,
				true, Load.ALL));
		qds.setReadOnly(false);
		// qds.setRowId("IDENTIFIED1N_ID",true);

		db1.openConnection();
		System.out.println("login succesfull");
		JdbTable tb1 = frame.getJdbTable1();

		// tb1.set
		// TableDataSet tds = new TableDataSet();
		// //tds.setd
		tb1.setDataSet(qds);
		JdbNavToolBar nav = frame.getJdbNav1();
		nav.setDataSet(qds);
		qds.open();
		/*
		 * Column[] cols = qds.getColumns();
		 * 
		 * for (int i = 0; i < cols.length; i++) { System.out.println(cols[i]);
		 * 
		 * }
		 */
		qds.setTableName("tbl_manual"); // for saving to persistent table
		qds.setRowId("tm_id", true);
		// pr(tb1.getCellEditor());

		// db1.closeConnection();

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
					JdbTableRenderer frame = new JdbTableRenderer();
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
	public JdbTableRenderer() {
		setTitle("Test JdbTableRenderer");
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

				_qds.close();
				db1.closeConnection();
			}
		});
		contentPane.add(btnCloseConn, BorderLayout.NORTH);

		jdbNav1 = new JdbNavToolBar();
		contentPane.add(jdbNav1, BorderLayout.SOUTH);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		jdbTable1 = new JdbTable() {
			public TableCellRenderer test(int row, int column) {
				return super.getCellRenderer(row, column);
			}
		};
		scrollPane.setViewportView(jdbTable1);
	}

	public JdbTable getJdbTable1() {
		return jdbTable1;
	}

	public JdbNavToolBar getJdbNav1() {
		return jdbNav1;
	}
}

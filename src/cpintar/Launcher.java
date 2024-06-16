package cpintar;

import i18n.LL;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.borland.dbswing.JdbTable;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.ConnectionDescriptor;

import config.AccountConfig;
import config.SessionTableConfig;
import cpintar.biometric.BioScanListener;
import cpintar.biometric.ScanEvent;
import cpintar.biometric.zkteco.ZKFPBioManager;
import db.MysqlConnector;
import db.RemotePair;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JSplitPane;

import moodle.FingerDatePair;
import moodle.MoodleRest;
import moodle.Utils;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;

import json.AttendanceInstance;
import json.AttendanceStatusInfo;
import json.Course;
import json.MoodleUser;
import json.Session;
import json.SessionDetail;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

/**
 * Main application GUI
 * 
 * @author Zulkarnaen hatala 2022
 * 
 */

public class Launcher extends JFrame implements MouseListener, BioScanListener {

	// connection profile
	private Vector<MoodleWSURL> moodleWSURLs = new Vector();

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=489,389
	 */
	private SessionUserTableModel tableModel = null;
	/**
	 * @wbp.nonvisual location=223,419
	 */
	private final Database database = new Database();

	/**
	 * Launch the application.
	 */
	CourseTreeNode root = new CourseTreeNode();
	private JTree courseTree = new JTree(root);
	private JScrollPane scrollPane;
	private JSplitPane splitPane;
	MoodleRest restConnector = new MoodleRest("http://127.0.0.1/moodle"); // localhost

	MysqlConnector mysqlConnector = new MysqlConnector();

	private JPanel studentPanel;
	private JdbTable userSessionTable;
	protected Font defaultFont = new Font("Tahoma", Font.PLAIN, 18);
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	public static final SimpleDateFormat dateFormatZone = new SimpleDateFormat(
			"dd/MM/yyyy 'T'HH:mm:ssZ");

	// error

	static ZKFPBioManager zkBioMgr = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
					TimeZone.setDefault(zone);
					dateFormatZone.setTimeZone(zone);
					Launcher frame = new Launcher();
					frame.loadCachedDates();
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					JScrollPane scP = frame.getScrollPane();
					frame.getScrollPane().setViewportView(frame.courseTree);
					JSplitPane sp = frame.getSplitPane();
					sp.setLeftComponent(scP);
					// sp.setDividerLocation(50);
					frame.initInput();
					frame.initFontSizes();
					frame.courseTree.setFont(frame.defaultFont);
					// frame.userSessionTable.set
					frame.initListeners();
					frame.initMenu();
					frame.pack(); // centering
					frame.setLocationRelativeTo(null);
					// util.Logger.log(frame.courseTree.getCellRenderer());
					frame.initGUI();
					frame.setVisible(true);
				} catch (Exception e) {

					showException(e);
					e.printStackTrace();
				}
			}
		});

	}

	protected void initGUI() {
		// TODO Auto-generated method stub
		courseTree.setCellRenderer(new CourseTreeRenderer());
	}

	protected void loadCachedDates() {
		// TODO Auto-generated method stub
		AccountConfig ac = AccountConfig.getInstance();
		String remoteid = ac.getUsername();
		CachedDate[] dates = mysqlConnector.getTeachersDatesCached(remoteid);
		if (dates != null)
			for (CachedDate d : dates) {
				Course c = new Course();
				c.fullname = d.getCoursefullname();
				c.shortname = d.courseshortname;
				root.addCourse(c, d);
			}
		// Course c = new Course();
		// root.addCourse(c, new Date());

		// DefaultMutableTreeNode currentNode = root;
		this.courseTree.expandPath(new TreePath(root.getPath()));
		/*
		 * do { if (currentNode.getLevel() == 0) this.courseTree.expandPath(new
		 * TreePath(currentNode.getPath())); currentNode =
		 * currentNode.getNextNode(); } while (currentNode != null);
		 */
	}

	protected void initMenu() {
		// TODO Auto-generated method stub
		// System.out.println("Initialize menu");
	}

	// TODO bigger font ?
	protected void initFontSizes() {
		// TODO Auto-generated method stub
		userSessionTable.getTableHeader().setFont(defaultFont);
	}

	protected void initInput() {
		// TODO Auto-generated method stub
		// courseDate.addItem(dateFormat.format(new Date()));
		// dd/MM/yyyy HH:mm:ss
		// courseDate.getEditor().setItem("25/01/2022 08:01:00");
		splitPane.setDividerLocation(250);
		// courseDate.getEditor().setItem("28/12/2021 08:01:00");
		// courseDate.getEditor().setItem("14/09/2021 07:01:00");
		courseDate.getEditor().setItem(dateFormat.format(new Date()));
		courseDate.setPreferredSize(new Dimension(400, 20));

		MoodleWSURL m = new MoodleWSURL("cpintar",
				"https://cs.cepatpintar.biz.id/moodle", "fp_cepatpintar");
		moodleWSURLs.add(m);
		m.setUsername(AccountConfig.getUsername());
		m.setPassword(AccountConfig.getPassword());
		m = new MoodleWSURL("yoga", "http://127.0.0.1/moodle", "fp_yoga");
		m.setUsername(AccountConfig.getUsername());
		m.setPassword(AccountConfig.getPassword());
		moodleWSURLs.add(m);

		renderConnProfiles(moodleWSURLs);
		/*
		 * moodleURL.addItem(new MoodleWSURL(
		 * "https://cs.cepatpintar.biz.id/moodle", "fp_cepatpintar")); moodleURL
		 * .addItem(new MoodleWSURL("http://127.0.0.1/moodle", "fp_yoga"));
		 */

		// System.out.println("uncomment this laucncher.java:161");
		// courseDate.getEditor().setItem("28/12/2021 08:01:00");
		// courseDate.getEditor().setItem("13/09/2022 08:01:00");
		// moodleURL.setSelectedIndex(1);
	}

	private void renderConnProfiles(Vector<MoodleWSURL> urls) {
		// TODO Auto-generated method stub
		for (int i = 0; i < urls.size(); i++) {
			moodleURL.addItem(urls.elementAt(i));

		}

	}

	protected void initListeners() {
		// TODO Auto-generated method stub
		courseTree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				courseTreeMouseClicked(me);
			}

		}

		);

		// TODO sometime shadowed ? NOT RECEIVED
		userSessionTable.addMouseListener(this);

		// userSessionTable.get

		// userSessionTable.add

	}

	protected void courseTreeMouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub

		TreePath tp = courseTree.getPathForLocation(me.getX(), me.getY());
		Object obj = null;
		DefaultMutableTreeNode node = null;
		if (tp != null) {

			// System.out.println(tp.getLastPathComponent().getClass());
			node = (DefaultMutableTreeNode) tp.getLastPathComponent();
			obj = node.getUserObject();
		} else {
			// System.out.println("no tree path ");
			return; //
		}
		int btnNumber = me.getButton();
		final int RIGHT_CLICK_EVENT = MouseEvent.BUTTON3;
		switch (btnNumber) {
		case MouseEvent.BUTTON1:

			if (node != null && obj != null && obj instanceof Session) {
				Session sess = (Session) node.getUserObject();
				if (sess.detail != null) {
					showSession(node);
				}
			}
			// System.out.println(node.getUserObject().getClass());
			break;
		case RIGHT_CLICK_EVENT:
			// System.out.println("Right click");
			showPopUpMenu(me, tp, node, obj);
			break;
		default:
			break;

		}
	}

	private void showPopUpMenu(MouseEvent me, TreePath tp,
			final DefaultMutableTreeNode node, final Object obj) {
		// TODO Auto-generated method stub

		if (obj != null && obj instanceof Date) {

			JPopupMenu popup = new JPopupMenu();
			ActionListener menuListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					// System.out.println("Popup menu item ["
					// + event.getActionCommand() + "] was pressed.");
					String ac = event.getActionCommand();
					if (ac.equalsIgnoreCase(LL.TR("Delete Date"))) {
						util.Logger.log("delete date " + node.getUserObject());
						courseDeleteDate(node, (Date) obj);
					}
				}
			};

			JMenuItem item;
			popup.add(item = new JMenuItem(LL.TR("Delete Date")));
			item.setHorizontalTextPosition(JMenuItem.RIGHT);
			item.addActionListener(menuListener);
			popup.setLabel("Justification");
			popup.show(this, me.getX(), me.getY());

		}

		if (obj != null && obj instanceof Session) {

			JPopupMenu popup = new JPopupMenu();
			ActionListener menuListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					// System.out.println("Popup menu item ["
					// + event.getActionCommand() + "] was pressed.");
					String ac = event.getActionCommand();
					if (ac.equalsIgnoreCase("Reload Session")) {
						reloadSession(node, (Session) obj);
					}
				}
			};
			JMenuItem item;
			// popup.add(item = new JMenuItem("Reload Session", new ImageIcon(
			// "1.gif")));
			popup.add(item = new JMenuItem("Reload Session"));
			item.setHorizontalTextPosition(JMenuItem.RIGHT);
			item.addActionListener(menuListener);
			/*
			 * popup.add(item = new JMenuItem("Center", new
			 * ImageIcon("2.gif")));
			 * item.setHorizontalTextPosition(JMenuItem.RIGHT);
			 * item.addActionListener(menuListener); popup.add(item = new
			 * JMenuItem("Right", new ImageIcon("3.gif")));
			 * item.setHorizontalTextPosition(JMenuItem.RIGHT);
			 * item.addActionListener(menuListener); popup.add(item = new
			 * JMenuItem("Full", new ImageIcon("4.gif")));
			 * item.setHorizontalTextPosition(JMenuItem.RIGHT);
			 * item.addActionListener(menuListener); popup.addSeparator();
			 * popup.add(item = new JMenuItem("Settings . . ."));
			 * item.addActionListener(menuListener);
			 */

			popup.setLabel("Justification");
			// popup.setBorder(new BevelBorder(BevelBorder.RAISED));
			// popup.addPopupMenuListener(new PopupPrintListener());
			popup.show(this, me.getX(), me.getY());

		}

	}

	/**
	 * 
	 * @param node
	 * @param obj
	 * @throws SQLException
	 */
	protected void courseDeleteDate(DefaultMutableTreeNode node, Date obj) {
		// TODO Auto-generated method stub
		if (node == null || obj == null)
			return;
		Object o = node.getUserObject();

		if (!(o instanceof CachedDate)) {
			util.Logger.log("try to remove non date: " + o.getClass());
			return;
		}

		int cnt = node.getChildCount();
		/*
		 * for(int i=0; i<cnt; i++){ DefaultMutableTreeNode
		 * courseN=(DefaultMutableTreeNode)node.getChildAt(i); Object
		 * co=courseN.getUserObject(); if(!(co instanceof Course)){
		 * util.Logger.log("\tnon course : "+co.getClass());
		 * 
		 * continue; } root.removeCourse((Course)co); }
		 */

		root.deleteCachedDate(node);
		DefaultTreeModel courseTreeNode = (DefaultTreeModel) courseTree
				.getModel();
		try {
			mysqlConnector.deleteTeachersDatesCached(
					AccountConfig.getUsername(), (Date) o);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		courseTreeNode.reload(root);
		// util.Logger.log("remoing "+o);
	}

	/**
	 * reload session
	 * 
	 * @param node
	 * @param obj
	 */
	protected void reloadSession(DefaultMutableTreeNode node, Session sess) {
		// TODO Auto-generated method stub
		// long sessId = sess.id;
		// sess.detail=restConnector.asynchGetSessionDetail(sessId);
		sess.detail = restConnector.getSessionDetail(sess);
		if (sess.detail != null) {
			DefaultTreeModel courseTreeNode = (DefaultTreeModel) courseTree
					.getModel();
			courseTreeNode.reload(node);
		}

	}

	// final UserSessionTableRenderer rdr = new UserSessionTableRenderer();
	StatusCellEditor edtr = null;
	private JComboBox courseDate;
	private ConnectionDescriptor mysqlDescriptor = new ConnectionDescriptor(
			"jdbc:mysql://localhost:3306/absensi", "root", "", false,
			"com.mysql.jdbc.Driver"); // TODO profile GUI editor
	private JButton btnAutoSetPresent;
	private JButton btnSyncChangeMoodle;
	private JButton btnUndoChange;
	private DefaultMutableTreeNode activeSessionNode;
	private JComboBox moodleURL;
	private int FINGER_HOUR_END_DAY = 21; // TODO profile GUI editor for late
											// tolerance for finger taking..
	private int FINGER_HOUR_BEGIN_DAY = 4; // 24 hour end of the day
	private JLabel lblCourse;
	private JLabel lblDate;
	private JLabel lblFinger;
	private JButton btnSetAllPresent;
	private JButton btnSettings;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmOpenFinger;
	private JMenu mnData;
	private JMenuItem mntmLocalremoteMapping;
	private JButton btLast;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btNextDay;

	/**
	 * show ad session Table ?
	 * 
	 * @param sessionNode
	 */
	private void showSession(DefaultMutableTreeNode sessionNode) {
		// TODO Auto-generated method stub
		if (sessionNode == null)
			return;
		Session sess = (Session) sessionNode.getUserObject();
		// System.out.println("showing session: " + sess);

		RemotePair[] rlPairs = null;
		try {
			rlPairs = populate_from_local(sess.detail.users);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showException(e1);
			return;
		}

		long[] localsDetected = new long[0];
		if (rlPairs != null)
			if (rlPairs.length > 0) {
				localsDetected = new long[rlPairs.length];
			}

		// if()
		// System.out.println("localsDetected length=" + localsDetected.length);
		for (int i = 0; i < localsDetected.length; i++) {
			localsDetected[i] = rlPairs[i].getLocal();
		}
		Date sessJDate = new Date(sess.detail.sessdate * 1000); // moodle time
																// stamp * 1000
																// to java
																// timestatmp
		long[] jtses = Utils.TSDateStartEnd(sessJDate, FINGER_HOUR_BEGIN_DAY,
				FINGER_HOUR_END_DAY);
		FingerDatePair[] fdPs = mysqlConnector.getFingerDatePairs(
				localsDetected, sess.detail.sessdate, sess.detail.duration,
				jtses[0] / 1000, jtses[1] / 1000); // ??different
													// GMT+7
													// and
													// gmt+9
													// local
													// of
													// mysql
													// server

		tableModel = new SessionUserTableModel(sess.detail, rlPairs, fdPs);

		// tablemodellistener can detect cell editor value changed
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("MainFr : table changed, " + e);
				handleTableModelChangedEvent(e);
			}

		});
		// userSessionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		userSessionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		userSessionTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		userSessionTable.setModel(tableModel);
		Enumeration<TableColumn> tce = userSessionTable.getColumnModel()
				.getColumns();
		if (userSessionTable.getColumnCount() > 2)
			while (tce.hasMoreElements()) {
				TableColumn tc = tce.nextElement();
				// System.out.println("sjdbt "+tc.getHeaderValue()+" width: " +
				// tc.getWidth());
				int width = SessionTableConfig.getWidth(tc.getHeaderValue()
						.toString());
				util.Logger.log("laucnher set width " + tc.getHeaderValue()
						+ "<=" + width);
				tc.setPreferredWidth(width);
				tc.setWidth(width);
			} /* On mouse release, check if column width has changed */

		// WrapCellRenderer wcr=new WrapCellRenderer();
		TableColumnModel cm = userSessionTable.getColumnModel();
		cm.getColumn(SessionUserTableModel.COL_LOCALID).setWidth(5);
		cm.getColumn(SessionUserTableModel.COL_REMOTEID).setWidth(5);

		/*
		 * userSessionTable = new JdbTable() { public TableCellRenderer
		 * getCellRenderer(int row, int column) { return rdr; } };
		 * userSessionTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 * studentPanel.add(userSessionTable);
		 */

		final UserSessionTableRenderer rdr = new UserSessionTableRenderer(
				tableModel);

		userSessionTable.setDefaultRenderer(Object.class, rdr);
		edtr = new StatusCellEditor(sess.detail.statuses);
		edtr.addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("edtr: " + edtr.getCellEditorValue());
				sessionEditorValue(edtr.getCellEditorValue());
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				// TODO Auto-generated method stub

			}
		});

		/*
		 * userSessionTable.setDefaultEditor(Object.class, null);//remove all
		 * editor
		 */
		userSessionTable.setDefaultEditor(Object.class, null);// can't edit all
																// cells
		TableColumn col = userSessionTable.getColumnModel().getColumn(
				SessionUserTableModel.COL_STATUS);
		col.setCellEditor(edtr);

		col = userSessionTable.getColumnModel().getColumn(
				SessionUserTableModel.COL_FINGERDATE);

		final FingerDateCellEditor fdEditor = new FingerDateCellEditor();

		col.setCellEditor(fdEditor);

		// System.out.println("MainFr: tablemode getrowcount() "
		// + userSessionTable.getModel().getRowCount());
		// System.out.println("MainFr: tablemode getcolcount() "
		// + userSessionTable.getModel().getColumnCount());

		// sess.detail.sessdate;
		fdEditor.addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				// TODO Auto-generated method stub
				sessionEditorValue(fdEditor.getCellEditorValue());
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Date sd = new Date(sess.detail.sessdate * 1000);

		lblDate.setText(dateFormatZone.format(sd));

		lblCourse.setText("Desc: " + sess.detail.description);
		lblFinger.setText("Log Marked:" + sess.detail.attendance_log.length
				+ "");

		btnAutoSetPresent.setEnabled(true);
		btnSetAllPresent.setEnabled(true);
		btnSyncChangeMoodle.setEnabled(false);
		btnUndoChange.setEnabled(false);
		activeSessionNode = sessionNode;

	}

	protected void handleRow2Clicked(int row, int column,
			JdbTable userSessionTable2) {
		// TODO Auto-generated method stub
		// System.out.println("LogDetail ..");
		LogDetail log = new LogDetail(this, row, tableModel);
		// log.setSize(300, 200);
		log.setVisible(true);
		log.dispose(); // MUST DISPOSE

	}

	protected void handleTableModelChangedEvent(TableModelEvent e) {
		int icol = e.getColumn();

		// System.out.println("tmev: " + e.getType());

		switch (e.getType()) {
		case TableModelEvent.INSERT:

			break;

		case TableModelEvent.UPDATE:
			// System.out.println("update in col=" + icol + ", row=" + icol);
			if (icol == SessionUserTableModel.COL_STATUS
					| icol == SessionUserTableModel.COL_FINGERDATE) {
				this.btnSyncChangeMoodle.setEnabled(true);
				btnUndoChange.setEnabled(true);
				//
			}

			break;
		case TableModelEvent.DELETE:
			break;
		}

	}

	protected void sessionEditorValue(Object cellEditorValue) {
		// TODO Auto-generated method stub
		// System.out.println("edtr: " + cellEditorValue);
	}

	/***
		 * 
		 */
	private void hello() {
		// tableModel.setDataSet(tableDataSet);
	}

	/**
	 * Create the frame.
	 */
	public Launcher() {
		updateTitle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1048, 736);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Bio Device");
		menuBar.add(mnNewMenu);

		mntmOpenFinger = new JMenuItem("Open");
		mntmOpenFinger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openZKFPBioDevice(e);
			}
		});
		mnNewMenu.add(mntmOpenFinger);

		mnData = new JMenu("Data");
		menuBar.add(mnData);

		mntmLocalremoteMapping = new JMenuItem("Local=Remote Mapping");
		mntmLocalremoteMapping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showUserMapping();
			}

		});
		mnData.add(mntmLocalremoteMapping);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel datePanel = new JPanel();
		contentPane.add(datePanel, BorderLayout.NORTH);
		datePanel.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		datePanel.add(panel, BorderLayout.CENTER);

		JLabel lblServer = new JLabel("Server");
		panel.add(lblServer);
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 18));

		moodleURL = new JComboBox();
		moodleURL.setEnabled(false);
		panel.add(moodleURL);
		moodleURL.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnSettings = new JButton("Settings");
		panel.add(btnSettings);

		JButton btnRefresh = new JButton("Get");
		panel.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectAndRefreshTree();

			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSettingsDialogs(getInstance());
			}
		});

		panel_1 = new JPanel();
		datePanel.add(panel_1, BorderLayout.EAST);

		JButton previousWeek = new JButton("<");
		panel_1.add(previousWeek);
		previousWeek.setToolTipText("last week");
		previousWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPreviousSession(courseDate);
			}
		});

		btLast = new JButton("-");
		panel_1.add(btLast);
		btLast.setToolTipText("last day");
		btLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLastDay(courseDate);
			}
		});

		btNextDay = new JButton("+");
		panel_1.add(btNextDay);
		btNextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNextDay(courseDate);
			}
		});

		JButton nextWeek = new JButton(">");
		panel_1.add(nextWeek);
		nextWeek.setToolTipText("next week");
		nextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNextSession(courseDate);
			}
		});

		courseDate = new JComboBox();
		panel_1.add(courseDate);
		courseDate.setToolTipText("enter valid date and press connect button");
		courseDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		courseDate.setEditable(true);

		splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);

		studentPanel = new JPanel();
		splitPane.setRightComponent(studentPanel);
		studentPanel.setLayout(new BorderLayout(0, 0));

		JPanel infoPanel = new JPanel();
		studentPanel.add(infoPanel, BorderLayout.NORTH);

		lblCourse = new JLabel("Course : Course");
		lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 19));
		infoPanel.add(lblCourse);

		lblDate = new JLabel("Date : 28/11/2021  (Today)");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 19));
		infoPanel.add(lblDate);

		lblFinger = new JLabel("Total Fingered: 0");
		lblFinger.setFont(new Font("Tahoma", Font.PLAIN, 19));
		infoPanel.add(lblFinger);

		userSessionTable = new SessionJDBTable();
		userSessionTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// studentPanel.add(userSessionTable);

		JScrollPane scrollPane_1 = new JScrollPane();
		studentPanel.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setViewportView(userSessionTable);

		JPanel southCommandPanel = new JPanel();
		studentPanel.add(southCommandPanel, BorderLayout.SOUTH);

		btnUndoChange = new JButton("Undo Changes");
		btnUndoChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoStatusChange(e);
			}
		});
		btnUndoChange.setEnabled(false);
		southCommandPanel.add(btnUndoChange);

		btnAutoSetPresent = new JButton("Auto Set Present");
		btnAutoSetPresent.setEnabled(false);
		btnAutoSetPresent
				.setToolTipText("Set Present for all with finger print taken");
		btnAutoSetPresent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				autoSetPresent(false);
			}
		});
		southCommandPanel.add(btnAutoSetPresent);

		btnSyncChangeMoodle = new JButton("save2 LMS server");
		btnSyncChangeMoodle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusSynch2Moodle(e);
			}
		});

		btnSetAllPresent = new JButton("Set All Present");
		btnSetAllPresent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				autoSetPresent(true);
			}
		});
		btnSetAllPresent
				.setToolTipText("Set Present for all with finger print taken");
		btnSetAllPresent.setEnabled(false);
		southCommandPanel.add(btnSetAllPresent);
		btnSyncChangeMoodle.setEnabled(false);
		southCommandPanel.add(btnSyncChangeMoodle);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
	}

	private void updateTitle() {
		// TODO Auto-generated method stub
		AccountConfig ac = AccountConfig.getInstance();
		setTitle("KALESANG (" + ac.getUsername() + ")");

	}

	// TODO : fix to be put in Preferences
	// ConnectionDescriptor desc = new ConnectionDescriptor(
	// "jdbc:mysql://localhost:3306/absensi", "root", "", false,
	// "com.mysql.jdbc.Driver"); // TODO profile GUI editor

	protected void openZKFPBioDevice(ActionEvent e) {
		// TODO Auto-generated method stub

		// if (zkBioMgr == null) {
		// zkBioMgr = new ZKFPBioManager();
		if (zkBioMgr != null) {
			zkBioMgr.disconectAll();
			zkBioMgr.dispose();
			zkBioMgr = null;
		}
		zkBioMgr = new ZKFPBioManager(SwingUtilities.windowForComponent(this));
		// }
		zkBioMgr.removeAllBioScanListener();
		zkBioMgr.addBioScanListener(this);
		// MysqlConnector mc = new MysqlConnector();
		// mc.setMySQLDescriptor(desc);
		mysqlConnector.setMySQLDescriptor(mysqlDescriptor);
		// zkBioMgr.setMySQLConnector(mc);
		zkBioMgr.setMySQLConnector(mysqlConnector);
		zkBioMgr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		zkBioMgr.setModal(true);
		zkBioMgr.gotoPreferLocation();
		zkBioMgr.setVisible(true);

	}

	protected void showUserMapping() {
		// TODO Auto-generated method stub
		l("showuserMapping()");

	}

	private void l(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	protected void openSettingsDialogs(Launcher owner) {
		// TODO Auto-generated method stub
		Settings sets = Settings.getInstance();

		if (sets == null) {
			sets = new Settings(owner, moodleWSURLs.toArray(new MoodleWSURL[0]));
		}
		// sets.setBounds(0,0,100,100);
		// showing settings dialog
		// sets.pack();
		sets.setVisible(true);
		updateTitle();

	}

	protected Launcher getInstance() {
		// TODO Auto-generated method stub
		return this;
	}

	protected void setPreviousSession(JComboBox cd) {
		// TODO Auto-generated method stub
		String original = cd.getEditor().getItem().toString();
		// System.out.println("sPS() original:"+original);
		try {
			Date newDate = dateFormat.parse(original);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(newDate);
			calendar.add(Calendar.DATE, -7);// increase a week
			newDate.setTime(calendar.getTime().getTime());

			cd.getEditor().setItem(dateFormat.format(newDate));
			;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showException(e);
		}

	}

	private static void showException(Exception e) {
		// TODO Auto-generated method stub
		if (e instanceof UnknownHostException) {
			JOptionPane.showMessageDialog(null,
					"Unknown Host " + "'" + e.getMessage()
							+ "'. Check internet connection!", "Error",
					JOptionPane.OK_OPTION);
			return;
		}
		JOptionPane.showMessageDialog(null, e + ":" + e.getMessage(), "Error",
				JOptionPane.OK_OPTION);

	}

	protected void setNextSession(JComboBox cd) {
		// TODO Auto-generated method stub
		String original = cd.getEditor().getItem().toString();
		// System.out.println("sPS() original:"+original);
		try {
			Date newDate = dateFormat.parse(original);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(newDate);
			calendar.add(Calendar.DATE, +7);// increase a week
			newDate.setTime(calendar.getTime().getTime());

			cd.getEditor().setItem(dateFormat.format(newDate));
			;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showException(e);
		}

	}

	protected void setNextDay(JComboBox cd) {
		// TODO Auto-generated method stub
		String original = cd.getEditor().getItem().toString();
		// System.out.println("sPS() original:"+original);
		try {
			Date newDate = dateFormat.parse(original);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(newDate);
			calendar.add(Calendar.DATE, +1);// increase a week
			newDate.setTime(calendar.getTime().getTime());

			cd.getEditor().setItem(dateFormat.format(newDate));
			;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showException(e);
		}

	}

	protected void setLastDay(JComboBox cd) {
		// TODO Auto-generated method stub
		String original = cd.getEditor().getItem().toString();
		// System.out.println("sPS() original:"+original);
		try {
			Date newDate = dateFormat.parse(original);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(newDate);
			calendar.add(Calendar.DATE, -1);// increase a week
			newDate.setTime(calendar.getTime().getTime());

			cd.getEditor().setItem(dateFormat.format(newDate));
			;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showException(e);
		}

	}

	protected void statusSynch2Moodle(ActionEvent e) {
		int dialogButton = JOptionPane.showConfirmDialog(null,
				"Save changes to moodle server?", "Confirm",
				JOptionPane.YES_NO_OPTION);

		if (dialogButton != JOptionPane.YES_OPTION) {
			return;
		}
		final int sessionid = tableModel.getSessionId();
		String statusset = tableModel.getStatusset();
		// if (statusset.equalsIgnoreCase(""))
		statusset = AttendanceStatusInfo.getStatusSet(tableModel
				.getStatusInfos());
		final String fst = statusset;
		int rcnt = tableModel.getRowCount();
		// final String st=sess.
		// this.tableModel.gets
		for (int i = 0; i < rcnt; i++) {
			// System.out.println("row " + i + " "
			// + tableModel.getValueAt(i, tableModel.COL_FIRSTNAME)
			// + " : isStChngd? " + tableModel.isStatusChanged(i));
			final String studentid = tableModel.getValueAt(i,
					tableModel.COL_REMOTEID) + "";
			final Object cs = tableModel.getValueAt(i, tableModel.COL_STATUS);
			if (cs == null) {
				l("cs=null, row number=" + i);
				continue;
			}
			final String statusid = AttendanceStatusInfo.descriptionToID(
					tableModel.getStatusInfos(), cs.toString()) + "";

			// TODO
			String remarks = null;
			Object fingerVal = tableModel.getValueAt(i,
					tableModel.COL_FINGERDATE);
			if (fingerVal != null)
				remarks = fingerVal.toString();
			if (fingerVal != null)
				if (fingerVal instanceof FingerDatePair) {
					remarks = "finger at: "
							+ this.dateFormatZone
									.format(new Date(
											((FingerDatePair) fingerVal)
													.getPRINT_TS()));

				}

			final String fnlRemarks = remarks;

			if (tableModel.isStatusChanged(i)) {
				Runnable r = new Runnable() {
					public void run() {

						try {
							// TODO change 220 to current user id
							setCursor(Cursor.WAIT_CURSOR);
							restConnector.updateUserStatus(sessionid,
									studentid, restConnector.getCurrentUserId()
											+ "", statusid, fst, fnlRemarks);
						} catch (ProtocolException e1) {
							// TODO Auto-generated catch block
							showException(e1);
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							showException(e2);
							e2.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							showException(e);
							e.printStackTrace();
						} finally {
							setCursor(Cursor.DEFAULT_CURSOR);
						}
					}
				};
				r.run();
			}
		}
	}

	protected void undoStatusChange(ActionEvent e) {
		// TODO optimize ?

		showSession(activeSessionNode);

	}

	/**
	 * compare status and fingerdate if finger status is not present and finger
	 * date is valid then set status to present
	 */
	protected void autoSetPresent(boolean isAll) {
		// TODO Auto-generated method stub
		int rn = tableModel.getRowCount();

		// System.out.println("aSP() row count:"+rn);
		// System.out.println("aSP() getVA() 0,0:"+tableModel.getValueAt(0,
		// SessionUserTableModel.COL_STATUS));
		// System.out.println("aSP() getVA() 0,0:"+tableModel.getValueAt(rn-1,
		// SessionUserTableModel.COL_STATUS));
		for (int i = 0; i < rn; i++) {
			Object status = tableModel.getValueAt(i,
					SessionUserTableModel.COL_STATUS).toString();
			Object tmp = tableModel.getValueAt(i,
					SessionUserTableModel.COL_FINGERDATE);
			// System.out.println("r " + i + " fingerdate:" + tmp + ", class:"
			// + tmp.getClass());
			if (!status.toString().equalsIgnoreCase("Present")) {
				if (!isAll) {
					System.out.println("autoSpr isAll = " + isAll);
					if (tmp instanceof FingerDatePair) { // TODO
															// not
															// taken
						boolean inTime = false; // is this our time ?

						SessionDetail session = tableModel
								.getJSONSessionetail();
						long sessionStart = session.sessdate;
						long sessEnd = session.duration + sessionStart; // session
																		// duration
																		// in
																		// seconds
						FingerDatePair fdp = (FingerDatePair) tmp;
						long fingerTime = fdp.getPRINT_TS() / 1000; // java.sql.Timestamp
																	// millis in
																	// 1/1000 to
																	// moodlee
																	// time

						// TODO 'earlier time' taken shadows 'correct time'
						if (fingerTime >= sessionStart & fingerTime <= sessEnd) {
							inTime = true;
						}

						// debugging
						String p = "autoSpr start:" + sessionStart + ", end:"
								+ sessEnd + ", time:" + fingerTime + " ==>"
								+ inTime;
						System.out.println(p);

						if (inTime)
							tableModel.setValueAt("Present", i,
									SessionUserTableModel.COL_STATUS);
					}
				} else {// isALL==true
					tableModel.setValueAt("Present", i,
							SessionUserTableModel.COL_STATUS);
				}

			}
		}

		btnAutoSetPresent.setEnabled(false);
		if (isAll)
			btnSetAllPresent.setEnabled(false);

	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	void connectAndRefreshTree() {
		int dialogButton = JOptionPane.showConfirmDialog(null,
				LL.TR("Get Data From server?"), LL.TR("Confirm"),
				JOptionPane.YES_NO_OPTION);

		if (dialogButton == JOptionPane.YES_OPTION) {
			if (tableModel != null)
				// if (tableModel.getDataVector() != null)
				// tableModel.getDataVector().removeAllElements(); //NOT WORK ?
				tableModel.setRowCount(0); // https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
		} else {
			return;
		}
		btnSyncChangeMoodle.setEnabled(false);//
		btnUndoChange.setEnabled(false);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					setCursor(Cursor.WAIT_CURSOR);

					btnAutoSetPresent.setEnabled(false);

					MoodleWSURL url = (MoodleWSURL) moodleURL.getSelectedItem();

					restConnector.setMoodleURL(url.url);

					// restConnector.setUsername("007");// TODO ask user input
					// restConnector.setPassword("007"); // TODO ask user input
					restConnector.setUsername(url.getUsername());
					restConnector.setPassword(url.getPassword());

					// root.removeAllChildren();

					// Date
					// d=dateFormat.parse(courseDate.getItemAt(0).toString());//same
					// formatter is used for Jcombobox
					// desc.getUserName();

					/*
					 * Session[] caches = mysqlConnector
					 * .loadSessionCaches(restConnector .getMoodleUsername());
					 * if (caches != null) { util.Logger.log(this,
					 * " sess cache found: " + caches.length); for (int id = 0;
					 * id < caches.length; id++) { root.addDateRoot(new Date(
					 * caches[id].sessdate * 1000)); }
					 * 
					 * }
					 */

					final Date d = dateFormat.parse(courseDate.getEditor()
							.getItem().toString());// same formatter is used for
													// Jcombobox

					DefaultMutableTreeNode dn = root.getDate(d);
					if (dn != null) {
						int dialogButton = JOptionPane.showConfirmDialog(null,
								"Date exist! Delete and Refresh ?",
								"Confirmation", JOptionPane.YES_NO_OPTION);

						if (dialogButton != JOptionPane.YES_OPTION) {
							return;
						}

						dn.removeAllChildren();
					}

					restConnector.setService(url.serviceName);

					final CoursesHolder ch = new CoursesHolder();

					Runnable r = new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								// ch.course = restConnector.get_date_courses(d
								// .getTime() + "");
								ch.course = restConnector
										.asyncGet_date_courses(d.getTime() + "");

							} catch (Exception e) {
								// TODO Auto-generated catch block
								showException(e);
								// e.printStackTrace();
								e.printStackTrace();
							}

						}

					};

					r.run();

					// wait or cancell connection ....
					// waitTillFinish(restConnector);

					Course[] courses = ch.course;
					if (courses == null)
						return;
					// System.out.print("course ret: " + courses.length);

					for (int i = 0; i < courses.length; i++) {
						DefaultMutableTreeNode courseNode = root.addCourse(
								courses[i], d);

						AttendanceInstance[] atts = courses[i]
								.getAttendance_instances();

						for (int J = 0; J < atts.length; J++) {
							DefaultMutableTreeNode attN = root.addAttendance(
									courses[i], atts[J]);
							Session[] sess = atts[J].today_sessions;
							for (int K = 0; K < sess.length; K++) {
								attN.add(new DefaultMutableTreeNode(sess[K]));
								// if(mysqlConnector=null)conn
								mysqlConnector
										.setMySQLDescriptor(mysqlDescriptor); // FIX
								// :
								// is
								// updated
								// ?
								/*
								 * mysqlConnector.addTeachersSessionCache(
								 * restConnector.getMoodleUsername(), sess[K]);
								 */
							}

						}

					}
					if (courses.length > 0) {
						CachedDate cd = new CachedDate();
						cd.setTime(d.getTime());
						mysqlConnector.addTeachersDatesCached(
								restConnector.getMoodleUsername(), cd);
					}
					DefaultTreeModel ctModel = (DefaultTreeModel) courseTree
							.getModel();
					ctModel.reload(root);
					// userSessionTable.setModel(null);

					// .setVisible(true);

				} catch (Exception e) {
					showException(e);
					e.printStackTrace();

				} finally {
					setCursor(Cursor.DEFAULT_CURSOR);
				}
			}
		});

	}

	/**
	 * if remote_id[] not empty get local_id[] and store in memory
	 * 
	 * @param users
	 * @return
	 * @throws Exception
	 */

	public RemotePair[] populate_from_local(MoodleUser[] users)
			throws Exception {
		// TODO Auto-generated method stub

		if (users == null)
			return null;
		// get current remote_id[]
		mysqlConnector = new MysqlConnector(null, null, null, null);
		long[] rids = new long[users.length];

		// System.out.println("p_f_l user info length:" + rids.length);
		for (int i = 0; i < rids.length; i++) {
			rids[i] = users[i].id;
		}

		mysqlConnector.setMySQLDescriptor(mysqlDescriptor);

		return mysqlConnector.get_remote_pairs(rids);
		// get local_id[] for remote_id[]

		// return null;
	}

	public JPanel getStudentPanel() {
		return studentPanel;
	}

	public JdbTable getUserSessionTable() {
		return userSessionTable;
	}

	public JComboBox getJBCourseDate() {
		return courseDate;
	}

	public JButton getBtnAutoSetPresent() {
		return btnAutoSetPresent;
	}

	public JButton getBtnSyncChangeMoodle() {
		return btnSyncChangeMoodle;
	}

	public JButton getBtnUndoChange() {
		return btnUndoChange;
	}

	public JComboBox getMoodleURL() {
		return moodleURL;
	}

	protected JLabel getLblCourse() {
		return lblCourse;
	}

	protected JLabel getLblDate() {
		return lblDate;
	}

	protected JLabel getLblFinger() {
		return lblFinger;
	}

	// listeners

	public void mouseClicked(MouseEvent me) {
		util.Logger.log("mouse Clicked");
		if (me.getClickCount() == 2) { // to detect doble click events
			JTable target = (JTable) me.getSource();
			int row = target.getSelectedRow(); // select a row
			int column = target.getSelectedColumn(); // select a column
			// JOptionPane.showMessageDialog(null,
			// userSessionTable.getValueAt(row, column)); // get the
			// value of a row and column.
			handleRow2Clicked(row, column, userSessionTable);
		}

	}

	public void mouseReleased(MouseEvent me) {
		util.Logger.log(this, "launcher mouse released");

		Enumeration<TableColumn> tce = userSessionTable.getColumnModel()
				.getColumns();

		while (tce.hasMoreElements()) {
			TableColumn tc = tce.nextElement();
			// System.out.println("launcher width: " + tc.getWidth());

		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// util.Logger.log("launcher mouse 2");

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// util.Logger.log("launcher mouse 1");

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		// util.Logger.log("launcher mouse 3");

	}

	@Override
	public void BioScanSucccess(ScanEvent ev) {
		// TODO BAD performance here, should only refresh changed rows
		util.Logger.log(this, "BioScanSucccess");
		showSession(activeSessionNode);
	}

	@Override
	public void BioScanFailed(ScanEvent ev) {
		// TODO Auto-generated method stub

	}

}

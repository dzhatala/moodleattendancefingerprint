package cpintar.biometric.zkteco;

import java.awt.Color;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import com.borland.dx.dataset.DataSetException;
import com.borland.dx.sql.dataset.ConnectionDescriptor;
import com.borland.dx.sql.dataset.ConnectionUpdateEvent;
import com.borland.dx.sql.dataset.ConnectionUpdateListener;
import com.mysql.jdbc.EscapeTokenizer;
import com.zkteco.biometric.FingerprintCaptureListener;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

import cpintar.biometric.BioScanListener;
import cpintar.biometric.BioScanner;
import cpintar.biometric.ScanEvent;
import cpintar.tts.TTSReader;
import cpintar.usb.USBLister;
import db.MysqlConnector;

import javax.swing.JTextField;
import javax.swing.JScrollPane;

import org.jfugue.player.Player;

import json.MoodleUser;
import util.Logger;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JCheckBox;

/**
 * compatible with c version https://github.com/dzhatala/zk4500biotime the
 * loading is based on template file of ZK 10 version TRUE FPID is extracted
 * from template file name ....
 * 
 * @author zh
 * 
 */
public class ZKFPBioManager extends JDialog implements BioScanner {

	/**
	 * memory/transient fpid to persistent filename
	 */
	Hashtable<Integer, String> fpid2template = new Hashtable<Integer, String>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton btnOpen = null;
	JButton btnEnroll = null;
	JButton btnVerify = null;
	JButton btnIdentify = null;
	JButton btnRegTPL10 = null;
	JButton btnIdentImg = null;
	JButton btnClose = null;
	JButton btnImg = null;
	JRadioButton radioISO = null;
	JRadioButton radioANSI = null;
	JRadioButton radioZK = null;

	private JTextArea textArea;

	// the width of fingerprint image
	int fpWidth = 0;
	// the height of fingerprint image
	int fpHeight = 0;
	// for verify test
	private byte[] lastRegTemp = new byte[2048];
	// the length of lastRegTemp
	private int cbRegTemp = 0;
	// pre-register template
	private byte[][] regtemparray = new byte[3][2048];
	// Register
	private boolean bRegister = false;
	// Identify
	private boolean bIdentify = true;
	// finger id
	private int iFid = 1;

	private int nFakeFunOn = 1;
	// must be 3
	static final int enroll_cnt = 3;
	// the index of pre-register function
	private int enroll_idx = 0;

	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	private int[] templateLen = new int[1];

	private boolean mbStop = true;
	private long mhDevice = 0;
	private long mhDB = 0;
	private WorkThread workThread = null; // working thread for zkteco
	private JTextField textField;
	private JScrollPane scrollPane;
	private MysqlConnector mySQLConn = null;
	protected String[] ZKFP_C_basedirs = new String[] {
			"Z:\\remove_programs\\yogacopy\\javaws\\hatala",
			"Z:\\remove_programs\\yogacopy\\javaws\\ang_2022",
	// "Z:\\remove_programs\\yogacopy\\javaws\\ang_2021",

	// based
	};
	// protected String ZKFP_C_basedir =
	// "Z:\\remove_programs\\yogacopy\\javaws\\2024";// based
	// dir
	// of
	// C
	// version
	private String IMAGE_DIR = "Z:\\remove_programs\\yogacopy\\javaws\\images";

	private JTextArea textUser;

	SimpleDateFormat imageDateFormat = new SimpleDateFormat(
			"yyyy_MM_dd_hh_mm_ss");

	Player audioplayer = new Player();

	Process ttsProcess, usbProcess = null;
	TTSReader ttsReader = new TTSReader();
	USBLister usbLister = new USBLister();
	private JTextField textField_1;
	private JButton btnMysqlOn;
	private JComboBox comboFinger;
	private JTextField textFPID;
	private JLabel label_1;
	private Vector<BioScanListener> bioSls = new Vector<BioScanListener>();

	public String[] getZKFP_C_basedirs() {
		return ZKFP_C_basedirs;
	}

	public void setZKFP_C_basedirs(String[] dirs) {
		ZKFP_C_basedirs = dirs;
	}

	public MysqlConnector getMc() {
		return mySQLConn;
	}

	public void setMySQLConnector(MysqlConnector mc) {
		this.mySQLConn = mc;
		if (mc != null) {
			mc.getDatabase().addConnectionUpdateListener(
					new ConnectionUpdateListener() {

						@Override
						public void connectionOpening(ConnectionUpdateEvent arg0) {
							// TODO Auto-generated method stub
							btnMysqlOn.setText("MySQL is ON");
						}

						@Override
						public void connectionClosed(ConnectionUpdateEvent arg0) {
							// TODO Auto-generated method stub
							btnMysqlOn.setText("MySQL is OFF");
						}

						@Override
						public void connectionChanged(ConnectionUpdateEvent arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void canChangeConnection(
								ConnectionUpdateEvent arg0) throws Exception {
							// TODO Auto-generated method stub

						}
					});
		}
	}

	long swingTimer = new Date().getTime();
	private JCheckBox chckbxAutoconnect;

	public ZKFPBioManager() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				// util.Logger.log(this, getBounds());
				// this.o
				if (new Date().getTime() - swingTimer > 2000) {
					saveLocationPreferences(getLocation());
				}
			}
		});
		getContentPane().setLayout(null);
		btnOpen = new JButton("Open");
		getContentPane().add(btnOpen);
		int nRsize = 20;
		btnOpen.setBounds(10, 70, 130, 30);

		btnEnroll = new JButton("Enroll");
		btnEnroll.setEnabled(false);
		getContentPane().add(btnEnroll);
		btnEnroll.setBounds(58, 191, 130, 30);

		btnMysqlOn = new JButton("MySQL is OFF");
		getContentPane().add(btnMysqlOn);
		btnMysqlOn.setBounds(10, 145, 130, 30);

		btnRegTPL10 = new JButton("LOAD TPL");
		getContentPane().add(btnRegTPL10);
		btnRegTPL10.setBounds(10, 104, 130, 30);

		btnClose = new JButton("RECONNECT USB (CLOSE)");
		btnClose.setEnabled(false);
		getContentPane().add(btnClose);
		btnClose.setBounds(10, 32, 200, 30);

		// For ISO/Ansi/ZK
		radioANSI = new JRadioButton("ANSI", true);
		getContentPane().add(radioANSI);
		radioANSI.setBounds(176, 78, 60, 30);

		radioISO = new JRadioButton("ISO");
		radioISO.setEnabled(false);
		getContentPane().add(radioISO);
		radioISO.setBounds(176, 98, 60, 30);

		radioZK = new JRadioButton("ZK");
		radioZK.setEnabled(false);
		getContentPane().add(radioZK);
		radioZK.setBounds(176, 118, 60, 30);

		ButtonGroup group = new ButtonGroup();
		group = new ButtonGroup();
		group.add(radioANSI);
		group.add(radioISO);
		group.add(radioZK);
		// For End

		btnImg = new JButton();
		btnImg.setBounds(246, 11, 343, 385);
		btnImg.setDefaultCapable(false);
		getContentPane().add(btnImg);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 403, 579, 177);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setSelectedTextColor(Color.RED);

		textUser = new JTextArea();
		textUser.setLineWrap(true);
		textUser.setEditable(false);
		textUser.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 15));
		textUser.setBounds(66, 309, 170, 41);
		getContentPane().add(textUser);

		textField_1 = new JTextField();
		textField_1.setText("-1");
		textField_1.setBounds(10, 308, 46, 30);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JButton button = new JButton(">");
		button.setBounds(188, 361, 48, 27);
		getContentPane().add(button);

		JButton btnNewButton = new JButton("<");
		btnNewButton.setBounds(10, 362, 48, 30);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("-");
		btnNewButton_1.setBounds(76, 361, 48, 30);
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("+");
		btnNewButton_2.setBounds(127, 361, 51, 30);
		getContentPane().add(btnNewButton_2);

		comboFinger = new JComboBox();
		comboFinger.setEnabled(false);
		comboFinger.setModel(new DefaultComboBoxModel(new String[] {
				"INDEX (RIGHT)", "PINKY (LEFT)", "RING (LEFT)", "MIDDLE(LEFT)",
				"INDEX(LEFT)", "THUMB(LEFT)", "PINKY (RIGHT)", "RING  (RIGHT)",
				"MIDDLE (RIGHT)", "THUMB (RIGHT)" }));
		comboFinger.setBounds(116, 271, 120, 27);
		getContentPane().add(comboFinger);

		textFPID = new JTextField();
		textFPID.setEditable(false);
		textFPID.setBounds(35, 266, 75, 32);
		getContentPane().add(textFPID);
		textFPID.setColumns(10);

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(20, 232, 89, 23);
		getContentPane().add(btnUpdate);

		JButton btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.setBounds(127, 237, 89, 23);
		getContentPane().add(btnUndo);

		JLabel lblFpid = new JLabel("FPID");
		lblFpid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFpid.setBounds(0, 257, 33, 50);
		getContentPane().add(lblFpid);

		label_1 = new JLabel("New label");
		label_1.setBounds(-76, 256, 200, 50);
		getContentPane().add(label_1);

		chckbxAutoconnect = new JCheckBox("AutoConnect");
		chckbxAutoconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// util.Logger.log(chckbxAutoconnect, "user click");
				handleAutoConnectChanged(e);
			}
		});
		chckbxAutoconnect.setBounds(10, 2, 167, 32);
		getContentPane().add(chckbxAutoconnect);

		this.setSize(605, 620);
		this.setLocationRelativeTo(null);
		// this.setVisible(true);
		this.setTitle("ZKFP Scanner");
		this.setResizable(false);

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openZKTecoScanner(btnOpen, e);
			}
		});

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FreeSensor();
				if (mySQLConn != null) {
					mySQLConn.disconnect();
				}
				log("Close succ!\n");
			}
		});

		btnEnroll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (0 == mhDevice) {
					log("Please Open device first!\n");
					return;
				}
				if (!bRegister) {
					enroll_idx = 0;
					bRegister = true;
					log("Please your finger 3 times!\n");
				}
			}
		});

		btnMysqlOn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mysql_onoff();

			}
		});

		/*
		 * btnRegImg.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if(0 == mhDB)
		 * { log("Please open device first!\n"); } //String path =
		 * "d:\\test\\fingerprint.bmp"; //String path = ".\\fingerprint.bmp";
		 * String path = ".\\FPID_6_2018_09_19_08_05_06.jpg"; byte[] fpTemplate
		 * = new byte[2048]; int[] sizeFPTemp = new int[1]; sizeFPTemp[0] =
		 * 2048; int ret = FingerprintSensorEx.ExtractFromImage( mhDB, path,
		 * 500, fpTemplate, sizeFPTemp); if (0 == ret) { ret =
		 * FingerprintSensorEx.DBAdd( mhDB, iFid, fpTemplate); if (0 == ret) {
		 * //String base64 = fingerprintSensor.BlobToBase64(fpTemplate,
		 * sizeFPTemp[0]); iFid++; cbRegTemp = sizeFPTemp[0];
		 * System.arraycopy(fpTemplate, 0, lastRegTemp, 0, cbRegTemp); //Base64
		 * Template //String strBase64 = Base64.encodeToString(regTemp, 0, ret,
		 * Base64.NO_WRAP); log("enroll succ\n"); } else {
		 * log("DBAdd fail, ret=" + ret + "\n"); } } else {
		 * log("ExtractFromImage fail, ret=" + ret + "\n"); } } });
		 */

		btnRegTPL10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// String
				// path1="Z:\\remove_programs\\yogacopy\\master\\TPL10_1.tpl";
				// addTemplate10(path1);
				// path1="Z:\\remove_programs\\yogacopy\\master\\TPL10_5.tpl";
				// addTemplate10(path1);
				// path1="Z:\\remove_programs\\yogacopy\\master\\TPL10_6.tpl";
				// addTemplate10(path1);
				// path1="Z:\\remove_programs\\yogacopy\\master\\TPL10_7.tpl";
				// addTemplate10(path1);
				// path1="Z:\\remove_programs\\yogacopy\\master\\TPL10_334.tpl";
				// addTemplate10(path1);

				//
				// String dir = "Z:\\remove_programs\\yogacopy\\master";
				loadTPLDirectories();
			}
		});

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				log("#Closing window#");
				int status = 0;
				try {
					chckbxAutoconnect.setSelected(false);
					handleAutoConnectChanged(null);
					if (autoConnectThread != null)
						autoConnectThread.interrupt();
					closeFP();
					if (mySQLConn != null)
						mySQLConn.disconnect();
					if (autoConnectThread != null)
						autoConnectThread.interrupt();// stop autoconnecthread
														// if exist
				} catch (Exception ex) {
					status = -1;
				}
				// System.exit(status);
			}
		});
	}

	public void loadTPLDirectories() {
		for (int i = 0; i < ZKFP_C_basedirs.length; i++) {

			String dir = ZKFP_C_basedirs[i] + "\\master";
			boolean dirValid = util.FileUtils.checkDir(dir);
			if (!dirValid) {
				log("'" + dir + "' is not valid directory");
			}
			loadTPL10Directory(dir);
		}
		log("Total load :" + iFid + " Templates");

	}

	protected void openZKTecoScanner(Object btnOpen2, ActionEvent e) {
		// TODO Auto-generated method stub
		if (0 != mhDevice) {
			// already inited
			log("Please close device first!\n");
			return;
		}

		int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
		// Initialize
		cbRegTemp = 0;
		bRegister = false;
		// bIdentify = false;
		iFid = 1;
		enroll_idx = 0;
		ret = FingerprintSensorEx.Init();
		if (FingerprintSensorErrorCode.ZKFP_ERR_OK != ret) {
			log("Init failed!, ret=" + ret + ", check cable USB \n");
			closeFP();
			return;
		}
		ret = FingerprintSensorEx.GetDeviceCount();
		if (ret < 0) {
			log("No devices connected!\n");
			FreeSensor();
			return;
		}
		if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0))) {
			log("Open device fail, ret = " + ret + "!\n");
			FreeSensor();
			return;
		}
		if (0 == (mhDB = FingerprintSensorEx.DBInit())) {
			log("Init DB fail, ret = " + ret + "!\n");
			FreeSensor();
			return;
		}

		// For ISO/Ansi
		int nFmt = 0; // Ansi
		if (radioISO.isSelected()) {
			nFmt = 1; // ISO
		}
		FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);
		// For ISO/Ansi End

		// set fakefun off
		// FingerprintSensorEx.SetParameter(mhDevice, 2002,
		// changeByte(nFakeFunOn), 4);

		byte[] paramValue = new byte[4];
		int[] size = new int[1];
		// GetFakeOn
		// size[0] = 4;
		// FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue,
		// size);
		// nFakeFunOn = byteArrayToInt(paramValue);

		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
		fpWidth = byteArrayToInt(paramValue);
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
		fpHeight = byteArrayToInt(paramValue);

		imgbuf = new byte[fpWidth * fpHeight];
		// btnImg.resize(fpWidth, fpHeight);
		mbStop = false;
		workThread = new WorkThread();
		workThread.start();// çº¿ç¨‹å�¯åŠ¨
		log("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight
				+ "\n");

	}

	Thread autoConnectThread = null;
	PrintStream customPS = new PrintStream(System.out) {
		public void test() {

		};

		// public void printStr(Object1 obj);

		public void println(Object obj) {
			// System.out.println(obj.getClass());
		}

		public void println(String str) {
			if (str.indexOf("desc=libusb0-dll:err") >= 0) {
				print("##ZKTECO USB ERROR ##");
				closeFP();
			}
			// super.println(str);
			// this.print(str.getClass());
			// System.out.println(str.getClass());

		}

		public PrintStream printf(Locale l, String format, Object... args) {
			return null;
		}

		public PrintStream printf(String format, Object... args) {
			return null;
		}

	};

	/**
	 * /** this message is output on console when, zkteco is opened, and lost
	 * usb before explicitlit closed ""usb read error, desc=libusb0-dll:err
	 * [control_msg] sending control message failed, win error: The device does
	 * not recognize the command""
	 * 
	 * @param chckbxAutoconnect2
	 * @param e
	 */

	protected void handleAutoConnectChanged(ActionEvent e) {
		// TODO Auto-generated method stub
		if (chckbxAutoconnect.isSelected()) {
			// btnClose.setEnabled(false);
			btnOpen.setEnabled(false);
			btnRegTPL10.setEnabled(false);
			btnMysqlOn.setEnabled(false);
			if (autoConnectThread != null)
				autoConnectThread.interrupt();
			autoConnectThread = new Thread() {

				public void run() {
					util.Logger.log("Autoconnect enabled ");
					// System.setOut(customPS);
					// System.setErr(customPS);
					while (true) {
						try {

							// util.Logger.log("Autoconnect usb : mhDevice="
							// + mhDevice + ", devcnt="
							// + FingerprintSensorEx.GetDeviceCount()
							// + ", dbcount"
							// + FingerprintSensorEx.DBCount(mhDB)
							// + ", ret_init=" + ret);
							if (usbProcess != null) {
								usbProcess.destroy();
							}
							try {
								usbProcess = usbLister.listUSB();
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(
												usbProcess.getInputStream()));
								String line = "";
								boolean zkfound = false;
								while ((line = reader.readLine()) != null) {
									// System.out.println ("Stdout: " +
									// line);
									if (line.indexOf("1b55") >= 0) {
										zkfound = true;
										break;
									}
								}

								if (!zkfound) {
									util.Logger
											.log("sub zkteco not found, cable ?? close and clean");
									closeFP();
									// break; break will remove autodectecion

								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break;
							}

							int ret = FingerprintSensorEx.Init();
							if (mhDevice == 0 || mhDB == 0
									|| FingerprintSensorEx.DBCount(mhDB) <= 0) {
								closeFP();
								openZKTecoScanner(null, null);
								try {
									mySQLConn.connect();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								loadTPLDirectories();
								/*
								 * String dir = ZKFP_C_basedir + "\\master";
								 * 
								 * loadTPL10Directory(dir);
								 */

							}
							// ret=FingerprintSensorEx.SetParameters(mhDevice,
							// 102,changeByte(1), 4);

							setGreenLEDONFFF(true);
							Thread.currentThread().sleep(1 * 200);
							setGreenLEDONFFF(false);
							Thread.currentThread().sleep(1 * 200);

							// util.Logger.log("setparam: ret="+ret);
							Thread.currentThread().sleep(2 * 1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							setGreenLEDONFFF(false);
							setREDLEDONFFF(false);
							util.Logger.log("stopping autoconnect usb");
							break;
						}
					}

				}

			};

			autoConnectThread.start();
		} else {
			if (autoConnectThread != null)
				autoConnectThread.interrupt();
			autoConnectThread.stop();
			btnClose.setEnabled(true);
			btnOpen.setEnabled(true);
			btnRegTPL10.setEnabled(true);
			btnMysqlOn.setEnabled(true);
		}
	}

	/**
	 * 
	 * @param b
	 *            true =ONN, false=OFF
	 * @return
	 */
	protected int setGreenLEDONFFF(boolean b) {
		// TODO Auto-generated method stub
		int onoff = b ? 1 : 0;
		return FingerprintSensorEx.SetParameters(mhDevice, 102,
				changeByte(onoff), 4);

	}

	/**
	 * 
	 * @param b
	 *            true =ONN, false=OFF
	 * @return
	 */
	protected int setREDLEDONFFF(boolean b) {
		// TODO Auto-generated method stub
		int onoff = b ? 1 : 0;
		return FingerprintSensorEx.SetParameters(mhDevice, 103,
				changeByte(onoff), 4);

	}

	protected void saveLocationPreferences(Point location) {
		// TODO Auto-generated method stub
		Preferences prefs = Preferences.userRoot().node(getClass().toString());
		prefs.putInt("LOC_X", location.x);
		prefs.putInt("LOC_Y", location.y);
		// util.Logger.log(this.getClass(), " save => " + location);
	}

	protected Point loadLocationPreferences() {
		Point ret = new Point();

		Preferences prefs = Preferences.userRoot().node(getClass().toString());
		ret.x = prefs.getInt("LOC_X", 50);
		ret.y = prefs.getInt("LOC_Y", 50);
		return ret;
	}

	public ZKFPBioManager(Window windowForComponent) {
		// TODO Auto-generated constructor stub
		this();
	}

	protected void mysql_onoff() {
		// TODO Auto-generated method stub
		boolean ison = mySQLConn.getDatabase().isOpen();
		if (ison) {
			/*
			 * MysqlConnector mc = new MysqlConnector(); ConnectionDescriptor
			 * desc = new ConnectionDescriptor(
			 * "jdbc:mysql://localhost:3306/absensi", "root", "", false,
			 * "com.mysql.jdbc.Driver"); // TODO profile GUI editor
			 * mc.setMySQLDescriptor(desc);
			 */

			mySQLConn.disconnect();

		} else {
			// MoodleUser user = mc.get_mdl_user(1);
			// Logger.log(user);
			try {
				mySQLConn.connect();
			} catch (DataSetException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				ttsSpeak("Basis Data Mati");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void ttsSpeak(String string) {
		// TODO Auto-generated method stub
		if (ttsProcess != null)
			ttsProcess.destroy();
		try {
			ttsProcess = ttsReader.espeakRead(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	private void FreeSensor() {
		mbStop = true;
		try { // wait for thread stopping
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB) {
			FingerprintSensorEx.DBFree(mhDB);
			mhDB = 0;
		}
		if (0 != mhDevice) {
			FingerprintSensorEx.CloseDevice(mhDevice);
			mhDevice = 0;
		}
		FingerprintSensorEx.Terminate();
	}

	public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
			String path) throws IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

		int w = (((nWidth + 3) / 4) * 4);
		int bfType = 0x424d; // ä½�å›¾æ–‡ä»¶ç±»åž‹ï¼ˆ0â€”1å­—èŠ‚ï¼‰
		int bfSize = 54 + 1024 + w * nHeight;// bmpæ–‡ä»¶çš„å¤§å°�ï¼ˆ2â€”5å­—èŠ‚ï¼‰
		int bfReserved1 = 0;// ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—ï¼Œå¿…é¡»ä¸º0ï¼ˆ6-7å­—èŠ‚ï¼‰
		int bfReserved2 = 0;// ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—ï¼Œå¿…é¡»ä¸º0ï¼ˆ8-9å­—èŠ‚ï¼‰
		int bfOffBits = 54 + 1024;// æ–‡ä»¶å¤´å¼€å§‹åˆ°ä½�å›¾å®žé™…æ•°æ�®ä¹‹é—´çš„å­—èŠ‚çš„å��ç§»é‡�ï¼ˆ10-13å­—èŠ‚ï¼‰

		dos.writeShort(bfType); // è¾“å…¥ä½�å›¾æ–‡ä»¶ç±»åž‹'BM'
		dos.write(changeByte(bfSize), 0, 4); // è¾“å…¥ä½�å›¾æ–‡ä»¶å¤§å°�
		dos.write(changeByte(bfReserved1), 0, 2);// è¾“å…¥ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—
		dos.write(changeByte(bfReserved2), 0, 2);// è¾“å…¥ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—
		dos.write(changeByte(bfOffBits), 0, 4);// è¾“å…¥ä½�å›¾æ–‡ä»¶å��ç§»é‡�

		int biSize = 40;// ä¿¡æ�¯å¤´æ‰€éœ€çš„å­—èŠ‚æ•°ï¼ˆ14-17å­—èŠ‚ï¼‰
		int biWidth = nWidth;// ä½�å›¾çš„å®½ï¼ˆ18-21å­—èŠ‚ï¼‰
		int biHeight = nHeight;// ä½�å›¾çš„é«˜ï¼ˆ22-25å­—èŠ‚ï¼‰
		int biPlanes = 1; // ç›®æ ‡è®¾å¤‡çš„çº§åˆ«ï¼Œå¿…é¡»æ˜¯1ï¼ˆ26-27å­—èŠ‚ï¼‰
		int biBitcount = 8;// æ¯�ä¸ªåƒ�ç´ æ‰€éœ€çš„ä½�æ•°ï¼ˆ28-29å­—èŠ‚ï¼‰ï¼Œå¿…é¡»æ˜¯1ä½�ï¼ˆå�Œè‰²ï¼‰ã€�4ä½�ï¼ˆ16è‰²ï¼‰ã€�8ä½�ï¼ˆ256è‰²ï¼‰æˆ–è€…24ä½�ï¼ˆçœŸå½©è‰²ï¼‰ä¹‹ä¸€ã€‚
		int biCompression = 0;// ä½�å›¾åŽ‹ç¼©ç±»åž‹ï¼Œå¿…é¡»æ˜¯0ï¼ˆä¸�åŽ‹ç¼©ï¼‰ï¼ˆ30-33å­—èŠ‚ï¼‰ã€�1ï¼ˆBI_RLEBåŽ‹ç¼©ç±»åž‹ï¼‰æˆ–2ï¼ˆBI_RLE4åŽ‹ç¼©ç±»åž‹ï¼‰ä¹‹ä¸€ã€‚
		int biSizeImage = w * nHeight;// å®žé™…ä½�å›¾å›¾åƒ�çš„å¤§å°�ï¼Œå�³æ•´ä¸ªå®žé™…ç»˜åˆ¶çš„å›¾åƒ�å¤§å°�ï¼ˆ34-37å­—èŠ‚ï¼‰
		int biXPelsPerMeter = 0;// ä½�å›¾æ°´å¹³åˆ†è¾¨çŽ‡ï¼Œæ¯�ç±³åƒ�ç´ æ•°ï¼ˆ38-41å­—èŠ‚ï¼‰è¿™ä¸ªæ•°æ˜¯ç³»ç»Ÿé»˜è®¤å€¼
		int biYPelsPerMeter = 0;// ä½�å›¾åž‚ç›´åˆ†è¾¨çŽ‡ï¼Œæ¯�ç±³åƒ�ç´ æ•°ï¼ˆ42-45å­—èŠ‚ï¼‰è¿™ä¸ªæ•°æ˜¯ç³»ç»Ÿé»˜è®¤å€¼
		int biClrUsed = 0;// ä½�å›¾å®žé™…ä½¿ç”¨çš„é¢œè‰²è¡¨ä¸­çš„é¢œè‰²æ•°ï¼ˆ46-49å­—èŠ‚ï¼‰ï¼Œå¦‚æžœä¸º0çš„è¯�ï¼Œè¯´æ˜Žå…¨éƒ¨ä½¿ç”¨äº†
		int biClrImportant = 0;// ä½�å›¾æ˜¾ç¤ºè¿‡ç¨‹ä¸­é‡�è¦�çš„é¢œè‰²æ•°(50-53å­—èŠ‚)ï¼Œå¦‚æžœä¸º0çš„è¯�ï¼Œè¯´æ˜Žå…¨éƒ¨é‡�è¦�

		dos.write(changeByte(biSize), 0, 4);// è¾“å…¥ä¿¡æ�¯å¤´æ•°æ�®çš„æ€»å­—èŠ‚æ•°
		dos.write(changeByte(biWidth), 0, 4);// è¾“å…¥ä½�å›¾çš„å®½
		dos.write(changeByte(biHeight), 0, 4);// è¾“å…¥ä½�å›¾çš„é«˜
		dos.write(changeByte(biPlanes), 0, 2);// è¾“å…¥ä½�å›¾çš„ç›®æ ‡è®¾å¤‡çº§åˆ«
		dos.write(changeByte(biBitcount), 0, 2);// è¾“å…¥æ¯�ä¸ªåƒ�ç´ å� æ�®çš„å­—èŠ‚æ•°
		dos.write(changeByte(biCompression), 0, 4);// è¾“å…¥ä½�å›¾çš„åŽ‹ç¼©ç±»åž‹
		dos.write(changeByte(biSizeImage), 0, 4);// è¾“å…¥ä½�å›¾çš„å®žé™…å¤§å°�
		dos.write(changeByte(biXPelsPerMeter), 0, 4);// è¾“å…¥ä½�å›¾çš„æ°´å¹³åˆ†è¾¨çŽ‡
		dos.write(changeByte(biYPelsPerMeter), 0, 4);// è¾“å…¥ä½�å›¾çš„åž‚ç›´åˆ†è¾¨çŽ‡
		dos.write(changeByte(biClrUsed), 0, 4);// è¾“å…¥ä½�å›¾ä½¿ç”¨çš„æ€»é¢œè‰²æ•°
		dos.write(changeByte(biClrImportant), 0, 4);// è¾“å…¥ä½�å›¾ä½¿ç”¨è¿‡ç¨‹ä¸­é‡�è¦�çš„é¢œè‰²æ•°

		for (int i = 0; i < 256; i++) {
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(0);
		}

		byte[] filter = null;
		if (w > nWidth) {
			filter = new byte[w - nWidth];
		}

		for (int i = 0; i < nHeight; i++) {
			dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
			if (w > nWidth)
				dos.write(filter, 0, w - nWidth);
		}
		dos.flush();
		dos.close();
		fos.close();
	}

	public static byte[] changeByte(int data) {
		return intToByteArray(data);
	}

	public static byte[] intToByteArray(final int number) {
		byte[] abyte = new byte[4];
		// "&"
		// ä¸Žï¼ˆANDï¼‰ï¼Œå¯¹ä¸¤ä¸ªæ•´åž‹æ“�ä½œæ•°ä¸­å¯¹åº”ä½�æ‰§è¡Œå¸ƒå°”ä»£æ•°ï¼Œä¸¤ä¸ªä½�éƒ½ä¸º1æ—¶è¾“å‡º1ï¼Œå�¦åˆ™0ã€‚
		abyte[0] = (byte) (0xff & number);
		// ">>"å�³ç§»ä½�ï¼Œè‹¥ä¸ºæ­£æ•°åˆ™é«˜ä½�è¡¥0ï¼Œè‹¥ä¸ºè´Ÿæ•°åˆ™é«˜ä½�è¡¥1
		abyte[1] = (byte) ((0xff00 & number) >> 8);
		abyte[2] = (byte) ((0xff0000 & number) >> 16);
		abyte[3] = (byte) ((0xff000000 & number) >> 24);
		return abyte;
	}

	public static int byteArrayToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;
		// "|="æŒ‰ä½�æˆ–èµ‹å€¼ã€‚
		number |= ((bytes[1] << 8) & 0xFF00);
		number |= ((bytes[2] << 16) & 0xFF0000);
		number |= ((bytes[3] << 24) & 0xFF000000);
		return number;
	}

	private class WorkThread extends Thread {
		@Override
		public void run() {
			super.run();
			int ret = 0;
			while (!mbStop) {
				templateLen[0] = 2048;
				// util.Logger.log("before acquire, ret=" + ret);
				if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(
						mhDevice, imgbuf, template, templateLen))) {
					if (nFakeFunOn == 1) {
						byte[] paramValue = new byte[4];
						int[] size = new int[1];
						size[0] = 4;
						int nFakeStatus = 0;
						// GetFakeStatus
						ret = FingerprintSensorEx.GetParameters(mhDevice, 2004,
								paramValue, size);
						nFakeStatus = byteArrayToInt(paramValue);
						log("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
						if (0 == ret && (byte) (nFakeStatus & 31) != 31) {
							log("Is a fake finger?\n");
							return;
						}
					}
					OnCatpureOK(imgbuf);
					OnExtractOK(template, templateLen[0]);
				}
				// util.Logger.log("after acquire, ret=" + ret);
				try {
					Thread.sleep(500);
					// Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private void OnCatpureOK(byte[] imgBuf) {
		// btnImg.setIcon(new ImageIcon(imgBuf));
		audioplayer.play("C");
		try {
			String bitmapfn = IMAGE_DIR + "\\fingerprint.bmp";
			writeBitmap(imgBuf, fpWidth, fpHeight, bitmapfn);
			btnImg.setIcon(new ImageIcon(ImageIO.read(new File(bitmapfn))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void OnExtractOK(byte[] template, int len) {
		if (bRegister) {
			int[] fid = new int[1];
			int[] score = new int[1];
			int ret = FingerprintSensorEx
					.DBIdentify(mhDB, template, fid, score);
			if (ret == 0) {
				log("the finger already enroll by " + fid[0]
						+ ",cancel enroll\n");
				bRegister = false;
				enroll_idx = 0;
				return;
			}
			if (enroll_idx > 0
					&& FingerprintSensorEx.DBMatch(mhDB,
							regtemparray[enroll_idx - 1], template) <= 0) {
				log("please press the same finger 3 times for the enrollment\n");
				return;
			}
			System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
			enroll_idx++;
			if (enroll_idx == 3) {
				int[] _retLen = new int[1];
				_retLen[0] = 2048;
				byte[] regTemp = new byte[_retLen[0]];

				if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB,
						regtemparray[0], regtemparray[1], regtemparray[2],
						regTemp, _retLen))
						&& 0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid,
								regTemp))) {
					iFid++;
					cbRegTemp = _retLen[0];
					System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
					// Base64 Template
					log("enroll succ:\n");

				} else {
					log("enroll fail, error code=" + ret + "\n");
				}
				bRegister = false;
			} else {
				log("You need to press the " + (3 - enroll_idx)
						+ " times fingerprint\n");
			}
		} else {
			if (bIdentify) {
				// textUser.setText("");// clear
				int[] fid = new int[1];
				int[] score = new int[1];
				int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid,
						score);

				final Date nowts = new Date();
				int fid_db = -1;
				if (ret == 0) {
					log("Identify succ, fid=" + fid[0] + ",score=" + score[0]
							+ "\n");
					log("reverse mapping: " + fid[0] + "->"
							+ fpid2template.get(fid[0]) + "\n");
					mysqlIdentifySuccess(fid[0], score[0], nowts);
					fid_db = fid[0];
				} else {
					if (ttsProcess != null)
						ttsProcess.destroy();
					try {
						ttsProcess = ttsReader.espeakRead("Tidak Di kenali");
						for (int il = 0; il < 5; il++) {
							setREDLEDONFFF(true);
							Thread.sleep(200);
							setREDLEDONFFF(false);
							Thread.sleep(200);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log("Identify fail, errcode=" + ret + "\n");
					// textUser.setText("FAILED!!!");
				}
				final int fnl_fid_db = fid_db;
				new Thread() {
					public void run() {
						backup_image(imgbuf, fnl_fid_db, nowts);

					}
				}.start();

			} else {
				if (cbRegTemp <= 0) {
					log("Please register first!\n");
				} else {
					int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp,
							template);
					if (ret > 0) {
						log("Verify succ, score=" + ret + "\n");
					} else {
						log("Verify fail, ret=" + ret + "\n");
					}
				}
			}
		}
	}

	private void backup_image(byte[] imgbuf2, int fid_db, Date nowts) {
		// TODO Auto-generated method stub
		try {
			String bitmapfn = IMAGE_DIR + "\\fingerprint.bmp";
			BufferedImage image = ImageIO.read(new File(bitmapfn));
			String userinfo = "";

			MoodleUser user = null;
			try {
				user = mySQLConn.get_mdl_user(fid_db);
			} catch (NotConnectedException ne) {
				nmySQLNotConnect(ne);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user != null && fid_db > 0) {
				userinfo = "_" + user.firstname + "_" + user.lastname + "_";
			} else {
				// textUser.setText("FPID "+fid_db+" not in mysql");
			}
			String fnout = IMAGE_DIR + "\\FPID_" + fid_db + "_"
					+ imageDateFormat.format(nowts) + userinfo + ".jpg";
			log("backup ==>" + fnout);
			File output = new File(fnout);

			// Write the image to the destination as a JPG
			ImageIO.write(image, "jpg", output);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void nmySQLNotConnect(NotConnectedException ne) {
		// TODO Auto-generated method stub
		if (ttsProcess != null)
			ttsProcess.destroy();
		try {
			ttsProcess = ttsReader.espeakRead("Akses Basis Data Gagal");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int mysqlIdentifySuccess(int fpid_temp, int score, Date nowts) {
		// TODO Auto-generated method stub
		int ret = -1;
		String templatefn = fpid2template.get(fpid_temp);
		final int FPID_C = extractFPIDfromFilename(templatefn);
		if (FPID_C > 0)
			textFPID.setText(FPID_C + "");
		ret = mySQLConn
				.insertIdentified1N(FPID_C, score, "JAVA_WS_BETA", nowts);

		ScanEvent ev = new ScanEvent();
		fireBioScanSucccess(ev);

		try {
			final MoodleUser found = mySQLConn.get_mdl_user(FPID_C);
			if (found == null && FPID_C > 0) {
				ttsSpeak( " TIDAK di  Basis Data. " + FPID_C );
				log("FPID_C:" + FPID_C + " not in mySQL\n");
				// textUser.setText("FPID " + FPID_C + " NOT in MySQL");
				return ret;
			}
			new Thread() {
				public void run() {

					if (ttsProcess != null)
						ttsProcess.destroy();
					try {
						ttsProcess = ttsReader.espeakRead(found.firstname + " "
								+ found.lastname);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					textUser.setText(found.firstname + " " + found.lastname
							+ ", " + found.username);
					textUser.repaint();

				}
			}.run();
			log(found + "\n");
			// text
		} catch (NotConnectedException nc) {
			log("MySQL is OFF \n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ret < 0)
			log("MySQL FALED\n");
		return ret;
	}

	private void fireBioScanSucccess(ScanEvent ev) {
		// TODO Auto-generated method stub
		for (BioScanListener l : bioSls) {
			l.BioScanSucccess(ev);
		}
	}

	/*
	 * public void open() { if (0 != mhDevice) { // already inited
	 * log("Please close device first!\n"); return; } int ret =
	 * FingerprintSensorErrorCode.ZKFP_ERR_OK; // Initialize cbRegTemp = 0;
	 * bRegister = false; bIdentify = false; iFid = 1; enroll_idx = 0;
	 * 
	 * ret= FingerprintSensorEx .Init(); if
	 * (FingerprintSensorErrorCode.ZKFP_ERR_OK !=ret) {
	 * log("Init failed!, ret "+ret+"\n"); return; } ret =
	 * FingerprintSensorEx.GetDeviceCount(); if (ret < 0) {
	 * log("No devices connected!\n"); FreeSensor(); return; } if (0 ==
	 * (mhDevice = FingerprintSensorEx.OpenDevice(0))) {
	 * log("Open device fail, ret = " + ret + "!\n"); FreeSensor(); return; } if
	 * (0 == (mhDB = FingerprintSensorEx.DBInit())) { log("Init DB fail, ret = "
	 * + ret + "!\n"); FreeSensor(); return; }
	 * 
	 * // For ISO/Ansi int nFmt = 0; // Ansi // if (radioISO.isSelected()) // {
	 * // nFmt = 1; //ISO // } FingerprintSensorEx.DBSetParameter(mhDB, 5010,
	 * nFmt); // For ISO/Ansi End
	 * 
	 * // set fakefun off // FingerprintSensorEx.SetParameter(mhDevice, 2002, //
	 * changeByte(nFakeFunOn), 4);
	 * 
	 * byte[] paramValue = new byte[4]; int[] size = new int[1]; // GetFakeOn //
	 * size[0] = 4; // FingerprintSensorEx.GetParameters(mhDevice, 2002,
	 * paramValue, size); // nFakeFunOn = byteArrayToInt(paramValue);
	 * 
	 * size[0] = 4; FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue,
	 * size); fpWidth = byteArrayToInt(paramValue); size[0] = 4;
	 * FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
	 * fpHeight = byteArrayToInt(paramValue);
	 * 
	 * imgbuf = new byte[fpWidth * fpHeight]; // btnImg.resize(fpWidth,
	 * fpHeight); mbStop = false; // workThread = new WorkThread(); //
	 * workThread.start();// çº¿ç¨‹å�¯åŠ¨ log("Open succ! Finger Image Width:" +
	 * fpWidth + ",Height:" + fpHeight + "\n");
	 * 
	 * }
	 */
	public void closeFP() {
		// TODO Auto-generated method stub
		setGreenLEDONFFF(false);
		setREDLEDONFFF(false);
		FreeSensor();

		log("Sensor closed!\n");
	}

	/*
	 * void test01() { open(); byte[] paramValue1 = new byte[4]; //
	 * FingerprintSensorEx.Int2ByteArray(1, paramValue1);
	 * 
	 * paramValue1 = ByteBuffer.allocate(4).putInt(1).array();
	 * 
	 * FingerprintSensorEx.SetParameters(mhDevice, 102, paramValue1, 4); try {
	 * Thread.currentThread().sleep(200); } catch (InterruptedException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } paramValue1 =
	 * ByteBuffer.allocate(4).putInt(0).array();
	 * FingerprintSensorEx.SetParameters(mhDevice, 102, paramValue1, 4);
	 * 
	 * }
	 */

	@Override
	public void addBioScanListener(BioScanListener l) {
		// TODO Auto-generated method stub
		bioSls.add(l);
	}

	@Override
	public void removeBioScanListener(BioScanListener l) {
		// TODO Auto-generated method stub
		bioSls.remove(l);
	}

	public int addTemplate10(String path1) {
		File nf = new File(path1);
		return addTemplate10(nf);
	}

	public int addTemplate10(File nf) {
		if (0 == mhDB) {
			log("Please open device first!\n");
			return -1;
		}
		// String path = "d:\\test\\fingerprint.bmp";
		// String path = ".\\fingerprint.bmp";
		// String path = ".\\FPID_6_2018_09_19_08_05_06.jpg";
		byte[] fpTemplate = new byte[2048];
		int[] sizeFPTemp = new int[1];
		sizeFPTemp[0] = 2048;
		// int ret = FingerprintSensorEx.ExtractFromImage( mhDB, path, 500,
		// fpTemplate, sizeFPTemp);
		// String
		// path1="G:\\rsync\\RESEARCHS\\moodle\\eclipse\\javaws\\moodle_att_https\\tpl\\TPL9_6.tpl";
		// String
		// path1="G:\\rsync\\RESEARCHS\\moodle\\eclipse\\javaws\\moodle_att_https\\tpl\\TPL10_6.tpl";
		try {
			// fpTemplate=Files.readAllBytes(nf.toPath());
			new BufferedInputStream(new FileInputStream(nf)).read(fpTemplate);
			// log("tpl length:" + nf.length());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// f.re
		int ret = 0; // fake extract
		if (0 == ret) {
			ret = FingerprintSensorEx.DBAdd(mhDB, iFid, fpTemplate);
			if (0 == ret) {
				// String base64 = fingerprintSensor.BlobToBase64(fpTemplate,
				// sizeFPTemp[0]);
				iFid++;
				cbRegTemp = sizeFPTemp[0];
				System.arraycopy(fpTemplate, 0, lastRegTemp, 0, cbRegTemp);
				// Base64 Template
				// String strBase64 = Base64.encodeToString(regTemp, 0, ret,
				// Base64.NO_WRAP);
				log("enroll succ #" + (iFid - 1) + "# " + nf.getAbsolutePath()
						+ "\n");
				fpid2template.put(iFid - 1, nf.getName());
				return iFid;
			} else {
				log("DBAdd fail, ret=" + ret + "\n");
			}
		} else {
			log("ExtractFromTPL fail, ret=" + ret + "\n");
		}

		return -1;
	}

	private void loadTPL10Directory(String sdir) {
		// TODO Auto-generated method stub
		if (0 == mhDB) {
			log("Please open device first!\n");
			return;
		}
		File fdir = new File(sdir);
		File[] files = fdir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {

				// if (name.startsWith("TPL9_"))
				// return true;

				if (!name.toLowerCase().startsWith("tpl10_"))
					return false;
				if (name.toLowerCase().endsWith(".tpl"))
					return true;

				return false;
			}
		});

		for (int i = 0; i < files.length; i++) {
			// if(i>20) break;
			log(files[i].getAbsolutePath());
			addTemplate10(files[i]);
		}

	}

	private void log(Object o) {
		if (o == null)
			return;
		log(o.toString());
	}

	private void log(String str) {
		// TODO Auto-generated method stub

		// Logger.log(str);

		textArea.append(str);
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());

	}

	/**
	 * backward compatibel old c version of zkfinger
	 * https://github.com/dzhatala/zk4500biotime
	 * 
	 * @param fname
	 *            filename
	 * @return FPID in mysql
	 */
	protected int extractFPIDfromFilename(String s) {
		// String s = "test string (67)";
		log("extractFPID from " + s + "\n");
		Pattern p = Pattern.compile("_.*?\\.");
		if (p == null | s == null)
			return -1;
		Matcher m = p.matcher(s);
		if (m.find()) {
			CharSequence cs = m.group().subSequence(1, m.group().length() - 1);
			String FPIDS = new String(cs.toString());
			// log();
			return new Integer(FPIDS).intValue();
		}
		return -1;
	}

	public void gotoPreferLocation() {
		setLocation(loadLocationPreferences());
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getLoadedLibraryNames() throws Exception {
		Set<String> allLibs = new TreeSet<String>();
		Field loadedLibraryNames = ClassLoader.class
				.getDeclaredField("loadedLibraryNames");
		loadedLibraryNames.setAccessible(true);
		// loadedLibraryNames.get
		// allLibs.addAll((List<String>)
		// loadedLibraryNames.get(SystemUtils.class.getClassLoader()));
		allLibs.addAll((Collection<? extends String>) loadedLibraryNames
				.get(ClassLoader.getSystemClassLoader()));
		// org.apache.commons.
		return allLibs;
	}

	public static void lunchZK() {

		final ZKFPBioManager bm = new ZKFPBioManager();

		// bm.extractFPIDfromFilename("TPL9_736.tpl");
		MysqlConnector mc = new MysqlConnector();

		ConnectionDescriptor desc = new ConnectionDescriptor(
				"jdbc:mysql://localhost:3306/absensi", "root", "", false,
				"com.mysql.jdbc.Driver"); // TODO profile GUI editor
		mc.setMySQLDescriptor(desc);
		bm.setMySQLConnector(mc);
		// bm.setDefaultCloseOperation(EXIT_ON_CLOSE);
		bm.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		bm.gotoPreferLocation();
		bm.setVisible(true);

		// ZKFPBioManager dm = new ZKFPBioManager();
		// dm.open();
		// String dir = "Z:\\remove_programs\\yogacopy\\master";
		// dm.loadTPL10Directory(dir);
		// dm.closeFP();
	}

	public static void main(String args[]) {
		// System.out.println(System.out.getClass()+" " +System.out);
		lunchZK();
	}

	static void testSound() {
		Player player = new Player();
		// player.play("A C# E");
		// player.play("C A B C D E F G H I");
		// player.play("C#");
	}

	public static void main2(String args[]) {
		Logger.log("test sound");
		testSound();
	}

	@Override
	public void removeAllBioScanListener() {
		// TODO Auto-generated method stub
		bioSls.removeAllElements();
	}

	/**
	 * disconnect bio device and database
	 */
	public void disconectAll() {
		// TODO Auto-generated method stub
		closeFP();
		boolean ison = mySQLConn.getDatabase().isOpen();
		if (ison) {
			/*
			 * MysqlConnector mc = new MysqlConnector(); ConnectionDescriptor
			 * desc = new ConnectionDescriptor(
			 * "jdbc:mysql://localhost:3306/absensi", "root", "", false,
			 * "com.mysql.jdbc.Driver"); // TODO profile GUI editor
			 * mc.setMySQLDescriptor(desc);
			 */

			mySQLConn.disconnect();

		}
	}

	public static void test2() {

		// FingerprintSensorEx.a/
		FingerprintCaptureListener l = new FingerprintCaptureListener() {

			@Override
			public void extractOK(byte[] arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void captureOK(byte[] arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void captureError(int arg0) {
				// TODO Auto-generated method stub

			}
		};
	}

	/***
	 * 
	 */
	public void addFingerContext(String parent) {

	}

	public void loadRootContext() {

	}
}

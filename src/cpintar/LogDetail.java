package cpintar;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import moodle_login_01.FingerDatePair;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogDetail extends JDialog {
	private JTextField localFIrstName;
	private JTextField localLastName;
	private JTextField remoteLastName;
	private JTextField remoteFirstName;
	private JTextField localID;
	private SessionUserTableModel tableModel;
	private int _row = -1;
	private JTextField remoteStatus;
	private int fingerTakenCount = 0;
	private JPanel panel;

	public LogDetail(Frame parent, int row, SessionUserTableModel tmodel) {
		super(parent);
		setTitle("LogDetail");
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_1 = new JLabel("Local Info");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblRemoteInfo = new JLabel("Remote Info");
		lblRemoteInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblRemoteInfo = new GridBagConstraints();
		gbc_lblRemoteInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemoteInfo.gridx = 4;
		gbc_lblRemoteInfo.gridy = 0;
		panel.add(lblRemoteInfo, gbc_lblRemoteInfo);

		JLabel lblLocalId = new JLabel("Local Id");
		lblLocalId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblLocalId = new GridBagConstraints();
		gbc_lblLocalId.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocalId.gridx = 0;
		gbc_lblLocalId.gridy = 2;
		panel.add(lblLocalId, gbc_lblLocalId);

		localID = new JTextField();
		localID.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_locaID = new GridBagConstraints();
		gbc_locaID.insets = new Insets(0, 0, 5, 5);
		gbc_locaID.fill = GridBagConstraints.HORIZONTAL;
		gbc_locaID.gridx = 1;
		gbc_locaID.gridy = 2;
		panel.add(localID, gbc_locaID);
		localID.setColumns(10);

		JLabel lblRemoteId = new JLabel("Remote Id");
		lblRemoteId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblRemoteId = new GridBagConstraints();
		gbc_lblRemoteId.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemoteId.gridx = 3;
		gbc_lblRemoteId.gridy = 2;
		panel.add(lblRemoteId, gbc_lblRemoteId);

		JTextArea remoteID = new JTextArea();
		remoteID.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_remoteID = new GridBagConstraints();
		gbc_remoteID.insets = new Insets(0, 0, 5, 5);
		gbc_remoteID.fill = GridBagConstraints.BOTH;
		gbc_remoteID.gridx = 4;
		gbc_remoteID.gridy = 2;
		panel.add(remoteID, gbc_remoteID);

		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		localFIrstName = new JTextField();
		localFIrstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_localFIrstName = new GridBagConstraints();
		gbc_localFIrstName.insets = new Insets(0, 0, 5, 5);
		gbc_localFIrstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_localFIrstName.gridx = 1;
		gbc_localFIrstName.gridy = 3;
		panel.add(localFIrstName, gbc_localFIrstName);
		localFIrstName.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("First Name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 3;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		remoteFirstName = new JTextField();
		remoteFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_remoteFirstName = new GridBagConstraints();
		gbc_remoteFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_remoteFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_remoteFirstName.gridx = 4;
		gbc_remoteFirstName.gridy = 3;
		panel.add(remoteFirstName, gbc_remoteFirstName);
		remoteFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 4;
		panel.add(lblLastName, gbc_lblLastName);

		localLastName = new JTextField();
		localLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_localLastName = new GridBagConstraints();
		gbc_localLastName.insets = new Insets(0, 0, 5, 5);
		gbc_localLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_localLastName.gridx = 1;
		gbc_localLastName.gridy = 4;
		panel.add(localLastName, gbc_localLastName);
		localLastName.setColumns(10);

		JLabel lblLastName_1 = new JLabel("Last Name");
		lblLastName_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblLastName_1 = new GridBagConstraints();
		gbc_lblLastName_1.anchor = GridBagConstraints.EAST;
		gbc_lblLastName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName_1.gridx = 3;
		gbc_lblLastName_1.gridy = 4;
		panel.add(lblLastName_1, gbc_lblLastName_1);

		remoteLastName = new JTextField();
		remoteLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_remoteLastName = new GridBagConstraints();
		gbc_remoteLastName.insets = new Insets(0, 0, 5, 5);
		gbc_remoteLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_remoteLastName.gridx = 4;
		gbc_remoteLastName.gridy = 4;
		panel.add(remoteLastName, gbc_remoteLastName);
		remoteLastName.setColumns(10);

		JLabel lblFingerDate = new JLabel("Finger Dates");
		lblFingerDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblFingerDate = new GridBagConstraints();
		gbc_lblFingerDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblFingerDate.anchor = GridBagConstraints.EAST;
		gbc_lblFingerDate.gridx = 0;
		gbc_lblFingerDate.gridy = 5;
		panel.add(lblFingerDate, gbc_lblFingerDate);

		JLabel lblLogStatus = new JLabel("Log Status");
		lblLogStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblLogStatus = new GridBagConstraints();
		gbc_lblLogStatus.anchor = GridBagConstraints.EAST;
		gbc_lblLogStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogStatus.gridx = 3;
		gbc_lblLogStatus.gridy = 5;
		panel.add(lblLogStatus, gbc_lblLogStatus);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		remoteStatus = new JTextField();
		remoteStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_remoteStatus = new GridBagConstraints();
		gbc_remoteStatus.insets = new Insets(0, 0, 5, 5);
		gbc_remoteStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_remoteStatus.gridx = 4;
		gbc_remoteStatus.gridy = 5;
		panel.add(remoteStatus, gbc_remoteStatus);
		remoteStatus.setColumns(10);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 3;
		gbc_btnClose.gridy = 7;
		panel.add(btnClose, gbc_btnClose);
		setModalityType(ModalityType.APPLICATION_MODAL);
		initGUI01(row, tmodel);

	}

	/** call from constractor */
	private void initGUI01(int row, SessionUserTableModel tmodel) {
		// TODO Auto-generated method stub
		this.tableModel = tmodel;
		this._row = row;

		renderValues();

		pack(); // centering
		setLocationRelativeTo(null);
	}

	private void renderValues() {
		// TODO Auto-generated method stub
		System.out.println("LogDetail: renderValues() tmpdel:" + tableModel);
		if (this.tableModel != null) {
			String firstname = tableModel.getRemotefirstName(this._row);
			this.remoteFirstName.setText(firstname);
			String lastname = tableModel.getRemoteLastname(_row);
			this.remoteLastName.setText(lastname);
			this.remoteStatus.setText(tableModel.getValueAt(_row,
					tableModel.COL_STATUS).toString());

			FingerDatePair fdp = tableModel.getFingerPair(this._row);
			if (fdp != null) {
				this.localFIrstName.setText(fdp.firstName);
				this.localLastName.setText(fdp.lastName);

				renderDatesRegname(fdp);
			}
		}

		// for test only
		renderDatesRegname(null);

	}

	private void renderDatesRegname(FingerDatePair fdp) {
		String[] dr = new String[2];
		// TODO Auto-generated method stub
		dr[0] = "senin";
		dr[1] = "progweb";
		// addFingerDate(dr);
		// addFingerDate(dr);
		// addFingerDate(dr);

		// TODO
		String regname[] = new String[2];
		// if(fdp.timestamp!=null)

	}

	private void addFingerDate(String[] dateReg) {
		// TODO Auto-generated method stub
		System.out.println("aFD");
		if (dateReg != null) {
			if (dateReg.length == 2) {
				// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
				JTextField t = new JTextField(dateReg[0] + " => " + dateReg[1]);
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 1;
				c.gridy = 5 + fingerTakenCount;
				panel.add(t, c);
				fingerTakenCount++;
			}
		}

	}

	public static void main(String args[]) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		LogDetail log = new LogDetail(f, -1, null);
		log.setVisible(true);

	}

}

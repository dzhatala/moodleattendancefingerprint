package cpintar;

import java.awt.GridBagLayout;

import javax.swing.JComboBox;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * MVC pattern .. detect in text fields ... JComboBox not listen to change in
 * text field, but will update model if button svae is pushed all jtextfields
 * listen to selection changed event in combo box
 * 
 * @author zulkarnaen hatala
 * 
 */
public class Settings extends JDialog {
	private JTextField hostname;
	private JTextField username;
	private JPasswordField password;
	private static Settings _instance = null;

	private MoodleWSURL[] WSurs = null;
	private JComboBox comboBox;
	private JTextField serviceName;
	private JTextField profileName;

	// TODO
	// load everything from properties file
	public Settings(String dirPath) {

	}

	// TODO
	public Settings(JFrame owner, MoodleWSURL[] urls) {
		//
		this(owner);

		_instance = this;
		renderUrls(urls);
		pack(); // centering
		setLocationRelativeTo(null);

	}

	private void renderUrls(MoodleWSURL[] urls) {
		// TODO Auto-generated method stub
		this.WSurs = urls;
		// renderUrls(urls);
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				comboBox.addItem(urls[i]);
			}
		}
	}

	/**
	 * @wbp.parser.constructor
	 */
	private Settings(JFrame owner) {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Connection", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblProfile = new JLabel("Daftar");
		GridBagConstraints gbc_lblProfile = new GridBagConstraints();
		gbc_lblProfile.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfile.anchor = GridBagConstraints.EAST;
		gbc_lblProfile.gridx = 1;
		gbc_lblProfile.gridy = 0;
		panel_1.add(lblProfile, gbc_lblProfile);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				handleItemStateChanged(e);
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);

		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.addItem(new MoodleWSURL("new profile", "edit url",
						"edit servicename"));
				comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnNewButton, gbc_btnNewButton);

		JLabel lblNewLabel = new JLabel("Profile Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		profileName = new JTextField();
		GridBagConstraints gbc_profileName = new GridBagConstraints();
		gbc_profileName.insets = new Insets(0, 0, 5, 5);
		gbc_profileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_profileName.gridx = 2;
		gbc_profileName.gridy = 1;
		panel_1.add(profileName, gbc_profileName);
		profileName.setColumns(10);

		JLabel Address = new JLabel("ServiceName");
		GridBagConstraints gbc_Address = new GridBagConstraints();
		gbc_Address.insets = new Insets(0, 0, 5, 5);
		gbc_Address.anchor = GridBagConstraints.EAST;
		gbc_Address.gridx = 1;
		gbc_Address.gridy = 2;
		panel_1.add(Address, gbc_Address);

		serviceName = new JTextField();
		GridBagConstraints gbc_serviceName = new GridBagConstraints();
		gbc_serviceName.insets = new Insets(0, 0, 5, 5);
		gbc_serviceName.fill = GridBagConstraints.HORIZONTAL;
		gbc_serviceName.gridx = 2;
		gbc_serviceName.gridy = 2;
		panel_1.add(serviceName, gbc_serviceName);
		serviceName.setColumns(10);

		JLabel wsURL = new JLabel("URL");
		GridBagConstraints gbc_wsURL = new GridBagConstraints();
		gbc_wsURL.anchor = GridBagConstraints.EAST;
		gbc_wsURL.insets = new Insets(0, 0, 5, 5);
		gbc_wsURL.gridx = 1;
		gbc_wsURL.gridy = 3;
		panel_1.add(wsURL, gbc_wsURL);

		hostname = new JTextField();
		GridBagConstraints gbc_hostname = new GridBagConstraints();
		gbc_hostname.insets = new Insets(0, 0, 5, 5);
		gbc_hostname.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostname.gridx = 2;
		gbc_hostname.gridy = 3;
		panel_1.add(hostname, gbc_hostname);
		hostname.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 4;
		panel_1.add(lblUsername, gbc_lblUsername);

		username = new JTextField();
		GridBagConstraints gbc_username = new GridBagConstraints();
		gbc_username.insets = new Insets(0, 0, 5, 5);
		gbc_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_username.gridx = 2;
		gbc_username.gridy = 4;
		panel_1.add(username, gbc_username);
		username.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 5;
		panel_1.add(lblPassword, gbc_lblPassword);

		password = new JPasswordField();
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.gridx = 2;
		gbc_password.gridy = 5;
		panel_1.add(password, gbc_password);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Look", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0 };
		gbl_panel_2.rowHeights = new int[] { 0 };
		gbl_panel_2.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setEnabled(false);
		panel.add(btnSave);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.getInstance().setVisible(false);
				;
			}
		});
		panel.add(btnClose);
	}

	protected void handleItemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if (src == comboBox) {
			System.out.println("combo change item");
			Object item = comboBox.getSelectedItem();
			if (item instanceof MoodleWSURL) {
				MoodleWSURL url = (MoodleWSURL) item;
				profileName.setText(url.profileName);
				serviceName.setText(url.serviceName);
				hostname.setText(url.url);
				username.setText(url.getUsername());
				password.setText(url.getPassword());

			}
		}

	}

	public static Settings getInstance() {
		return _instance;
	}

	public static void main(String args[]) {

		MoodleWSURL[] test = new MoodleWSURL[2];
		MoodleWSURL m = new MoodleWSURL("cpintar",
				"https://cs.cepatpintar.biz.id/moodle", "fp_cepatpintar");

		m.setUsername("007");
		m.setPassword("007");
		test[0] = m;
		m = new MoodleWSURL("yoga", "http://127.0.0.1/moodle", "fp_yoga");
		m.setUsername("007");
		m.setPassword("007");
		test[1] = m;

		JFrame f = new JFrame("tst");
		Settings s = new Settings(f, test);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		JButton j = new JButton("show");
		s.pack();
		final Settings fs = s;
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fs.setVisible(true);
			}
		});
		f.getContentPane().add(j);
		f.pack();
		f.setVisible(true);

	}
}

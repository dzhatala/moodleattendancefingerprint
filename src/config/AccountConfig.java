package config;

import java.io.Serializable;
import java.util.prefs.Preferences;

/*
 * 
 */
public final class AccountConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1513020680882703358L;
	private static AccountConfig instance;
	String username = null;
	String password = null;
	static Preferences prefs = Preferences.userRoot().node(
			"config.AccountConfig");

	public static AccountConfig getInstance() {
		if (instance == null)
			instance = new AccountConfig();

		return instance;
	}

	public static String getUsername() {
		// TODO Auto-generated method stub
		String ret = prefs.get("USER_NAME", "NewUser");
		return ret;
	}

	public static void setUsername(String s) {
		prefs.put("USER_NAME", s);
	}

	public static void setPassword(String p) {
		// TODO Auto-generated method stub
		prefs.put("PASSWORD", p);

	}

	public static String getPassword() {
		// TODO Auto-generated method stub
		String ret = prefs.get("PASSWORD", "1234");
		return ret;
	}

}

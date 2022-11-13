package cpintar;

/**
 * connection moodle web service URL
 * 
 * @author yoga520
 * 
 */
public class MoodleWSURL {

	// TODO
	public enum PasswordTypes {
		REMEMBER_TILL_EXIT, DONT_REMEMBER, SAVE_IN_FILE
	};

	public MoodleWSURL(String profile, String url, String service) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.profileName = profile;
		this.serviceName = service;
	}

	public String profileName;
	public String url;
	public String serviceName;
	private String username;
	private String password;
	//
	private PasswordTypes rememberPassword = PasswordTypes.REMEMBER_TILL_EXIT;

	public PasswordTypes isRememberPassword() {
		// return rememberPassword; //TODO
		return PasswordTypes.REMEMBER_TILL_EXIT; // TODO for security reason
													// don't save
	}

	public String toString() {
		return profileName + " " + url + " (" + serviceName + ")";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

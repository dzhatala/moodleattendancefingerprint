package json;

public class MoodleUser {
	public long id; // 'id' field in table mdl_user
	public String firstname;
	public String lastname;

	public String toString() {
		return ("MoodleUser id:" + id + ", FN:" + firstname + ", LN:" + lastname);
	}

}

package json;

public class Token {
	private String token;
	String privatetoken;
	
	public String toString(){
		return "token is: " +getToken() + "  private is: " + privatetoken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

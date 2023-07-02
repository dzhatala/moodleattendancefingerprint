package moodle_login_01;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import json.AttendanceInstance;
import json.Course;
import json.Session;
import json.Token;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

//import net.beaconhillcott.moodlerest.MoodleCallRestWebService;

//import net.beaconhillcott.moodlerest
/**
 * REST MOODLE Client It's very basic. You'll have to write the JavaObject2POST
 * code.
 * 
 */

public class TestSpecificDate {

	static String domainName = "http://127.0.0.1/moodle"; // Your Moodle domain;

	/**
	 * Do a REST call to Moodle without paraameters Result are displayed in the
	 * console log.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void unused_core_user_get_user_preferences1(String[] args)
			throws ProtocolException, IOException {

		// / NEED TO BE CHANGED
		// String token = "Your_token"; // no space here
		// String token = "b588f7bbbb582dd6b7c14db171b2db0e";
		String token = "31bf9b62424ae70de44269537d26cf9c";

		// String domainName = "http://pnbp1.local"; // Your Moodle domain
		// String domainName = "http://127.0.0.1/moodle;"; // Your Moodle domain

		// / REST RETURNED VALUES FORMAT
		String restformat = "xml"; // Also possible in Moodle 2.2 and later:
									// 'json'

		// Setting it to 'json' will fail all calls on earlier Moodle version

		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		}

		else {
			restformat = "";
		}

		// / PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		// String functionName = "core_user_get_users";
		String functionName = "core_user_get_user_preferences";

		String urlParameters = ""; // criteria[0][key]=id&criteria[0][value]=2";
		urlParameters = "";

		// / REST CALL

		// Send request

		String serverurl = domainName + "/webservice/rest/server.php"
				+ "?wstoken=" + token + "&wsfunction=" + functionName;

		System.out.println("\n" + serverurl);

		HttpURLConnection con = (HttpURLConnection) new URL(serverurl)
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// Get Response

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		System.out.println(response.toString());
	}

	public static void main(String[] args) throws ProtocolException,
			IOException, ParseException {
		// core_user_get_user_preferences(args);
		// core_user_get_users_by_field(args);
		// use_login_password(args);
		//get_attendances();

	}

	/*
	private static void get_attendances() throws ProtocolException,
			IOException, ParseException {
		// TODO Auto-generated method stub
		String format = "json";
		TestSpecificDate m = new TestSpecificDate();

		System.out.println("TSD: get att(). ");

		Token token = m.getToken("http://127.0.0.1/moodle", "007", "007",
				"fp_yoga");
		NameValuePair nv1 = new NameValuePair("field", "username");
		NameValuePair nv2 = new NameValuePair("values[0]", "007");

		// core_user_get_users_by_field must be combined into fp_yoga in order
		// to be used here
		String responseBody = (String) m.REST("http://127.0.0.1/moodle",
				token.getToken(), "core_user_get_users_by_field",
				new NameValuePair[] { nv1, nv2 }, format);
		Gson gson = new Gson();
		MoodleUser[] users = (MoodleUser[]) gson.fromJson(responseBody,
				MoodleUser[].class);
		token = m.getToken("http://127.0.0.1/moodle", "007", "007", "fp_yoga");

		if (users.length < 1) {
			System.out.print("Error getting user id");
			System.exit(-1);
		} else {
			System.out.println("userid returned:" + users[0].id);
		}

		// must get user numerical userid here to pass to attendance ..
		NameValuePair userid_nv = new NameValuePair("userid", users[0].id + "");
		Date d = new Date();

		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // dd/MM/yyyy
																			// HH:mm:ss
																			// 28/01/2022
																			// 15:33:03
		System.out.println(f.format(d));

		// changing between date
		// d=f.parse("28/01/2022 15:33:03");
		d = f.parse("25/01/2022 15:33:03");

		String strunixtime = d.getTime() + "";
		// strunixtime+="";
		System.out.println("Unix Time: " + strunixtime);
		NameValuePair sessdate_nv = new NameValuePair("sessdate", strunixtime);

		System.out.println("mod_wsattendance_get_courses_with_date_sessions");
		String jsonstring = m.REST("http://127.0.0.1/moodle", token.getToken(),
				"mod_wsattendance_get_courses_with_date_sessions",
				new NameValuePair[] { userid_nv, sessdate_nv }, null);

		Course[] courses = gson.fromJson(jsonstring, Course[].class);
		System.out.println("gtc() num:" + courses.length);

		for (int i = 0; i < courses.length; i++) {
			AttendanceInstance[] atts = courses[i].getAttendance_instances();
			System.out.println("\tnum att:" + atts.length);
			for (int j = 0; j < atts.length; j++) {
				System.out.println("\t\t:" + atts[j]);
				Session[] sess = atts[j].today_sessions;
				for (int k = 0; k < sess.length; k++) {
					System.out.println(sess[i]);
					NameValuePair nv = new NameValuePair("sessionid",
							sess[k].id + "");
					System.out.println("Getting Session info id:" + sess[k].id);
					jsonstring = m.REST("http://127.0.0.1/moodle",
							token.getToken(), "mod_wsattendance_get_session",
							new NameValuePair[] { nv }, null);

					SessionDetail sessDetail = gson.fromJson(jsonstring,
							SessionDetail.class);
					MoodleUser[] musers = sessDetail.users;
					for (int L = 0; L < musers.length; L++) {
						System.out.println("\t\t\t" + musers[L]);

						nv1 = new NameValuePair("field", "id");
						nv2 = new NameValuePair("values[0]", musers[L].id + "");

						// core_user_get_users_by_field must be combined into
						// fp_yoga in order
						// to be used here
						responseBody = m.REST("http://127.0.0.1/moodle",
								token.getToken(),
								"core_user_get_users_by_field",
								new NameValuePair[] { nv1, nv2 }, format);
					}

					AttendanceStudentInfo[] logs = sessDetail.attendance_log;
					for (int L = 0; L < logs.length; L++) {
						System.out.println("\t\t\t" + logs[L]);
					}
				}
			}
		}

	}
	
	*/

	/**
	 * Do a REST call to Moodle with parameters field. Result are displayed in
	 * the console log.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void core_user_get_users_by_field(String[] args)
			throws ProtocolException, IOException {

		// / NEED TO BE CHANGED
		// String token = "Your_token"; // no space here
		String token = "8409d7c0e48bd503f0a45f67538b3ac6";
		// String domainName = "http://pnbp1.local"; // Your Moodle domain

		// / REST RETURNED VALUES FORMAT
		String restformat = "xml"; // Also possible in Moodle 2.2 and later:
									// 'json'

		// Setting it to 'json' will fail all calls on earlier Moodle version

		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		}

		else {
			restformat = "";
		}

		// / PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		// String functionName = "core_user_get_users";
		String functionName = "core_user_get_users_by_field";

		String urlParameters = "";

		// urlParameters = "&field=username&values[0]=007"; //same return with
		// or without &
		urlParameters = "field=username&values[0]=007";

		// / REST CALL

		// Send request

		String serverurl = domainName + "/webservice/rest/server.php"
				+ "?wstoken=" + token + "&wsfunction=" + functionName;

		System.out.println("\n" + serverurl);

		HttpURLConnection con = (HttpURLConnection) new URL(serverurl)
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// Get Response

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\n');
		}
		rd.close();
		System.out.println(response.toString());

	}

	/*
	public Token getToken(String moodleURL, String username, String password,
			String service) throws ProtocolException, IOException {
		String token = null;
		String urlParameters = "";

		String serverurl = moodleURL + "/login/token.php" + "";
		System.out.println("\n" + serverurl);

		urlParameters = "field[0]=username&values[0]=" + username;

		// PostMethod pm=new PostMethod(serverurl);
		PostMethod post = new PostMethod(serverurl);
		// RequestEntity entity = new StringRequestEntity(message, contentType,
		// charSet);
		// post.setRequestEntity(entity);
		post.addParameter(new NameValuePair("username", username));
		post.addParameter(new NameValuePair("password", password));
		post.addParameter(new NameValuePair("service", service));
		HttpClient httpclient = new HttpClient();
		try {
			int result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);
			System.out.println("Response body: ");
			System.out.println(post.getResponseBodyAsString());
			Gson gson = new Gson();
			Token gtoken = gson.fromJson(post.getResponseBodyAsString(),
					Token.class);
			return gtoken;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}

		// rd.close();
		// System.out.println(response.toString());

		// System.out.println("Token: "+token);
		return null;

	}
	
	*/
	/**
	 * 
	 * @param moodleURL
	 * @param wstoken
	 * @param wsfunction
	 * @param parameters
	 * @param req_Format
	 * @return json/xml string
	 * @throws ProtocolException
	 * @throws IOException
	 */
	
	/*
	public String REST(String moodleURL, String wstoken, String wsfunction,
			NameValuePair[] parameters, String req_Format)
			throws ProtocolException, IOException {
		// String token = null;
		// String urlParameters = "";

		String responseBody = null;

		String serverurl = moodleURL + "/webservice/rest/server.php" + "";
		System.out.println("REST \n" + serverurl);

		PostMethod post = new PostMethod(serverurl);

		post.addParameter(new NameValuePair("wstoken", wstoken));
		post.addParameter(new NameValuePair("wsfunction", wsfunction));
		String format = "json";
		if (req_Format != null)
			format = req_Format;
		post.addParameter(new NameValuePair("moodlewsrestformat", format));

		for (int i = 0; i < parameters.length; i++) {
			post.addParameter(parameters[i]);
		}

		HttpClient httpclient = new HttpClient();
		try {
			int result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);
			System.out.println("Response body: ");
			responseBody = post.getResponseBodyAsString();
			System.out.println(responseBody);

			return responseBody;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}

		// rd.close();
		// System.out.println(response.toString());

		// System.out.println("Token: "+token);
		return null;

	}
	
	*/

	// zoel

	public void RESTConnect(String token, String moodleURL)
			throws ProtocolException, IOException {

		// / NEED TO BE CHANGED
		// String token = "Your_token"; // no space here
		// String token = "8409d7c0e48bd503f0a45f67538b3ac6";
		// String domainName = "http://pnbp1.local"; // Your Moodle domain

		// / REST RETURNED VALUES FORMAT
		String restformat = "xml"; // Also possible in Moodle 2.2 and later:
									// 'json'

		// Setting it to 'json' will fail all calls on earlier Moodle version

		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		}

		else {
			restformat = "";
		}

		// / PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		// String functionName = "core_user_get_users";
		String functionName = "core_user_get_users_by_field";

		String urlParameters = "";

		// urlParameters = "&field=username&values[0]=007"; //same return with
		// or without &
		urlParameters = "field=username&values[0]=007";

		// / REST CALL

		// Send request

		String serverurl = moodleURL + "/webservice/rest/server.php"
				+ "?wstoken=" + token + "&wsfunction=" + functionName;

		System.out.println("\n" + serverurl);

		HttpURLConnection con = (HttpURLConnection) new URL(serverurl)
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// Get Response

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\n');
		}
		rd.close();
		System.out.println(response.toString());

	}

	public static void use_login_password(String[] args)
			throws ProtocolException, IOException {

		// / NEED TO BE CHANGED
		// String token = "Your_token"; // no space here
		// String token = "8409d7c0e48bd503f0a45f67538b3ac6"; //admin token
		String token;
		// String domainName = "http://pnbp1.local"; // Your Moodle domain

		// / REST RETURNED VALUES FORMAT
		String restformat = "xml"; // Also possible in Moodle 2.2 and later:
									// 'json'

		// Setting it to 'json' will fail all calls on earlier Moodle version

		restformat = "json";

		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		}

		else {
			restformat = "";

		}
		System.out.println("restformat: " + restformat);

		// / PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		// String functionName = "core_user_get_users";
		String functionName = "core_user_get_users_by_field";

		String urlParameters = "";

		// urlParameters = "&field=username&values[0]=007"; //same return with
		// or without &
		urlParameters = "field=username&values[0]=007";

		// / REST CALL

		// Send request

		String service = "fp_x240";
		String username = "007";
		String password = "007";
		String serverurl = domainName + "/login/token.php?username=" + username
				+ "&password=" + password + "&service=" + service;

		System.out.println("\n #1: " + serverurl);

		HttpURLConnection con = (HttpURLConnection) new URL(serverurl)
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// Get Response

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		String str = response.toString();
		System.out.println(str);
		// @todo use token to login
		Map<String, String> map = parseJSON(str);
		String myToken;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			myToken = entry.getValue();
			System.out.println(entry.getKey() + " " + myToken);
			break;
		}

		// https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
		JSONObject jo = new JSONObject(map);

		System.out.println("jo below:");

		System.out.println(jo);

		Gson gson = new Gson();
		Token gtoken = gson.fromJson(response.toString(), Token.class);
		System.out.println("GSON result is below:");
		System.out.println(gtoken);

		// domainName = "http://pnbp1.local"; // Your Moodle domain
		// functionName = "core_user_get_users_by_field";
		// urlParameters = "field=username&values[0]=007";
		functionName = "core_user_get_user_preferences";
		urlParameters = "";

		token = gtoken.getToken();
		String jsonstring = moodleREST(token, domainName, functionName,
				urlParameters);
		System.out.println(jsonstring);

		functionName = "mod_wsattendance_get_courses_with_today_sessions";
		// functionName = "mod_wsattendance_get_session";
		urlParameters = "&userid=220";
		// token="8409d7c0e48bd503f0a45f67538b3ac6" ; //admin token
		// service="fp_x240";
		jsonstring = moodleREST(token, domainName, functionName, urlParameters);

		System.out.println(jsonstring);
		Course[] courses = gson.fromJson(jsonstring, Course[].class);
		System.out.println("count course :" + courses.length);
		for (int i = 0; i < courses.length; i++) {
			System.out.println(courses[i]);
			AttendanceInstance[] atts = courses[i].getAttendance_instances();
			for (int j = 0; j < atts.length; j++) {
				System.out.println("\t" + atts[j]);
				Session[] sessions = atts[j].today_sessions;
				for (int k = 0; k < sessions.length; k++) {
					System.out.println("\t\t" + sessions[k]);
				}
			}

		}

	}

	/**
	 * 
	 * @param token
	 * @param domainName
	 * @param functionName
	 *            web service f name
	 * @param externalParams
	 *            start with & , example "&userid=220"
	 * @return
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public static String moodleREST(String token, String domainName,
			String functionName, String externalParams)
			throws ProtocolException, IOException {

		// / NEED TO BE CHANGED
		// String token = "Your_token"; // no space here
		// String token = "8409d7c0e48bd503f0a45f67538b3ac6";
		// String domainName = "http://pnbp1.local"; // Your Moodle domain

		// / REST RETURNED VALUES FORMAT
		String restformat = "xml"; // Also possible in Moodle 2.2 and later:
									// 'json'

		// Setting it to 'json' will fail all calls on earlier Moodle version

		restformat = "json";

		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		}

		else {
			restformat = "";

		}
		System.out.println("functionName: " + functionName);

		// / PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		// String functionName = "core_user_get_users";
		// String functionName = "core_user_get_users_by_field";

		// String urlParameters = "";

		// urlParameters = "&field=username&values[0]=007"; //same return with
		// or without &
		// urlParameters = "field=username&values[0]=007";

		// / REST CALL

		// Send request

		// String serverurl = domainName + "/webservice/rest/server.php"
		// //insecure
		// + "?wstoken=" + token + "&wsfunction=" + functionName; // iNSECURE

		String serverurl = domainName + "/webservice/rest/server.php";
		String urlParameters = "wstoken=" + token + "&wsfunction="
				+ functionName + externalParams;
		urlParameters += "&moodlewsrestformat=json";

		serverurl = domainName + "/webservice/rest/server.php?" + urlParameters;
		urlParameters = ""; // INSECURE

		System.out.println("\n" + serverurl);

		HttpURLConnection con = (HttpURLConnection) new URL(serverurl)
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		System.out.println("\nurlParameters: " + urlParameters);
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		// Get Response

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		String str = response.toString();
		return str;

		// Gson gson = new Gson();
		// Token gtoken = gson.fromJson(response.toString(), Token.class);
		// System.out.println("GSON result is below:");
		// System.out.println(gtoken);

	}

	public static Map parseJSON(String text) {
		JSONParser parser = new JSONParser();
		// String text =
		// "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
		ContainerFactory containerFactory = new ContainerFactory() {
			// @Override
			public Map createObjectContainer() {
				return new LinkedHashMap<>();
			}

			// @Override
			public List creatArrayContainer() {
				return new LinkedList<>();
			}
		};
		try {
			Map<String, String> map = (Map<String, String>) parser.parse(text,
					containerFactory);
			return map;
			// Object k,v;
			/*
			 * Map <String, String> map = (Map)parser.parse(text,
			 * containerFactory); // Map.Entry<K, V> //map.values(); for
			 * (Map.Entry<String, String> entry : map.entrySet()) {
			 * System.out.println(entry.getKey() + " " + entry.getValue()); }
			 */
			/*
			 * map.forEach((k,v)->System.out.println("Key : " + k + " Value : "
			 * + v));
			 */
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// static void use_beacon(){
	// MoodleCallRestWebService.init();

	// zoel
}
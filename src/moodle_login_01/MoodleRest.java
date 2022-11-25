package moodle_login_01;

import java.io.IOException;
import java.net.ProtocolException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import json.AttendanceInstance;
import json.Course;
import json.MoodleUser;
import json.Session;
import json.SessionDetail;
import json.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
//import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Response;

public class MoodleRest {

	String moodleURL = "http://127.0.0.1/moodle";
	String moodleUsername = null;
	long currentUserId = -1; // cache userId from last invocation
	private String moodlePassword;
	private Token lastToken = null;
	String service;
	public static final int HTTP_CHECK_INTERVAL = 1000;// check in miliseconds
	public static int HTPP_TIMEOUT = 5000; // timeout in miliseconds

	RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(2000).setConnectTimeout(2000)
			.setSocketTimeout(2000)

			.build();

	public CloseableHttpClient defaultHTTPClient() {
		return HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.build();
	}

	public long getCurrentUserId() {
		return currentUserId;
	}

	public String getMoodleURL() {
		return moodleURL;
	}

	public void setMoodleURL(String moodleURL) {
		this.moodleURL = moodleURL;
	}

	public String getMoodleUsername() {
		return moodleUsername;
	}

	public void setMoodleUsername(String moodleUsername) {
		this.moodleUsername = moodleUsername;
	}

	public Token getLastToken() {
		return lastToken;
	}

	public void setLastToken(Token lastToken) {
		this.lastToken = lastToken;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPassword() {
		return moodlePassword;
	}

	public MoodleRest(String murl) {
		moodleURL = murl;
	}

	/*
	 * if username and password not empty then alter _caches
	 */

	public Token getToken(String username, String password, String service)
			throws ProtocolException, IOException {

		if (username != null)
			moodleUsername = username;
		if (password != null)
			moodlePassword = password;
		String serverurl = moodleURL + "/login/token.php" + "";
		System.out.println("\n" + serverurl);

		// urlParameters = "field[0]=username&values[0]=" + username;

		HttpPost post = new HttpPost(serverurl);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", moodleUsername));
		params.add(new BasicNameValuePair("password", moodlePassword));
		params.add(new BasicNameValuePair("service", service));
		post.setEntity(new UrlEncodedFormEntity(params));

		// CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = this.defaultHTTPClient();

		CloseableHttpResponse response = httpclient.execute(post);
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(responseString);
		Gson gson = new Gson();
		// Token gtoken = new Token();
		Token gtoken = gson.fromJson(responseString, Token.class);
		lastToken = gtoken;

		post.releaseConnection();
		if (gtoken != null)
			return gtoken;

		return null;

	}

	/**
	 * set moodleURL, moodleUsername, password and service first ...
	 * 
	 * @throws Exception
	 */

	public Course[] get_date_courses(String javaEpochLongTS) throws Exception {
		// System.out.println("gdcs_() " + moodleUsername + ":" +
		// moodlePassword);
		// TODO Auto-generated method stub
		String format = "json";

		// Main m = new Main();
		// service = "fp_yoga"; // custom service at moodle server
		// service = "fp_cepatpintar"; // custom service at moodle server
		if (service == null) {
			throw new Exception("must identify service");
		}
		getToken(moodleUsername, moodlePassword, service);
		// System.out.println("usertoken:"+token.getToken()); System.exit(-1);
		Token t = null;
		if (lastToken != null)
			t = lastToken;
		return get_date_courses(format, t, javaEpochLongTS);
	}

	public Course[] get_date_courses(String format, Token token,
			String javaEpochLongTS) throws Exception {
		BasicNameValuePair nv1 = new BasicNameValuePair("field", "username");
		BasicNameValuePair nv2 = new BasicNameValuePair("values[0]",
				moodleUsername);

		// core_user_get_users_by_field must be combined into fp_yoga in order
		// to be used here
		RestReturn rr = REST(moodleURL, token.getToken(),
				"core_user_get_users_by_field", new BasicNameValuePair[] { nv1,
						nv2 }, format);
		final Gson gson = new Gson();
		MoodleUser[] users = (MoodleUser[]) gson.fromJson(rr.response,
				MoodleUser[].class);

		// token = getToken(moodleUsername, password, service); // no need if
		// service is already fp_yoga before

		if (users.length < 1) {
			System.out.print("Error getting user id");
			System.exit(-1);
		} else {
			System.out.println("userid returned:" + users[0].id);
			currentUserId = users[0].id;
		}

		// must get user numerical userid here to pass to attendance ..
		BasicNameValuePair userid_nv = new BasicNameValuePair("userid",
				users[0].id + "");

		System.out.println("Unix Time: " + javaEpochLongTS);
		BasicNameValuePair sessdate_nv = new BasicNameValuePair("sessdate",
				javaEpochLongTS);

		// format="xml";
		format = "json";

		// if (false)
		rr = REST(moodleURL, token.getToken(),
				"mod_wsattendance_get_courses_with_date_sessions",
				new NameValuePair[] { userid_nv, sessdate_nv }, format);

		System.out.println(rr.response);
		Course[] courses = gson.fromJson(rr.response, Course[].class);
		System.out.println("count course :" + courses.length);

		// if (false)
		for (int i = 0; i < courses.length; i++) {
			AttendanceInstance[] atts = courses[i].getAttendance_instances();
			System.out.println("\tnum att:" + atts.length);
			for (int j = 0; j < atts.length; j++) {
				System.out.println("\t\t:" + atts[j]);
				final Session[] sess = atts[j].today_sessions;
				for (int k = 0; k < sess.length; k++) {
					System.out.println(sess[k]);
					BasicNameValuePair nv = new BasicNameValuePair("sessionid",
							sess[k].id + "");
					System.out.println("Getting Session info id:" + sess[k].id);
					// todo multipleThread here ...

					// finalizing variables for inner class
					final Token tf = token;
					final BasicNameValuePair nvf = nv;
					final int kf = k;
					Runnable r = new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							RestReturn rrf = null;
							try {
								rrf = REST(moodleURL, tf.getToken(),
										"mod_wsattendance_get_session",
										new BasicNameValuePair[] { nvf }, null);
								SessionDetail sessDetail = gson.fromJson(
										rrf.response, SessionDetail.class);

								sess[kf].detail = sessDetail;
								// MoodleUser[] musers = sessDetail.users;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					};

					Thread t = new Thread(r);
					t.start();

				}
			}
		}
		return courses;

	}

	/**
	 * blocking call, USE new thread in swing or main thread ...
	 * 
	 * @param moodleURL
	 * @param wstoken
	 * @param wsfunction
	 * @param parameters
	 * @param req_Format
	 * @return
	 * @throws Exception
	 */

	public RestReturn REST(String moodleURL, String wstoken, String wsfunction,
			NameValuePair[] parameters, String req_Format) throws Exception {

		System.out.println("REST: " + moodleURL + " " + wstoken + " "
				+ wsfunction);

		String responseBody = null;

		String serverurl = moodleURL + "/webservice/rest/server.php" + "";
		System.out.println("REST wsfunction : " + wsfunction);
		System.out.println("REST url : " + serverurl);
		// System.out.println("REST url : " + serverurl);

		HttpPost post = new HttpPost(serverurl);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("wstoken", wstoken));
		// params.add(new BasicNameValuePair("service", service)); // extra
		// parameter failed, token is already contain info for service
		params.add(new BasicNameValuePair("wsfunction", wsfunction));

		String format = "json";
		if (req_Format != null)
			format = req_Format;
		params.add(new BasicNameValuePair("moodlewsrestformat", format));

		for (int i = 0; i < parameters.length; i++) {
			System.out.println("nv adding to post:" + parameters[i]);
			;
			params.add(parameters[i]);
		}
		post.setEntity(new UrlEncodedFormEntity(params));
		RestReturn myret = null;
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = this.defaultHTTPClient();
		try {

			CloseableHttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			myret = new RestReturn();
			myret.response = EntityUtils.toString(entity, "UTF-8");
			System.out.println(myret.response);
			response.close();

			return myret;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}

		if (myret != null) {
			if (myret.response.toLowerCase().indexOf("error") >= 0)
				throw new Exception("JSON REPLY contain ERROR");
		}

		return null;

	}

	public void setUsername(String string) {
		// TODO Auto-generated method stub
		moodleUsername = string;

	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		moodlePassword = string;

	}

	public void updateUserStatus(int sessionid, String studentid,
			String takenbyid, String statusid, String statusset, String remarks)
			throws Exception {
		// TODO

		System.out.println("uUS() studentid:#" + studentid + "#");
		System.out.println("uUS() statusid:#" + statusid + "#");
		System.out.println("uUS() statusset:#" + statusset + "#");
		// TODO Auto-generated method stub
		String format = "json";
		// Main m = new Main();
		// service = "fp_yoga"; // custom service at moodle server

		Token token = getLastToken();
		if (service == null)
			throw new Exception("must identify service name");
		if (token == null)
			getToken(moodleUsername, moodlePassword, service);
		// System.out.println("usertoken:"+token.getToken()); System.exit(-1);

		BasicNameValuePair nv1 = new BasicNameValuePair("sessionid", sessionid
				+ "");
		BasicNameValuePair nv2 = new BasicNameValuePair("studentid", studentid);
		BasicNameValuePair nv3 = new BasicNameValuePair("takenbyid", takenbyid);
		BasicNameValuePair nv4 = new BasicNameValuePair("statusid", statusid);
		BasicNameValuePair nv5 = new BasicNameValuePair("statusset", statusset);

		RestReturn rr = null;

		if (remarks == null) {
			rr = REST(moodleURL, token.getToken(),
					"mod_wsattendance_update_user_status",
					new BasicNameValuePair[] { nv1, nv2, nv3, nv4, nv5 },
					format);
		} else {
			System.out.println("u_u_statues remarks: " + remarks);
			BasicNameValuePair nv_remark = new BasicNameValuePair("remark",
					remarks);

			rr = asyncREST(moodleURL, token.getToken(),
					"mod_wsattendance_update_user_status_remark",
					new BasicNameValuePair[] { nv1, nv2, nv3, nv_remark, nv4,
							nv5 }, format);
		}

	}

	public static void testTokenCP() throws ProtocolException, IOException {

		String url = "https://cs.cepatpintar.biz.id/moodle";
		MoodleRest restConnector = new MoodleRest(url);

		restConnector.setUsername("007");// TODO ask user input
		restConnector.setPassword("007"); // TODO ask user input

		Token t = restConnector.getToken(null, null, "fp_cepatpintar");

	}

	public SessionDetail asyncGetSessionDetail(int sessionId) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RC: asynchGS sessid=" + sessionId);
		throw new Exception("not implemented");
		// return null;
	}

	/***
	 * asynchronous version of REST
	 * 
	 * @param moodleURL
	 * @param wstoken
	 * @param wsfunction
	 * @param parameters
	 * @param req_Format
	 * @return
	 * @throws Exception
	 */
	public RestReturn asyncREST(String moodleURL, String wstoken,
			String wsfunction, NameValuePair[] parameters, String req_Format)
			throws Exception {

		System.out.println("async REST: " + moodleURL + " " + wstoken + " "
				+ wsfunction);

		String responseBody = null;

		String serverurl = moodleURL + "/webservice/rest/server.php" + "";
		System.out.println("REST wsfunction : " + wsfunction);
		System.out.println("REST url : " + serverurl);
		// System.out.println("REST url : " + serverurl);

		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		BoundRequestBuilder post = asyncHttpClient.preparePost(serverurl);

		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("wstoken", wstoken));
		post.addParameter("wstoken", wstoken);
		// params.add(new BasicNameValuePair("service", service)); // extra
		// parameter failed, token is already contain info for service
		// params.add(new BasicNameValuePair("wsfunction", wsfunction));
		post.addParameter("wsfunction", wsfunction);

		String format = "json";
		if (req_Format != null)
			format = req_Format;
		// params.add(new BasicNameValuePair("moodlewsrestformat", format));
		post.addParameter("moodlewsrestformat", format);
		for (int i = 0; i < parameters.length; i++) {
			System.out.println("nv adding to post: " + parameters[i]);
			;
			// params.add(parameters[i]);
			post.addParameter(parameters[i].getName(), parameters[i].getValue());
		}

		// TODO replace namepair with asyncversion here ...
		// post.setEntity(new UrlEncodedFormEntity(params));
		// post.

		RestReturn myret = null;

		try {

			Future<Response> f = post.execute();
			if (f == null) {
				System.out.println("st wrong f is null");
				return null;
			}
			long wait = 0;
			while (!f.isCancelled() && !f.isDone()) {
				// System.out.println(f.isCancelled() +" " +f.isDone());
				System.out.println("Not done " + wait + " ms");
				Thread.currentThread().sleep(HTTP_CHECK_INTERVAL);
				wait += HTTP_CHECK_INTERVAL;
				if (wait >= this.HTPP_TIMEOUT) {
					f.cancel(true);
					System.out.println("cancel at " + wait + " ms");
					return null;
				}
				System.out.flush();

			}

			System.out.println("after while " + f.isCancelled() + " "
					+ f.isDone());
			if (f.isCancelled()) {
				System.out.println("cancelled returning null " + f.toString());
				return null;
			}
			Response r = f.get();

			// HttpEntity entity = response.getEntity();
			myret = new RestReturn();
			// myret.response = EntityUtils.toString(entity, "UTF-8");
			myret.response = r.getResponseBody("UTF-8");
			System.out.println(myret.response);
			// response.close();

			return myret;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// post.releaseConnection();
		}

		if (myret != null) {
			if (myret.response.toLowerCase().indexOf("error") >= 0)
				throw new Exception("JSON REPLY contain ERROR");
		}

		return null;

	}

	/**
	 * blocking/synchronous
	 * 
	 * @param sess
	 * @return
	 */

	public SessionDetail getSessionDetail(final Session sess) {
		// TODO Auto-generated method stub
		final Token tf = this.lastToken;
		BasicNameValuePair nv = new BasicNameValuePair("sessionid", sess.id
				+ "");
		System.out.println("Getting Session info id:" + sess.id);
		final BasicNameValuePair nvf = nv;
		final Gson gson = new Gson();

		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				RestReturn rrf = null;
				try {
					rrf = asyncREST(moodleURL, tf.getToken(),
							"mod_wsattendance_get_session",
							new BasicNameValuePair[] { nvf }, null);
					SessionDetail sessDetail = gson.fromJson(rrf.response,
							SessionDetail.class);

					sess.detail = sessDetail;
					// MoodleUser[] musers = sessDetail.users;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		// Thread t = new Thread(r);
		// t.start();
		r.run();
		return sess.detail;
	}

	public Token asyncGetToken(String username, String password, String service)
			throws ProtocolException, IOException, InterruptedException,
			ExecutionException {

		if (username != null)
			moodleUsername = username;
		if (password != null)
			moodlePassword = password;
		String serverurl = moodleURL + "/login/token.php" + "";
		System.out.println("asyncGetToken() " + serverurl);

		// urlParameters = "field[0]=username&values[0]=" + username;

		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		BoundRequestBuilder post = asyncHttpClient.preparePost(serverurl);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		post = post.addParameter("username", moodleUsername);
		post = post.addParameter("password", moodlePassword);
		post = post.addParameter("service", service);

		System.out.println("aGT() Executing post");
		Future<Response> f = post.execute();
		System.out.println("aGT() blocking check ?");

		long wait = 0;
		while (!f.isCancelled() && !f.isDone()) {
			Thread.currentThread().sleep(HTTP_CHECK_INTERVAL);
			System.out.println("Not done");
			wait += HTTP_CHECK_INTERVAL;
			if (wait >= this.HTPP_TIMEOUT) {
				f.cancel(true);
				System.out.println("cancel at " + wait + " ms");
				break;
			}

			System.out.flush();
		}
		if (f.isCancelled())
			return null;
		Response r = f.get();

		String responseString = r.getResponseBody("UTF-8");
		System.out.println(responseString);
		Gson gson = new Gson();
		// Token gtoken = new Token();
		Token gtoken = gson.fromJson(responseString, Token.class);
		lastToken = gtoken;

		// post.

		if (gtoken != null)
			return gtoken;

		return null;

	}

	/**
	 * async version
	 * 
	 * @param format
	 * @param token
	 * @param javaEpochLongTS
	 * @return
	 * @throws Exception
	 */
	public Course[] asyncGet_date_courses(String format, Token token,
			String javaEpochLongTS) throws Exception {
		BasicNameValuePair nv1 = new BasicNameValuePair("field", "username");
		BasicNameValuePair nv2 = new BasicNameValuePair("values[0]",
				moodleUsername);

		// core_user_get_users_by_field must be combined into fp_yoga in order
		// to be used here
		String passToken = getLastToken().getToken();
		if (token != null)
			passToken = token.getToken();
		RestReturn rr = asyncREST(moodleURL, passToken,
				"core_user_get_users_by_field", new BasicNameValuePair[] { nv1,
						nv2 }, format);
		final Gson gson = new Gson();
		if (rr == null)
			return new Course[0];
		MoodleUser[] users = (MoodleUser[]) gson.fromJson(rr.response,
				MoodleUser[].class);

		// token = getToken(moodleUsername, password, service); // no need if
		// service is already fp_yoga before

		if (users.length < 1) {
			System.out.print("Error getting user id");
			System.exit(-1);
		} else {
			System.out.println("userid returned:" + users[0].id);
			currentUserId = users[0].id;
		}

		// must get user numerical userid here to pass to attendance ..
		BasicNameValuePair userid_nv = new BasicNameValuePair("userid",
				users[0].id + "");

		System.out.println("Unix Time: " + javaEpochLongTS);
		BasicNameValuePair sessdate_nv = new BasicNameValuePair("sessdate",
				javaEpochLongTS);

		// format="xml";
		format = "json";

		// if (false)
		rr = asyncREST(moodleURL, passToken,
				"mod_wsattendance_get_courses_with_date_sessions",
				new NameValuePair[] { userid_nv, sessdate_nv }, format);

		if (rr == null)
			return new Course[0];
		System.out.println(rr.response);
		Course[] courses = gson.fromJson(rr.response, Course[].class);
		System.out.println("count course :" + courses.length);

		// if (false)
		for (int i = 0; i < courses.length; i++) {
			AttendanceInstance[] atts = courses[i].getAttendance_instances();
			System.out.println("\tnum att:" + atts.length);
			for (int j = 0; j < atts.length; j++) {
				System.out.println("\t\t:" + atts[j]);
				final Session[] sess = atts[j].today_sessions;
				for (int k = 0; k < sess.length; k++) {
					System.out.println(sess[k]);
					BasicNameValuePair nv = new BasicNameValuePair("sessionid",
							sess[k].id + "");
					System.out.println("Getting Session info id:" + sess[k].id);
					// todo multipleThread here ...

					// finalizing variables for inner class
					final String stf = passToken;
					final BasicNameValuePair nvf = nv;
					final int kf = k;
					Runnable r = new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							RestReturn rrf = null;
							try {
								rrf = asyncREST(moodleURL, stf,
										"mod_wsattendance_get_session",
										new BasicNameValuePair[] { nvf }, null);
								SessionDetail sessDetail = null;
								if (rrf != null)
									gson.fromJson(rrf.response,
											SessionDetail.class);

								sess[kf].detail = sessDetail;
								// MoodleUser[] musers = sessDetail.users;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					};

					Thread t = new Thread(r);
					t.start();
					// r.run();

				}
			}
		}
		return courses;

	}

	public Course[] asyncGet_date_courses(String javaEpochLongTS)
			throws Exception {
		// System.out.println("gdcs_() " + moodleUsername + ":" +
		// moodlePassword);
		// TODO Auto-generated method stub
		String format = "json";

		// Main m = new Main();
		// service = "fp_yoga"; // custom service at moodle server
		// service = "fp_cepatpintar"; // custom service at moodle server
		if (service == null) {
			throw new Exception("must identify service");
		}
		asyncGetToken(moodleUsername, moodlePassword, service);
		// System.out.println("usertoken:"+token.getToken()); System.exit(-1);
		Token t = null;
		if (lastToken != null)
			t = lastToken;
		return asyncGet_date_courses(format, t, javaEpochLongTS);
	}

	public static void testAsynchTokenCP() throws ProtocolException,
			IOException, InterruptedException, ExecutionException {

		String url = "https://cs.cepatpintar.biz.id/moodle";
		MoodleRest restConnector = new MoodleRest(url);

		restConnector.setUsername("007");// TODO ask user input
		restConnector.setPassword("007"); // TODO ask user input

		Token t = restConnector.asyncGetToken(null, null, "fp_cepatpintar");

	}

	public static void main2(String[] args) throws Exception {
		// MoodleRest restConnector=new MoodleRest(moodleURL);//parameter can't
		// be initialized before constructor ?
		// MoodleRest restConnector = new MoodleRest("http://127.0.0.1/moodle");
		MoodleRest restConnector = new MoodleRest(
				"https://cs.cepatpintar.biz.id/moodle");
		// restConnector.setMoodleURL(moodleURL.getText());
		restConnector.setUsername("007");// TODO ask user input
		restConnector.setPassword("007"); // TODO ask user input

		String d = "28/12/2021 08:01:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");

		String javaEpochLongTS = dateFormat.parse(d).getTime() + "";
		restConnector.setService("fp_cepatpintar");
		restConnector.get_date_courses(javaEpochLongTS);
		// restConnector.updateUserStatus(50, "609", "220", "17",
		// "17,19,20,18");

	}

	public static void testAsync01(String[] args) throws Exception {
		// MoodleRest restConnector=new MoodleRest(moodleURL);//parameter can't
		// be initialized before constructor ?
		// MoodleRest restConnector = new MoodleRest("http://127.0.0.1/moodle");
		MoodleRest restConnector = new MoodleRest(
				"https://cs.cepatpintar.biz.id/moodle");
		// restConnector.setMoodleURL(moodleURL.getText());
		restConnector.setUsername("007");// TODO ask user input
		restConnector.setPassword("007"); // TODO ask user input

		Token t = restConnector.asyncGetToken(null, null, "fp_cepatpintar");

		if (restConnector.lastToken == null) {
			System.out.println("can not get token ");
			return;
		}
		String d = "28/12/2021 08:01:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");

		String javaEpochLongTS = dateFormat.parse(d).getTime() + "";
		restConnector.setService("fp_cepatpintar");
		restConnector.asyncGet_date_courses(null, null, javaEpochLongTS);
		// restConnector.updateUserStatus(50, "609", "220", "17",
		// "17,19,20,18");

	}

	public static void main(String[] args) throws Exception {
		// main2(args);
		// testTokenCP();
		// testAsynchTokenCP();
		testAsync01(args);

	}

}

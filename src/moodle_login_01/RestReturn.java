package moodle_login_01;


enum ReturnType{XML,JSON};

public class RestReturn {
	
	ReturnType type=ReturnType.JSON;
	String response; //json string or xml string ?
	
}

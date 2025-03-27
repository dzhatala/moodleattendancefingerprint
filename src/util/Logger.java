package util;

import java.util.Date;

public final class Logger {

	public static void log(String str) { 
		// TODO Auto-generated method stub
		System.out.println(new Date()+": "+ Logger.class+":"+str);
	}

	public static void log(Object str) {
		// TODO Auto-generated method stub
		log(str+"");
	}
	
	public static void log(Object ctx, Object str) {
		// TODO Auto-generated method stub
		log(ctx+":"+str+"");
	}

	public static void log(Class cls, Object str) {
		// TODO Auto-generated method stub
		log(cls+":"+str+"");
	}

}

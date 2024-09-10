package config;

import java.util.prefs.Preferences;


public final class SessionTableConfig {
//	public static final int[] COLUMN_WIDTHS=new int[]{200,200};

	static Preferences prefs=Preferences.userRoot().node("config.SessionTableConfig");
	public static void setWidth(String string, int width) {
		// TODO Auto-generated method stub
//		util.Logger.log("put "+string +"->"+width);
		prefs.putInt(string, width);
	}
	
	/**
	 * get the column last width
	 * @param key the column name
	 * @return
	 */
	public static int getWidth(String key) {
		// TODO Auto-generated method stub
		return prefs.getInt(key, 100);
		
	}
}

package util;

public final class FileUtils {

	public static boolean checkDir(String dir) {
		// TODO Auto-generated method stub
		boolean ret=false;
		
		java.io.File f = new java.io.File(dir);
		if(!f.isDirectory())return false;
		if(f.isDirectory())return true;
		
		return ret;
	}

}

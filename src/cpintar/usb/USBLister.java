package cpintar.usb;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import util.Logger;

public final class USBLister {
	String cygwindir = "z:\\rps\\cygwin64";

	public Process listUSB() throws Exception {

//		String[] cmd = new String[] { cygwindir + "\\bin\\lsusb", "-v" };
		String[] cmd = new String[] { cygwindir + "\\bin\\lsusb" };
		// Logger.log(cmd);

		long startTime = System.nanoTime();

		Process p = executeCommands(cmd);
		long endTime = System.nanoTime();

		long duration = (endTime - startTime); // divide by 1000000 to get
												// milliseconds.

		Logger.log("ex time:" + (duration / 1000000) + " ms");
		return p;
		// p.destroy(); // test destroyablep
//		try {
//			p.waitFor();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static void main(String args[]) throws Exception {
		USBLister r = new USBLister();

		Process p =r.listUSB();
		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line="";
		while ((line = reader.readLine ()) != null) {
			System.out.println ("Stdout: " + line);
			}
	}

	public static Process executeCommands(String[] commandAndArgs) {
		if (commandAndArgs.length == 0) {
			return null;
		}
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(commandAndArgs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return process;
	}
}

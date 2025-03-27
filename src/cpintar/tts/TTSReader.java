package cpintar.tts;

import java.io.IOException;

import util.Logger;

public final class TTSReader {
	//String cygwindir = "z:\\rps\\cygwin64";
	String cygwindir = "c:\\cygwin64";

	public Process espeakRead(String text) throws Exception {

		String[] cmd = new String[] { cygwindir + "\\bin\\espeak", "-v", "id",
				"\"" + text + "\"" };
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
		TTSReader r = new TTSReader();

//		r.espeakRead("  Saya makan nasi");
//		r.espeakRead(" dia cuma minum air. ");
		r.espeakRead("mereka habiskan segalanya");
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

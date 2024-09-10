package cpintar.tts;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import de.dfki.lt.freetts.en.us.MbrolaVoice;
import de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory;

/**
 * Getting started with text to speech Accessing those tricky mbrola voices
 * 
 * @author jmcneil (c) copyright Software Pulse 2019
 * 
 */
public class SoftwarePulse01 {
	private static Voice[] voices;

	MbrolaVoice vc = null;

	/*
	 * static {
	 * 
	 * System.loadLibrary("cygwin1.dll"); }
	 */

	public static void main(String[] args) throws Exception {

		// System.setProperty("mbrola.base", "c:\\mbrola");
		System.setProperty("mbrola.base",
				"G:\\rsync\\RESEARCHS\\tts-text_to_speech\\MBROLA-voices\\data");
		// System.setProperty("mbrola.base",
		// "G:\\rsync\\RESEARCHS\\tts-text_to_speech\\Mbrola Tools");

		MbrolaVoiceDirectory.main(args);

		// if(true || true)return ;

		VoiceManager vm;
		// VoiceManager uses the singleton approach to creating and
		// providing an instance

		System.setProperty("freetts.voices",
				"de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");

		vm = VoiceManager.getInstance();
		// Get all the voices which the VoiceManager knows about
		voices = vm.getVoices();

		for (Voice voice : voices) {
			// Find out what voices are available.
			System.out
					.println(voice.getName() + " - " + voice.getDescription());
		}

		// Voice voice = vm.getVoice("mbrola_id1");
//		Voice v1 = vm.getVoice("mbrola_us1");
		Voice v1 = vm.getVoice("mbrola_id1");
		
		v1.allocate();
		v1.speak("saya sedang bekerja");
		// voice.
		v1.deallocate();

//		v2.allocate();
//		v2.speak("saya sedang erja");
//		// voice.
//		v2.deallocate();
	}

}

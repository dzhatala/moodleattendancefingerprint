package cpintar.tts;


	// Java code to convert text to speech 
	  
	import java.util.Locale; 

import javax.speech.Central; 
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc; 
	  
	public class TestTTS01 {
	  
	    public static void main(String[] args) 
	    { 
	  
	        try {
	        	
	    		System.setProperty("mbrola.base",
	    				"G:\\rsync\\RESEARCHS\\tts-text_to_speech\\MBROLA-voices\\data");

	        	
	            // Set property as Kevin Dictionary 
//	            System.setProperty( 
//	                "freetts.voices", 
//	                "com.sun.speech.freetts.en.us"
//	                    + ".cmu_us_kal.KevinVoiceDirectory"); 
//
//	            
	    		
	    		System.setProperty("freetts.voices",
	    				"de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");


	            
	            
	            // Register Engine 
	            Central.registerEngineCentral( 
	                "com.sun.speech.freetts"
	                + ".jsapi.FreeTTSEngineCentral"); 
	  
	            // Create a Synthesizer 
	            Synthesizer synthesizer 
	                = Central.createSynthesizer( 
	                    new SynthesizerModeDesc(Locale.US)); 

//	            synthesizer 
//                = Central.createSynthesizer( 
//                    new SynthesizerModeDesc(Locale.GERMAN)); 

	            // Allocate synthesizer 
	            synthesizer.allocate(); 
	  
	            // Resume Synthesizer 
	            synthesizer.resume(); 
	  
	            // Speaks the given text 
	            // until the queue is empty. 
	            synthesizer.speakPlainText( 
	                "GeeksforGeeks", null); 
	            synthesizer.speakPlainText( 
		                "Zulkarnaen Hatala", null); 
	            synthesizer.waitEngineState( 
	                Synthesizer.QUEUE_EMPTY); 
	  
	            // Deallocate the Synthesizer. 
	            synthesizer.deallocate(); 
	        } 
	  
	        catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	} 


package cpintar.biometric;


/**
 * 
 * @author dzulqarnaenhatala@gmail.com
 *
 */
public class ScanEvent {
	
	public static enum EVENT_TYPE{EVENT_FAILED,EVENT_SUCCESS};
	public EVENT_TYPE type=EVENT_TYPE.EVENT_FAILED;
	public Object metadata;
}

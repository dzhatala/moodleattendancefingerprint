package cpintar.biometric;


/**
 * implement this to 
 * @author User
 *
 */
public interface BioScanListener {
	
	void BioScanSucccess(ScanEvent ev);
	void BioScanFailed(ScanEvent ev);
	
}
